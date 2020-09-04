package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.Review;
import no.kristiania.backend.entity.User;
import org.springframework.stereotype.Service;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 *
 * NOTE this is modified and taken from:
 * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/test/java/org/tsdes/intro/exercises/quizgame/backend/service/ResetService.java
 */

@Service
@Transactional
public class ResetService {

    @PersistenceContext
    private EntityManager entityManager;

    public void resetDatabase(){
        Query query = entityManager.createNativeQuery("delete from user_roles");
        query.executeUpdate();

        deleteEntities(Review.class);
        deleteEntities(Movie.class);
        deleteEntities(User.class);
    }

    private void deleteEntities(Class<?> entity){

        if(entity == null || entity.getAnnotation(Entity.class) == null){
            throw new IllegalArgumentException("Invalid non-entity class");
        }

        String name = entity.getSimpleName();

        Query query = entityManager.createQuery("delete from " + name);
        query.executeUpdate();
    }

}
