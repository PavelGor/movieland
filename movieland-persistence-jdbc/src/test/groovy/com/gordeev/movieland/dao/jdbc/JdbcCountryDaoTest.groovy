package com.gordeev.movieland.dao.jdbc

import com.gordeev.movieland.dao.CountryDao
import com.gordeev.movieland.entity.Country
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-context.xml")
class JdbcCountryDaoTest extends GroovyTestCase {

    @Autowired
    private CountryDao countryDao

    @Test
    void testGetAll() {
        Country firstCountry = new Country()
        firstCountry.setId(1)
        firstCountry.setName("Франция")
        Country secondCountry = new Country()
        secondCountry.setId(2)
        secondCountry.setName("Италия")

        List<Country> genres = countryDao.getAll()

        assertTrue(!genres.isEmpty())
        assertEquals(firstCountry.getId(),genres.get(0).getId())
        assertEquals(secondCountry.getId(),genres.get(1).getId())

        assertEquals(firstCountry.getName(),genres.get(0).getName())
        assertEquals(secondCountry.getName(),genres.get(1).getName())
    }

}
