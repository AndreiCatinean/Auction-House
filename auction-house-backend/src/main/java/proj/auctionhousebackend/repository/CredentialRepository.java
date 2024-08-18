package proj.auctionhousebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proj.auctionhousebackend.model.CredentialEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CredentialRepository extends JpaRepository<CredentialEntity, UUID> {

    Optional<CredentialEntity> findByEmail(String email);
}