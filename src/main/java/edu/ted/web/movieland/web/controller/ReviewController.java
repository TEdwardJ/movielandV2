package edu.ted.web.movieland.web.controller;

import edu.ted.web.movieland.entity.UserSession;
import edu.ted.web.movieland.request.AddReviewRequest;
import edu.ted.web.movieland.service.ReviewService;
import edu.ted.web.movieland.web.annotation.UserRequired;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

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

    @ExceptionHandler({ServletRequestBindingException.class})
    private void handle(HttpServletResponse res, Exception ex){
        res.setStatus(UNAUTHORIZED.value());
    }
}
