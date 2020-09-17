package com.project.primes.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.primes.service.PrimesService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/primes")
public class PrimesController {

	@Autowired
	PrimesService primesService;

	@GetMapping("/checkprimelong/{s}")
	@ApiOperation(value = "Determines if a given numer is prime. Accepts input values up to 9223372036854775807", notes = "This method uses long data types to perform the determination, and thus only works with input values up to Long.MAX_VALUE</br>"
			+ "Inputs higher than this margin or invalid numbers will result in the 'Invalid number' result</br>"
			+ "This api operation is only exposed to compare performance to the BigInteger approach used in @checkprimebig</br>")
	Map<String, String> checkprimelong(
			@ApiParam(value = "the number to be checked", required = true) @PathVariable String s) {
		Map<String, String> response = new HashMap<>();
		long start = System.currentTimeMillis();
		try {
			long n = Long.parseLong(s);
			String result = primesService.isPrime(n) ? "The number is prime" : "The number is not prime";
			response.put("result", result);
		} catch (NumberFormatException nfe) {
			response.put("result", "Invalid number");
		}
		response.put("duration", (System.currentTimeMillis() - start) / 1000 + " seconds");
		return response;
	}

	@GetMapping("/nextprimelong/{s}")
	@ApiOperation(value = "Retrieves the next prime number higher than the given number. Accepts input values up to 9223372036854775807", notes = "This method uses long data types to perform the determination, and thus only works with input values up to Long.MAX_VALUE</br>"
			+ "Inputs higher than this margin or invalid numbers will result in the 'Invalid number' result</br>"
			+ "Also, while determining the next prime number, if the Long.MAX_VALUE is exceeded, an error response 'Long range exceeded' will be returned."
			+ "This api operation is only exposed to compare performance to the BigInteger approach used in @nextprimebig</br>")
	Map<String, String> nextprimelong(
			@ApiParam(value = "number to determine the next prime from", required = true) @PathVariable String s) {
		Map<String, String> response = new HashMap<>();
		long start = System.currentTimeMillis();
		try {
			long n = Long.parseLong(s);
			String result = String.valueOf(primesService.nextPrime(n));
			response.put("result", result);
		} catch (NumberFormatException nfe) {
			response.put("result", "Invalid number");
		} catch (RuntimeException e) {
			response.put("result", e.getMessage());
		}
		response.put("duration", (System.currentTimeMillis() - start) / 1000 + " seconds");
		return response;
	}


	@GetMapping("/checkprimebig/{s}")
	@ApiOperation(value = "Determines if a given numer is prime. Accepts input values higher than 9223372036854775807", notes = "This method uses BigInteger data types to perform the determination, and thus are not limited by a max value</br>"
			+ "Invalid numbers for input will result in the 'Invalid number' result</br>"
			+ "This api operation is only exposed to compare performance to the long approach used in @checkprimelong</br>")
	Map<String, String> checkprimebig(
			@ApiParam(value = "the number to be checked", required = true) @PathVariable String s) {
		Map<String, String> response = new HashMap<>();
		long start = System.currentTimeMillis();
		try {
			BigInteger n = new BigInteger(s);
			String result = primesService.isPrime(n) ? "The number is prime" : "The number is not prime";
			response.put("result", result);
		} catch (NumberFormatException nfe) {
			response.put("result", "Invalid number");
		}
		response.put("duration", (System.currentTimeMillis() - start) / 1000 + " seconds");
		return response;
	}

