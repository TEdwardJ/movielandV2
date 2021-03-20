package edu.ted.web.movieland.request;

import edu.ted.web.movieland.entity.User;
import lombok.*;

@Data
@RequiredArgsConstructor
public class AddReviewRequest {
    private final long movieId;
    private final String text;
    @Setter
    @Getter
    private User user;
}
