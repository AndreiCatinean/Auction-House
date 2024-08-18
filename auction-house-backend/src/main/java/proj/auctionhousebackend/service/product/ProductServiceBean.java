package proj.auctionhousebackend.service.product;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import proj.auctionhousebackend.dto.CollectionResponseDTO;
import proj.auctionhousebackend.dto.PageRequestDTO;
import proj.auctionhousebackend.dto.product.ProductRequestDTO;
import proj.auctionhousebackend.dto.product.ProductResponseDTO;
import proj.auctionhousebackend.exception.ExceptionCode;
import proj.auctionhousebackend.exception.NotFoundException;
import proj.auctionhousebackend.mapper.ProductMapper;
import proj.auctionhousebackend.model.ProductEntity;
import proj.auctionhousebackend.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class ProductServiceBean implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final String applicationName;

    @Override
    public ProductResponseDTO findById(UUID id) {
        log.info("Finding product by ID: {}", id);
        return productRepository.findById(id)
                .map(productMapper::productEntityToProductResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR003_PRODUCT_NOT_FOUND.getMessage(),
                        id
                )));
    }

    @Override
    public List<ProductResponseDTO> findAll() {

        log.info("Getting all products for application {}", applicationName);

        List<ProductEntity> productEntityList = productRepository.findAll();

        return productMapper.productEntityListToProductResponseDTOList(productEntityList);
    }

    @Override
    public CollectionResponseDTO<ProductResponseDTO> findAllPaged(PageRequestDTO page) {

        log.info("Getting all products with pagination");
        Page<ProductEntity> productEntityPage = productRepository.findAll(PageRequest.of(
                page.getPageNumber(),
                page.getPageSize()
        ));
        List<ProductResponseDTO> products = productMapper.productEntityListToProductResponseDTOList(productEntityPage.getContent());

        return new CollectionResponseDTO<>(products, productEntityPage.getTotalElements());
    }

    @Override
    public List<ProductResponseDTO> findAllByCategory(Integer categoryId) {

        log.info("Finding all products by category ID: {}", categoryId);
        List<ProductEntity> productEntityList = productRepository.findAllByCategoryId(categoryId);
        if (productEntityList.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionCode.ERR004_CATEGORY_NOT_FOUND.getMessage(), categoryId));
        }

        return productMapper.productEntityListToProductResponseDTOList(productEntityList);
    }

    @Override
    public ProductResponseDTO save(ProductRequestDTO productRequestDTO) {
        log.info("Saving product: {}", productRequestDTO);
        ProductEntity productEntity = productMapper.productRequestDTOToProductEntity(productRequestDTO);
        productEntity.setStatus("active");
        productEntity.setStartTime(LocalDateTime.now());
        productEntity.setEndTime(LocalDateTime.now().plusDays(5));
        productEntity = productRepository.save(productEntity);
        return productMapper.productEntityToProductResponseDTO(productEntity);
    }

    @Override
    public ProductResponseDTO updateDescription(UUID id, String description) {
        log.info("Updating description for product with ID {}: {}", id, description);
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                ExceptionCode.ERR003_PRODUCT_NOT_FOUND.getMessage(),
                id)));
        productEntity.setDescription(description);
        productEntity = productRepository.save(productEntity);
        return productMapper.productEntityToProductResponseDTO(productEntity);
    }

    @Override
    public ProductResponseDTO updateStatus(UUID id, String status) {
        log.info("Updating status for product with ID {}: {}", id, status);
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                ExceptionCode.ERR003_PRODUCT_NOT_FOUND.getMessage(),
                id)));
        productEntity.setStatus(status);
        productEntity = productRepository.save(productEntity);
        return productMapper.productEntityToProductResponseDTO(productEntity);
    }

    public List<ProductResponseDTO> findBySellerEmail(String sellerEmail) {
        log.info("Getting all products   for  {}", sellerEmail);
        List<ProductEntity> productEntityList = productRepository.findBySellerEmail(sellerEmail);
        return productMapper.productEntityListToProductResponseDTOList(productEntityList);
    }

    @Override
    public List<ProductResponseDTO> findClosingOffers() {
        log.info("Getting all expired offers");
        LocalDateTime now = LocalDateTime.now();
        List<ProductEntity> productEntityList = productRepository.findByEndTimeBefore(now);
        return productMapper.productEntityListToProductResponseDTOList(productEntityList);
    }

}
