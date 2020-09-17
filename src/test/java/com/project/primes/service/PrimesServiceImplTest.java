package com.project.primes.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

public class PrimesServiceImplTest {

	PrimesService primesService;

	@Before
	public void setUp() {
		primesService = new PrimesServiceImpl();
	}

	@Test
	public void testIsPrimeLong() {
		assertTrue(primesService.isPrime(13));
		assertFalse(primesService.isPrime(14));
	}

	@Test
	public void testIsPrimeLongMaxLong() {
		assertFalse(primesService.isPrime(9223372036854775807l));// Long.MAX_VALUE
	}

	@Test
	public void testIsPrimeBig() {
		assertTrue(primesService.isPrime(new BigInteger("13")));
		assertFalse(primesService.isPrime(new BigInteger("14")));
	}

	@Test
	public void testIsPrimeBigMaxLong() {
		assertFalse(primesService.isPrime(new BigInteger("9223372036854775807"))); // Long.MAX_VALUE
	}

	@Test
	public void testIsPrimeBigAboveMaxLong() {
		assertFalse(primesService.isPrime(new BigInteger("9223372036854775809")));// above Long.MAX_VALUE
	}

	@Test
	public void testNextPrimeLong() {
		assertEquals(17l, primesService.nextPrime(13));
	}

	@Test(expected = RuntimeException.class)
	public void testNextPrimeLongRangeExceeded() {
		primesService.nextPrime(9223372036854775783l);// largest prime below Long.MAX_VALUE
	}

	@Test
	public void testNextPrimeBig() {
		assertEquals(new BigInteger("17"), primesService.nextPrime(new BigInteger("13")));
	}

	@Test
	public void testIsPrime() {
		assertTrue(primesService.isPrime("13"));
		assertTrue(primesService.isPrime("9223372036854775783"));// largest prime below Long.MAX_VALUE
		assertFalse(primesService.isPrime("9223372036854775807"));// Long.MAX_VALUE
		assertFalse(primesService.isPrime("9223372036854775809"));// above Long.MAX_VALUE
	}

	@Test
	public void testNextPrime() {
		assertEquals("17", primesService.nextPrime("13"));
		assertEquals("9223372036854775783", primesService.nextPrime("9223372036854775781"));
	}

	@Test
	public void testPreviousPrime() {
		// finding largest prime below Long.MAX_VALUE
		assertEquals(9223372036854775783l, primesService.previousPrime(9223372036854775807l));
	}

}
