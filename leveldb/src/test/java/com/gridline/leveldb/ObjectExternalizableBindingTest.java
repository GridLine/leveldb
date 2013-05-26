// ---------------------------------------------------------
// Copyright, all rights reserved 2013 GridLine Amsterdam
// ---------------------------------------------------------
package com.gridline.leveldb;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

/**
 * [purpose]
 * <p />
 * Project leveldb<br />
 * ObjectBindingTest.java created May 25, 2013
 * <p />
 * Copyright, all rights reserved 2013 GridLine Amsterdam
 * @author <a href="mailto:job@gridline.nl">Job</a>
 * @version $Revision:$, $Date:$
 */
public class ObjectExternalizableBindingTest
{

	private final String s = "This is a test String";

	@Rule
	public TestRule benchmarkRun = new BenchmarkRule();

	@Test
	@BenchmarkOptions(benchmarkRounds = 20000, warmupRounds = 1000)
	public void testSerializeTwenty() throws Exception
	{
		ObjectExternalizableBinding<SmallObjectExternalizable> binding = new ObjectExternalizableBinding<>(
				SmallObjectExternalizable.class);

		SmallObjectExternalizable in = SmallObjectExternalizable.create(s, 100, 2000L);

		byte[] object = binding.serialize(in);

		SmallObjectExternalizable out = binding.dezerialize(object);

		assertEquals(in.getS(), out.getS());
		assertEquals(in.getI(), out.getI());
		assertEquals(in.getL(), out.getL());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 50000, warmupRounds = 1000)
	public void testSerializeFifty() throws Exception
	{
		ObjectExternalizableBinding<SmallObjectExternalizable> binding = new ObjectExternalizableBinding<>(
				SmallObjectExternalizable.class);

		SmallObjectExternalizable in = SmallObjectExternalizable.create(s, 100, 2000L);

		byte[] object = binding.serialize(in);

		SmallObjectExternalizable out = binding.dezerialize(object);

		assertEquals(in.getS(), out.getS());
		assertEquals(in.getI(), out.getI());
		assertEquals(in.getL(), out.getL());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 1000)
	public void testSerializeHundred() throws Exception
	{
		ObjectExternalizableBinding<SmallObjectExternalizable> binding = new ObjectExternalizableBinding<>(
				SmallObjectExternalizable.class);

		SmallObjectExternalizable in = SmallObjectExternalizable.create(s, 100, 2000L);

		byte[] object = binding.serialize(in);

		SmallObjectExternalizable out = binding.dezerialize(object);

		assertEquals(in.getS(), out.getS());
		assertEquals(in.getI(), out.getI());
		assertEquals(in.getL(), out.getL());
	}

}