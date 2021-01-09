package edu.ted.web.movieland.web;

import edu.ted.web.movieland.annotation.MovieRequestParameter;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;

public class MovieRequestResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(MovieRequestParameter.class);
    }

    @Override
    public MovieRequest resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        Map<String, String[]> parameterMap = nativeWebRequest.getParameterMap();
        return createMovieRequestWithSorting(parameterMap);
    }

    private MovieRequest createMovieRequestWithSorting(Map<String, String[]> parameterMap) {
        for (String key : parameterMap.keySet()) {
            OrderByColumn column = scanEnumForValue(OrderByColumn.class, key);
            if (!Objects.isNull(column)) {
                OrderDirection direction = scanEnumForValue(OrderDirection.class, parameterMap.get(key)[0]);
                if (column == OrderByColumn.RATING && direction == null) {
                    direction = OrderDirection.DESC;
                }
                return new MovieRequest(column, direction);
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

