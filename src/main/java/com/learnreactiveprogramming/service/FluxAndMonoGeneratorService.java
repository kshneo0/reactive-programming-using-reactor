package com.learnreactiveprogramming.service;

import java.util.List;

import reactor.core.publisher.Flux;

public class FluxAndMonoGeneratorService {

	public Flux<String> namesFlux(){
		
		return Flux.fromIterable(List.of("alex","ben","chloe"));	//db or a remote service call
	}
	
	public static void main(String[] args) {
		
		FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
		
		fluxAndMonoGeneratorService.namesFlux()
			.subscribe(name -> System.out.println("Name is : " + name));
		

	}

}
