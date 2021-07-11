package edu.ted.web.movieland.web.controller;

import edu.ted.web.movieland.security.SessionHandler;
import edu.ted.web.movieland.request.AddReviewRequest;
import edu.ted.web.movieland.service.ReviewService;
import edu.ted.web.movieland.web.annotation.UserRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @UserRequired
    @PostMapping
    public ResponseEntity<?> addReview(AddReviewRequest review) {
        var userFromSession = SessionHandler.getUserSession().getUser();
        review.setUser(userFromSession);
        var addedReview = reviewService.addNewReview(review);

        return ResponseEntity.ok().build();
    }

}
