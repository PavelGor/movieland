package com.gordeev.movieland.controller

import com.gordeev.movieland.entity.Movie
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import static org.hamcrest.Matchers.*
import static org.mockito.MockitoAnnotations.initMocks
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-context.xml")
@WebAppConfiguration
class MovieControllerTest extends GroovyTestCase {
    private List<Movie> expectedMovies

    private MockMvc mockMvc

    @Autowired
    private MovieController controller

    @Before
    void setup() {
        initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

        //List of movies setUp
        expectedMovies = new ArrayList<>()
        Movie firstMovie = new Movie()
        Movie secondMovie = new Movie()
        expectedMovies.add(firstMovie)
        expectedMovies.add(secondMovie)

        //First movie setUp
        firstMovie.setId(1)
        firstMovie.setNameRussian("Побег из Шоушенка")
        firstMovie.setNameNative("The Shawshank Redemption")
        firstMovie.setYearOfRelease(1994)
        firstMovie.setDescription("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.")
        firstMovie.setRating(8.9 as double)
        firstMovie.setPrice(123.45 as double)
        firstMovie.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")

        //Second movie setUp
        secondMovie.setId(6)
        secondMovie.setNameRussian("Начало")
        secondMovie.setNameNative("Inception")
        secondMovie.setYearOfRelease(2010)
        secondMovie.setDescription("Кобб — талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим. Редкие способности Кобба сделали его ценным игроком в привычном к предательству мире промышленного шпионажа, но они же превратили его в извечного беглеца и лишили всего, что он когда-либо любил.")
        secondMovie.setRating(8.6 as double)
        secondMovie.setPrice(130.0 as double)
        secondMovie.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1._SY209_CR0,0,140,209_.jpg")
    }

    @Test
    void testGetAllMovie() throws Exception {
        checkJson("/movie", 25)

    }

    @Test
    void testGetThreeRandomMovie() throws Exception {
        mockMvc.perform(get("/movie/random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$", hasSize(3)))

    }

    @Test
    void getMovieByGenre() throws Exception {
        checkJson("/movie/genre/1", 16)
    }

    private void checkJson(String link, int size) throws Exception {
        Movie expectedFirstMovie = expectedMovies.get(0)
        Movie expectedSecondMovie = expectedMovies.get(1)
        mockMvc.perform(get(link))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$", hasSize(size)))
                .andExpect(jsonPath("\$[0].id", is(expectedFirstMovie.getId())))
                .andExpect(jsonPath("\$[0].nameRussian", is(expectedFirstMovie.getNameRussian())))
                .andExpect(jsonPath("\$[0].nameNative", is(expectedFirstMovie.getNameNative())))
                .andExpect(jsonPath("\$[0].yearOfRelease", is(expectedFirstMovie.getYearOfRelease())))
                .andExpect(jsonPath("\$[0].description", is(expectedFirstMovie.getDescription())))
                .andExpect(jsonPath("\$[0].rating", is(expectedFirstMovie.getRating())))
                .andExpect(jsonPath("\$[0].price", is(expectedFirstMovie.getPrice())))
                .andExpect(jsonPath("\$[0].picturePath", is(expectedFirstMovie.getPicturePath())))
                .andExpect(jsonPath("\$[5].id", is(expectedSecondMovie.getId())))
                .andExpect(jsonPath("\$[5].nameRussian", is(expectedSecondMovie.getNameRussian())))
                .andExpect(jsonPath("\$[5].nameNative", is(expectedSecondMovie.getNameNative())))
                .andExpect(jsonPath("\$[5].yearOfRelease", is(expectedSecondMovie.getYearOfRelease())))
                .andExpect(jsonPath("\$[5].description", is(expectedSecondMovie.getDescription())))
                .andExpect(jsonPath("\$[5].rating", is(expectedSecondMovie.getRating())))
                .andExpect(jsonPath("\$[5].price", is(expectedSecondMovie.getPrice())))
                .andExpect(jsonPath("\$[5].picturePath", is(expectedSecondMovie.getPicturePath())))
    }

    @Test
    void getMovieById() {
        Movie expectedFirstMovie = expectedMovies.get(0)
        mockMvc.perform(get("/movie/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(expectedFirstMovie.getId())))
                .andExpect(jsonPath("nameRussian", is(expectedFirstMovie.getNameRussian())))
                .andExpect(jsonPath("nameNative", is(expectedFirstMovie.getNameNative())))
                .andExpect(jsonPath("yearOfRelease", is(expectedFirstMovie.getYearOfRelease())))
                .andExpect(jsonPath("description", is(expectedFirstMovie.getDescription())))
                .andExpect(jsonPath("rating", is(expectedFirstMovie.getRating())))
                .andExpect(jsonPath("price", is(expectedFirstMovie.getPrice())))
                .andExpect(jsonPath("picturePath", is(expectedFirstMovie.getPicturePath())))
                .andExpect(jsonPath("reviews[0].text", is("Гениальное кино! Смотришь и думаешь «Так не бывает!», но позже понимаешь, что только так и должно быть. Начинаешь заново осмысливать значение фразы, которую постоянно используешь в своей жизни, «Надежда умирает последней». Ведь если ты не надеешься, то все в твоей жизни гаснет, не остается смысла. Фильм наполнен бесконечным числом правильных афоризмов. Я уверена, что буду пересматривать его сотни раз.")))
                .andExpect(jsonPath("reviews", hasSize(2)))
    }
}
