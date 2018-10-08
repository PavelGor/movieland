package com.gordeev.movieland.controller

import com.gordeev.movieland.entity.Genre
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.mockito.MockitoAnnotations.initMocks
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-context.xml")
@WebAppConfiguration
class GenreControllerTest extends GroovyTestCase {
    private MockMvc mockMvc

    @Autowired
    private GenreController controller

    @Before
    void setup() {
        initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Test
    void testGetAllGenre() {
        Genre firstGenre = new Genre()
        firstGenre.setId(1)
        firstGenre.setName("драма")
        Genre secondGenre = new Genre()
        secondGenre.setId(2)
        secondGenre.setName("криминал")

        mockMvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$", hasSize(15)))
                .andExpect(jsonPath("\$[0].id", is(firstGenre.getId())))
                .andExpect(jsonPath("\$[0].name", is(firstGenre.getName())))
                .andExpect(jsonPath("\$[1].id", is(secondGenre.getId())))
                .andExpect(jsonPath("\$[1].name", is(secondGenre.getName())))
    }
}
