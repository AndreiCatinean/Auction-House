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
import proj.auctionhousebackend.dto.PageRequestDTO;
import proj.auctionhousebackend.dto.bid.BidRequestDTO;
import proj.auctionhousebackend.dto.bid.BidResponseDTO;
import proj.auctionhousebackend.exception.ExceptionBody;
import proj.auctionhousebackend.service.bid.BidService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/bid/v1")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get bid by ID")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BidResponseDTO.class))})
    @ApiResponse(responseCode = "404", description = "Bid not found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<BidResponseDTO> findById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                bidService.findById(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Save a new bid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bid created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BidResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<BidResponseDTO> saveBid(@RequestBody BidRequestDTO bidRequestDTO) throws Exception {
        return new ResponseEntity<>(
                bidService.save(bidRequestDTO),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/by-product/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get bids by product ID")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    @ApiResponse(responseCode = "404", description = "Product not found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<List<BidResponseDTO>> findByProductId(@PathVariable("productId") UUID productId) {
        return new ResponseEntity<>(
                bidService.findByProductId(productId),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-bidder/{bidderEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get bids by bidder email")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    @ApiResponse(responseCode = "404", description = "Bidder not found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<List<BidResponseDTO>> findByBidderEmail(@PathVariable("bidderEmail") String bidderEmail) {
        return new ResponseEntity<>(
                bidService.findByBidderEmail(bidderEmail),
                HttpStatus.OK
        );
    }

    @GetMapping("/latest-by-product/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get latest bid by product ID")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BidResponseDTO.class))})
    @ApiResponse(responseCode = "404", description = "Product not found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<BidResponseDTO> findLatestBidByProductId(@PathVariable("productId") UUID productId) {
        return new ResponseEntity<>(
                bidService.findLatestBidByProductId(productId),
                HttpStatus.OK
        );
    }


    @GetMapping("/by-product-paged/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get bids by product ID with pagination")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CollectionResponseDTO.class))})
    @ApiResponse(responseCode = "404", description = "Product not found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<CollectionResponseDTO<BidResponseDTO>> findByProductIdPaged(
            @PathVariable("productId") UUID productId,
            @Valid PageRequestDTO page
    ) {
        return new ResponseEntity<>(
                bidService.findByProductIdPaged(productId, page),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-buyer-email-paged/{bidderEmail}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get bids by buyer email with pagination")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CollectionResponseDTO.class))})
    @ApiResponse(responseCode = "404", description = "Bidder not found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<CollectionResponseDTO<BidResponseDTO>> findByBuyerEmailPaged(
            @Valid PageRequestDTO page,
            @PathVariable String bidderEmail) {
        return new ResponseEntity<>(
                bidService.findByBidderEmailPaged(bidderEmail, page),
                HttpStatus.OK
        );
    }


}
