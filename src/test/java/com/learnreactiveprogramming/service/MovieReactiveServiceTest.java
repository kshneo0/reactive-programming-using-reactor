package com.learnreactiveprogramming.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.learnreactiveprogramming.domain.Movie;

import reactor.core.publisher.Mono;
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
	
	@Test
	void getMovieById() {
		
		long movieId = 100L;
		
		var movieMono = movieReactiveService.getMovieById(movieId);
		
		StepVerifier.create(movieMono)
		.assertNext(movie -> {
			assertEquals("Batman Begins", movie.getMovieInfo().getName());
			assertEquals(2, movie.getReviewList().size());
			//name of the movie
			//reviewList
		})
		.verifyComplete();
	}
	
	@Test
	void getMovieById_usingFlatMap() {

	    //given
	    long movieId = 1L;

	    //when
	    Mono<Movie> movieMono = movieReactiveService.getMovieById_usingFlatMap(movieId);

	    //then
	    StepVerifier.create(movieMono)
	            .assertNext(movieInfo -> {
	                assertEquals("Batman Begins", movieInfo.getMovieInfo().getName());
	                assertEquals(movieInfo.getReviewList().size(), 2);
	            })
	            .verifyComplete();
	}
}