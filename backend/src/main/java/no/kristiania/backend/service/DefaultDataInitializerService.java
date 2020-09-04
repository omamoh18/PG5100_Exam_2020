package no.kristiania.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.function.Supplier;

/**
 * NOTE this file is taken from:
 * // https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/main/java/org/tsdes/intro/exercises/quizgame/backend/service/DefaultDataInitializerService.java
 */

@Service
public class DefaultDataInitializerService {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ReviewService reviewService;


    @PostConstruct
    public void Init() {

        String email = "foo@bar.com";
        String email2 = "foo2@bar.com";
        String email3 = "foo3@bar.com";
        String adminEmail = "admin@admin.com";

        attempt(() -> userService.createUser(email, "foo1", "bar", "a", false));
        attempt(() -> userService.createUser(email2, "foo2", "bar", "a", false));
        attempt(() -> userService.createUser(email3, "foo3", "bar", "a", false));
        attempt(() -> userService.createUser(adminEmail, "admin", "admin", "a", true));


        Long movie1Id = movieService.createMovie("Your name", " Makoto Shinkai", 2016);
        Long movie2Id = movieService.createMovie("Avengers: Endgame", "Joe Russo", 2019);
        Long movie3Id = movieService.createMovie("Spider-Man", "Sam Raimi", 2002);
        Long movie4Id = movieService.createMovie("Star Wars: The Last Jedi", " Rian Johnson", 2017);
        Long movie5Id = movieService.createMovie("Jokrt", "Todd Phillips", 2019);
        Long movie6Id = movieService.createMovie("Parasite", "Bong Joon-ho", 2020);
        Long movie7Id = movieService.createMovie("Bad boys for life", "Adil El Arbi", 2020);
        Long movie8Id = movieService.createMovie("The Dark Knight", "Christopher Nolan", 2008);
        Long movie10Id = movieService.createMovie("Dunkirk", "Christopher Nolan", 2017);
        Long movieId = movieService.createMovie("Jumanji: The Next Level", "Jake Kasdan", 2019);
        Long movie11Id = movieService.createMovie("Sex and the City", "Darren Star", 1998);
        Long movie12Id = movieService.createMovie("Frozen 2", "Chris Buck", 2019);
        Long movie13Id = movieService.createMovie("Pokémon: The First Movie", "Kunihiko Yuyama", 2000);
        Long movie14Id= movieService.createMovie("Digimon: The Movie", "Yamauchi Shigeyasu", 2000);
        Long movie15Id= movieService.createMovie("Death Note", "Adam Wingard", 2017);
        Long movie16Id= movieService.createMovie("Weathering with You", "Makoto Shinkai", 2020);
        Long movie17Id= movieService.createMovie("One Piece: Stampede", "Takashi Otsuka", 2019);
        Long movie18Id= movieService.createMovie("The Last: Naruto the Movie", "Tsuneo Kobayashi", 2014);
        Long movie19Id= movieService.createMovie("Kal Ho Naa Ho", "Nikkhil Advani", 2003);
        Long movie20Id= movieService.createMovie("Snowden", "Oliver Stone", 2016);

        reviewService.createReview(email, movie1Id, " Marvelous movie!", 5, LocalDate.of(2020,12,22));
        reviewService.createReview(email2, movie1Id, "Worst movie", 1, LocalDate.of(2020,3,4));
        reviewService.createReview(email3, movie1Id, "Bad movie", 2, LocalDate.of(2020,2,10));
        reviewService.createReview(email2, movie2Id, "Good movie", 4, LocalDate.of(2020,1,15));
        reviewService.createReview(email, movie4Id, "Bad movie", 2, LocalDate.of(2020,7,1));
        reviewService.createReview(email3, movie3Id, "Ok movie", 3, LocalDate.of(2020,4,9));
        reviewService.createReview(email2, movie5Id, "Worst movie ever!", 1, LocalDate.of(2020,9,4));


    }

    private  <T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }

}
