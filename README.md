# primenumbers

### Swagger
Available at http://localhost:8080/swagger-ui.html .

Each exposed API has a detailed description.

Of 7 operations exposed, only 2 represent the requirements for the project :
 - /primes/checkprime/{s} (checks if a number is prime)
 - /primes/nextprime/{s} (calculates the next prime number after the given number)


The other 5 are exposed to show the difference in performance and max value limitations between the 2 solutions : using long or BigInteger as data types for the calculations.

### Data types used for performance improvement

Because BigInteger occupies much more memory and has much slower operations (compared to primitive long), I have only used it to handle values greater than the Long.MAX_VALUE: 9223372036854775807.

So, for the 2 main methods, the threshold values for determining the data types used are:
 - 9223372036854775807 (Long.MAX_VALUE) for /primes/checkprime/{s} and
 - 9223372036854775783 for /primes/nextprime/{s}.

I have used this value because this is the highest prime number lower than Long.MAX_VALUE. The next prime would be higher than the Long.MAX_VALUE, and so the Long range would have been exceeded.
	
I have used one of the exposed operations to find this number:

 - /primes/previousprimelong/{s} using the input value 9223372036854775807 (Long.MAX_VALUE).

To test the difference in performance between the long and BigInteger approach, I chose this value which is roughly the largest prime lower than Long.MAX_VALUE/2:

4623372036854775751

Results in execution time (seconds):

 - /primes/checkprimelong/{s}: "4 seconds"
 - /primes/checkprimebig/{s}: "56 seconds"

### Algorithm

Both approaches, using long or BigInteger use the same algorithms for testing if the number(n) is prime:

Knowing that with the exception of 2 and 3, all primes are of the form 6k ± 1 we can test for divisors of only this type, up until sqrt(n).

The time complexity of this solution is O(√n)

For finding the next prime number, we apply the same primality test as above by iterating through odd numbers starting with the given number
 
The largest number I've tested to find the next prime number is 9223372036854775807 (Long.MAX_VALUE).

The result was: 9223372036854775837 and I have obtained it in 86 seconds on my local machine.

Because BigInteger is used, there is a theoretical value limit of: 2^Integer.MAX_VALUE, except that the execution times are increasingly larger.

### Memory consumption

I used jprofiler to analyse the difference between the long and BigInteger implementations:
 - /primes/checkprimelong/4623372036854775751 : used memory 0.04GB;
 - /primes/checkprimebig/4623372036854775751 : spiked at 1.37GB;

This is the total memory consumption, for the Heap analysis only BigInteger values were recorded, as primitives are not stored on the heap, but stack
 
BigIntegers are also immutable and thus require another instance for each value, making them performance-heavy, especially when used in these types of complex math calculations.

### Thread safety
The service class PrimesServiceImpl is thread safe:

It holds no instance variables and also all the calculation methods contained are stateless implementations:
 - they are deterministic - given a specific input they produce the same output;
 - they don't rely on external state nor maintain any state; 

### Docker

The Dockerfile along with the already built fat jar are contained in the /docker folder

### Source code

Available at:

https://github.com/ebergher/primenumbers.git

