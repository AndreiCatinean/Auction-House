package proj.auctionhousebackend.service.bid;

import proj.auctionhousebackend.dto.CollectionResponseDTO;
import proj.auctionhousebackend.dto.PageRequestDTO;
import proj.auctionhousebackend.dto.bid.BidRequestDTO;
import proj.auctionhousebackend.dto.bid.BidResponseDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing bids.
 * This includes operations like:
 * - Retrieving a bid by its ID
 * - Retrieving bids for a given product ID with pagination
 * - Retrieving bids for a given bidder email with pagination
 * - Saving a new bid
 * - Retrieving all bids for a given product ID
 * - Retrieving all bids for a given bidder email
 * - Retrieving the latest bid for a given product ID
 */
public interface BidService {

    /**
     * Retrieves a bid by its unique ID.
     *
     * @param id The unique ID of the bid.
     * @return The details of the bid if found, otherwise null.
     */
    BidResponseDTO findById(UUID id);

    /**
     * Retrieves bids for a given product ID with pagination support.
     *
     * @param productId The ID of the product for which bids are to be retrieved.
     * @param page      The pagination details.
     * @return A paginated list of bids associated with the specified product ID.
     */
    CollectionResponseDTO<BidResponseDTO> findByProductIdPaged(UUID productId, PageRequestDTO page);

    /**
     * Retrieves bids for a given bidder email with pagination support.
     *
     * @param bidderEmail The email address of the bidder for which bids are to be retrieved.
     * @param page        The pagination details.
     * @return A paginated list of bids associated with the specified bidder email.
     */
    CollectionResponseDTO<BidResponseDTO> findByBidderEmailPaged(String bidderEmail, PageRequestDTO page);

    /**
     * Saves a new bid.
     *
     * @param bidRequestDTO The details of the bid to be saved.
     * @return The details of the newly saved bid.
     */
    BidResponseDTO save(BidRequestDTO bidRequestDTO) throws Exception;

    /**
     * Retrieves all bids associated with a given product ID.
     *
     * @param productId The ID of the product.
     * @return A list of bids associated with the specified product ID.
     */
    List<BidResponseDTO> findByProductId(UUID productId);

    /**
     * Retrieves all bids associated with a given bidder email.
     *
     * @param bidderEmail The email address of the bidder.
     * @return A list of bids associated with the specified bidder email.
     */
    List<BidResponseDTO> findByBidderEmail(String bidderEmail);

    /**
     * Retrieves the latest bid associated with a given product ID.
     *
     * @param productId The ID of the product.
     * @return The details of the latest bid associated with the specified product ID.
     */
    BidResponseDTO findLatestBidByProductId(UUID productId);
}
