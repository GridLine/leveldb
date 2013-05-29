// ---------------------------------------------------------
// Copyright, all rights reserved 2013 GridLine Amsterdam
// ---------------------------------------------------------
package com.gridline.leveldb;

/**
 * Implementation of {@link EntryBinding} for Strings. Uses getBytes() and
 * String(byte[]).
 * <p />
 * Project leveldb<br />
 * StringBinding.java created May 29, 2013
 * <p />
 * Copyright, all rights reserved 2013 GridLine Amsterdam
 * @author <a href="mailto:niels@gridline.nl">Niels Slot</a>
 * @version $Revision:$, $Date:$
 */
public class StringBinding implements EntryBinding<String>
{
	@Override
	public byte[] serialize(String object)
	{
		return object.getBytes();
	}

	@Override
	public String deserialize(byte[] object)
	{
		return new String(object);
	}

}
