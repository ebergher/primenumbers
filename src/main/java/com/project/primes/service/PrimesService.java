package com.project.primes.service;

import java.math.BigInteger;

public interface PrimesService {

	boolean isPrime(String s);

	String nextPrime(String s);

	boolean isPrime(long n);

	long nextPrime(long n);

	long previousPrime(long n);

	boolean isPrime(BigInteger n);

	BigInteger nextPrime(BigInteger n);

}
