package nl.jworks.movie;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import javafx.animation.Animation;

import java.util.List;

import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;
import static nl.jworks.movie.Genre.*;
import static nl.jworks.movie.Language.ENGLISH;
import static nl.jworks.movie.Language.HINDI;

/**
 * @author Erik Pragt
 */
public class MovieDao {

    public static final int HIGH_RATING = 8;

    private static List<Movie> movies = asList(
            new Movie("The Dark Knight", 2008, 9.0, "Christopher Nolan", asList(THRILLER, CRIME, DRAMA, ACTION), ENGLISH),
            new Movie("Pulp Fiction", 1994, 9.0, "Quentin Tarantino", asList(THRILLER, DRAMA, CRIME), ENGLISH),
            new Movie("Se7en", 1995, 8.7, "David Fincher ", asList(THRILLER, CRIME, MYSTERY), ENGLISH),
            new Movie("Gunday", 2014, 1.2, "Ali Abbas Zafar", asList(ACTION, CRIME, DRAMA, ROMANCE, THRILLER), HINDI),
            new Movie("Toy Story 3", 2010, 8.4, "Lee Unkrich", asList(ANIMATION, ADVENTURE, COMEDY, FAMILY, FANTASY), ENGLISH)
    );

    public List<Movie> findMovies() {
        return ImmutableList.copyOf(movies);
    }

    public List<Movie> findThrillers() {
        return from(movies).filter(thrillerFilter()).toList();
    }

    private Predicate<Movie> thrillerFilter() {
        return new Predicate<Movie>() {
            @Override
            public boolean apply(Movie movie) {
                return movie.getGenres().contains(THRILLER);
            }
        };
    }

    public List<String> findTop3Directors() {
        return from(movies)
                .filter(higlyRated())
                .transform(new Function<Movie, String>() {
                    @Override
                    public String apply(Movie input) {
                        return input.getDirector();
                    }
                })
                .limit(3)
                .toList();
    }

    private Predicate<? super Movie> higlyRated() {
        return new Predicate<Movie>() {
            @Override
            public boolean apply(Movie movie) {
                return movie.getRating() >= HIGH_RATING;
            }
        };
    }
}
