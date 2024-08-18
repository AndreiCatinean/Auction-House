package proj.auctionhousebackend.dto.bid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BidRequestDTO {
    private UUID productId;
    private String bidderEmail;
    private BigDecimal bidAmount;
    private LocalDateTime bidTime;
}
