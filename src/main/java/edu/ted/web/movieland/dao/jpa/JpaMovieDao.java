package edu.ted.web.movieland.dao.jpa;

import edu.ted.web.movieland.common.OrderDirection;
import edu.ted.web.movieland.common.Sorting;
import edu.ted.web.movieland.dao.MovieDao;
import edu.ted.web.movieland.entity.Genre;
import edu.ted.web.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

import static edu.ted.web.movieland.common.OrderDirection.ASC;
import static edu.ted.web.movieland.common.OrderDirection.DESC;

@Repository
public class JpaMovieDao implements MovieDao {

    @PersistenceContext
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    private CriteriaQuery<Movie> randomMovieQuery;
    private ParameterExpression<Integer> genreIdParameter;

    @Autowired
    private JpaMovieDaoHelper jpaMovieDaoHelper;

    @PostConstruct
    void init() {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        genreIdParameter = criteriaBuilder.parameter(Integer.class);

        randomMovieQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> randomMovieQueryRoot = randomMovieQuery.from(Movie.class);
        jpaMovieDaoHelper.setMultiSelectFields(randomMovieQuery, randomMovieQueryRoot);
        randomMovieQuery.orderBy(criteriaBuilder.asc(criteriaBuilder.function("RANDOM", Double.class)));
    }

    @Override
    public List<Movie> findAll(Sorting sorting) {
        return getQuery(jpaMovieDaoHelper.createFindAllMoviesQuery(sorting))
                .getResultList();
    }

    @Override
    public List<Movie> getNRandomMovies(int count) {
        return getQuery(randomMovieQuery)
                .setMaxResults(count)
                .getResultList();
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId, Sorting sorting) {
        return getQuery(jpaMovieDaoHelper.createMoviesByGenreQuery(sorting))
                .setParameter(genreIdParameter, genreId)
                .getResultList();
    }

    @Override
    public Optional<Movie> getMovieById(long movieId) {
        return Optional.ofNullable(entityManager.find(Movie.class, movieId));
    }

    TypedQuery<Movie> getQuery(CriteriaQuery<Movie> movieQuery) {
        return entityManager.createQuery(movieQuery);
    }

    @Component
    protected class JpaMovieDaoHelper {

        @Cacheable(cacheNames = "findMoviesByGenreQueriesCache")
        public CriteriaQuery<Movie> createMoviesByGenreQuery(Sorting sorting) {
            CriteriaQuery<Movie> findMoviesByGenreQuery = criteriaBuilder.createQuery(Movie.class);
            Root<Movie> findMoviesByGenreRoot = findMoviesByGenreQuery.from(Movie.class);
            Join<Movie, Genre> genresJoin = findMoviesByGenreRoot.join("genres");
            setMultiSelectFields(findMoviesByGenreQuery, findMoviesByGenreRoot);
            addSortingOption(findMoviesByGenreQuery, sorting, findMoviesByGenreRoot);
            return findMoviesByGenreQuery.where(criteriaBuilder.equal(genresJoin.get("id"), genreIdParameter));
        }

        @Cacheable(cacheNames = "findAllMoviesQueriesCache")
        public CriteriaQuery<Movie> createFindAllMoviesQuery(Sorting sorting) {
            CriteriaQuery<Movie> findAllMoviesQuery = criteriaBuilder.createQuery(Movie.class);
            Root<Movie> findAllMoviesRoot = findAllMoviesQuery.from(Movie.class);
            setMultiSelectFields(findAllMoviesQuery, findAllMoviesRoot);
            return addSortingOption(findAllMoviesQuery, sorting, findAllMoviesRoot);
        }

        void setMultiSelectFields(CriteriaQuery<Movie> movieQuery, Root<Movie> movieRoot) {
            movieQuery.select(criteriaBuilder.construct(Movie.class,
                    movieRoot.get("id"),
                    movieRoot.get("russianName"),
                    movieRoot.get("nativeName"),
                    movieRoot.get("description"),
                    movieRoot.get("releaseYear"),
                    movieRoot.get("rating"),
                    movieRoot.get("price"),
                    movieRoot.get("pictureUrl")));
        }

        CriteriaQuery<Movie> addSortingOption(CriteriaQuery<Movie> movieQuery, Sorting sorting, Root<Movie> movieRoot) {
            var orderedColumn = sorting.getOrderedColumn();
            if (orderedColumn == null) {
                return movieQuery;
            }
            OrderDirection orderDirection = sorting.getOrderDirection();
            Path<Object> sortingColumn = movieRoot.get(orderedColumn.getName());
            if (orderDirection == ASC) {
                movieQuery.orderBy(criteriaBuilder.asc(sortingColumn));
            }
            if (orderDirection == DESC) {
                movieQuery.orderBy(criteriaBuilder.desc(sortingColumn));
            }
            return movieQuery;
        }

    }

}
