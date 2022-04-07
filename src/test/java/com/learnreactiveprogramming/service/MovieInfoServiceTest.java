package com.learnreactiveprogramming.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.test.StepVerifier;

public class MovieInfoServiceTest {
	
	WebClient webClient = WebClient.builder()
			.baseUrl("http://localhost:8080/movies")
			.build();
	
	MovieInfoService movieInfoService = new MovieInfoService(webClient);
	
	@Test
	void retrieveAllMovieInfo_RestClient(){
		var movieInfoFlux = movieInfoService.retrieveAllMovieInfo_RestClient();
		
		StepVerifier.create(movieInfoFlux)
		.expectNextCount(7)
		.verifyComplete();
	}
	
	@Test
    void retrieveMovieInfoById_RestClient() {

        //given
        Long movieInfoId = 1L;

        //when
        var movieInfoFlux = movieInfoService.retrieveMovieInfoById_RestClient(movieInfoId);

        //then
        StepVerifier.create(movieInfoFlux)
                //.expectNextCount(7)
                .assertNext( movieInfo ->
                        assertEquals("Batman Begins", movieInfo.getName())

                )
                .verifyComplete();
    }
}
