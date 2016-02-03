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
public class UserDataRangeScale {

    private final long rangeMin;
    private final long rangeMax;
    private final double scaleMin;
    private final double scaleMax;

    public UserDataRangeScale(final long rangeMin, final long rangeMax, final double scaleMin, final double scaleMax) {
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
        this.scaleMin = scaleMin;
        this.scaleMax = scaleMax;
    }

    public long getRangeMin() {
        return rangeMin;
    }

    public long getRangeMax() {
        return rangeMax;
    }

    public double getScaleMin() {
        return scaleMin;
    }

    public double getScaleMax() {
        return scaleMax;
    }

}
