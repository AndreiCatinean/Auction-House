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
import proj.auctionhousebackend.dto.product.ProductRequestDTO;
import proj.auctionhousebackend.dto.product.ProductResponseDTO;
import proj.auctionhousebackend.exception.ExceptionBody;
import proj.auctionhousebackend.service.product.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/product/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Gets product by ID", description = "Product must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                productService.findById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return new ResponseEntity<>(
                productService.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/all-paged")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get all products with pagination")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CollectionResponseDTO.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<CollectionResponseDTO<ProductResponseDTO>> findAllPaged(@Valid PageRequestDTO page) {
        return new ResponseEntity<>(
                productService.findAllPaged(page),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-category/{categoryId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get all products by category ID")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    @ApiResponse(responseCode = "404", description = "Category not found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<List<ProductResponseDTO>> findAllByCategory(@PathVariable("categoryId") Integer categoryId) {
        return new ResponseEntity<>(
                productService.findAllByCategory(categoryId),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/save")
    @Operation(summary = "Save a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<ProductResponseDTO> saveProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        return new ResponseEntity<>(
                productService.save(productRequestDTO),
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/update-description")
    @Operation(summary = "Update product description by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product description updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<ProductResponseDTO> updateDescription(@PathVariable("id") UUID id, @RequestBody String description) {
        return new ResponseEntity<>(productService.updateDescription(id, description), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/close-status/{id}")
    @Operation(summary = "Update product status by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product status updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<ProductResponseDTO> updateStatus(@PathVariable("id") UUID id)  {
        System.out.println("haha");
        return new ResponseEntity<>(productService.updateStatus(id, "inactive"), HttpStatus.OK);
    }


    @GetMapping("/seller/{sellerEmail}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Gets products by seller_email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "404", description = "No products found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<List<ProductResponseDTO>> findBySellerEmail(@PathVariable("sellerEmail") String sellerEmail) {
        List<ProductResponseDTO> products = productService.findBySellerEmail(sellerEmail);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping("/closing-offers")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get closing offers", description = "Retrieve products that are nearing their end time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Closing offers found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<List<ProductResponseDTO>> findClosingOffers() {
        return new ResponseEntity<>(
                productService.findClosingOffers(),
                HttpStatus.OK
        );
    }


}
