package com.gordeev.movieland.service.impl

import com.gordeev.movieland.entity.Genre
import com.gordeev.movieland.service.GenreService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

import static org.junit.Assert.*

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-context.xml")
@WebAppConfiguration
class DefaultGenreServiceTest {
    @Autowired
    private GenreService genreService

    @Test
    void testGetAll() {
        Genre firstGenre = new Genre()
        firstGenre.setId(1)
        firstGenre.setName("драма")
        Genre secondGenre = new Genre()
        secondGenre.setId(2)
        secondGenre.setName("криминал")

        List<Genre> genres = genreService.getAll()

        assertTrue(!genres.isEmpty())
        assertEquals(firstGenre.getId(),genres.get(0).getId())
        assertEquals(secondGenre.getId(),genres.get(1).getId())

        assertEquals(firstGenre.getName(),genres.get(0).getName())
        assertEquals(secondGenre.getName(),genres.get(1).getName())
    }
}