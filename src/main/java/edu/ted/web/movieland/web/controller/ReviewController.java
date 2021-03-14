package edu.ted.web.movieland.web.controller;

import edu.ted.web.movieland.entity.Review;
import edu.ted.web.movieland.entity.UserToken;
import edu.ted.web.movieland.service.ReviewService;
import edu.ted.web.movieland.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class ReviewController {

    private final SecurityService securityService;
    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<?> addReview(Review review, @RequestParam(required = false) String uuid){
        Optional<UserToken> userToken;
        if (uuid == null || (userToken = securityService.findUserToken(uuid)).isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        review.setUser(userToken.get().getUser());
        var addedReview = reviewService.addNewReview(review);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
