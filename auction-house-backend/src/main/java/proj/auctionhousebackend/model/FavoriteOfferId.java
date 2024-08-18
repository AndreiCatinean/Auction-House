package proj.auctionhousebackend.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class FavoriteOfferId implements Serializable {

    private String userEmail;
    private UUID productId;

    public FavoriteOfferId() {}

    public FavoriteOfferId(String userEmail, UUID productId) {
        this.userEmail = userEmail;
        this.productId = productId;
    }

    // Getters and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteOfferId that = (FavoriteOfferId) o;
        return Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, productId);
    }
}
