package edu.ted.web.movieland.dao.jpa;

import edu.ted.web.movieland.dao.UserDao;
import edu.ted.web.movieland.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@Slf4j
public class JpaUserDao implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findUserByEmail(String email) {
        var userQuery = entityManager.createQuery("SELECT u\n" +
                "                         FROM User u LEFT JOIN FETCH u.roles \n" +
                "                        WHERE u.email = :email", User.class)
                .setParameter("email", email);
        try {
            var singleUser = userQuery.getSingleResult();
            return Optional.ofNullable(singleUser);
        } catch (Exception e) {
            log.debug("Error while finding user with email {}", email, e);
            return Optional.empty();
        }
    }

    @Override
    public boolean isPasswordValid(String email, String encryptedPassword) {
        var checkQuery = entityManager.createNativeQuery("SELECT COUNT(*) \n" +
                "        FROM movie.user mu \n" +
                "        WHERE mu.usr_email = :email \n" +
                "        AND usr_password_enc = :enc_password")
                .setParameter("email", email)
                .setParameter("enc_password", encryptedPassword);

        return Integer.valueOf(1).equals(checkQuery.getSingleResult());
    }


}


