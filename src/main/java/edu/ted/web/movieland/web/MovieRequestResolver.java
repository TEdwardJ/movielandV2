package edu.ted.web.movieland.web;

import edu.ted.web.movieland.web.annotation.MovieRequestParameter;
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
        var parameterMap = nativeWebRequest.getParameterMap();
        return createMovieRequestWithSorting(parameterMap);
    }

    private MovieRequest createMovieRequestWithSorting(Map<String, String[]> parameterMap) {
        for (var key : parameterMap.keySet()) {
            var column = scanEnumForValue(OrderByColumn.class, key);
            if (!Objects.isNull(column)) {
                return new MovieRequest(column, scanEnumForValue(OrderDirection.class, parameterMap.get(key)[0]));
            }
        }
        return new MovieRequest();
    }

    private <E extends Enum<E>> E scanEnumForValue(Class<E> enumList, String value) {
        for (E e : EnumSet.allOf(enumList)) {
            if (e.toString().equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }
}

