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
 * Simple implementation of {@link EntryBinding} serializes objects using {@link ObjectInputStream} and
 * {@link ObjectOutputStream}. It relies on the {@link Externalizable} interface to read and write the object. This is
 * much faster than normal {@link Serializable}. See example code <a
 * href="http://www.javacodegeeks.com/2010/07/java-best-practices-high-performance.html">here</a>
 * <p />
 * Project leveldb<br />
 * ObjectBinding.java created May 24, 2013
 * <p />
 * Copyright, all rights reserved 2013 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class ObjectExternalizableBinding<K extends Externalizable> implements EntryBinding<K>
{

	private static final Logger LOG = LoggerFactory.getLogger(ObjectExternalizableBinding.class);

	private final Class<K> clazz;

	public ObjectExternalizableBinding(Class<K> clazz)
	{
		this.clazz = clazz;
	}

	@Override
	public byte[] serialize(K object)
	{
		FastOutputStream b = new FastOutputStream();
		try (ObjectOutputStream o = new ObjectOutputStream(b))
		{
			object.writeExternal(o);
		}
		catch (IOException e)
		{
			LOG.error("Failed to serialize object", e);
		}
		return b.toArray();
	}

	@Override
	public K dezerialize(byte[] bytes)
	{
		FastInputStream b = new FastInputStream(bytes);
		try (ObjectInputStream o = new ObjectInputStream(b))
		{
			final K result = clazz.newInstance();
			result.readExternal(o);
			return result;
		}
		catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			LOG.error("Failed to deserialize object", e);
		}
		return null;
	}
}
