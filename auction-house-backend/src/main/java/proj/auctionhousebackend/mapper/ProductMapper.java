package proj.auctionhousebackend.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import proj.auctionhousebackend.dto.product.ProductRequestDTO;
import proj.auctionhousebackend.dto.product.ProductResponseDTO;
import proj.auctionhousebackend.model.ProductEntity;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface ProductMapper {

    ProductResponseDTO productEntityToProductResponseDTO(ProductEntity productEntity);

    List<ProductResponseDTO> productEntityListToProductResponseDTOList(List<ProductEntity> productEntityList);

    ProductEntity productRequestDTOToProductEntity(ProductRequestDTO productRequestDTO);
}
