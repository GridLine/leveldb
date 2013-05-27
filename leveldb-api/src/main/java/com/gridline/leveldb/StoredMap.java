// ---------------------------------------------------------
// Copyright, all rights reserved 2013 GridLine Amsterdam
// ---------------------------------------------------------
package com.gridline.leveldb;

import java.util.Map;

import org.iq80.leveldb.DB;

/**
 * <p>
 * {@link Map} interface on top of the {@link DB} implementation
 * </p>
 * <p>
 * Goal is to support serializers for {@code K} and {@code V} similar to the BerkeleyDB implementations
 * </p>
 * Project leveldb-api<br />
 * StoredMap.java created May 24, 2013
 * <p />
 * Copyright, all rights reserved 2013 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 * @param <K>
 * @param <V>
 */
public interface StoredMap<K, V> extends Map<K, V>
{

}
