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
package eu.aleon.aleoncean.device.local;

import eu.aleon.aleoncean.device.*;
import eu.aleon.aleoncean.packet.EnOceanId;
import eu.aleon.aleoncean.packet.radio.userdata.eepa53808.UserDataEEPA53808CMD01;
import eu.aleon.aleoncean.packet.radio.userdata.teachin4bs.UserData4BSTeachInVariant1;
import eu.aleon.aleoncean.rxtx.ESP3Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author Markus Rathgeb {@literal <maggu2810@gmail.com>}
 */
public class LocalDeviceEEPA53808CMD01 extends StandardDevice implements RemoteDevice {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDeviceEEPA53808CMD01.class);

    private Boolean teachIn;
    private Boolean on;

    public LocalDeviceEEPA53808CMD01(final ESP3Connector conn, final EnOceanId addressRemote,
                                     final EnOceanId addressLocal) {
        super(conn, addressRemote, addressLocal);
    }

    public Boolean getTeachIn() {
        return teachIn;
    }

    public void setTeachIn(final DeviceParameterUpdatedInitiation initiation, final Boolean newValue) {
        final Boolean oldValue = this.teachIn;
        this.teachIn = newValue;
        fireParameterChanged(DeviceParameter.TEACHIN, initiation, oldValue, newValue);
    }

    public Boolean getOn() {
        return on;
    }

    public void setOn(final DeviceParameterUpdatedInitiation initiation, final Boolean on) {
        final Boolean old = this.on;
        this.on = on;
        fireParameterChanged(DeviceParameter.SWITCH, initiation, old, on);
    }

    private void sendPacket() {
        if (teachIn == null || !teachIn) {
            sendPacketData();
        } else {
            sendPacketTeachIn();
        }
    }

    private void sendPacketTeachIn() {
        final UserData4BSTeachInVariant1 userData = new UserData4BSTeachInVariant1();
        send(userData);
    }

    private void sendPacketData() {
        try {
            final UserDataEEPA53808CMD01 userData = new UserDataEEPA53808CMD01();
            userData.setTeachIn(false);

            userData.setSwitchingCommand(getOn());

            send(userData);
        } catch (final NullPointerException ex) {
            LOGGER.debug("Do not send data, caused by null value.");
        }
    }

    @Override
    protected void fillParameters(final Set<DeviceParameter> params) {
        params.add(DeviceParameter.SWITCH);
        params.add(DeviceParameter.TEACHIN);
    }

    @Override
    public Object getByParameter(final DeviceParameter parameter) throws IllegalDeviceParameterException {
        switch (parameter) {
            case SWITCH:
                return getOn();
            case TEACHIN:
                return getTeachIn();
            default:
                return super.getByParameter(parameter);
        }
    }

    @Override
    public void setByParameter(final DeviceParameter parameter, final Object value)
            throws IllegalDeviceParameterException {
        assert DeviceParameter.getSupportedClass(parameter).isAssignableFrom(value.getClass());
        switch (parameter) {
            case SWITCH:
                setOn(DeviceParameterUpdatedInitiation.SET_PARAMETER, (Boolean) value);
                sendPacket();
                break;
            case TEACHIN:
                setTeachIn(DeviceParameterUpdatedInitiation.SET_PARAMETER, (Boolean) value);
                sendPacket();
                break;
            default:
                super.setByParameter(parameter, value);
                break;
        }
    }

}
