package edu.ted.web.movieland.web.controller;

import edu.ted.web.movieland.entity.UserSession;
import edu.ted.web.movieland.request.AddReviewRequest;
import edu.ted.web.movieland.service.ReviewService;
import edu.ted.web.movieland.web.annotation.UserRequired;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {


    private final ReviewService reviewService;

    @UserRequired
    @PostMapping
    public ResponseEntity<?> addReview(AddReviewRequest review, @SessionAttribute(value = "edu.ted.web.movieland.movieLandUserToken") UserSession userSession) {
        review.setUser(userSession.getUser());
        var addedReview = reviewService.addNewReview(review);
        return new ResponseEntity<>(OK);
    }

}
