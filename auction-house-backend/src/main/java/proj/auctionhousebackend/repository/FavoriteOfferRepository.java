package proj.auctionhousebackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import proj.auctionhousebackend.model.FavoriteOfferEntity;
import proj.auctionhousebackend.model.FavoriteOfferId;

import java.util.List;
import java.util.UUID;

public interface FavoriteOfferRepository extends JpaRepository<FavoriteOfferEntity, FavoriteOfferId> {
    List<FavoriteOfferEntity> findByUserEmail(String userEmail);

    List<FavoriteOfferEntity> findByProductId(UUID productId);

    Page<FavoriteOfferEntity> findByUserEmail(String userId, Pageable pageable);



    void deleteByProductIdAndUserEmail(UUID productId, String email);
}
