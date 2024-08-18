package proj.auctionhousebackend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import proj.auctionhousebackend.dto.transaction.TransactionRequestDTO;
import proj.auctionhousebackend.dto.transaction.TransactionResponseDTO;
import proj.auctionhousebackend.exception.NotFoundException;
import proj.auctionhousebackend.mapper.TransactionMapper;
import proj.auctionhousebackend.model.TransactionEntity;
import proj.auctionhousebackend.repository.TransactionRepository;
import proj.auctionhousebackend.service.transaction.TransactionServiceBean;

class TransactionServiceBeanTest {

    private static final UUID id = UUID.fromString("00000000-0000-0000-0000-000000000000");
    LocalDateTime fixedDateTime = LocalDateTime.of(2024, 6, 1, 20, 4, 0);


    @InjectMocks
    private TransactionServiceBean underTest;

    @Mock
    private TransactionRepository transactionRepositoryMock;

    @Mock
    private TransactionMapper transactionMapperMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.underTest = new TransactionServiceBean(
                transactionRepositoryMock,
                transactionMapperMock
        );
    }

    @Test
    void givenValidTransactionRequestDTO_whenSaveIsCalled_thenReturnTransactionResponseDTO() {
        final var transactionRequestDTO = getTransactionRequestDTO();
        final var transactionEntity = getTransactionEntity();
        final var transactionResponseDTO = getTransactionResponseDTO();

        when(transactionMapperMock.transactionRequestDTOToTransactionEntity(any(TransactionRequestDTO.class)))
                .thenReturn(transactionEntity);
        when(transactionRepositoryMock.save(any(TransactionEntity.class)))
                .thenReturn(transactionEntity);
        when(transactionMapperMock.transactionEntityToTransactionResponseDTO(any(TransactionEntity.class)))
                .thenReturn(transactionResponseDTO);

        final var response = underTest.save(transactionRequestDTO);

        assertThat(response).isEqualTo(transactionResponseDTO);
        verify(transactionRepositoryMock).save(any(TransactionEntity.class));
        verify(transactionMapperMock).transactionRequestDTOToTransactionEntity(any(TransactionRequestDTO.class));
        verify(transactionMapperMock).transactionEntityToTransactionResponseDTO(any(TransactionEntity.class));
    }

    @Test
    void givenValidSellerEmail_whenFindBySellerEmailIsCalled_thenReturnTransactionResponseDTOList() {
        final var sellerEmail = "seller@example.com";
        final var transactionEntities = List.of(getTransactionEntity());
        final var transactionResponseDTOs = List.of(getTransactionResponseDTO());

        when(transactionRepositoryMock.findBySellerEmail(sellerEmail))
                .thenReturn(transactionEntities);
        when(transactionMapperMock.transactionEntityListToTransactionResponseDTOList(any(List.class)))
                .thenReturn(transactionResponseDTOs);

        final var response = underTest.findBySellerEmail(sellerEmail);

        assertThat(response).isEqualTo(transactionResponseDTOs);
        verify(transactionRepositoryMock).findBySellerEmail(sellerEmail);
        verify(transactionMapperMock).transactionEntityListToTransactionResponseDTOList(any(List.class));
    }

    @Test
    void givenInvalidSellerEmail_whenFindBySellerEmailIsCalled_thenThrowNotFoundException() {
        final var sellerEmail = "invalid@example.com";

        when(transactionRepositoryMock.findBySellerEmail(sellerEmail))
                .thenReturn(List.of());

        assertThatThrownBy(() -> underTest.findBySellerEmail(sellerEmail))
                .isInstanceOf(NotFoundException.class);
        verify(transactionRepositoryMock).findBySellerEmail(sellerEmail);
        verify(transactionMapperMock, never()).transactionEntityListToTransactionResponseDTOList(any(List.class));
    }

    @Test
    void givenValidBuyerEmail_whenFindByBuyerEmailIsCalled_thenReturnTransactionResponseDTOList() {
        final var buyerEmail = "buyer@example.com";
        final var transactionEntities = List.of(getTransactionEntity());
        final var transactionResponseDTOs = List.of(getTransactionResponseDTO());

        when(transactionRepositoryMock.findByBuyerEmail(buyerEmail))
                .thenReturn(transactionEntities);
        when(transactionMapperMock.transactionEntityListToTransactionResponseDTOList(any(List.class)))
                .thenReturn(transactionResponseDTOs);

        final var response = underTest.findByBuyerEmail(buyerEmail);

        assertThat(response).isEqualTo(transactionResponseDTOs);
        verify(transactionRepositoryMock).findByBuyerEmail(buyerEmail);
        verify(transactionMapperMock).transactionEntityListToTransactionResponseDTOList(any(List.class));
    }

    @Test
    void givenInvalidBuyerEmail_whenFindByBuyerEmailIsCalled_thenThrowNotFoundException() {
        final var buyerEmail = "invalid@example.com";

        when(transactionRepositoryMock.findByBuyerEmail(buyerEmail))
                .thenReturn(List.of());

        assertThatThrownBy(() -> underTest.findByBuyerEmail(buyerEmail))
                .isInstanceOf(NotFoundException.class);
        verify(transactionRepositoryMock).findByBuyerEmail(buyerEmail);
        verify(transactionMapperMock, never()).transactionEntityListToTransactionResponseDTOList(any(List.class));
    }

    @Test
    void givenValidBidId_whenFindByProductIdIsCalled_thenReturnTransactionResponseDTOList() {
        final var bidId = UUID.randomUUID();
        final var transactionEntities = List.of(getTransactionEntity());
        final var transactionResponseDTOs = List.of(getTransactionResponseDTO());

        when(transactionRepositoryMock.findByProductId(bidId))
                .thenReturn(transactionEntities);
        when(transactionMapperMock.transactionEntityListToTransactionResponseDTOList(any(List.class)))
                .thenReturn(transactionResponseDTOs);

        final var response = underTest.findByProductId(bidId);

        assertThat(response).isEqualTo(transactionResponseDTOs);
        verify(transactionRepositoryMock).findByProductId(bidId);
        verify(transactionMapperMock).transactionEntityListToTransactionResponseDTOList(any(List.class));
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
