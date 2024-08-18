package proj.auctionhousebackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import proj.auctionhousebackend.dto.transaction.TransactionRequestDTO;
import proj.auctionhousebackend.dto.transaction.TransactionResponseDTO;
import proj.auctionhousebackend.exception.ExceptionBody;
import proj.auctionhousebackend.service.transaction.TransactionService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/transaction/v1")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;


    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Save a new transaction")
    @ApiResponse(responseCode = "201", description = "Transaction created",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponseDTO.class))})
    @ApiResponse(responseCode = "400", description = "Bad request",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<TransactionResponseDTO> saveTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        return new ResponseEntity<>(
                transactionService.save(transactionRequestDTO),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/by-seller/{sellerEmail}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get transactions by seller email")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    @ApiResponse(responseCode = "404", description = "Seller email not found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<List<TransactionResponseDTO>> findBySellerEmail(@PathVariable("sellerEmail") String sellerEmail) {
        return new ResponseEntity<>(transactionService.findBySellerEmail(sellerEmail), HttpStatus.OK);
    }

    @GetMapping("/by-buyer/{buyerEmail}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get transactions by buyer email")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    @ApiResponse(responseCode = "404", description = "Email not found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<List<TransactionResponseDTO>> findByBuyerEmail(@PathVariable("buyerEmail") String buyerEmail) {
        return new ResponseEntity<>(transactionService.findByBuyerEmail(buyerEmail), HttpStatus.OK);
    }

    @GetMapping("/by-bid/{bidId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get transactions by bid ID")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    @ApiResponse(responseCode = "404", description = "Transaction not found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<List<TransactionResponseDTO>> findByBidId(@PathVariable("bidId") UUID bidId) {
        return new ResponseEntity<>(transactionService.findByProductId(bidId), HttpStatus.OK);
    }
}
