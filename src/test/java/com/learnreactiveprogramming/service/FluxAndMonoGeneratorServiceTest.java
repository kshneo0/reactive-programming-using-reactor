package com.learnreactiveprogramming.service;


import java.util.List;

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

	    var stringMono = fluxAndMonoGeneratorService.namesMono();

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
	
	@Test
	void namesFlux_flatmap() {
		
		int stringLength = 3;
		var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap(stringLength);
		
		StepVerifier.create(namesFlux)
			.expectNext("A","L","E","X","C","H","L","O","E")
			.verifyComplete();
	}
	
	@Test
	void namesFlux_flatmap_async() {
		
		int stringLength = 3;
		var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_async(stringLength);
		
		StepVerifier.create(namesFlux)
//			.expectNext("A","L","E","X","C","H","L","O","E")
			.expectNextCount(9)
			.verifyComplete();
	}
	
	@Test
	void namesFlux_concatmap() {
		
		int stringLength = 3;
		var namesFlux = fluxAndMonoGeneratorService.namesFlux_concatmap(stringLength);
		
		StepVerifier.create(namesFlux)
			.expectNext("A","L","E","X","C","H","L","O","E")
			.verifyComplete();
	}
	
	@Test
	void namesMono_flatMap() {
		
		int stringLength = 3;
		var value = fluxAndMonoGeneratorService.namesMono_flatMap(stringLength);
		
		StepVerifier.create(value)
			.expectNext(List.of("A","L","E","X"))
			.verifyComplete();
	}
	
	
}