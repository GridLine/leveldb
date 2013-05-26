package com.gridline.leveldb;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class ObjectSerializableBindingTest
{

	private final String s = "This is a test String";

	@Rule
	public TestRule benchmarkRun = new BenchmarkRule();

	@Test
	@BenchmarkOptions(benchmarkRounds = 20000, warmupRounds = 1000)
	public void testSerializeTwenty() throws Exception
	{
		ObjectSerializableBinding<SmallObjectSerializable> binding = new ObjectSerializableBinding<>();

		SmallObjectSerializable in = SmallObjectSerializable.create(s, 100, 2000L);

		byte[] object = binding.serialize(in);

		SmallObjectSerializable out = binding.deserialize(object);

		assertEquals(in.getS(), out.getS());
		assertEquals(in.getI(), out.getI());
		assertEquals(in.getL(), out.getL());
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 50000, warmupRounds = 1000)
	public void testSerializeFifty() throws Exception
	{
		ObjectSerializableBinding<SmallObjectSerializable> binding = new ObjectSerializableBinding<>();

		SmallObjectSerializable in = SmallObjectSerializable.create(s, 100, 2000L);

		byte[] object = binding.serialize(in);

		SmallObjectSerializable out = binding.deserialize(object);

		assertEquals(in.getS(), out.getS());
		assertEquals(in.getI(), out.getI());
		assertEquals(in.getL(), out.getL());

	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 100000, warmupRounds = 1000)
	public void testSerializeHundred() throws Exception
	{
		ObjectSerializableBinding<SmallObjectSerializable> binding = new ObjectSerializableBinding<>();

		SmallObjectSerializable in = SmallObjectSerializable.create(s, 100, 2000L);

		byte[] object = binding.serialize(in);

		SmallObjectSerializable out = binding.deserialize(object);

		assertEquals(in.getS(), out.getS());
		assertEquals(in.getI(), out.getI());
		assertEquals(in.getL(), out.getL());

	}

}
