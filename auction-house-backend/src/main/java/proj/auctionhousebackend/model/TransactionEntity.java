package proj.auctionhousebackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "buyer_email", nullable = false)
    private String buyerEmail;

    @Column(name = "seller_email", nullable = false)
    private String sellerEmail;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime transactionTime;
}
