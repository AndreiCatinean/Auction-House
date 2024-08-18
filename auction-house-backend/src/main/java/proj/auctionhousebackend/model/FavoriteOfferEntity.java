package proj.auctionhousebackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "favorite_offer")
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FavoriteOfferId.class)
public class FavoriteOfferEntity {

    @Id
    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Id
    @Column(name = "product_id", nullable = false)
    private UUID productId;
}
