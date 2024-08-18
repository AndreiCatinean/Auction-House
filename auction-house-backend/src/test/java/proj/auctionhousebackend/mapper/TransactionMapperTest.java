package proj.auctionhousebackend.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import proj.auctionhousebackend.dto.transaction.TransactionRequestDTO;
import proj.auctionhousebackend.dto.transaction.TransactionResponseDTO;
import proj.auctionhousebackend.model.TransactionEntity;

class TransactionMapperTest {

    private TransactionMapper underTest;

    private static final UUID id = UUID.fromString("00000000-0000-0000-0000-000000000000");
    LocalDateTime fixedDateTime = LocalDateTime.of(2024, 6, 1, 20, 4, 0);

    @BeforeEach
    void setUp() {
        underTest = Mappers.getMapper(TransactionMapper.class);
    }

    @Test
    void givenValidTransactionEntity_whenMapperCalled_thenReturnValidTransactionResponseDTO() {
        final var transactionEntity = getTransactionEntity();
        final var expected = getTransactionResponseDTO();

        final var response = underTest.transactionEntityToTransactionResponseDTO(transactionEntity);

        assertThat(response).isEqualTo(expected);
    }

    @Test
    void givenValidTransactionRequestDTO_whenMapperCalled_thenReturnValidTransactionEntity() {
        final var transactionRequestDTO = getTransactionRequestDTO();
        final var expected = getTransactionEntity();
        expected.setTransactionTime(null);

        final var response = underTest.transactionRequestDTOToTransactionEntity(transactionRequestDTO);

        assertThat(response.getProductId()).isEqualTo(expected.getProductId());
        assertThat(response.getBuyerEmail()).isEqualTo(expected.getBuyerEmail());
        assertThat(response.getSellerEmail()).isEqualTo(expected.getSellerEmail());
        assertThat(response.getAmount()).isEqualTo(expected.getAmount());
    }

    @Test
    void givenValidTransactionEntityList_whenMapperCalled_thenReturnValidTransactionResponseDTOList() {
        final var transactionEntities = List.of(getTransactionEntity());
        final var expected = List.of(getTransactionResponseDTO());

        final var response = underTest.transactionEntityListToTransactionResponseDTOList(transactionEntities);

        assertThat(response).isEqualTo(expected);
    }

    private TransactionEntity getTransactionEntity() {
        return TransactionEntity.builder()
                .productId(id)
                .buyerEmail("buyer@example.com")
                .sellerEmail("seller@example.com")
                .amount(BigDecimal.valueOf(100.0))
                .transactionTime(fixedDateTime)
                .build();
    }

    private TransactionResponseDTO getTransactionResponseDTO() {
        return TransactionResponseDTO.builder()
                .productId(id)
                .buyerEmail("buyer@example.com")
                .sellerEmail("seller@example.com")
                .amount(BigDecimal.valueOf(100.0))
                .transactionTime(fixedDateTime)
                .build();
    }

    private TransactionRequestDTO getTransactionRequestDTO() {
        return TransactionRequestDTO.builder()
                .productId(id)
                .buyerEmail("buyer@example.com")
                .sellerEmail("seller@example.com")
                .amount(BigDecimal.valueOf(100.0))
                .build();
    }
}
