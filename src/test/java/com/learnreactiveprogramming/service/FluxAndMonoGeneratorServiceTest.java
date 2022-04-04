package com.learnreactiveprogramming.service;


import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class FluxAndMonoGeneratorServiceTest {
	
	FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
	
	@Test
	void namesFlux() {
		
		var namesFlux = fluxAndMonoGeneratorService.namesFlux();
		
		StepVerifier.create(namesFlux)
//			.expectNext("alex","ben","chloe")
//			.expectNextCount(3)
			.expectNext("alex")
			.expectNextCount(2)
			.verifyComplete();
	}
	
	@Test
	void namesMono() {

	    var stringMono = fluxAndMonoGeneratorService.nameMono();

	    StepVerifier.create(stringMono)
	            .expectNext("alex")
	            .verifyComplete();

	}
}
