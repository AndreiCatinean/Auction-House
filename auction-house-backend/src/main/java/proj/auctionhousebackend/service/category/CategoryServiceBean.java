package proj.auctionhousebackend.service.category;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import proj.auctionhousebackend.dto.CollectionResponseDTO;
import proj.auctionhousebackend.dto.PageRequestDTO;
import proj.auctionhousebackend.dto.category.CategoryRequestDTO;
import proj.auctionhousebackend.dto.category.CategoryResponseDTO;
import proj.auctionhousebackend.exception.ExceptionCode;
import proj.auctionhousebackend.exception.NotFoundException;
import proj.auctionhousebackend.mapper.CategoryMapper;
import proj.auctionhousebackend.model.CategoryEntity;
import proj.auctionhousebackend.repository.CategoryRepository;

@Slf4j
@RequiredArgsConstructor
public class CategoryServiceBean implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDTO findById(Integer id) {
        log.info("Finding category by ID: {}", id);
        return categoryRepository.findById(id)
                .map(categoryMapper::categoryEntityToCategoryResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR004_CATEGORY_NOT_FOUND.getMessage(),
                        id
                )));
    }

    @Override
    public CollectionResponseDTO<CategoryResponseDTO> findAllPaged(PageRequestDTO page) {
        log.info("Finding all categories with pagination");
        Page<CategoryEntity> categoryEntityPage = categoryRepository.findAll(PageRequest.of(
                page.getPageNumber(),
                page.getPageSize()
        ));
        return new CollectionResponseDTO<>(
                categoryMapper.categoryEntityListToCategoryResponseDTOList(categoryEntityPage.getContent()),
                categoryEntityPage.getTotalElements()
        );
    }

    @Override
    public CategoryResponseDTO save(CategoryRequestDTO categoryRequestDTO) {
        log.info("Saving category: {}", categoryRequestDTO);
        CategoryEntity categoryEntity = categoryMapper.categoryRequestDTOToCategoryEntity(categoryRequestDTO);
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryMapper.categoryEntityToCategoryResponseDTO(categoryEntity);
    }
}
