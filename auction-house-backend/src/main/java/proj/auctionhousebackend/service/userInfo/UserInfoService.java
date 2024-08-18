package proj.auctionhousebackend.service.userInfo;

import proj.auctionhousebackend.dto.CollectionResponseDTO;
import proj.auctionhousebackend.dto.PageRequestDTO;
import proj.auctionhousebackend.dto.userInfo.UserInfoResponseDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing user information.
 * This includes operations like:
 * - Retrieving user information by its ID
 * - Retrieving all user information
 * - Retrieving all user information with pagination
 * - Retrieving all user information sorted by a specific criteria
 * - Retrieving user information by email
 */
public interface UserInfoService {

    /**
     * Retrieves user information by its ID.
     *
     * @param id The ID of the user information to retrieve.
     * @return The details of the user information if found, otherwise null.
     */
    UserInfoResponseDTO findById(UUID id);

    /**
     * Retrieves all user information.
     *
     * @return A list of all user information.
     */
    List<UserInfoResponseDTO> findAll();

    /**
     * Retrieves all user information with pagination support.
     *
     * @param page The pagination details.
     * @return A paginated list of all user information.
     */
    CollectionResponseDTO<UserInfoResponseDTO> findAllPaged(PageRequestDTO page);

    /**
     * Retrieves all user information sorted by a specific criteria.
     *
     * @param sortBy The criteria by which to sort the user information.
     * @return A list of user information sorted by the specified criteria.
     */
    List<UserInfoResponseDTO> findAllSorted(String sortBy);

    /**
     * Retrieves user information by email.
     *
     * @param email The email address of the user.
     * @return The details of the user information associated with the specified email.
     */
    UserInfoResponseDTO findByEmail(String email);
}
