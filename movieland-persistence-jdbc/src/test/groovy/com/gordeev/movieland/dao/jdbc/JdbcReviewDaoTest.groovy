package com.gordeev.movieland.dao.jdbc

import com.gordeev.movieland.dao.ReviewDao
import com.gordeev.movieland.entity.Review
import com.gordeev.movieland.entity.User
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/test-context.xml")
class JdbcReviewDaoTest extends GroovyTestCase {
    private List<Review> expectedReviews
    @Autowired
    private ReviewDao reviewDao

    @Before
    void setUp(){
        //List of reviews setUp
        expectedReviews = new ArrayList<>()
        Review firstReview = new Review()
        Review secondReview = new Review()
        expectedReviews.add(firstReview)
        expectedReviews.add(secondReview)

        //First review setUp
        firstReview.setId(1)
        firstReview.setMovieId(1)
        firstReview.setText("Гениальное кино! Смотришь и думаешь «Так не бывает!», но позже понимаешь, что только так и должно быть. Начинаешь заново осмысливать значение фразы, которую постоянно используешь в своей жизни, «Надежда умирает последней». Ведь если ты не надеешься, то все в твоей жизни гаснет, не остается смысла. Фильм наполнен бесконечным числом правильных афоризмов. Я уверена, что буду пересматривать его сотни раз.")
        User user = new User()
        user.setId(3)
        firstReview.setUser(user)

        //Second review setUp
        secondReview.setId(2)
        secondReview.setMovieId(1)
        secondReview.setText("Кино это является, безусловно, «со знаком качества». Что же до первого места в рейтинге, то, думаю, здесь имело место быть выставление «десяточек» от большинства зрителей вкупе с раздутыми восторженными откликами кинокритиков. Фильм атмосферный. Он драматичный. И, конечно, заслуживает того, чтобы находиться довольно высоко в мировом кинематографе.")
        User userTwo = new User()
        userTwo.setId(4)
        secondReview.setUser(userTwo)
    }
    @Test
    void testgetByMovieId() {
        List<Review> actualReviews = reviewDao.getByMovieId(1)

        assertEquals(actualReviews, expectedReviews)
    }
}
