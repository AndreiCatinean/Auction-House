package proj.auctionhousebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proj.auctionhousebackend.model.ProductEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findAllByCategoryId(Integer categoryId);

    List<ProductEntity> findBySellerEmail(String sellerEmail);

    List<ProductEntity> findByEndTimeBefore(LocalDateTime now);
}
