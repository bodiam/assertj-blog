package nl.jworks.movie;

import lombok.Value;

import java.util.List;

/**
 * @author Erik Pragt
 */

@Value
public class Movie {

    String title;
    int year;
    double rating;
    String director;
    List<Genre> genres;
    Language language;
}
