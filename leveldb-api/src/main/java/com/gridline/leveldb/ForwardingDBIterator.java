// ---------------------------------------------------------
// Copyright, all rights reserved 2013 GridLine Amsterdam
// ---------------------------------------------------------
package com.gridline.leveldb;

import java.io.IOException;
import java.util.Map.Entry;

import org.iq80.leveldb.DBIterator;

/**
 * <p />
 * Project leveldb-api<br />
 * ForwardingDBIterator.java created May 28, 2013
 * <p />
 * Copyright, all rights reserved 2013 GridLine Amsterdam
 * @author <a href="mailto:niels@gridline.nl">Niels Slot</a>
 * @version $Revision:$, $Date:$
 */
public abstract class ForwardingDBIterator implements DBIterator
{

	protected abstract DBIterator delegate();

	@Override
	public boolean hasNext()
	{
		return delegate().hasNext();
	}

	@Override
	public Entry<byte[], byte[]> next()
	{
		return delegate().next();
	}

	@Override
	public void remove()
	{
		delegate().remove();
	}

	@Override
	public void close() throws IOException
	{
		delegate().close();
	}

	@Override
	public void seek(byte[] key)
	{
		delegate().seek(key);
	}

	@Override
	public void seekToFirst()
	{
		delegate().seekToFirst();
	}

	@Override
	public Entry<byte[], byte[]> peekNext()
	{
		return delegate().peekNext();
	}

	@Override
	public boolean hasPrev()
	{
		return delegate().hasPrev();
	}

	@Override
	public Entry<byte[], byte[]> prev()
	{
		return delegate().prev();
	}

	@Override
	public Entry<byte[], byte[]> peekPrev()
	{
		return delegate().peekPrev();
	}

	@Override
	public void seekToLast()
	{
		delegate().seekToLast();
	}
}
