package proj.auctionhousebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proj.auctionhousebackend.model.TransactionEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findBySellerEmail(String sellerEmail);

    List<TransactionEntity> findByBuyerEmail(String buyerEmail);

    List<TransactionEntity> findByProductId(UUID productId);
}
