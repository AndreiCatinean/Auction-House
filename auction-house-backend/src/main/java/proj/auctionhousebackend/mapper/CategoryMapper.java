package proj.auctionhousebackend.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import proj.auctionhousebackend.dto.category.CategoryRequestDTO;
import proj.auctionhousebackend.dto.category.CategoryResponseDTO;
import proj.auctionhousebackend.model.CategoryEntity;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryResponseDTO categoryEntityToCategoryResponseDTO(CategoryEntity categoryEntity);

    List<CategoryResponseDTO> categoryEntityListToCategoryResponseDTOList(List<CategoryEntity> categoryEntityList);

    CategoryEntity categoryRequestDTOToCategoryEntity(CategoryRequestDTO categoryRequestDTO);
}
