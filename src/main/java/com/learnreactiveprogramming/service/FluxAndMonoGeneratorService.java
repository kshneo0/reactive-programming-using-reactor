package com.learnreactiveprogramming.service;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxAndMonoGeneratorService {

	public Flux<String> namesFlux(){
		
		return Flux.fromIterable(List.of("alex","ben","chloe"))
				.log();	//db or a remote service call
	}
	
	public Mono<String> nameMono(){
		return Mono.just("alex")
				.log();
	}
	
	public Flux<String> namesFlux_map(){
		
		return Flux.fromIterable(List.of("alex","ben","chloe"))
				.map(String::toUpperCase)
				.log();	//db or a remote service call
	}
	
	public Flux<String> namesFlux_immutability(){
		
		var namesFlux =  Flux.fromIterable(List.of("alex","ben","chloe"));
		namesFlux.map(String::toUpperCase);
		return namesFlux;
		
	}
	
	public static void main(String[] args) {
		
		FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
		
		fluxAndMonoGeneratorService.namesFlux()
			.subscribe(name -> System.out.println("Name is : " + name));
		
		System.out.println("-----------------------------");
		
		fluxAndMonoGeneratorService.nameMono()
		.subscribe(name -> System.out.println("Mono name is : " + name));
		

	}

}
