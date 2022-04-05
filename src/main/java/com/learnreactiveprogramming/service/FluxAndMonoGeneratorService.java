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
	
	public Flux<String> namesFlux_map(int stringLength){
		//filter the string whose length is greater than 3
		return Flux.fromIterable(List.of("alex","ben","chloe"))
				.map(String::toUpperCase)
				.filter( s -> s.length() > stringLength)	// 4-ALEX, 5-CHOLE
				.map(s -> s.length() + "-" + s)
				.log();	//db or a remote service call
	}
	
	public Flux<String> namesFlux_flatmap(int stringLength){
		//filter the string whose length is greater than 3
		return Flux.fromIterable(List.of("alex","ben","chloe"))
				.map(String::toUpperCase)
				.filter( s -> s.length() > stringLength)	// 4-ALEX, 5-CHOLE
				// ALEX, CHLOE -> A, L, E, X, C, H, L, O, E 
				.flatMap(s -> splitString(s))
				.log();	//db or a remote service call
	}
	
	//ALEX -> FLUX(A,L,E,X)
	public Flux<String> splitString(String name){
		var charArray = name.split("");
		return Flux.fromArray(charArray);
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
