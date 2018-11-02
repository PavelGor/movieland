package com.gordeev.movieland.controller

import com.gordeev.movieland.entity.Country
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
class CountryControllerTest extends GroovyTestCase {
    private MockMvc mockMvc

    @Autowired
    CountryController countryController

    @Before
    void setup() {
        initMocks(this)
        this.mockMvc = MockMvcBuilders.standaloneSetup(countryController).build()
    }
    @Test
    void testGetAllGenre() {
        Country firstCountry = new Country(1, "Франция")
        Country secondCountry = new Country(2, "Италия")

        mockMvc.perform(get("/country"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$", hasSize(7)))
                .andExpect(jsonPath("\$[0].id", is(firstCountry.getId())))
                .andExpect(jsonPath("\$[0].name", is(firstCountry.getName())))
                .andExpect(jsonPath("\$[1].id", is(secondCountry.getId())))
                .andExpect(jsonPath("\$[1].name", is(secondCountry.getName())))
    }
}
