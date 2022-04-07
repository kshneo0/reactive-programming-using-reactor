package com.learnreactiveprogramming;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

import static com.learnreactiveprogramming.util.CommonUtil.delay;
import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;


public class ColdAndHotPublisherTest {

	@Test
	void coldPublisherTest() {
		
		var flux = Flux.range(1,10);
		
		flux.subscribe(i -> System.out.println("Subscriber 1 : " + i));
		
		flux.subscribe(i -> System.out.println("Subscriber 2 : " + i));		
		
	}
	
	@Test
	void hotPublisherTest() {
		
		var flux = Flux.range(1,10)
				.delayElements(Duration.ofSeconds(1));
		
		ConnectableFlux<Integer> connectableFux = flux.publish();
		connectableFux.connect();
		
		connectableFux.subscribe(i -> System.out.println("Subscriber 1 : " + i));
		delay(4000);
		connectableFux.subscribe(i -> System.out.println("Subscriber 2 : " + i));
		
		delay(10000);
			
		
	}
	
	@Test
	void hotPublisherTest_autoConnect() {
		
		var flux = Flux.range(1,10)
				.delayElements(Duration.ofSeconds(1));
		
		Flux<Integer> hotSource = flux.publish().autoConnect(2);
		
		hotSource.subscribe(i -> System.out.println("Subscriber 1 : " + i));
		delay(2000);
		hotSource.subscribe(i -> System.out.println("Subscriber 2 : " + i));
		System.err.println("Two Subscribers are connected");
		delay(2000);
		hotSource.subscribe(i -> System.out.println("Subscriber 3 : " + i));
		delay(10000);
			
		
	}

	@Test
	void hotPublisherTest_refCount() {
		
		var flux = Flux.range(1,10)
				.delayElements(Duration.ofSeconds(1))
				.doOnCancel( ()->{
					System.out.println("Received Cancel Signal");
				});
		
		Flux<Integer> hotSource = flux.publish().refCount(2);
		
		var disposable = hotSource.subscribe(i -> System.out.println("Subscriber 1 : " + i));
		delay(2000);
		var disposable1 = hotSource.subscribe(i -> System.out.println("Subscriber 2 : " + i));
		System.err.println("Two Subscribers are connected");
		delay(2000);
		disposable.dispose();
		disposable1.dispose();
		hotSource.subscribe(i -> System.out.println("Subscriber 3 : " + i));
		delay(2000);
		hotSource.subscribe(i -> System.out.println("Subscriber 4 : " + i));
		delay(10000);
		
		
	}
}
