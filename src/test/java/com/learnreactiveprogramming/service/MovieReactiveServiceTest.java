package com.learnreactiveprogramming.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

public class MovieReactiveServiceTest {
	
	private MovieInfoService movieInfoService = new MovieInfoService();;
	private ReviewService reviewService = new ReviewService();
	
	MovieReactiveService movieReactiveService = new MovieReactiveService(movieInfoService, reviewService);

	@Test
	void getAllMovies() {
		
		var movieFlux = movieReactiveService.getAllMovies();
		
		StepVerifier.create(movieFlux)
			.assertNext(movie -> {
				assertEquals("Batman Begins", movie.getMovieInfo().getName());
				assertEquals(2, movie.getReviewList().size());
				//name of the movie
				//reviewList
			})
			.assertNext(movie -> {
				assertEquals("The Dark Knight", movie.getMovieInfo().getName());
				assertEquals(2, movie.getReviewList().size());
				//name of the movie
				//reviewList
			})
			.assertNext(movie -> {
				assertEquals("Dark Knight Rises", movie.getMovieInfo().getName());
				assertEquals(2, movie.getReviewList().size());
				//name of the movie
				//reviewList
			})
			.verifyComplete();
		
	}
}