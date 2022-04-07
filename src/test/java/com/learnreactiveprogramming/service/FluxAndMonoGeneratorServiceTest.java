package com.learnreactiveprogramming.service;


import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.learnreactiveprogramming.exception.ReactorException;

import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

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
	void namesFlux_concatmap_virtualTimer() {
		
		VirtualTimeScheduler.getOrSet();
		int stringLength = 3;
		var namesFlux = fluxAndMonoGeneratorService.namesFlux_concatmap(stringLength);
		
		StepVerifier.withVirtualTime(() -> namesFlux)
		.thenAwait(Duration.ofSeconds(10))
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
	void explore_mergeSequential() {
		

		var value = fluxAndMonoGeneratorService.explore_mergeSequential();
		
		StepVerifier.create(value)
			.expectNext("A", "B", "C", "D", "E", "F")
			.verifyComplete();
	}
	
	@Test
	void explore_zip() {
		

		var value = fluxAndMonoGeneratorService.explore_zip();
		
		StepVerifier.create(value)
			.expectNext("AD", "BE", "CF")
			.verifyComplete();
	}
	
	@Test
	void explore_zip_1() {
		

		var value = fluxAndMonoGeneratorService.explore_zip_1();
		
		StepVerifier.create(value)
			.expectNext("AD14", "BE25", "CF36")
			.verifyComplete();
	}
	
	@Test
	void explore_zipWith() {
		

		var value = fluxAndMonoGeneratorService.explore_zipWith();
		
		StepVerifier.create(value)
			.expectNext("AD", "BE", "CF")
			.verifyComplete();
	}	
	
	@Test
	void explore_zipWith_mono() {

	    //given

	    //when
	    var value = fluxAndMonoGeneratorService.explore_zipWith_mono().log();

	    //then
	    StepVerifier.create(value)
	            .expectNext("AB")
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
	
	@Test 
	void exception_flux() {
		
		var value = fluxAndMonoGeneratorService.exception_flux();
		
		StepVerifier.create(value)
			.expectNext("A","B","C")
			.expectError(RuntimeException.class)
			.verify();
			
	}
	@Test 
	void exception_flux_1() {
		
		var value = fluxAndMonoGeneratorService.exception_flux();
		
		StepVerifier.create(value)
		.expectNext("A","B","C")
		.expectError()
		.verify();
		
	}
	
	@Test 
	void exception_flux_2() {
		
		var value = fluxAndMonoGeneratorService.exception_flux();
		
		StepVerifier.create(value)
		.expectNext("A","B","C")
		.expectErrorMessage("Exception Occurred")
		.verify();
		
	}
	
	@Test
	void exception_OnErrorReturn() {
		
		var value = fluxAndMonoGeneratorService.exception_OnErrorReturn();
		
		StepVerifier.create(value)
		.expectNext("A","B","C","D")
		.verifyComplete();
		
	}
	
	@Test
	void exception_OnErrorResume() {
		
		var e = new IllegalStateException("Not a valis State");
		
		var value = fluxAndMonoGeneratorService.exception_OnErrorResume(e);
		
		StepVerifier.create(value)
		.expectNext("A","B","C","D","E","F")
		.verifyComplete();
	}
	
	@Test
	void exception_OnErrorResume_1() {
		
		var e = new RuntimeException("Not a valis State");
		
		var value = fluxAndMonoGeneratorService.exception_OnErrorResume(e);
		
		StepVerifier.create(value)
		.expectNext("A","B","C")
		.expectError(RuntimeException.class)
		.verify();
	}
	
	@Test
	void exception_OnErrorContinue() {
		
		var value = fluxAndMonoGeneratorService.exception_OnErrorContinue();
		
		StepVerifier.create(value)
		.expectNext("A","C","D")
		.verifyComplete();
		
	}
	@Test
	void exception_OnErrorMap() {
		
		var value = fluxAndMonoGeneratorService.exception_OnErrorMap();
		
		StepVerifier.create(value)
		.expectNext("A")
		.expectError(ReactorException.class)
		.verify();
	}
	
	@Test
	void explore_doOnError() {
		
		var value = fluxAndMonoGeneratorService.explore_doOnError();
		
		StepVerifier.create(value)
		.expectNext("A","B","C")
		.expectError(IllegalStateException.class)
		.verify();
	}
	
	@Test
	void explore_Mono_OnErrorReturn() {
		
		var value = fluxAndMonoGeneratorService.explore_Mono_OnErrorReturn();
		
		StepVerifier.create(value)
		.expectNext("abc")
		.verifyComplete();
	}	
	
	@Test
	void exception_mono_onErrorContinue() {

	    //given
	    var input = "abc";

	    //when
	    var mono=fluxAndMonoGeneratorService.exception_mono_onErrorContinue(input);

	    //then
	    StepVerifier.create(mono)
	            .verifyComplete();
	}
	
	@Test
	void exception_mono_onErrorContinue_1() {

	    //given
	    var input = "reactor";

	    //when
	    var mono=fluxAndMonoGeneratorService.exception_mono_onErrorContinue(input);

	    //then
	    StepVerifier.create(mono)
	            .expectNext(input)
	            .verifyComplete();
	}
	
	@Test
	void explore_generate() {
		
		var flux=fluxAndMonoGeneratorService.explore_generate().log();
		
		StepVerifier.create(flux)
        .expectNextCount(10)
        .verifyComplete();
	}
	
}