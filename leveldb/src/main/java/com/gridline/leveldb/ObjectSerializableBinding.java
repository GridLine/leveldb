package com.gridline.leveldb;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gridline.leveldb.io.FastInputStream;
import com.gridline.leveldb.io.FastOutputStream;

/**
 * Implementation of {@link EntryBinding} (de)serializes object using {@link ObjectInputStream} and
 * {@link ObjectOutputStream}. The implementation relies on {@link Serializable} interface to serialize objects.
 * Tests show that a {@link Externalizable} implementation is much faster. If possible use
 * {@link ObjectExternalizableBinding}
 * <p />
 * Project leveldb<br />
 * ObjectSerializableBinding.java created May 26, 2013
 * <p />
 * Copyright, all rights reserved 2013 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class ObjectSerializableBinding<K extends Serializable> implements EntryBinding<K>
{

	private static final Logger LOG = LoggerFactory.getLogger(ObjectSerializableBinding.class);

	@Override
	public byte[] serialize(K object)
	{
		FastOutputStream b = new FastOutputStream();
		try (ObjectOutputStream o = new ObjectOutputStream(b))
		{
			o.writeObject(object);
			return b.toArray();
		}
		catch (IOException e)
		{
			LOG.error("Failed to serialize object", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public K dezerialize(byte[] buffer)
	{
		FastInputStream b = new FastInputStream(buffer);
		try (ObjectInputStream i = new ObjectInputStream(b))
		{
			Object r = i.readObject();
			return (K) r;
		}
		catch (IOException | ClassNotFoundException e)
		{
			LOG.error("Failed to deserialize object", e);
		}
		return null;
	}
}
