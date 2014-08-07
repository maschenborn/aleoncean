/*
 * Copyright (c) 2014 aleon GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Markus Rathgeb - initial API and implementation and/or initial documentation
 */
package eu.aleon.aleoncean.util;

public class CalculationUtil {

    public static <T extends Comparable<T>> T fitInRange(T value, T min, T max) {
        if (value.compareTo(min) < 0) {
            return min;
        } else if (value.compareTo(max) > 0) {
            return max;
        } else {
            return value;
        }
    }

    public static double rangeToScale(long rawValue, long rangeMin, long rangeMax, double scaleMin, double scaleMax) {
        final double multiplier = (scaleMax - scaleMin) / (rangeMax - rangeMin);
        final double devValue = multiplier * (rawValue - rangeMin) + scaleMin;
        return fitInRange(devValue, scaleMin, scaleMax);
    }

    /**
     * Calculate the given scale value to the corresponding range value.
     *
     * @param devValue The value (scale) for the calculation.
     * @param scaleMin The minimum value of scale.
     * @param scaleMax The maximum value of scale.
     * @param rangeMin The minimum value of range.
     * @param rangeMax The maximum value of range.
     * @return The calculated range value.
     */
    public static long scaleToRange(double devValue, double scaleMin, double scaleMax, long rangeMin, long rangeMax) {
        final double multiplier = (rangeMax - rangeMin) / (scaleMax - scaleMin);
        final double rawValue = multiplier * (devValue - scaleMin) + rangeMin;

        // The range could also be inverse (255..0 instead of 0..255),
        // so we have to improve the check and set the correct min and max values.
        if(rangeMin <= rangeMax){
            return fitInRange((long) rawValue, rangeMin, rangeMax);
        }
        else{
            return fitInRange((long) rawValue, rangeMax, rangeMin);
        }
    }

    private CalculationUtil() {
    }

}
