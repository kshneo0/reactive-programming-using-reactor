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
	
	@Test
	void namesFlux_map() {
		
		int stringLength = 3;
		var namesFlux = fluxAndMonoGeneratorService.namesFlux_map(stringLength);
		
		StepVerifier.create(namesFlux)
//			.expectNext("ALEX","BEN","CHLOE")
//			.expectNext("ALEX","CHLOE")
			.expectNext("4-ALEX","5-CHLOE")
			.verifyComplete();
	}
	
	@Test
	void namesFlux_immutability() {
		
		var namesFlux = fluxAndMonoGeneratorService.namesFlux_immutability();
		
		StepVerifier.create(namesFlux)
			.expectNext("alex","ben","chloe")
			.verifyComplete();
	}
	
}