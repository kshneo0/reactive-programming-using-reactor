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
	
	@Test
	void namesMono_flatMapMany() {
		
		int stringLength = 3;
		var value = fluxAndMonoGeneratorService.namesMono_flatMapMany(stringLength);
		
		StepVerifier.create(value)
			.expectNext("A","L","E","X")
			.verifyComplete();
	}
	
	@Test
	void namesMono_map_filter_switchIfEmpty() {

	    //given
	    int stringLength = 4;

	    //when
	    var stringMono = fluxAndMonoGeneratorService.namesMono_map_filter_switchIfEmpty(stringLength);

	    //then
	    StepVerifier.create(stringMono)
	            .expectNext("default")
	            .verifyComplete();

	}
	
	@Test
	void namesMono_map_empty() {

	    //given
	    int stringLength = 4;

	    //when
	    var stringMono = fluxAndMonoGeneratorService.namesMono_map_filter(stringLength);

	    //then
	    StepVerifier.create(stringMono)
	            .expectNext("default")
	            .verifyComplete();

	}
	
	@Test
	void namesFlux_transform() {
		
		int stringLength = 3;
		var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);
		
		StepVerifier.create(namesFlux)
			.expectNext("A","L","E","X","C","H","L","O","E")
			.verifyComplete();
	}
	
	@Test
	void namesFlux_transform_1() {
		
		int stringLength = 6;
		var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform(stringLength);
		
		StepVerifier.create(namesFlux)
//			.expectNext("A","L","E","X","C","H","L","O","E")
			.expectNext("default")
			.verifyComplete();
	}
	
	@Test
	void namesFlux_transform_switchIfEmpty() {
		
		int stringLength = 6;
		var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform_switchIfEmpty(stringLength);
		
		StepVerifier.create(namesFlux)
			.expectNext("D", "E", "F", "A", "U", "L", "T")
			.verifyComplete();
	}
	
	@Test
	void explore_concat() {
		

		var concatFlux = fluxAndMonoGeneratorService.explore_concat();
		
		StepVerifier.create(concatFlux)
			.expectNext("A", "B", "C", "D", "E", "F")
			.verifyComplete();
	}
	
	@Test
	void explore_concatWith() {
		

		var concatFlux = fluxAndMonoGeneratorService.explore_concatWith();
		
		StepVerifier.create(concatFlux)
			.expectNext("A", "B", "C", "D", "E", "F")
			.verifyComplete();
	}
	
	@Test
	void explore_concat_mono() {

	    var value = fluxAndMonoGeneratorService.explore_concatWith_mono();

	    //then
	    StepVerifier.create(value)
	            .expectNext("A", "B")
	            .verifyComplete();

	}
	
	@Test
	void explore_merge() {
		

		var value = fluxAndMonoGeneratorService.explore_merge();
		
		StepVerifier.create(value)
			.expectNext("A", "D", "B", "E", "C", "F")
			.verifyComplete();
	}
	
	@Test
	void explore_mergeWith() {
		

		var value = fluxAndMonoGeneratorService.explore_mergeWith();
		
		StepVerifier.create(value)
			.expectNext("A", "D", "B", "E", "C", "F")
			.verifyComplete();
	}
	
	@Test
	void explore_mergeWith_mono() {

	    var value = fluxAndMonoGeneratorService.explore_mergeWith_mono();

	    StepVerifier.create(value)
	            .expectNext("A", "B")
	            .verifyComplete();

	}
	
}