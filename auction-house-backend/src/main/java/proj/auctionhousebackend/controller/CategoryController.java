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
import proj.auctionhousebackend.dto.category.CategoryRequestDTO;
import proj.auctionhousebackend.dto.category.CategoryResponseDTO;
import proj.auctionhousebackend.exception.ExceptionBody;
import proj.auctionhousebackend.service.category.CategoryService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/category/v1")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Gets category by ID", description = "Category must exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(
                categoryService.findById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/all-paged")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Get all categories with pagination")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CollectionResponseDTO.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    public ResponseEntity<CollectionResponseDTO<CategoryResponseDTO>> findAllPaged(@Valid PageRequestDTO page) {
        return new ResponseEntity<>(
                categoryService.findAllPaged(page),
                HttpStatus.OK
        );
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Save a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionBody.class))})
    })
    public ResponseEntity<CategoryResponseDTO> saveCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return new ResponseEntity<>(
                categoryService.save(categoryRequestDTO),
                HttpStatus.CREATED
        );
    }
}
