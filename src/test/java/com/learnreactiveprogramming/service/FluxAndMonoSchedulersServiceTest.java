package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class FluxAndMonoSchedulersServiceTest {

	FluxAndMonoSchedulersService fluxAndMonoSchedulersService = new FluxAndMonoSchedulersService();
	@Test
	void explore_publishOn(){
		
		var flux = fluxAndMonoSchedulersService.explore_publishOn();
		
		StepVerifier.create(flux)
		.expectNextCount(6)
		.verifyComplete();
		
		
	}
}
