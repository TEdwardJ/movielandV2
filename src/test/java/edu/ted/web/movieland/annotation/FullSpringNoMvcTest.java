package edu.ted.web.movieland.annotation;


import edu.ted.web.movieland.configuration.NoWebSpringTestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@SpringJUnitConfig(classes = {NoWebSpringTestConfiguration.class})
public @interface FullSpringNoMvcTest {
}
