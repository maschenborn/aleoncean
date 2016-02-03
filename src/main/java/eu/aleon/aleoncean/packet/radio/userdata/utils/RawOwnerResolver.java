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

import eu.aleon.aleoncean.packet.radio.userdata.UserDataScaleValueException;
import eu.aleon.aleoncean.util.IdOwner;
import eu.aleon.aleoncean.util.IdOwnerResolver;

/**
 * This class extends the {@link IdOwnerResolver} by raising an {@link UserDataScaleValueException} is raw value is
 * unknown.
 *
 * @author Markus Rathgeb {@literal <maggu2810@gmail.com>}
 * @param <T> the type of the id owner
 */
public class RawOwnerResolver<T extends IdOwner<Long>> extends IdOwnerResolver<T, Long> {

    /**
     * Create a new object.
     *
     * @param owners all owners that should be used by the resolver.
     */
    public RawOwnerResolver(final T[] owners) {
        super(owners);
    }

    /**
     * Get an owner by the raw value.
     *
     * @param raw the raw value
     * @return the owner that is using this raw value
     * @throws UserDataScaleValueException if no owner for this raw value is known
     */
    public T fromRaw(final long raw) throws UserDataScaleValueException {
        if (contains(raw)) {
            return super.get(raw);
        } else {
            throw new UserDataScaleValueException("raw value not found");
        }
    }
}
