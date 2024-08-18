package proj.auctionhousebackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import proj.auctionhousebackend.dto.CollectionResponseDTO;
import proj.auctionhousebackend.dto.PageRequestDTO;
import proj.auctionhousebackend.dto.userInfo.UserInfoResponseDTO;
import proj.auctionhousebackend.exception.ExceptionBody;
import proj.auctionhousebackend.service.userInfo.UserInfoService;

import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/userinfo/v1")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Gets userInfo by ID", description = "User must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<UserInfoResponseDTO> findById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                userInfoService.findById(id),
                HttpStatus.OK
        );
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    @Operation(summary = "Get all user information")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<List<UserInfoResponseDTO>> findAll() {
        return new ResponseEntity<>(
                userInfoService.findAll(),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-paged")
    @Operation(summary = "Get all user information with pagination")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CollectionResponseDTO.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<CollectionResponseDTO<UserInfoResponseDTO>> findAllPaged(@Valid PageRequestDTO page) {
        return new ResponseEntity<>(
                userInfoService.findAllPaged(page),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all-sorted")
    @Operation(summary = "Get all user information sorted")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<List<UserInfoResponseDTO>> findAllSorted(
            @RequestParam(value = "sortBy", defaultValue = "", required = false) String sortBy
    ) {
        return new ResponseEntity<>(
                userInfoService.findAllSorted(sortBy),
                HttpStatus.OK
        );
    }

    @GetMapping("/info")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get logged user information")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoResponseDTO.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<UserInfoResponseDTO> getLoggedUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();

        return new ResponseEntity<>(
                userInfoService.findByEmail(email),
                HttpStatus.OK
        );
    }


}