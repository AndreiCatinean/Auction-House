package proj.auctionhousebackend.service.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import proj.auctionhousebackend.dto.CollectionResponseDTO;
import proj.auctionhousebackend.dto.PageRequestDTO;
import proj.auctionhousebackend.dto.transaction.TransactionRequestDTO;
import proj.auctionhousebackend.dto.transaction.TransactionResponseDTO;
import proj.auctionhousebackend.exception.ExceptionCode;
import proj.auctionhousebackend.exception.NotFoundException;
import proj.auctionhousebackend.mapper.TransactionMapper;
import proj.auctionhousebackend.model.TransactionEntity;
import proj.auctionhousebackend.repository.TransactionRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class TransactionServiceBean implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;



    @Override
    public CollectionResponseDTO<TransactionResponseDTO> findAllPaged(PageRequestDTO page) {
        log.info("Finding all transactions with pagination");
        Page<TransactionEntity> transactionEntityPage = transactionRepository.findAll(PageRequest.of(
                page.getPageNumber(),
                page.getPageSize()
        ));

        List<TransactionResponseDTO> transactions = transactionMapper.transactionEntityListToTransactionResponseDTOList(transactionEntityPage.getContent());
        return new CollectionResponseDTO<>(transactions, transactionEntityPage.getTotalElements());
    }


    @Override
    public TransactionResponseDTO save(TransactionRequestDTO transactionRequestDTO) {
        log.info("Saving transaction: {}", transactionRequestDTO);
        TransactionEntity transactionEntity = transactionMapper.transactionRequestDTOToTransactionEntity(transactionRequestDTO);
        transactionEntity = transactionRepository.save(transactionEntity);
        return transactionMapper.transactionEntityToTransactionResponseDTO(transactionEntity);
    }

    @Override
    public List<TransactionResponseDTO> findBySellerEmail(String sellerEmail) {
        log.info("Finding transactions by seller email: {}", sellerEmail);
        List<TransactionEntity> transactions = transactionRepository.findBySellerEmail(sellerEmail);
        if (transactions.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionCode.ERR002_EMAIL_NOT_FOUND.getMessage(), sellerEmail));
        }
        return transactionMapper.transactionEntityListToTransactionResponseDTOList(transactions);
    }

    @Override
    public List<TransactionResponseDTO> findByBuyerEmail(String buyerEmail) {
        log.info("Finding transactions by buyer email: {}", buyerEmail);
        List<TransactionEntity> transactions = transactionRepository.findByBuyerEmail(buyerEmail);
        if (transactions.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionCode.ERR002_EMAIL_NOT_FOUND.getMessage(), buyerEmail));
        }
        return transactionMapper.transactionEntityListToTransactionResponseDTOList(transactions);
    }

    @Override
    public List<TransactionResponseDTO> findByProductId(UUID productId) {
        log.info("Finding transactions by product ID: {}", productId);
        List<TransactionEntity> transactions = transactionRepository.findByProductId(productId);
        return transactionMapper.transactionEntityListToTransactionResponseDTOList(transactions);
    }
}
