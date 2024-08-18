package proj.auctionhousebackend.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    private String sellerEmail;
    private String title;
    private String description;
    private BigDecimal startingPrice;
    private String imageUrl;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer categoryId;
}
