package edu.ted.web.movieland.annotation;


import edu.ted.web.movieland.configuration.FullSpringTestConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({SpringExtension.class})
@WebAppConfiguration("")
@ContextConfiguration(classes = {FullSpringTestConfiguration.class})
public @interface FullSpringMvcTest {
}
