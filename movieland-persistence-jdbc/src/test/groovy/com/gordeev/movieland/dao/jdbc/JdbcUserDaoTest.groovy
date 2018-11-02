package com.gordeev.movieland.dao.jdbc

import com.gordeev.movieland.dao.UserDao
import com.gordeev.movieland.entity.User
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-context.xml")
class JdbcUserDaoTest extends GroovyTestCase {
    List<User> expectedUsers

    @Before
    void setUp(){
        //List of reviews setUp
        expectedUsers = new ArrayList<>()
        User firstUser = new User()
        User secondUser = new User()
        expectedUsers.add(firstUser)
        expectedUsers.add(secondUser)

        //First User setUp
        firstUser.setId(1)
        firstUser.setEmail("ronald.reynolds66@example.com")
        firstUser.setNickname("Рональд Рейнольдс")
        firstUser.setPassword("paco")

        //Second User setUp
        secondUser.setId(2)
        secondUser.setEmail("darlene.edwards15@example.com")
        secondUser.setNickname("Дарлин Эдвардс")
        secondUser.setPassword("bricks")
    }

    @Autowired
    private UserDao userDao

    @Test
    void testGetUsersByIds() {
        List<User> actualUsers = userDao.getUsersByIds(new HashSet<Integer>(Arrays.asList(1, 2)))

        assertEquals(actualUsers.size(), expectedUsers.size())
        assertEquals(actualUsers[0].getId(), expectedUsers[0].getId())
        assertEquals(actualUsers[0].getNickname(), expectedUsers[0].getNickname())
        assertEquals(actualUsers[0].getPassword(), expectedUsers[0].getPassword())
        assertEquals(actualUsers[0].getEmail(), expectedUsers[0].getEmail())
        assertEquals(actualUsers[1].getEmail(), expectedUsers[1].getEmail())
    }

    @Test
    void testGetUserByEmail(){
        User actualUser = userDao.getUserByEmail("ronald.reynolds66@example.com")

        assertEquals(actualUser.getId(), expectedUsers[0].getId())
        assertEquals(actualUser.getNickname(), expectedUsers[0].getNickname())
        assertEquals(actualUser.getPassword(), expectedUsers[0].getPassword())
        assertEquals(actualUser.getEmail(), expectedUsers[0].getEmail())
    }
}
