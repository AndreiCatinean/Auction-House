package proj.auctionhousebackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import proj.auctionhousebackend.dto.FavoriteOfferRequestDTO;
import proj.auctionhousebackend.dto.FavoriteOfferResponseDTO;
import proj.auctionhousebackend.model.FavoriteOfferEntity;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FavoriteOfferMapper {

    FavoriteOfferResponseDTO favoriteOfferEntityToFavoriteOfferResponseDTO(FavoriteOfferEntity favoriteOfferEntity);

    List<FavoriteOfferResponseDTO> favoriteOfferEntityListToFavoriteOfferResponseDTOList(List<FavoriteOfferEntity> favoriteOfferEntityList);

    FavoriteOfferEntity favoriteOfferRequestDTOToFavoriteOfferEntity(FavoriteOfferRequestDTO favoriteOfferRequestDTO);
}
