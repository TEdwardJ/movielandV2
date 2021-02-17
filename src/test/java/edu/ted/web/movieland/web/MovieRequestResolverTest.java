package edu.ted.web.movieland.web;

import edu.ted.web.movieland.common.OrderByColumn;
import edu.ted.web.movieland.common.OrderDirection;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MovieRequestResolverTest {

    private final MovieRequestResolver requestResolver = new MovieRequestResolver();

    @Test
    void givenRequestParameters_withRatingEqualsDesc_whenRequestCreated_thenCorrect() {
        Map<String, String[]> parametersMap = Map.of("rating",new String[]{"desc"});
        var movieRequest = requestResolver.createMovieRequestWithSorting(parametersMap);
        var sorting = movieRequest.getSorting();
        assertEquals(OrderByColumn.RATING, sorting.getOrderedColumn());
        assertEquals(OrderDirection.DESC, sorting.getOrderDirection());
    }

    @Test
    void givenRequestParameters_withRatingNoValue_whenRequestCreated_thenCorrect() {
        Map<String, String[]> parametersMap = Map.of("rating",new String[]{""});
        var movieRequest = requestResolver.createMovieRequestWithSorting(parametersMap);
        var sorting = movieRequest.getSorting();
        assertEquals(OrderByColumn.RATING, sorting.getOrderedColumn());
        assertEquals(OrderDirection.DESC, sorting.getOrderDirection());
    }

    @Test
    void givenRequestParameters_withPriceEqualsDesc_whenRequestCreated_thenCorrect() {
        Map<String, String[]> parametersMap = Map.of("price",new String[]{"desc"});
        var movieRequest = requestResolver.createMovieRequestWithSorting(parametersMap);
        var sorting = movieRequest.getSorting();
        assertEquals(OrderByColumn.PRICE, sorting.getOrderedColumn());
        assertEquals(OrderDirection.DESC, sorting.getOrderDirection());
    }

    @Test
    void givenRequestParameters_withPriceEqualsAscMixedCase_whenRequestCreated_thenCorrect() {
        Map<String, String[]> parametersMap = Map.of("prICe",new String[]{"aSc"});
        var movieRequest = requestResolver.createMovieRequestWithSorting(parametersMap);
        var sorting = movieRequest.getSorting();
        assertEquals(OrderByColumn.PRICE, sorting.getOrderedColumn());
        assertEquals(OrderDirection.ASC, sorting.getOrderDirection());
    }
}