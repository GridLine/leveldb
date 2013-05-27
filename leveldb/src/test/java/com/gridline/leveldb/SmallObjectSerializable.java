package com.gridline.leveldb;

import java.io.Serializable;

public class SmallObjectSerializable implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6779707264700567264L;

	private String s;
	private int i;
	private long l;

	public SmallObjectSerializable()
	{

	}

	public static SmallObjectSerializable create(String s, int i, long l)
	{
		final SmallObjectSerializable r = new SmallObjectSerializable();
		r.s = s;
		r.i = i;
		r.l = l;
		return r;
	}

	public String getS()
	{
		return s;
	}

	public int getI()
	{
		return i;
	}

	public long getL()
	{
		return l;
	}
}
