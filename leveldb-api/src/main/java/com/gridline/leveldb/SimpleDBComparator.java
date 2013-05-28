// ---------------------------------------------------------
// Copyright, all rights reserved 2013 GridLine Amsterdam
// ---------------------------------------------------------
package com.gridline.leveldb;

import org.iq80.leveldb.DBComparator;

/**
 * <p />
 * Project leveldb-api<br />
 * SimpleDBComparator.java created May 28, 2013
 * <p />
 * Copyright, all rights reserved 2013 GridLine Amsterdam
 * @author <a href="mailto:niels@gridline.nl">Niels Slot</a>
 * @version $Revision:$, $Date:$
 */
public abstract class SimpleDBComparator implements DBComparator
{

	@Override
	public byte[] findShortestSeparator(byte[] start, byte[] limit)
	{
		return start;
	}

	@Override
	public byte[] findShortSuccessor(byte[] key)
	{
		return key;
	}

}
