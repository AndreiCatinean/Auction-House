package proj.auctionhousebackend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestTemplateException extends RuntimeException {

    private final String message;
}
