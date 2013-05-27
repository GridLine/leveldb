// ---------------------------------------------------------
// Copyright, all rights reserved 2013 GridLine Amsterdam
// ---------------------------------------------------------
package com.gridline.leveldb;

import java.io.IOException;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.WriteBatch;

/**
 * <p />
 * Project leveldb-api<br />
 * LevelDBStoredMap.java created May 24, 2013
 * <p />
 * Copyright, all rights reserved 2013 GridLine Amsterdam
 * @author <a href="mailto:niels@gridline.nl">Niels Slot</a>
 * @version $Revision:$, $Date:$
 */
public class LevelDBStoredMap<K, V> implements StoredMap<K, V>
{

	private final DB db;
	private final EntryBinding<K> keyBinding;
	private final EntryBinding<V> valueBinding;

	public LevelDBStoredMap(DB db, EntryBinding<K> keyBinding, EntryBinding<V> valueBinding)
	{
		this.db = db;
		this.keyBinding = keyBinding;
		this.valueBinding = valueBinding;
	}

	@Override
	public void clear()
	{
		try (WriteBatch batch = db.createWriteBatch())
		{
			try (DBIterator i = db.iterator())
			{
				for (i.seekToFirst(); i.hasNext(); i.next())
				{
					batch.delete(i.peekNext().getKey());
				}
			}
			db.write(batch);
		}
		catch (IOException e)
		{
		}
	}

	@Override
	public boolean containsKey(Object key)
	{
		return db.get(byteKey(key)) != null;
	}

	@Override
	public boolean containsValue(Object value)
	{
		byte[] byteValue = byteValue(value);
		try (DBIterator i = db.iterator())
		{
			for (i.seekToFirst(); i.hasNext(); i.next())
			{
				if (Arrays.equals(byteValue, i.peekNext().getValue()))
				{
					return true;
				}
			}
		}
		catch (IOException e)
		{
		}

		return false;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet()
	{
		return new EntrySet();
	}

	@Override
	public V get(Object key)
	{
		byte[] rawObject = db.get(byteKey(key));
		if (rawObject == null)
		{
			return null;
		}
		else
		{
			return valueBinding.dezerialize(rawObject);
		}
	}

	@Override
	public boolean isEmpty()
	{
		return size() == 0;
	}

	@Override
	public Set<K> keySet()
	{
		return new KeySet();
	}

	@Override
	public V put(K key, V value)
	{
		if (key == null || value == null)
		{
			throw new NullPointerException();
		}

		V oldValue = null;

		if (containsKey(key))
		{
			oldValue = get(key);
		}

		db.put(byteKey(key), byteValue(value));
		return oldValue;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m)
	{
		try (WriteBatch batch = db.createWriteBatch())
		{
			for (java.util.Map.Entry<? extends K, ? extends V> entry : m.entrySet())
			{
				batch.put(byteKey(entry.getKey()), byteValue(entry.getValue()));
			}

			db.write(batch);
		}
		catch (IOException e)
		{
		}
	}

	@Override
	public V remove(Object key)
	{
		if (key == null)
		{
			throw new NullPointerException();
		}

		V oldValue = null;

		if (containsKey(key))
		{
			oldValue = get(key);
		}

		db.delete(byteKey(key));

		return oldValue;
	}

	@Override
	public int size()
	{
		int c = 0;
		try (DBIterator i = db.iterator())
		{
			for (i.seekToFirst(); i.hasNext(); i.next())
			{
				c++;
			}
		}
		catch (IOException e)
		{
		}
		return c;
	}

	@Override
	public Collection<V> values()
	{
		return new ValueCollection();
	}

	private byte[] byteKey(Object key)
	{
		@SuppressWarnings("unchecked")
		final K keyObject = (K) key;
		return keyBinding.serialize(keyObject);
	}

	private byte[] byteValue(Object value)
	{
		@SuppressWarnings("unchecked")
		final V valueObject = (V) value;
		return valueBinding.serialize(valueObject);
	}

	private class RawEntryIterator implements Iterator<Entry<byte[], byte[]>>
	{
		private byte[] currentKey = null;
		private boolean performedDelete = false;

		@Override
		public Entry<byte[], byte[]> next()
		{
			Entry<byte[], byte[]> entry = computeNext();
			if (entry == null)
			{
				throw new NoSuchElementException();
			}
			currentKey = entry.getKey();
			performedDelete = false;
			return entry;
		}

		@Override
		public boolean hasNext()
		{
			Entry<byte[], byte[]> entry = computeNext();
			return entry != null;
		}

		protected Entry<byte[], byte[]> computeNext()
		{
			try (DBIterator i = db.iterator())
			{
				if (currentKey == null)
				{
					i.seekToFirst();
				}
				else
				{
					i.seek(currentKey);
					if (!performedDelete)
					{
						i.next();
					}
				}
				if (!i.hasNext())
				{
					return null;
				}
				return i.peekNext();
			}
			catch (IOException e)
			{
			}
			return null;
		}

		@Override
		public void remove()
		{
			if (performedDelete || currentKey == null)
			{
				throw new IllegalStateException();
			}
			db.delete(currentKey);
			performedDelete = true;

		}
	}

	private class EntrySet extends AbstractSet<java.util.Map.Entry<K, V>>
	{

		@Override
		public Iterator<java.util.Map.Entry<K, V>> iterator()
		{
			return new EntryIterator();
		}

		@Override
		public int size()
		{
			return LevelDBStoredMap.this.size();
		}

		private class EntryIterator implements Iterator<java.util.Map.Entry<K, V>>
		{
			private final RawEntryIterator rawEntryIterator = new RawEntryIterator();

			@Override
			public boolean hasNext()
			{
				return rawEntryIterator.hasNext();
			}

			@Override
			public java.util.Map.Entry<K, V> next()
			{
				Entry<byte[], byte[]> rawEntry = rawEntryIterator.next();
				return new AbstractMap.SimpleEntry<K, V>(keyBinding.dezerialize(rawEntry.getKey()),
						valueBinding.dezerialize(rawEntry.getValue()));
			}

			@Override
			public void remove()
			{
				rawEntryIterator.remove();
			}

		}

	}

	private class KeySet extends AbstractSet<K>
	{

		@Override
		public Iterator<K> iterator()
		{
			return new KeyIterator();
		}

		@Override
		public int size()
		{
			return LevelDBStoredMap.this.size();
		}

		private class KeyIterator implements Iterator<K>
		{
			private final RawEntryIterator rawEntryIterator = new RawEntryIterator();

			@Override
			public boolean hasNext()
			{
				return rawEntryIterator.hasNext();
			}

			@Override
			public K next()
			{
				return keyBinding.dezerialize(rawEntryIterator.next().getKey());
			}

			@Override
			public void remove()
			{
				rawEntryIterator.remove();
			}
		}
	}

	private class ValueCollection extends AbstractCollection<V>
	{

		@Override
		public Iterator<V> iterator()
		{
			return new ValueIterator();
		}

		@Override
		public int size()
		{
			return LevelDBStoredMap.this.size();
		}

		private class ValueIterator implements Iterator<V>
		{
			private final RawEntryIterator rawEntryIterator = new RawEntryIterator();

			@Override
			public boolean hasNext()
			{
				return rawEntryIterator.hasNext();
			}

			@Override
			public V next()
			{
				return valueBinding.dezerialize(rawEntryIterator.next().getValue());
			}

			@Override
			public void remove()
			{
				rawEntryIterator.remove();
			}

		}

	}

}
