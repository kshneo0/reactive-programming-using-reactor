package com.learnreactiveprogramming.service;

import static com.learnreactiveprogramming.util.CommonUtil.delay;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.reactive.function.client.WebClient;

import com.learnreactiveprogramming.domain.MovieInfo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MovieInfoService {

	private WebClient webClient;
	
    public MovieInfoService(WebClient webClient) {
		this.webClient = webClient;
	}
    public MovieInfoService() {
    	
    }
    
    public Flux<MovieInfo> retrieveAllMovieInfo_RestClient(){
    	return webClient.get().uri("/v1/movie_infos")
    		.retrieve()
    		.bodyToFlux(MovieInfo.class)
    		.log();
    }

	public  Flux<MovieInfo> retrieveMoviesFlux(){

        var movieInfoList = List.of(new MovieInfo(1l,100l, "Batman Begins", 2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(2l, 101l,"The Dark Knight", 2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo(3l, 102l,"Dark Knight Rises", 2008, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        return Flux.fromIterable(movieInfoList);
    }

    public  Mono<MovieInfo> retrieveMovieInfoMonoUsingId(long movieId){

        var movie = new MovieInfo(movieId, 100l, "Batman Begins", 2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

        return Mono.just(movie);
    }

    public  List<MovieInfo> movieList(){
        delay(1000);
        var moviesList = List.of(new MovieInfo(1l,100l, "Batman Begins", 2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(2l, 101l,"The Dark Knight", 2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo(3l, 102l,"Dark Knight Rises", 2008, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        return moviesList;
    }

    public  MovieInfo retrieveMovieUsingId(long movieId){
        delay(1000);
        var movie = new MovieInfo(movieId, 100l, "Batman Begins", 2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

        return movie;
    }

}
