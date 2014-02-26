package nl.jworks.movie;

import com.google.common.collect.ComparisonChain;
import org.assertj.core.api.Condition;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


/**
 * @author Erik Pragt
 */
public class MovieDaoTest {

    private MovieDao movieDao = new MovieDao();

    @Test
    public void findThrillers() {
        List<Movie> thrillers = movieDao.findThrillers();

        assertThat(thrillers).extracting("title").hasSize(4).contains(
                "The Dark Knight", "Pulp Fiction", "Se7en", "Gunday"
        );
    }

    @Test
    public void findThrillersAndYear() {
        List<Movie> thrillers = movieDao.findThrillers();

        assertThat(thrillers)
                .extracting("title", "year")
                .contains(
                        tuple("The Dark Knight", 2008),
                        tuple("Pulp Fiction", 1994),
                        tuple("Se7en", 1995)
                );
    }

    @Test
    public void findBestDirectors() {
        List<String> directors = movieDao.findTop3Directors();

        assertThat(directors)
                .contains("Quentin Tarantino")
                .doesNotContain("Ali Abbas Zafar");
    }

    @Test
    public void filterNonEnglishResultsFrom2014AndUp() {
        List<Movie> thrillers = movieDao.findThrillers();

        assertThat(filter(thrillers).with("language").notEqualsTo(Language.ENGLISH).having(new Condition<Movie>() {
            @Override
            public boolean matches(Movie value) {
                return value.getYear() >= 2014;
            }
        }).get()).extracting("title").containsOnly("Gunday");
    }

    @Test
    public void yearComparator() {
        List<Movie> thrillers = movieDao.findMovies();

        Comparator<Movie> ratingComparator = new Comparator<Movie>() {
            @Override
            public int compare(Movie one, Movie other) {
                return ComparisonChain.start().compare(one.getRating(), other.getRating()).result();
            }
        };

        assertThat(thrillers.get(0)).usingComparator(ratingComparator).isEqualTo(thrillers.get(1));
    }

}
