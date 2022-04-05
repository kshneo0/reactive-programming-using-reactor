package com.learnreactiveprogramming.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.learnreactiveprogramming.exception.MovieException;

import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class MovieReactiveServiceMockTest {
	
    @Mock
    MovieInfoService movieInfoService;

    @Mock
    ReviewService reviewService;
	
    @InjectMocks
    MovieReactiveService reactiveMovieService;

	@Test
	void getAllMovies() {
		
        when(movieInfoService.retrieveMoviesFlux()).thenCallRealMethod();
        when(reviewService.retrieveReviewsFlux(anyLong())).thenCallRealMethod();
		
		var movieFlux  =  reactiveMovieService.getAllMovies();

		StepVerifier.create(movieFlux)
	                .expectNextCount(3)
	                .verifyComplete();
		
	}
	
	@Test
	void getAllMovies_1() {
		
		var errorMessage = "Exception occurred in ReviewService";
		when(movieInfoService.retrieveMoviesFlux()).thenCallRealMethod();
		when(reviewService.retrieveReviewsFlux(anyLong())).thenThrow(new RuntimeException(errorMessage));
		
		var movieFlux  =  reactiveMovieService.getAllMovies();
		
		StepVerifier.create(movieFlux)
//		.expectError(MovieException.class)
		.expectErrorMessage(errorMessage)
		.verify();
		
	}
}
