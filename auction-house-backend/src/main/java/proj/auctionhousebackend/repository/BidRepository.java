package proj.auctionhousebackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proj.auctionhousebackend.model.BidEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BidRepository extends JpaRepository<BidEntity, UUID> {

    List<BidEntity> findByProductId(UUID productId);

    List<BidEntity> findByBidderEmail(String bidderEmail);

   BidEntity findTopByProductIdOrderByBidAmountDesc(UUID productId);

    Page<BidEntity> findByProductId(UUID productId, Pageable pageable);

    Page<BidEntity> findByBidderEmail(String bidderEmail, Pageable pageable);


}
