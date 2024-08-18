package proj.auctionhousebackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import proj.auctionhousebackend.dto.bid.BidRequestDTO;
import proj.auctionhousebackend.dto.bid.BidResponseDTO;
import proj.auctionhousebackend.model.BidEntity;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BidMapper {
    BidResponseDTO bidEntityToBidResponseDTO(BidEntity bidEntity);

    List<BidResponseDTO> bidEntityListToBidResponseDTOList(List<BidEntity> bidEntityList);

    BidEntity bidRequestDTOToBidEntity(BidRequestDTO bidRequestDTO);
}
