package edu.ted.web.movieland.web.controller;

import edu.ted.web.movieland.entity.User;
import edu.ted.web.movieland.request.AddReviewRequest;
import edu.ted.web.movieland.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    //@UserRequired
    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody AddReviewRequest review) {
        var userFromSession = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
        review.setUser(userFromSession);
        var addedReview = reviewService.addNewReview(review);

        return ResponseEntity.ok().build();
    }

}
