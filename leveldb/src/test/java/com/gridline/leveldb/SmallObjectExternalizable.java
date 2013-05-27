package com.gridline.leveldb;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

// small test object
public class SmallObjectExternalizable implements Externalizable
{

	private String s;

	private int i;
	private long l;

	public SmallObjectExternalizable()
	{

	}

	public static SmallObjectExternalizable create(String s, int i, long l)
	{
		final SmallObjectExternalizable r = new SmallObjectExternalizable();
		r.s = s;
		r.i = i;
		r.l = l;
		return r;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException
	{
		out.writeUTF(s);
		out.writeInt(i);
		out.writeLong(l);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
	{
		s = in.readUTF();
		i = in.readInt();
		l = in.readLong();
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
