package edu.ted.web.movieland.annotation;


import edu.ted.web.movieland.configuration.FullSpringTestConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@SpringJUnitWebConfig(classes = {FullSpringTestConfiguration.class})
public @interface FullSpringMvcTest {
}
