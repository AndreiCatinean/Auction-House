package proj.auctionhousebackend.service.product;

import proj.auctionhousebackend.dto.CollectionResponseDTO;
import proj.auctionhousebackend.dto.PageRequestDTO;
import proj.auctionhousebackend.dto.product.ProductRequestDTO;
import proj.auctionhousebackend.dto.product.ProductResponseDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing products.
 * This includes operations like:
 * - Retrieving a product by its ID
 * - Retrieving all products
 * - Retrieving all products with a given category
 * - Saving a new product
 * - Updating the description of a product
 * - Updating the status of a product
 */
public interface ProductService {

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The details of the product if found, otherwise null.
     */
    ProductResponseDTO findById(UUID id);

    /**
     * Retrieves all products.
     *
     * @return A list of all products.
     */
    List<ProductResponseDTO> findAll();

    /**
     * Retrieves all products with pagination support.
     *
     * @param page The pagination details.
     * @return A paginated list of all products.
     */
    CollectionResponseDTO<ProductResponseDTO> findAllPaged(PageRequestDTO page);

    /**
     * Retrieves all products with a given category.
     *
     * @param categoryId The ID of the category for which products are to be retrieved.
     * @return A list of products associated with the specified category.
     */
    List<ProductResponseDTO> findAllByCategory(Integer categoryId);

    /**
     * Saves a new product.
     *
     * @param productRequestDTO The details of the product to be saved.
     * @return The details of the newly saved product.
     */
    ProductResponseDTO save(ProductRequestDTO productRequestDTO);

    /**
     * Updates the description of a product.
     *
     * @param id          The ID of the product.
     * @param description The new description of the product.
     * @return The details of the updated product.
     */
    ProductResponseDTO updateDescription(UUID id, String description);

    /**
     * Updates the status of a product.
     *
     * @param id     The ID of the product.
     * @param status The new status of the product.
     * @return The details of the updated product.
     */
    ProductResponseDTO updateStatus(UUID id, String status);

    List<ProductResponseDTO> findBySellerEmail(String sellerEmail);

    List<ProductResponseDTO> findClosingOffers();

}
