package com.learnreactiveprogramming;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Slf4j
public class BackpressureTest {
	
	@Test
	void testBackPressure() {
		var numberRange = Flux.range(1, 100).log();
		
		numberRange
//		.subscribe(num -> {
//			log.info("Number is : {}", num);
//		});
		.subscribe(new BaseSubscriber<Integer> () {
			@Override
            protected void hookOnSubscribe(Subscription subscription) {
				request(2);
            }

            @Override
            protected void hookOnNext(Integer value) {
            	log.info("hookOnNext : {}", value);
            	if(value==2) {
            		cancel();
            	}
            }

            @Override
            protected void hookOnError(Throwable throwable) {


            }
            
            @Override
            protected void hookOnCancel() {
                log.info("Inside OnCancel");
            }

            @Override
            protected void hookOnComplete() {

            }
		});
	}
}
