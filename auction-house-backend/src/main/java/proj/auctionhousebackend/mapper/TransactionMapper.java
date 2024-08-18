package proj.auctionhousebackend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import proj.auctionhousebackend.dto.transaction.TransactionRequestDTO;
import proj.auctionhousebackend.dto.transaction.TransactionResponseDTO;
import proj.auctionhousebackend.model.TransactionEntity;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {
    TransactionResponseDTO transactionEntityToTransactionResponseDTO(TransactionEntity transactionEntity);

    List<TransactionResponseDTO> transactionEntityListToTransactionResponseDTOList(List<TransactionEntity> transactionEntityList);

    TransactionEntity transactionRequestDTOToTransactionEntity(TransactionRequestDTO transactionRequestDTO);
}
