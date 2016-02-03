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
package eu.aleon.aleoncean.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Markus Rathgeb {@literal <maggu2810@gmail.com>}
 * @param <E> the type of the id owner
 * @param <T> the type of the id
 */
public class IdOwnerResolver<E extends IdOwner<T>, T> {

    private final Map<T, E> fromIdMap = new HashMap<>();

    public IdOwnerResolver(final E[] owners) {
        for (final E owner : owners) {
            final E old = fromIdMap.put(owner.getId(), owner);
            if (old != null) {
                throw new IllegalArgumentException("The identifier must be unique");
            }
        }
    }

    /**
     * Flag that indicates if the id is known.
     *
     * @param id the id
     * @return true if the id is known, otherwise false
     */
    public boolean contains(final T id) {
        return fromIdMap.containsKey(id);
    }

    /**
     * Get by ID.
     *
     * @param id the id that should be looked up
     * @return the owner assigned by that id. If no owner is using that ID null is returned. The IDs are part of an
     *         {@link IdOwner} so, there is no ID that owner is null.
     */
    public E get(final T id) {
        return fromIdMap.get(id);
    }

}
