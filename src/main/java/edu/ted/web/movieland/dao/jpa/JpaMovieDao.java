package edu.ted.web.movieland.dao.jpa;

import edu.ted.web.movieland.common.OrderDirection;
import edu.ted.web.movieland.common.Sorting;
import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.dao.jdbc.impl.JdbcMovieDao;
import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
@Primary
public class JpaMovieDao implements MovieDao {
    @Autowired
    private JdbcMovieDao movieDao;

    @PersistenceContext
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    @Value("${getMoviesByGenreQuery}")
    private String getMoviesByGenreQuery;
    private CriteriaQuery<Movie> findAllMoviesQuery;
    private Root<Movie> findAllMoviesRoot;
    private Query movieByGenreQuery;

    @PostConstruct
    void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        findAllMoviesQuery = criteriaBuilder.createQuery(Movie.class);
        findAllMoviesRoot = findAllMoviesQuery.from(Movie.class);
        movieByGenreQuery = entityManager.createNativeQuery(getMoviesByGenreQuery);
    }

    @Override
    public List<Movie> findAll(Sorting sorting) {

        addSortingOption(sorting, findAllMoviesRoot);

        TypedQuery<Movie> query = entityManager.createQuery(findAllMoviesQuery);

        return query.getResultList();
    }

    private void addSortingOption(Sorting sorting, Root<Movie> movieRoot) {
        if (sorting != null) {
            if (sorting.getOrderDirection() == OrderDirection.ASC) {
                findAllMoviesQuery.orderBy(criteriaBuilder.asc(movieRoot.get(sorting.getOrderedColumn().getName())));
            } else if (sorting.getOrderDirection() == OrderDirection.DESC) {
                findAllMoviesQuery.orderBy(criteriaBuilder.desc(movieRoot.get(sorting.getOrderedColumn().getName())));
            }
        }
    }

    @Override
    public List<Movie> getNRandomMovies(int count) {
        Random random = new Random();
        CriteriaQuery<Movie> movieQuery = criteriaBuilder.createQuery(Movie.class);

        Root<Movie> root = movieQuery.from(Movie.class);
        movieQuery
                .orderBy(criteriaBuilder.asc(criteriaBuilder.function("RANDOM", Double.class, new Expression[]{})));

        TypedQuery<Movie> query = entityManager
                .createQuery(movieQuery)
                .setMaxResults(count);

        List<Movie> movieList = query.getResultList();
        return movieList;
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId, Sorting sorting) {
        entityManager.createQuery("SELECT m from movie m");
                //.setParameter("genre", genreId);
        List<Movie> res =  movieByGenreQuery.getResultList();
        return res;
/*        movieByGenreQuery.setParameter(1, genreId);
        return movieByGenreQuery.getResultList();*/
    }

    @Override
    public Optional<Movie> getMovieById(long movieId) {
        return Optional.ofNullable(entityManager.find(Movie.class, movieId));
    }
}
