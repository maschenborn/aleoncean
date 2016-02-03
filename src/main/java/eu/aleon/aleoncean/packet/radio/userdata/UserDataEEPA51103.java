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
package eu.aleon.aleoncean.packet.radio.userdata;

import eu.aleon.aleoncean.packet.radio.userdata.utils.RawOwnerResolver;
import eu.aleon.aleoncean.packet.radio.userdata.utils.UserDataBitRange;
import eu.aleon.aleoncean.packet.radio.userdata.utils.UserDataRangeScale;
import eu.aleon.aleoncean.util.IdOwner;

/**
 *
 * @author Markus Rathgeb {@literal <maggu2810@gmail.com>}
 */
public class UserDataEEPA51103 extends UserData4BS {

    public static enum ErrorState implements IdOwner<Long> {
        NO_ERROR_PRESENT(0),
        END_POSITIONS_NOT_CONFIGURED(1),
        INTERNAL_FAILURE(2),
        NOT_USED(3);

        public static RawOwnerResolver<ErrorState> handler = new RawOwnerResolver<>(values());

        private final long raw;

        private ErrorState(final long raw) {
            this.raw = raw;
        }

        @Override
        public Long getId() {
            return raw;
        }

    }

    public static enum EndPosition implements IdOwner<Long> {
        NO_ENDPOSITION_AVAILABLE(0),
        NO_ENDPOSITION_REACHED(1),
        BLIND_FULLY_OPEN(2),
        BLIND_FULLY_CLOSED(3);

        public static RawOwnerResolver<EndPosition> handler = new RawOwnerResolver<>(values());

        private final long raw;

        private EndPosition(final long raw) {
            this.raw = raw;
        }

        @Override
        public Long getId() {
            return raw;
        }

    }

    public static enum Status implements IdOwner<Long> {
        NO_STATUS_AVAILABLE(0),
        BLIND_IS_STOPPED(1),
        BLIND_OPENS(2),
        BLIND_CLOSES(3);

        public static RawOwnerResolver<Status> handler = new RawOwnerResolver<>(values());

        private final long raw;

        private Status(final long raw) {
            this.raw = raw;
        }

        @Override
        public Long getId() {
            return raw;
        }
    }

    private static final UserDataBitRange BSP_BITRANGE = new UserDataBitRange(3, 7, 3, 0);
    private static final UserDataRangeScale BSP_RANGESCALE = new UserDataRangeScale(0, 100, 0, 100);

    private static final UserDataBitRange AS_BITRANGE = new UserDataBitRange(2, 7);

    private static final UserDataBitRange AN_BITRANGE = new UserDataBitRange(2, 6, 2, 0);
    private static final UserDataRangeScale AN_RANGESCALE = new UserDataRangeScale(0, 180, 0, 360);

    private static final UserDataBitRange PVF_BITRANGE = new UserDataBitRange(1, 7);

    private static final UserDataBitRange AVF_BITRANGE = new UserDataBitRange(1, 6);

    private static final UserDataBitRange ES_BITRANGE = new UserDataBitRange(1, 5, 1, 4);

    private static final UserDataBitRange EP_BITRANGE = new UserDataBitRange(1, 3, 1, 2);

    private static final UserDataBitRange ST_BITRANGE = new UserDataBitRange(1, 1, 1, 0);

    private static final UserDataBitRange SM_BITRANGE = new UserDataBitRange(0, 7);

    private static final UserDataBitRange MOTP_BITRANGE = new UserDataBitRange(0, 6);

    public UserDataEEPA51103() {
    }

    public UserDataEEPA51103(final byte[] eepData) {
        super(eepData);
    }

    public int getBlindShutterPosition() throws UserDataScaleValueException {
        return (int) decodeData(BSP_BITRANGE, BSP_RANGESCALE);
    }

    public void setBlindShutterPosition(final int pos) throws UserDataScaleValueException {
        encodeData(BSP_BITRANGE, BSP_RANGESCALE, pos);
    }

    public boolean isAnglePositive() {
        return !decodeBit(AS_BITRANGE);
    }

    public void setAnglePositive(final boolean positive) {
        encodeBit(AS_BITRANGE, !positive);
    }

    public int getAngle() throws UserDataScaleValueException {
        return (int) decodeData(AN_BITRANGE, AN_RANGESCALE);
    }

    public void setAngle(final int angle) throws UserDataScaleValueException {
        encodeData(AN_BITRANGE, AN_RANGESCALE, angle);
    }

    public boolean isPositionAvailable() {
        return decodeBit(PVF_BITRANGE);
    }

    public void setPositionAvailable(final boolean available) {
        encodeBit(PVF_BITRANGE, available);
    }

    public boolean isAngleAvailable() {
        return decodeBit(AVF_BITRANGE);
    }

    public void setAngleAvailable(final boolean available) {
        encodeBit(AVF_BITRANGE, available);
    }

    public ErrorState getErrorState() throws UserDataScaleValueException {
        return ErrorState.handler.fromRaw(decodeDataRaw(ES_BITRANGE));
    }

    public void setErrorState(final ErrorState errorState) {
        encodeDataRaw(ES_BITRANGE, errorState.getId());
    }

    public EndPosition getEndPosition() throws UserDataScaleValueException {
        return EndPosition.handler.fromRaw(decodeDataRaw(EP_BITRANGE));
    }

    public void setEndPosition(final EndPosition endPosition) {
        encodeDataRaw(EP_BITRANGE, endPosition.getId());
    }

    public Status getStatus() throws UserDataScaleValueException {
        return Status.handler.fromRaw(decodeDataRaw(ST_BITRANGE));
    }

    public void setStatus(final Status status) {
        encodeDataRaw(ST_BITRANGE, status.getId());
    }

    public boolean isServiceMode() {
        return decodeBit(SM_BITRANGE);
    }

    public void setServiceMode(final boolean serviceMode) {
        encodeBit(SM_BITRANGE, serviceMode);
    }

    public boolean isInverseMode() {
        return decodeBit(MOTP_BITRANGE);
    }

    public void setInverseMode(final boolean inverseMode) {
        encodeBit(MOTP_BITRANGE, inverseMode);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(256);
        sb.append(this.getClass().getSimpleName());
        sb.append("{");
        sb.append(String.format("BSP: %d, ", decodeDataRaw(BSP_BITRANGE)));
        sb.append(String.format("AS: %d, ", decodeDataRaw(AS_BITRANGE)));
        sb.append(String.format("AN: %d, ", decodeDataRaw(AN_BITRANGE)));
        sb.append(String.format("PVF: %d, ", decodeDataRaw(PVF_BITRANGE)));
        sb.append(String.format("AVF: %d, ", decodeDataRaw(AVF_BITRANGE)));
        sb.append(String.format("ES: %d, ", decodeDataRaw(ES_BITRANGE)));
        sb.append(String.format("EP: %d, ", decodeDataRaw(EP_BITRANGE)));
        sb.append(String.format("ST: %d, ", decodeDataRaw(ST_BITRANGE)));
        sb.append(String.format("SM: %d, ", decodeDataRaw(SM_BITRANGE)));
        sb.append(String.format("MOTP: %d, ", decodeDataRaw(MOTP_BITRANGE)));
        sb.append(String.format("LRNB: %d", decodeDataRaw(LRNB_BITRANGE)));
        sb.append("}");
        return sb.toString();
    }
}
