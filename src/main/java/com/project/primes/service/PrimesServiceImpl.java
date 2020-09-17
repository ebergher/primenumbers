package com.project.primes.service;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

@Service
public class PrimesServiceImpl implements PrimesService {

	@Override
	public boolean isPrime(long n) {
		if (n <= 3) {
			return n > 1;
		}
		if (n % 2 == 0 || n % 3 == 0) {
			return false;
		}
		long sqrt = (long) Math.sqrt(new Double(n));
		for (long i = 5; i <= sqrt; i = i + 6) {
			if (n % i == 0 || n % (i + 2) == 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public long nextPrime(long n) {
		if (n <= 1) {
			return 2;
		}

		long prime = n % 2 == 0 ? n - 1 : n;
		boolean found = false;

		while (!found) {
			if (prime >= Long.MAX_VALUE - 1) {
				throw new RuntimeException("Long range exceeded");
			}

			prime = prime + 2;

			if (isPrime(prime)) {
				found = true;
			}
		}

		return prime;
	}

	@Override
	public boolean isPrime(BigInteger n) {
		if (n.compareTo(new BigInteger("3")) <= 0) {
			return n.compareTo(BigInteger.ONE) > 0;
		}

		if (n.remainder(new BigInteger("2")).equals(BigInteger.ZERO)
				|| n.remainder(new BigInteger("3")).equals(BigInteger.ZERO)) {
			return false;
		}

		BigInteger i = new BigInteger("5");
		while (i.multiply(i).compareTo(n) <= 0) {
			if (n.remainder(i).equals(BigInteger.ZERO)
					|| n.remainder(i.add(new BigInteger("2"))).equals(BigInteger.ZERO)) {
				return false;
			}
			i = i.add(new BigInteger("6"));

		}
		return true;
	}

	@Override
	public BigInteger nextPrime(BigInteger n) {
		if (n.compareTo(BigInteger.ONE) <= 0) {
			return new BigInteger("2");
		}

		BigInteger prime = n.remainder(new BigInteger("2")).equals(BigInteger.ZERO) ? n.subtract(BigInteger.ONE) : n;
		boolean found = false;

		while (!found) {
			prime = prime.add(new BigInteger("2"));
			if (isPrime(prime)) {
				found = true;
			}
		}

		return prime;
	}

	@Override
	public long previousPrime(long n) {
		if (n <= 3) {
			return 2;
		}
		long prime = n % 2 == 0 ? n - 1 : n;
		boolean found = false;
		while (!found) {
			prime = prime - 2;
			if (isPrime(prime)) {
				found = true;
			}
		}

		return prime;
	}

	@Override
	public boolean isPrime(String s) {
		BigInteger nbigint = new BigInteger(s);
		BigInteger longMaxValue = new BigInteger(String.valueOf(Long.MAX_VALUE));
		if (nbigint.compareTo(longMaxValue) > 0) {
			return isPrime(nbigint);
		} else {
			long nlong = Long.parseLong(s);
			return isPrime(nlong);
		}
	}

	@Override
	public String nextPrime(String s) {
		BigInteger nbigint = new BigInteger(s);
		// largest prime number smaller than Long.MAX_VALUE
		BigInteger maxPrimeForLong = new BigInteger("9223372036854775783");
		if (nbigint.compareTo(maxPrimeForLong) > 0) {
			return String.valueOf(nextPrime(nbigint));
		} else {
			long nlong = Long.parseLong(s);
			return String.valueOf(nextPrime(nlong));
		}
	}

}
