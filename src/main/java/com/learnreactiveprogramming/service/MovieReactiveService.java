package com.learnreactiveprogramming.service;

import java.util.List;

import com.learnreactiveprogramming.domain.Movie;
import com.learnreactiveprogramming.domain.MovieInfo;
import com.learnreactiveprogramming.domain.Review;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MovieReactiveService {
	
	private MovieInfoService movieInfoService;
	private ReviewService reviewService;
		
	public MovieReactiveService(MovieInfoService movieInfoService, ReviewService reviewService) {
		this.movieInfoService = movieInfoService;
		this.reviewService = reviewService;
	}
		
	public Flux<Movie> getAllMovies() {
		
		var movieInfoFlux = movieInfoService.retrieveMoviesFlux();
		return movieInfoFlux
			.flatMap(movieInfo -> {
				Mono<List<Review>> reviewsMono = reviewService.retrieveReviewsFlux(movieInfo.getMovieInfoId())
				.collectList();
				return reviewsMono
						.map(reviewList -> new Movie(movieInfo, reviewList));
			}).log();
		
	}
	
	public Mono<Movie> getMovieById(long movieId){
		
		
		var movieInfoMono = movieInfoService.retrieveMovieInfoMonoUsingId(movieId);
		var reviewsFlux = reviewService.retrieveReviewsFlux(movieId)
				.collectList();
		
		return movieInfoMono.zipWith(reviewsFlux, (movieInfo, reviews) -> new Movie(movieInfo, reviews));
	}
	
	public Mono<Movie> getMovieById_usingFlatMap(long movieId) {

	    var movieInfoMono = movieInfoService.retrieveMovieInfoMonoUsingId(movieId);
	    return movieInfoMono
	            .flatMap(movieInfo -> {
	                Mono<List<Review>> reviewsMono = reviewService.retrieveReviewsFlux(movieInfo.getMovieInfoId())
	                        .collectList();
	                return reviewsMono
	                        .map(movieList -> new Movie( movieInfo, movieList));

	            });
	}
}
