package edu.ted.web.movieland.dao.jdbc;

import edu.ted.web.movieland.annotation.FullSpringNoMvcTest;
import edu.ted.web.movieland.dao.GenreDao;
import edu.ted.web.movieland.entity.Genre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@FullSpringNoMvcTest
class JpaGenreDaoTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EntityManagerFactory emFactory;

    @Autowired
    @Qualifier("jpaGenreDao")
    private GenreDao dao;

    @Test
    void getAllGenres() {
        var allGenres = dao.findAll();
        assertNotNull(allGenres);
        assertFalse(allGenres.isEmpty());
    }

    @Test
    void getAllGenresCacheChecking() {
        SessionFactory sessionFactory = emFactory.unwrap(SessionFactory.class);
        em.unwrap(Session.class).clear();
        Statistics statistics = sessionFactory.getStatistics();
        var secondLevelCache = sessionFactory.getCache();
        secondLevelCache.evictAll();
        secondLevelCache.evictDefaultQueryRegion();
        statistics.clear();
        assertEquals(0, statistics.getQueryCacheHitCount());
        assertEquals(0, statistics.getQueryCachePutCount());
        assertFalse(secondLevelCache.containsEntity(Genre.class, 63));
        getGenresTest();
        assertEquals(0, statistics.getQueryCacheHitCount());
        assertEquals(1, statistics.getQueryCachePutCount());
        assertTrue(secondLevelCache.containsEntity(Genre.class, 63));
        getGenresTest();
        assertEquals(1, statistics.getQueryCacheHitCount());
        assertEquals(1, statistics.getQueryCachePutCount());
        assertTrue(secondLevelCache.containsEntity(Genre.class, 63));
    }

    void getGenresTest(){
        var allGenres = dao.findAll();
        assertNotNull(allGenres);
        assertFalse(allGenres.isEmpty());

    }

    @Test
    void givenMovieId_whenListOfGenresIsReceived_thenCorrect() {
        var allGenres = dao.getGenreByMovieId(103);
        assertFalse(allGenres.isEmpty());
        assertTrue(allGenres.contains(new Genre(0, "фэнтези")));
        assertTrue(containsGenre(allGenres, "фэнтези"));
        assertTrue(containsGenre(allGenres, "драма"));
        for (Genre genre : allGenres) {
            assertFalse(genre.getName().isEmpty());
            assertNotEquals(0, genre.getId());
        }
    }

    private boolean containsGenre(List<Genre> genresList, String givenGenre) {
        return genresList.stream().anyMatch(genre -> genre.getName().equals(givenGenre));
    }
}