package com.gordeev.movieland.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.gordeev.movieland.entity.Review
import com.gordeev.movieland.entity.User
import com.gordeev.movieland.service.SecurityService
import com.gordeev.movieland.vo.UserRole
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static org.mockito.MockitoAnnotations.initMocks
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-context.xml")
@WebAppConfiguration
class ReviewControllerTest extends GroovyTestCase {
    private MockMvc mockMvcReview
    private MockMvc mockMvcMovie
    private ObjectMapper mapper = new ObjectMapper()

    @Autowired
    ReviewController reviewController

    @Autowired
    MovieController movieController

    @Before
    void setup() {
        initMocks(this)
        this.mockMvcReview = MockMvcBuilders.standaloneSetup(reviewController).build()
        this.mockMvcMovie = MockMvcBuilders.standaloneSetup(movieController).build()
    }

    @Test
    void testAdd() {
        Review review = new Review()
        review.setMovieId(1)
        review.setText("Очень понравилось!")
        User user = new User()
        user.setId(1)
        user.setUserRole(UserRole.USER)
        review.setUser(user)
        String json = mapper.writeValueAsString(review)

        String uuid = "987654321"
        SecurityService securityService = mock(SecurityService.class)
        when(securityService.getUser(uuid)).thenReturn(user)

        this.mockMvcReview.perform(post("/review")
                .contentType(MediaType.APPLICATION_JSON).content(json))

        this.mockMvcMovie.perform(get("/movie/1"))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("reviews[2].text", is("Очень понравилось!")))
                .andExpect(jsonPath("reviews", hasSize(2)))

    }
}
