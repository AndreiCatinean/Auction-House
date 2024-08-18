package proj.auctionhousebackend.service.favoriteOffer;

import proj.auctionhousebackend.dto.CollectionResponseDTO;
import proj.auctionhousebackend.dto.FavoriteOfferRequestDTO;
import proj.auctionhousebackend.dto.FavoriteOfferResponseDTO;
import proj.auctionhousebackend.dto.PageRequestDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing favorite offers.
 * This includes operations like:
 * - Retrieving a favorite offer by its ID
 * - Retrieving all favorite offers
 * - Saving a new favorite offer
 * - Retrieving favorite offers by user ID
 * - Retrieving favorite offers by product ID
 * - Retrieving all favorite offers with pagination
 * - Retrieving favorite offers by user ID with pagination
 * - Deleting a favorite offer by its ID
 */
public interface FavoriteOfferService {



    /**
     * Retrieves all favorite offers.
     *
     * @return A list of all favorite offers.
     */
    List<FavoriteOfferResponseDTO> findAll();

    /**
     * Saves a new favorite offer.
     *
     * @param favoriteOfferDTO The details of the favorite offer to be saved.
     * @return The details of the newly saved favorite offer.
     */
    FavoriteOfferResponseDTO save(FavoriteOfferRequestDTO favoriteOfferDTO);

    /**
     * Retrieves favorite offers by user ID.
     *
     * @param userId The ID of the user whose favorite offers are to be retrieved.
     * @return A list of favorite offers associated with the specified user ID.
     */
    List<FavoriteOfferResponseDTO> findByUserId(String userId);

    /**
     * Retrieves favorite offers by product ID.
     *
     * @param productId The ID of the product whose favorite offers are to be retrieved.
     * @return A list of favorite offers associated with the specified product ID.
     */
    List<FavoriteOfferResponseDTO> findByProductId(UUID productId);

    /**
     * Retrieves all favorite offers with pagination.
     *
     * @param page The pagination details.
     * @return A paginated list of all favorite offers.
     */
    CollectionResponseDTO<FavoriteOfferResponseDTO> findAllPaged(PageRequestDTO page);

    /**
     * Retrieves favorite offers by user ID with pagination.
     *
     * @param userId The ID of the user whose favorite offers are to be retrieved.
     * @param page   The pagination details.
     * @return A paginated list of favorite offers associated with the specified user ID.
     */
    CollectionResponseDTO<FavoriteOfferResponseDTO> findByUserIdPaged(String userId, PageRequestDTO page);

    /**
     * Deletes a favorite offer by its ID.
     *
     * @param id The ID of the favorite offer to delete.
     */


    void deleteByProductIdAndEmail(UUID productId, String email);
}
