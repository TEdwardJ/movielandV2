package edu.ted.web.movieland.dao.jpa;

import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class JpaGenreDao implements GenreDao {

    @PersistenceContext
    private EntityManager entityManager;
    private CriteriaQuery<Genre> findAllGenresQuery;

    @PostConstruct
    void init() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        findAllGenresQuery = criteriaBuilder.createQuery(Genre.class);
        Root<Genre> genreRoot = findAllGenresQuery.from(Genre.class);
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = getQuery(findAllGenresQuery)
                .setHint("org.hibernate.cacheable", true);
        return query.getResultList();
    }

    @Override
    public List<Genre> getGenreByMovieId(long id) {
        var movieQuery = entityManager.createNativeQuery("SELECT gnr_id, gnr_name FROM movie.v_all_movie_genres_ui as m WHERE m_id = :id", Genre.class)
                .setParameter("id", id)
                .setHint("org.hibernate.cacheable", true);

        return movieQuery.getResultList();
    }

    TypedQuery<Genre> getQuery(CriteriaQuery<Genre> criteriaQuery) {
        return entityManager
                .createQuery(criteriaQuery);
    }
}
