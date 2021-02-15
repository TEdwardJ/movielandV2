package edu.ted.web.movieland.web;

import edu.ted.web.movieland.common.OrderByColumn;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.*;

public class MovieRequestResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(MovieRequest.class);
    }

    @Override
    public MovieRequest resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        return createMovieRequestWithSorting(nativeWebRequest.getParameterMap());
    }

    MovieRequest createMovieRequestWithSorting(Map<String, String[]> parameterMap) {
        return parameterMap
                .entrySet()
                .stream()
                .filter(entry -> OrderByColumn.isValid(entry.getKey()))
                .map(entry -> new MovieRequest(entry.getKey(), entry.getValue()[0]))
                .findFirst()
                .orElse(new MovieRequest());
    }
}
