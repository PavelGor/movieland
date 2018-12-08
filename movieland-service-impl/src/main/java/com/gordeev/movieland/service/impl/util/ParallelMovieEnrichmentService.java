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

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

@Service
public class ParallelMovieEnrichmentService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private int enrichTimeout;
    private ReviewService reviewService;
    private GenreService genreService;
    private CountryService countryService;
    private ExecutorService executorService;

    public void enrich(List<Movie> movies) {

        Collection<Callable<Boolean>> tasks = new CopyOnWriteArrayList<>();

        try {
            Callable<Boolean> genre = () -> genreService.enrich(movies);
            Callable<Boolean> country = () -> countryService.enrich(movies);
            Callable<Boolean> review = () -> reviewService.enrich(movies);
            tasks.add(country);
            tasks.add(genre);
            tasks.add(review);
            List<Future<Boolean>> futures = executorService.invokeAll(tasks, enrichTimeout, TimeUnit.SECONDS);
            for (Future<Boolean> future : futures) {
                if (future.isCancelled()) {
                    logger.warn("Parallel movie data enricher was unable to finish in {} seconds", enrichTimeout);
                }
            }
        } catch (InterruptedException e) {
            logger.warn("Parallel movie data enricher failed during execution", e);
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
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Value("${enrichMovie.timeOut}")
    public void setEnrichTimeout(int enrichTimeout) {
        this.enrichTimeout = enrichTimeout;
    }
}