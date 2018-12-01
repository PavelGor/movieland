package com.gordeev.movieland.service.impl.util;

import com.gordeev.movieland.entity.Movie;
import com.gordeev.movieland.service.CountryService;
import com.gordeev.movieland.service.GenreService;
import com.gordeev.movieland.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

@Service
public class ParallelMovieEnrichmentService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private int enrichTimeout;
    private ReviewService reviewService;
    private GenreService genreService;
    private CountryService countryService;
    private Executor threadPoolExecutor;

    public void enrich(List<Movie> movies) {

        CompletableFuture<Void> countries = CompletableFuture.runAsync(() -> countryService.enrich(movies), threadPoolExecutor);
        CompletableFuture<Void> genres = CompletableFuture.runAsync(() -> genreService.enrich(movies), threadPoolExecutor);

        Movie movie = movies.get(0);
        CompletableFuture<Void> reviews = CompletableFuture
                .supplyAsync(() -> reviewService.getByMovieId(movie.getId()), threadPoolExecutor)
                .thenAccept(movie::setReviews);

        CompletableFuture<Void> future = CompletableFuture.allOf(countries, genres, reviews);
        try {
            future.get(enrichTimeout, TimeUnit.SECONDS);
            logger.info("Parallel movie data enricher has worked");

        } catch (InterruptedException | ExecutionException e) {
            logger.error("Parallel movie data enricher failed during execution", e);

        } catch (TimeoutException e) {
            logger.warn("Parallel movie data enricher was unable to finish in {} seconds", enrichTimeout);
            throw new RuntimeException("Parallel movie data enricher was unable to finish", e);
        }
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @Autowired
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Autowired
    public void setThreadPoolExecutor(Executor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Value("${enrichMovie.timeOut}")
    public void setEnrichTimeout(int enrichTimeout) {
        this.enrichTimeout = enrichTimeout;
    }
}