package proj.auctionhousebackend.dto.transaction;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO {

    private UUID productId;
    private String buyerEmail;
    private String sellerEmail;
    private BigDecimal amount;
    private LocalDateTime transactionTime;
}
