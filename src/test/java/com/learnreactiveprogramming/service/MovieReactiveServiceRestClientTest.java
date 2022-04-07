package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.test.StepVerifier;

public class MovieReactiveServiceRestClientTest {

    WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/movies")
            .build();
    MovieInfoService mis = new MovieInfoService(webClient);
    ReviewService rs = new ReviewService(webClient);
    RevenueService revenueService = new RevenueService();
    MovieReactiveService movieReactiveService = new MovieReactiveService(mis, rs,revenueService);
    
    @Test
    void getAllMovies_RestClient() {
    	
    	var moviesFlux = movieReactiveService.getAllMovies_restClient();
    	
    	  StepVerifier.create(moviesFlux)
    	  	.expectNextCount(7)
            .verifyComplete();
    	
    }
    
    @Test
    void getMovieById_RestClient() {
    	
    	var moviesMono = movieReactiveService.getMovieById_RestClient(1L);
    	
    	  StepVerifier.create(moviesMono)
    	  	.expectNextCount(1)
            .verifyComplete();
    	
    }
}
