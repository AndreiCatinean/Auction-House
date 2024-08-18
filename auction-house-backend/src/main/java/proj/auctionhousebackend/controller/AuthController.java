package proj.auctionhousebackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import proj.auctionhousebackend.dto.userInfo.UserInfoResponseDTO;
import proj.auctionhousebackend.exception.ExceptionBody;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<Void> login() {
        log.info("Login request detected...");
        return ResponseEntity.ok().build();
    }




}
