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
package eu.aleon.aleoncean.packet.radio.userdata.utils;

/**
 *
 * @author Markus Rathgeb {@literal <maggu2810@gmail.com>}
 */
public class UserDataBitRange {

    final int startByte;
    final int startBit;
    final int endByte;
    final int endBit;

    /**
     * Create a new user data bit range that encodes exactly one bit.
     *
     * @param dataByte the data byte
     * @param dataBit the data bit
     */
    public UserDataBitRange(final int dataByte, final int dataBit) {
        this(dataByte, dataBit, dataByte, dataBit);
    }

    /**
     * Create a new user data bit range
     *
     * <p>
     * The start position is higher then the end position e.g. {@code UserDataBitRange(3, 7, 3, 0)}.
     * </p>
     *
     * @param startByte the start data byte
     * @param startBit the start data bit
     * @param endByte the end data byte
     * @param endBit the end data bit
     */
    public UserDataBitRange(final int startByte, final int startBit, final int endByte, final int endBit) {
        this.startByte = startByte;
        this.startBit = startBit;
        this.endByte = endByte;
        this.endBit = endBit;
    }

    /**
     * Flag if exactly one bit is encoded.
     *
     * @return true if one bit is encoded, otherwise false
     */
    public boolean isBit() {
        return startByte == endByte && startBit == endBit;
    }

    public int getStartByte() {
        return startByte;
    }

    public int getStartBit() {
        return startBit;
    }

    public int getEndByte() {
        return endByte;
    }

    public int getEndBit() {
        return endBit;
    }

}
