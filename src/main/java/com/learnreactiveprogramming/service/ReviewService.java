package com.learnreactiveprogramming.service;

import java.util.List;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.learnreactiveprogramming.domain.Review;

import reactor.core.publisher.Flux;

public class ReviewService {
	
	private WebClient webClient;
	

    public ReviewService(WebClient webClient) {
		this.webClient = webClient;
	}
    
    public ReviewService() {
    	
    }

	public  List<Review> retrieveReviews(long movieInfoId){

        var reviewsList = List.of(new Review(movieInfoId, "Awesome Movie", 8.9),
                new Review(movieInfoId, "Excellent Movie", 9.0));
        return reviewsList;
    }

    public Flux<Review> retrieveReviewsFlux_RestClient(long movieInfoId){
    	var uri = UriComponentsBuilder.fromUriString("/v1/reviews")
    		.queryParam("movieInfoId",movieInfoId)
    		.buildAndExpand()
    		.toUriString();
    	
    	return webClient.get().uri(uri)
	    	.retrieve()
	    	.bodyToFlux(Review.class)
	    	.log();
    	
    }
    
    public Flux<Review> retrieveReviewsFlux(long MovieId){

        var reviewsList = List.of(new Review(MovieId, "Awesome Movie", 8.9),
                new Review(MovieId, "Excellent Movie", 9.0));
        return Flux.fromIterable(reviewsList);
    }
}
