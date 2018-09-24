package com.gordeev.movieland.service.impl

import com.gordeev.movieland.entity.Country
import com.gordeev.movieland.service.CountryService
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
class DefaultCountryServiceTest {
    @Autowired
    private  CountryService countryService

    @Test
    void testGetAll() {
        Country firstCountry = new Country()
        firstCountry.setId(1)
        firstCountry.setName("Франция")
        Country secondCountry = new Country()
        secondCountry.setId(2)
        secondCountry.setName("Италия")

        List<Country> genres = countryService.getAll()

        assertTrue(!genres.isEmpty())
        assertEquals(firstCountry.getId(),genres.get(0).getId())
        assertEquals(secondCountry.getId(),genres.get(1).getId())

        assertEquals(firstCountry.getName(),genres.get(0).getName())
        assertEquals(secondCountry.getName(),genres.get(1).getName())
    }
}