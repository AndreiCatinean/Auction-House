package proj.auctionhousebackend.service.transaction;

import proj.auctionhousebackend.dto.CollectionResponseDTO;
import proj.auctionhousebackend.dto.PageRequestDTO;
import proj.auctionhousebackend.dto.transaction.TransactionRequestDTO;
import proj.auctionhousebackend.dto.transaction.TransactionResponseDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing transactions.
 * This includes operations like:
 * - Retrieving a transaction by its ID
 * - Retrieving all transactions with pagination
 * - Saving a new transaction
 * - Retrieving transactions by seller email
 * - Retrieving transactions by buyer email
 * - Retrieving transactions by bid ID
 */
public interface TransactionService {



    /**
     * Retrieves all transactions with pagination support.
     *
     * @param page The pagination details.
     * @return A paginated list of all transactions.
     */
    CollectionResponseDTO<TransactionResponseDTO> findAllPaged(PageRequestDTO page);

    /**
     * Saves a new transaction.
     *
     * @param transactionRequestDTO The details of the transaction to be saved.
     * @return The details of the newly saved transaction.
     */
    TransactionResponseDTO save(TransactionRequestDTO transactionRequestDTO);

    /**
     * Retrieves transactions by seller email.
     *
     * @param sellerEmail The email address of the seller.
     * @return A list of transactions associated with the specified seller email.
     */
    List<TransactionResponseDTO> findBySellerEmail(String sellerEmail);

    /**
     * Retrieves transactions by buyer email.
     *
     * @param buyerEmail The email address of the buyer.
     * @return A list of transactions associated with the specified buyer email.
     */
    List<TransactionResponseDTO> findByBuyerEmail(String buyerEmail);

    /**
     * Retrieves transactions by product ID.
     *
     * @param productId The ID of the product.
     * @return A list of transactions associated with the specified bid ID.
     */
    List<TransactionResponseDTO> findByProductId(UUID productId);
}
