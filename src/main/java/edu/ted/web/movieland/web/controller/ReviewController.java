package edu.ted.web.movieland.web.controller;

import edu.ted.web.movieland.entity.Review;
import edu.ted.web.movieland.entity.UserToken;
import edu.ted.web.movieland.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> addReview(Review review, @RequestAttribute("movieLandUserToken") UserToken userToken){
        review.setUser(userToken.getUser());
        var addedReview = reviewService.addNewReview(review);
        return new ResponseEntity<>(OK);
    }
}
