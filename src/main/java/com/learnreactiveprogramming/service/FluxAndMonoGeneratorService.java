package com.learnreactiveprogramming.service;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxAndMonoGeneratorService {

	public Flux<String> namesFlux(){
		
		return Flux.fromIterable(List.of("alex","ben","chloe"))
				.log();	//db or a remote service call
	}
	
	public Mono<String> namesMono(){
		return Mono.just("alex")
				.log();
	}
	
	public Mono<String> namesMono_map_filter(int stringLength){
		return Mono.just("alex")
				.map(String::toUpperCase)
				.filter(s -> s.length() > stringLength);
	}
	
	public Mono<List<String>> namesMono_flatMap(int stringLength){
		return Mono.just("alex")
				.map(String::toUpperCase)
				.filter(s -> s.length() > stringLength)
				.flatMap(this::splitStringMono);	//Mono<List of A,L,E,X>
	}
	
	private Mono<List<String>> splitStringMono(String s) {
		var charArray = s.split("");
		var charList = List.of(charArray);	//ALEX -> A, L, E, X
		return Mono.just(charList);
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
	
	public Flux<String> namesFlux_flatmap_async(int stringLength){
		//filter the string whose length is greater than 3
		return Flux.fromIterable(List.of("alex","ben","chloe"))
				.map(String::toUpperCase)
				.filter( s -> s.length() > stringLength)	// 4-ALEX, 5-CHOLE
				// ALEX, CHLOE -> A, L, E, X, C, H, L, O, E 
				.flatMap(s -> splitString_withDelay(s))
				.log();	//db or a remote service call
	}
	
	public Flux<String> namesFlux_concatmap(int stringLength){
		//filter the string whose length is greater than 3
		return Flux.fromIterable(List.of("alex","ben","chloe"))
				.map(String::toUpperCase)
				.filter( s -> s.length() > stringLength)	// 4-ALEX, 5-CHOLE
				// ALEX, CHLOE -> A, L, E, X, C, H, L, O, E 
				.concatMap(s -> splitString_withDelay(s))
				.log();	//db or a remote service call
	}
	
	//ALEX -> FLUX(A,L,E,X)
	public Flux<String> splitString(String name){
		var charArray = name.split("");
		return Flux.fromArray(charArray);
	}
	
	public Flux<String> splitString_withDelay(String name){
		var charArray = name.split("");
		var delay = new Random().nextInt(1000);
		return Flux.fromArray(charArray)
				.delayElements(Duration.ofMillis(delay));
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
		
		fluxAndMonoGeneratorService.namesMono()
		.subscribe(name -> System.out.println("Mono name is : " + name));
		

	}

}
