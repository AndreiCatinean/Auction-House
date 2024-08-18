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
import org.springframework.web.bind.annotation.*;
import proj.auctionhousebackend.dto.CollectionResponseDTO;
import proj.auctionhousebackend.dto.FavoriteOfferRequestDTO;
import proj.auctionhousebackend.dto.FavoriteOfferResponseDTO;
import proj.auctionhousebackend.dto.PageRequestDTO;
import proj.auctionhousebackend.exception.ExceptionBody;
import proj.auctionhousebackend.service.favoriteOffer.FavoriteOfferService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/favorite-offers")
@RequiredArgsConstructor
public class FavoriteOfferController {

    private final FavoriteOfferService favoriteOfferService;




    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all favorite offers")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<List<FavoriteOfferResponseDTO>> findAll() {
        return ResponseEntity.ok(favoriteOfferService.findAll());
    }

    @GetMapping("/user/{userEmail}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get favorite offers by user email")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    @ApiResponse(responseCode = "404", description = "User not found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<List<FavoriteOfferResponseDTO>> findByUserId(@PathVariable("userEmail") String userEmail) {
        return ResponseEntity.ok(favoriteOfferService.findByUserId(userEmail));
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Save favorite offer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Favorite offer saved",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = FavoriteOfferResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<FavoriteOfferResponseDTO> save(@RequestBody FavoriteOfferRequestDTO favoriteOfferRequestDTO) {
        FavoriteOfferResponseDTO responseDTO = favoriteOfferService.save(favoriteOfferRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Delete favorite offer by product ID and email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Favorite offer deleted"),
            @ApiResponse(responseCode = "404", description = "Favorite offer not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<Void> delete(@RequestParam("productId") UUID productId, @RequestParam("email") String email) {
        System.out.println("Aiciii");
        favoriteOfferService.deleteByProductIdAndEmail(productId, email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get favorite offers by product ID")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    @ApiResponse(responseCode = "404", description = "Product not found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})

    public ResponseEntity<List<FavoriteOfferResponseDTO>> findByProductId(@PathVariable("productId") UUID productId) {
        return ResponseEntity.ok(favoriteOfferService.findByProductId(productId));
    }


    @GetMapping("/all-paged")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all favorite offers with pagination")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CollectionResponseDTO.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})

    public ResponseEntity<CollectionResponseDTO<FavoriteOfferResponseDTO>> findAllPaged(@Valid PageRequestDTO page) {
        return ResponseEntity.ok(favoriteOfferService.findAllPaged(page));
    }

    @GetMapping("/user/{userId}/paged")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get favorite offers by user ID with pagination")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CollectionResponseDTO.class))})
    @ApiResponse(responseCode = "404", description = "User not found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})

    public ResponseEntity<CollectionResponseDTO<FavoriteOfferResponseDTO>> findByUserIdPaged(@PathVariable("userId") String userEmail,
                                                                                             @Valid PageRequestDTO page) {
        return ResponseEntity.ok(favoriteOfferService.findByUserIdPaged(userEmail, page));
    }
}
