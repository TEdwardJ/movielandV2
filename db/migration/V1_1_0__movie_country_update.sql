UPDATE movie.country
   SET cntr_name = TRIM(cntr_name)
 WHERE cntr_name <> TRIM(cntr_name);