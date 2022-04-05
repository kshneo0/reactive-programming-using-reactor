package com.learnreactiveprogramming.service;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.learnreactiveprogramming.exception.ReactorException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
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
				.filter(s -> s.length() > stringLength)
				.defaultIfEmpty("default");
	}
	
	public Mono<String> namesMono_map_filter_switchIfEmpty(int stringLength) {
	    Mono<String> defaultMono = Mono.just("default");
	    return Mono.just("alex")
	            .map(String::toUpperCase)
	            .filter(s -> s.length() > stringLength)
	            .switchIfEmpty(defaultMono);

	}
	
	public Mono<List<String>> namesMono_flatMap(int stringLength){
		return Mono.just("alex")
				.map(String::toUpperCase)
				.filter(s -> s.length() > stringLength)
				.flatMap(this::splitStringMono);	//Mono<List of A,L,E,X>
	}
	
	public Flux<String> namesMono_flatMapMany(int stringLength){
		return Mono.just("alex")
				.map(String::toUpperCase)
				.filter(s -> s.length() > stringLength)
				.flatMapMany(this::splitString);	
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
				.doOnNext(name -> {
					System.out.println("Name is : " + name);
					name.toLowerCase();
				})
				.doOnSubscribe(s -> {
					System.out.println("Subscription is : " + s);
				})
				.doOnComplete(() -> {
					System.out.println("Inside the complete callback");
				})
				.doFinally(signalType -> {
					System.out.println("inside dofinally : "+ signalType);
				})
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
	
	public Flux<String> namesFlux_transform(int stringLength){
		//filter the string whose length is greater than 3
		
		Function<Flux<String>, Flux<String>> filterMap =  name -> 
			name.map(String::toUpperCase).filter( s -> s.length() > stringLength);
			
		return Flux.fromIterable(List.of("alex","ben","chloe"))
				.transform(filterMap)
				.flatMap(s -> splitString(s)) // ALEX, CHLOE -> A, L, E, X, C, H, L, O, E
				.defaultIfEmpty("default")
				.log();	//db or a remote service call
	}
	
	public Flux<String> namesFlux_transform_switchIfEmpty(int stringLength){
		//filter the string whose length is greater than 3
		
		Function<Flux<String>, Flux<String>> filterMap =  name -> name
				.map(String::toUpperCase)
				.filter( s -> s.length() > stringLength)
				.flatMap(s -> splitString(s));
		
		var defaultFlux = Flux.just("default")
		.transform(filterMap);	//"D", "E", "F", "A", "U", "L", "T"
		
		return Flux.fromIterable(List.of("alex","ben","chloe"))
				.transform(filterMap)
				.flatMap(s -> splitString(s)) // ALEX, CHLOE -> A, L, E, X, C, H, L, O, E
				.switchIfEmpty(defaultFlux)
				.log();	//db or a remote service call
	}
	
	public Flux<String> explore_concat(){
		var abcFlux = Flux.just("A","B","C");
		var defFlux = Flux.just("D","E","F");
		return Flux.concat(abcFlux, defFlux).log();
	}
	
	public Flux<String> explore_concatWith(){
		var abcFlux = Flux.just("A","B","C");
		var defFlux = Flux.just("D","E","F");
		return abcFlux.concatWith(defFlux).log();
	}
	
	public Flux<String> explore_concatWith_mono(){
		var aMono = Mono.just("A");
		var bMono = Mono.just("B");
		return aMono.concatWith(bMono).log();
	}
	
	public Flux<String> explore_merge(){
		var abcFlux = Flux.just("A","B","C")
				.delayElements(Duration.ofMillis(100));
		var defFlux = Flux.just("D","E","F")
				.delayElements(Duration.ofMillis(125));
		return Flux.merge(abcFlux, defFlux).log();
	}
	
	public Flux<String> explore_mergeWith(){
		var abcFlux = Flux.just("A","B","C")
				.delayElements(Duration.ofMillis(100));
		var defFlux = Flux.just("D","E","F")
				.delayElements(Duration.ofMillis(125));
		return abcFlux.mergeWith(defFlux).log();
	}
	
	public Flux<String> explore_mergeWith_mono(){
		var aMono = Mono.just("A");
		var bMono = Mono.just("B");
		return aMono.mergeWith(bMono).log();
	}
	
	public Flux<String> explore_mergeSequential(){
		var abcFlux = Flux.just("A","B","C")
				.delayElements(Duration.ofMillis(100));
		var defFlux = Flux.just("D","E","F")
				.delayElements(Duration.ofMillis(125));
		return Flux.mergeSequential(abcFlux, defFlux).log();
	}
	
	public Flux<String> explore_zip(){
		var abcFlux = Flux.just("A","B","C");
		var defFlux = Flux.just("D","E","F");
		return Flux.zip(abcFlux, defFlux,(first, second) -> first + second).log();
	}
	
	public Flux<String> explore_zip_1(){
		var abcFlux = Flux.just("A","B","C");
		var defFlux = Flux.just("D","E","F");
		var _123Flux = Flux.just("1","2","3");
		var _456Flux = Flux.just("4","5","6");
		return Flux.zip(abcFlux, defFlux, _123Flux, _456Flux)
				.map(t4 -> t4.getT1() + t4.getT2() + t4.getT3() + t4.getT4())
				.log();
	}
	
	public Flux<String> explore_zipWith(){
		var abcFlux = Flux.just("A","B","C");
		var defFlux = Flux.just("D","E","F");
		return abcFlux.zipWith(defFlux,(first, second) -> first + second).log();
	}
	
	public Mono<String> explore_zipWith_mono(){
		var aMono = Mono.just("A");
		var bMono = Mono.just("B");
		return aMono.zipWith(bMono)
				.map(t2 -> t2.getT1() + t2.getT2())
				.log();
	}
	
	public Flux<String> exception_flux(){
		return Flux.just("A","B","C")
		.concatWith(Flux.error(new RuntimeException("Exception Occurred")))
		.concatWith(Flux.just("D"))
		.log();
	}
	
	public Flux<String> exception_OnErrorReturn(){
		return Flux.just("A","B","C")
				.concatWith(Flux.error(new IllegalStateException("Exception Occurred")))
				.onErrorReturn("D")
				.log();
	}
	
	public Flux<String> exception_OnErrorResume(Exception e){
		
		var recoveryFlux = Flux.just("D","E","F");
		
		return Flux.just("A","B","C")
				.concatWith(Flux.error(e))
				.onErrorResume(ex -> {
					log.error("Exception is ", ex);
					if(ex instanceof IllegalStateException)
						return recoveryFlux;
					else
						return Flux.error(ex);
				})
				.log();
	}
	
	public Flux<String> exception_OnErrorContinue(){
		
		return Flux.just("A","B","C")
				.map(name -> {
					if(name.equals("B"))
						throw new IllegalStateException("Exception Occurred");
					return name;
				})
				.concatWith(Flux.just("D"))
				.onErrorContinue((ex,name) -> {
					log.error("Exception is ", ex);
					log.info("name is {}", name);
				})
				.log();
	}
	
	public Flux<String> exception_OnErrorMap(){
		
		return Flux.just("A","B","C")
				.map(name -> {
					if(name.equals("B"))
						throw new IllegalStateException("Exception Occurred");
					return name;
				})
				.concatWith(Flux.just("D"))
				.onErrorMap((ex) -> {
					log.error("Exception is ", ex);
					return new ReactorException(ex,ex.getMessage());
				})
				.log();
	}
	
	public Flux<String> explore_doOnError(){
		return Flux.just("A","B","C")
				.concatWith(Flux.error(new IllegalStateException("Exception Occurred")))
				.doOnError(ex -> {
					log.error("Exception is ", ex);
				})
				.log();
	}
	
	public Mono<Object> explore_Mono_OnErrorReturn(){
		return Mono.just("A")
				.map(value -> {
					throw new RuntimeException("Exception Occurred");
				})
				.onErrorReturn("abc")
				.log();
	}
	
	 public Mono<String> exception_mono_onErrorContinue(String input) {

	        return Mono.just(input).
	                map(data -> {
	                    if (data.equals("abc"))
	                        throw new RuntimeException("Exception Occurred");
	                    else
	                        return data;
	                }).
	                onErrorContinue((ex, val) -> {
	                    log.error("Exception is " + ex);
	                    log.error("Value that caused the exception is " + val);

	                });
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
		return Flux.fromArray(charArray);
//				.delayElements(Duration.ofMillis(delay));
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
