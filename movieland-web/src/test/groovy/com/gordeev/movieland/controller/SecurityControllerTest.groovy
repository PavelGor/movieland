package com.gordeev.movieland.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.gordeev.movieland.entity.User
import com.gordeev.movieland.service.impl.security.vo.UserVO
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

import static org.hamcrest.Matchers.is
import static org.mockito.MockitoAnnotations.initMocks
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-context.xml")
@WebAppConfiguration
class SecurityControllerTest extends GroovyTestCase {
    private MockMvc mockMvc
    private ObjectMapper mapper = new ObjectMapper()

    @Autowired
    SecurityController securityController

    @Before
    void setup() {
        initMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(securityController).build()
    }

    @Test
    void testLogin() {
        User user = new User()
        user.setEmail("ronald.reynolds66@example.com")
        user.setPassword("paco")
        String json = mapper.writeValueAsString(user)

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nickname", is("Рональд Рейнольдс")))

    }

    @Test
    void testLogout() {
        UserVO user = new UserVO("uuidTest","Рональд Рейнольдс")

        String json = mapper.writeValueAsString(user)

        mockMvc.perform(delete("/logout")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
    }

}
