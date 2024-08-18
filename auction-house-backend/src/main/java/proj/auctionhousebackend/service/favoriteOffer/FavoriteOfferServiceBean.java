package proj.auctionhousebackend.service.favoriteOffer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import proj.auctionhousebackend.dto.CollectionResponseDTO;
import proj.auctionhousebackend.dto.FavoriteOfferRequestDTO;
import proj.auctionhousebackend.dto.FavoriteOfferResponseDTO;
import proj.auctionhousebackend.dto.PageRequestDTO;
import proj.auctionhousebackend.exception.ExceptionCode;
import proj.auctionhousebackend.exception.NotFoundException;
import proj.auctionhousebackend.mapper.FavoriteOfferMapper;
import proj.auctionhousebackend.model.FavoriteOfferEntity;
import proj.auctionhousebackend.repository.FavoriteOfferRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class FavoriteOfferServiceBean implements FavoriteOfferService {

    private final FavoriteOfferRepository favoriteOfferRepository;
    private final FavoriteOfferMapper favoriteOfferMapper;

    @Override
    public List<FavoriteOfferResponseDTO> findByUserId(String userEmail) {
        log.info("Finding favorite offers by user ID: {}", userEmail);
        return favoriteOfferRepository.findByUserEmail(userEmail).stream()
                .map(favoriteOfferMapper::favoriteOfferEntityToFavoriteOfferResponseDTO)
                .collect(Collectors.toList());
    }



    @Override
    public List<FavoriteOfferResponseDTO> findAll() {
        log.info("Finding all favorite offers");
        return favoriteOfferRepository.findAll().stream()
                .map(favoriteOfferMapper::favoriteOfferEntityToFavoriteOfferResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FavoriteOfferResponseDTO save(FavoriteOfferRequestDTO favoriteOfferRequestDTO) {
        log.info("Saving favorite offer: {}", favoriteOfferRequestDTO);
        FavoriteOfferEntity favoriteOfferEntity = favoriteOfferMapper.favoriteOfferRequestDTOToFavoriteOfferEntity(favoriteOfferRequestDTO);
        favoriteOfferEntity = favoriteOfferRepository.save(favoriteOfferEntity);
        return favoriteOfferMapper.favoriteOfferEntityToFavoriteOfferResponseDTO(favoriteOfferEntity);
    }

    @Override
    public List<FavoriteOfferResponseDTO> findByProductId(UUID productId) {
        log.info("Finding favorite offers by product ID: {}", productId);
        List<FavoriteOfferEntity> favoriteOfferEntities = favoriteOfferRepository.findByProductId(productId);
        return favoriteOfferEntities.stream()
                .map(favoriteOfferMapper::favoriteOfferEntityToFavoriteOfferResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CollectionResponseDTO<FavoriteOfferResponseDTO> findAllPaged(PageRequestDTO page) {
        log.info("Finding all favorite offers with pagination");
        Page<FavoriteOfferEntity> favoriteOfferPage = favoriteOfferRepository.findAll(PageRequest.of(
                page.getPageNumber(),
                page.getPageSize()
        ));
        List<FavoriteOfferResponseDTO> favoriteOffers = favoriteOfferMapper.favoriteOfferEntityListToFavoriteOfferResponseDTOList(favoriteOfferPage.getContent());

        return new CollectionResponseDTO<>(favoriteOffers, favoriteOfferPage.getTotalElements());
    }

    @Override
    public CollectionResponseDTO<FavoriteOfferResponseDTO> findByUserIdPaged(String userId, PageRequestDTO page) {
        log.info("Finding favorite offers by user ID with pagination: {}", userId);
        Page<FavoriteOfferEntity> favoriteOfferPage = favoriteOfferRepository.findByUserEmail(userId, PageRequest.of(
                page.getPageNumber(),
                page.getPageSize()
        ));
        List<FavoriteOfferResponseDTO> favoriteOffers = favoriteOfferMapper.favoriteOfferEntityListToFavoriteOfferResponseDTOList(favoriteOfferPage.getContent());
        if (favoriteOffers.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionCode.ERR001_USER_NOT_FOUND.getMessage(), userId));
        }

        return new CollectionResponseDTO<>(favoriteOffers, favoriteOfferPage.getTotalElements());
    }

    @Override
    @Transactional
    public void deleteByProductIdAndEmail(UUID productId, String email) {
        log.info("Deleting favorite offer with Product ID: {} and Email: {}", productId, email);
        favoriteOfferRepository.deleteByProductIdAndUserEmail(productId, email);
    }

}
