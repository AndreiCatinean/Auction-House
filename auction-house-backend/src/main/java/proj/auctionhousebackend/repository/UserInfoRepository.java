package proj.auctionhousebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proj.auctionhousebackend.model.UserInfoEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, UUID> {

    Optional<UserInfoEntity> findByEmail(String email);
}