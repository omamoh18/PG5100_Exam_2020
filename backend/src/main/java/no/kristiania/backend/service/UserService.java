package no.kristiania.backend.service;

import no.kristiania.backend.entity.Review;
import no.kristiania.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //Checked
    public boolean createUser(String email, String name, String surname, String password, Boolean isAdmin) {
        String hashedPassword = passwordEncoder.encode(password);

        if (entityManager.find(User.class, email) != null) {
            return false;
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(hashedPassword);
        user.setEnabled(true);
        if (isAdmin) {
            user.setRoles(Collections.singleton("ROLE_ADMIN"));
        } else {
            user.setRoles(Collections.singleton("ROLE_USER"));
        }

        entityManager.persist(user);

        return true;
    }

    public User getUser(String email){
        return entityManager.find(User.class, email);
    }

    public List<User> getAllUsers(){
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    public String getUserFullName(String email){
        User user = entityManager.find(User.class, email);
        return user.getName() + " " + user.getSurname();
    }


    public void changeEnabled(String email){
        User user = entityManager.find(User.class, email);
        user.setEnabled(!user.getEnabled());
    }

    public List<Review> getUserReview(String email){
        return entityManager.find(User.class, email).getReviewList();
    }

}
