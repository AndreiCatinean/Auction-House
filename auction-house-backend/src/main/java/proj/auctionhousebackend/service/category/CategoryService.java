package proj.auctionhousebackend.service.category;

import proj.auctionhousebackend.dto.CollectionResponseDTO;
import proj.auctionhousebackend.dto.PageRequestDTO;
import proj.auctionhousebackend.dto.category.CategoryRequestDTO;
import proj.auctionhousebackend.dto.category.CategoryResponseDTO;

/**
 * Service interface for managing categories.
 * This includes operations like:
 * - Finding a category by its ID
 * - Retrieving all categories with pagination
 * - Saving a new category
 */
public interface CategoryService {

    /**
     * Finds a category by its unique ID.
     *
     * @param id The unique ID of the category.
     * @return The category details if found, otherwise null.
     */
    CategoryResponseDTO findById(Integer id);

    /**
     * Retrieves all categories with pagination support.
     *
     * @param page The pagination details.
     * @return A list of categories based on the pagination criteria.
     */
    CollectionResponseDTO<CategoryResponseDTO> findAllPaged(PageRequestDTO page);

    /**
     * Saves a new category.
     *
     * @param categoryRequestDTO The details of the category to be saved.
     * @return The details of the newly saved category.
     */
    CategoryResponseDTO save(CategoryRequestDTO categoryRequestDTO);
}
