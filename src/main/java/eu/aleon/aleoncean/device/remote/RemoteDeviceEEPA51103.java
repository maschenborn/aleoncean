/*
 * Copyright (c) 2014 aleon GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Note for all commercial users of this library:
 * Please contact the EnOcean Alliance (http://www.enocean-alliance.org/)
 * about a possible requirement to become member of the alliance to use the
 * EnOcean protocol implementations.
 *
 * Contributors:
 *    Markus Rathgeb - initial API and implementation and/or initial documentation
 */
package eu.aleon.aleoncean.device.remote;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.aleon.aleoncean.device.DeviceParameter;
import eu.aleon.aleoncean.device.DeviceParameterUpdatedInitiation;
import eu.aleon.aleoncean.device.IllegalDeviceParameterException;
import eu.aleon.aleoncean.device.RemoteDevice;
import eu.aleon.aleoncean.device.StandardDevice;
import eu.aleon.aleoncean.packet.EnOceanId;
import eu.aleon.aleoncean.packet.radio.RadioPacket4BS;
import eu.aleon.aleoncean.packet.radio.userdata.UserDataEEPA51103;
import eu.aleon.aleoncean.packet.radio.userdata.UserDataScaleValueException;
import eu.aleon.aleoncean.rxtx.ESP3Connector;

/**
 *
 * @author Markus Rathgeb {@literal <maggu2810@gmail.com>}
 */
public class RemoteDeviceEEPA51103 extends StandardDevice implements RemoteDevice {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Integer position;
    private Integer angle;

    public RemoteDeviceEEPA51103(final ESP3Connector conn, final EnOceanId addressRemote,
            final EnOceanId addressLocal) {
        super(conn, addressRemote, addressLocal);
    }

    @Override
    protected void parseRadioPacket4BS(final RadioPacket4BS packet) {
        final UserDataEEPA51103 userData = new UserDataEEPA51103(packet.getUserDataRaw());

        try {
            // Check for angle
            if (userData.isAngleAvailable()) {
                setAngle(DeviceParameterUpdatedInitiation.RADIO_PACKET, userData.getAngle());
            } else {
                setAngle(DeviceParameterUpdatedInitiation.RADIO_PACKET, null);
            }

            // Check for position
            if (userData.isPositionAvailable()) {
                setPosition(DeviceParameterUpdatedInitiation.RADIO_PACKET, userData.getBlindShutterPosition());
            } else {
                setPosition(DeviceParameterUpdatedInitiation.RADIO_PACKET, null);
            }
        } catch (final UserDataScaleValueException ex) {
            logger.info("The incoming data package is (perhaps) not EEP conform (or this implementation).", ex);
        }
    }

    @Override
    protected void fillParameters(final Set<DeviceParameter> params) {
        params.add(DeviceParameter.ANGLE_DEGREE);
        params.add(DeviceParameter.POSITION_PERCENT);
    }

    @Override
    public Object getByParameter(final DeviceParameter parameter) throws IllegalDeviceParameterException {
        switch (parameter) {
            case ANGLE_DEGREE:
                return getAngle();
            case POSITION_PERCENT:
                return getPosition();
            default:
                return super.getByParameter(parameter);
        }
    }

    @Override
    public void setByParameter(final DeviceParameter parameter, final Object value)
            throws IllegalDeviceParameterException {
        assert DeviceParameter.getSupportedClass(parameter).isAssignableFrom(value.getClass());
        super.setByParameter(parameter, value);
    }

    public Integer getAngle() {
        return angle;
    }

    public void setAngle(final DeviceParameterUpdatedInitiation initiation, final Integer newValue) {
        final Integer oldValue = this.angle;
        this.angle = newValue;
        fireParameterChanged(DeviceParameter.ANGLE_DEGREE, initiation, oldValue, newValue);
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(final DeviceParameterUpdatedInitiation initiation, final Integer newValue) {
        final Integer oldValue = this.position;
        this.position = newValue;
        fireParameterChanged(DeviceParameter.POSITION_PERCENT, initiation, oldValue, newValue);
    }

}
