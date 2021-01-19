package edu.ted.web.movieland.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.ted.web.movieland.utils.MovieMapper;
import edu.ted.web.movieland.utils.MovieMapperImpl;
import edu.ted.web.movieland.web.MovieRequestResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"edu.ted.web.movieland.web.controller"})
public class WebMovieLandJavaConfiguration  implements WebMvcConfigurer {
    @Bean
    public MovieMapper getMovieMapper() {
        return new MovieMapperImpl();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(getConverter());
    }

    @Override
    public void addArgumentResolvers(
            List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MovieRequestResolver());
    }

    public MappingJackson2HttpMessageConverter getConverter(){
        return new MappingJackson2HttpMessageConverter(
                new Jackson2ObjectMapperBuilder()
                        .indentOutput(true)
                        .serializationInclusion(JsonInclude.Include.NON_NULL)
                        .build()
        );
    }
}
