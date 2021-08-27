CREATE OR REPLACE VIEW movie.v_movie
 AS
 SELECT m.m_id,
    m.m_title AS m_russian_name,
    m.m_title_en AS m_native_name,
    m.m_price,
    m.m_release_year,
    m.m_description,
    m.m_rating
   FROM movie.movie m;

/*ALTER TABLE movie.v_movie
    OWNER TO postgres;*/

