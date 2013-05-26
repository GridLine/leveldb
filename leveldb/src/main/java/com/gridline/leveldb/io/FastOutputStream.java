package com.gridline.leveldb.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Non synchronized, no null checking version of a {@link ByteArrayOutputStream} - that should be a bit faster.
 * <p />
 * Project leveldb<br />
 * FastOutputStream.java created May 25, 2013
 * <p />
 * Copyright, all rights reserved 2013 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class FastOutputStream extends OutputStream
{

	protected byte[] buf = null;

	protected int size = 0;

	public FastOutputStream()
	{
		this(1024);
	}

	public FastOutputStream(int size)
	{
		this.size = 0;
		buf = new byte[size];
	}

	private void ensure(int sz)
	{
		if (sz > buf.length)
		{
			byte[] current = buf;
			buf = new byte[Math.max(sz, 2 * buf.length)];
			System.arraycopy(current, 0, buf, 0, current.length);
			current = null;
		}
	}

	public int getSize()
	{
		return size;
	}

	public byte[] getBuf()
	{
		return buf;
	}

	public byte[] toArray()
	{
		if (size == buf.length)
		{
			return buf;
		}
		final byte[] result = new byte[size];
		System.arraycopy(buf, 0, result, 0, Math.min(buf.length, size));
		return result;
	}

	@Override
	public void write(int b) throws IOException
	{
		ensure(size + 1);
		buf[size++] = (byte) b;
	}

	@Override
	public void write(byte[] b) throws IOException
	{
		ensure(size + b.length);
		System.arraycopy(b, 0, buf, size, b.length);
		size += b.length;
	}

	@Override
	public void write(byte[] b, int offset, int length) throws IOException
	{
		ensure(size + length);
		System.arraycopy(b, offset, buf, size, length);
		size += length;
	}

}