	@GetMapping("/nextprimebig/{s}")
	@ApiOperation(value = "Retrieves the next prime number higher than the given number. Accepts input values higher than 9223372036854775807", notes = "This method uses BigInteger data types to perform the determination, and thus are not limited by a max value</br>"
			+ "Invalid numbers for input will result in the 'Invalid number' result</br>"
			+ "This api operation is only exposed to compare performance to the long approach used in @nextprimelong</br>")
	Map<String, String> nextprimebig(
			@ApiParam(value = "number to determine the next prime from", required = true) @PathVariable String s) {
		Map<String, String> response = new HashMap<>();
		long start = System.currentTimeMillis();
		try {
			BigInteger n = new BigInteger(s);
			String result = String.valueOf(primesService.nextPrime(n));
			response.put("result", result);
		} catch (NumberFormatException nfe) {
			response.put("result", "Invalid number");
		}
		response.put("duration", (System.currentTimeMillis() - start) / 1000 + " seconds");
		return response;
	}

	@GetMapping("/previousprimelong/{s}")
	@ApiOperation(value = "Retrieves the previous prime number higher than the given number. Accepts input values up to 9223372036854775807", notes = "This method uses long data types to perform the determination, and thus only works with input values up to Long.MAX_VALUE</br>"
			+ "Inputs higher than this margin or invalid numbers will result in the 'Invalid number' result</br>"
			+ "This api operation is only exposed to show how the biggest prime number smaller than Long.MAX_VALUE is determined. This retrieved value is used by the @checkprime and @nextprime optimised methods")
	Map<String, String> previousprimelong(
			@ApiParam(value = "number to determine the previous prime from", required = true) @PathVariable String s) {
		Map<String, String> response = new HashMap<>();
		long start = System.currentTimeMillis();
		try {
			long n = Long.parseLong(s);
			String result = String.valueOf(primesService.previousPrime(n));
			response.put("result", result);
		} catch (NumberFormatException nfe) {
			response.put("result", "Invalid number");
		}
		response.put("duration", (System.currentTimeMillis() - start) / 1000 + " seconds");
		return response;
	}

	@GetMapping("/checkprime/{s}")
	@ApiOperation(value = "Determines if a given numer is prime. Accepts input values higher than 9223372036854775807", notes = "The input parameter is not limited by a max value</br>"
			+ "Invalid numbers for input will result in the 'Invalid number' result</br>"
			+ "This api operation combines the usage of long or BigInteger data types based on the value of the input parameter</br>"
			+ "This is done to increase performance for the inputs which are less than Long.MAX_VALUE because BigInteger types consume much more memory than a long primitive and operations on these types are also much slower")
	Map<String, String> checkprime(
			@ApiParam(value = "the number to be checked", required = true) @PathVariable String s) {
		Map<String, String> response = new HashMap<>();
		long start = System.currentTimeMillis();
		try {
			String result = primesService.isPrime(s) ? "The number is prime" : "The number is not prime";
			response.put("result", result);
		} catch (NumberFormatException nfe) {
			response.put("result", "Invalid number");
		}
		response.put("duration", (System.currentTimeMillis() - start) / 1000 + " seconds");
		return response;
	}

	@GetMapping("/nextprime/{s}")
	@ApiOperation(value = "Retrieves if a given numer is prime. Accepts input values higher than 9223372036854775807", notes = "The input parameter is not limited by a max value</br>"
			+ "Invalid numbers for input will result in the 'Invalid number' result</br>"
			+ "This api operation combines the usage of long or BigInteger data types based on the value of the input parameter</br>"
			+ "This is done to increase performance for the inputs which are less than Long.MAX_VALUE because BigInteger types consume much more memory than a long primitive and operations on these types are also much slower</br>"
			+ "The value 9223372036854775783 which may be retrieved using the @previousprimelong operation is used in determining if the data type used should be long (values below this threshhold) or BigInteger (any value above)")
	Map<String, String> nextprime(
			@ApiParam(value = "number to determine the next prime from", required = true) @PathVariable String s) {
		Map<String, String> response = new HashMap<>();
		long start = System.currentTimeMillis();
		try {
			String result = primesService.nextPrime(s);
			response.put("result", result);
		} catch (NumberFormatException nfe) {
			response.put("result", "Invalid number");
		}
		response.put("duration", (System.currentTimeMillis() - start) / 1000 + " seconds");
		return response;
	}
}
