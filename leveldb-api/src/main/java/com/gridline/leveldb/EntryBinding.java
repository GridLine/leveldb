// ---------------------------------------------------------
// Copyright, all rights reserved 2013 GridLine Amsterdam
// ---------------------------------------------------------
package com.gridline.leveldb;

/**
 * {@link StoredMap} entry (de)serializer
 * <p />
 * Project leveldb-api<br />
 * EntryBinding.java created May 24, 2013
 * <p />
 * Copyright, all rights reserved 2013 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 * @param <K> object type to (de)serialize
 */
public interface EntryBinding<K>
{
	/**
	 * Serializes {@code object} into a byte array
	 * @param object some object
	 * @return non null byte array
	 */
	byte[] serialize(K object);

	/**
	 * Deserializes {@code object} into {@code K}
	 * @param object non null byte array
	 * @return the original object, can be null if the byte array is empty
	 */
	K deserialize(byte[] object);
}
