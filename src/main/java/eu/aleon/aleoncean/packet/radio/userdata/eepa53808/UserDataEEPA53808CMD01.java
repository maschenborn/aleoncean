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
package eu.aleon.aleoncean.packet.radio.userdata.eepa53808;

import eu.aleon.aleoncean.packet.radio.userdata.UserDataScaleValueException;
import eu.aleon.aleoncean.util.Bits;

/**
 * @author Markus Rathgeb {@literal <maggu2810@gmail.com>}
 */
public class UserDataEEPA53808CMD01 extends UserDataEEPA53808 {

    public static final byte CMD = UserDataEEPA53808.CMD_SWITCHING;

    private static final int OFFSET_STR = 30;
    private static final int LENGTH_STR = 1;

    private static final int OFFSET_SW = 31;
    private static final int LENGTH_SW = 1;

    public UserDataEEPA53808CMD01() {
        super(CMD);
    }

    public UserDataEEPA53808CMD01(final byte[] data) {
        super(data);
    }

    public boolean getStoreFinalValue() {
        final long raw = Bits.getBitsFromBytes(getUserData(), OFFSET_STR, LENGTH_STR);
        return raw != 0;
    }

    public void setStoreFinalValue(final boolean value) {
        Bits.setBitsOfBytes(value ? 1 : 0, getUserData(), OFFSET_STR, LENGTH_STR);
    }

    public boolean getSwitchingCommand() {
        final long raw = Bits.getBitsFromBytes(getUserData(), OFFSET_SW, LENGTH_SW);
        return raw != 0;
    }

    public void setSwitchingCommand(final boolean value) {
        Bits.setBitsOfBytes(value ? 1 : 0, getUserData(), OFFSET_SW, LENGTH_SW);
    }

}
