package edu.ted.web.movieland.web;

import edu.ted.web.movieland.web.annotation.MovieRequestParameter;
import edu.ted.web.movieland.web.entity.OrderByColumn;
import edu.ted.web.movieland.web.entity.OrderDirection;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.*;

public class MovieRequestResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(MovieRequestParameter.class);
    }

    @Override
    public MovieRequest resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        return createMovieRequestWithSorting(nativeWebRequest.getParameterMap());
    }

    MovieRequest createMovieRequestWithSorting(Map<String, String[]> parameterMap) {
        return parameterMap.keySet()
                .stream()
                .filter(key -> getValueFromEnum(OrderByColumn.class, key) != null)
                .findFirst()
                .map(key -> new MovieRequest(getValueFromEnum(OrderByColumn.class, key), getValueFromEnum(OrderDirection.class, parameterMap.get(key)[0])))
                .orElse(new MovieRequest());
    }

    private <E extends Enum<E>> E getValueFromEnum(Class<E> enumList, String value) {
        try {
            return Enum.valueOf(enumList, value.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
}
