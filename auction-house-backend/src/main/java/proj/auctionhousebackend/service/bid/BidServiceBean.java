package proj.auctionhousebackend.service.bid;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import proj.auctionhousebackend.dto.CollectionResponseDTO;
import proj.auctionhousebackend.dto.PageRequestDTO;
import proj.auctionhousebackend.dto.bid.BidRequestDTO;
import proj.auctionhousebackend.dto.bid.BidResponseDTO;
import proj.auctionhousebackend.exception.ExceptionCode;
import proj.auctionhousebackend.exception.NotFoundException;
import proj.auctionhousebackend.mapper.BidMapper;
import proj.auctionhousebackend.model.BidEntity;
import proj.auctionhousebackend.model.ProductEntity;
import proj.auctionhousebackend.repository.BidRepository;
import proj.auctionhousebackend.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class BidServiceBean implements BidService {

    private final BidRepository bidRepository;
    private final ProductRepository productRepository;
    private final BidMapper bidMapper;

    @Override
    public BidResponseDTO findById(UUID id) {
        log.info("Finding bid by ID: {}", id);
        return bidRepository.findById(id)
                .map(bidMapper::bidEntityToBidResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format(
                        ExceptionCode.ERR006_BID_NOT_FOUND.getMessage(),
                        id
                )));
    }


    @Override
    @Transactional
    public BidResponseDTO save(BidRequestDTO bidRequestDTO) throws Exception {
        log.info("Saving bid: {}", bidRequestDTO);

        UUID productId = bidRequestDTO.getProductId();
        ProductEntity product = productRepository.findById(productId).orElseThrow(() ->
                new NotFoundException(String.format(ExceptionCode.ERR003_PRODUCT_NOT_FOUND.getMessage(), productId)));

        if (product.getEndTime().isBefore(LocalDateTime.now())) {
            throw new Exception("Cannot place bid. The auction has already ended.");
        }

        Optional<BidEntity> latestBidOpt = Optional.ofNullable(bidRepository.findTopByProductIdOrderByBidAmountDesc(productId));
        BigDecimal minBidAmount;
        if (latestBidOpt.isPresent()) {
            minBidAmount = latestBidOpt.get().getBidAmount();
        } else {
            minBidAmount = product.getStartingPrice();
        }

        if (bidRequestDTO.getBidAmount().compareTo(minBidAmount) <= 0) {
            throw new Exception("Bid amount must be greater than the current highest bid or the starting price.");
        }

        BidEntity bidEntity = bidMapper.bidRequestDTOToBidEntity(bidRequestDTO);
        bidEntity.setBidTime(LocalDateTime.now());
        bidEntity = bidRepository.save(bidEntity);
        return bidMapper.bidEntityToBidResponseDTO(bidEntity);
    }


    @Override
    public List<BidResponseDTO> findByProductId(UUID productId) {
        log.info("Finding bids by product ID: {}", productId);
        List<BidEntity> bidEntities = bidRepository.findByProductId(productId);

        if (bidEntities.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionCode.ERR003_PRODUCT_NOT_FOUND.getMessage(), productId));
        }

        return bidMapper.bidEntityListToBidResponseDTOList(bidEntities);
    }

    @Override
    public List<BidResponseDTO> findByBidderEmail(String bidderEmail) {
        log.info("Finding bids by bidder email: {}", bidderEmail);
        List<BidEntity> bidEntities = bidRepository.findByBidderEmail(bidderEmail);

        if (bidEntities.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionCode.ERR002_EMAIL_NOT_FOUND.getMessage(), bidderEmail));
        }

        return bidMapper.bidEntityListToBidResponseDTOList(bidEntities);
    }

    @Override
    public BidResponseDTO findLatestBidByProductId(UUID productId) {
        log.info("Finding latest bid by product ID: {}", productId);
        BidEntity latestBid = bidRepository.findTopByProductIdOrderByBidAmountDesc(productId);
        if (latestBid == null) {
            throw new NotFoundException(String.format(ExceptionCode.ERR003_PRODUCT_NOT_FOUND.getMessage(), productId));
        }
        return bidMapper.bidEntityToBidResponseDTO(latestBid);
    }

    @Override
    public CollectionResponseDTO<BidResponseDTO> findByProductIdPaged(UUID productId, PageRequestDTO page) {
        log.info("Finding bids by product ID with pagination: {}", productId);
        Page<BidEntity> bidEntityPage = bidRepository.findByProductId(productId, PageRequest.of(
                page.getPageNumber(),
                page.getPageSize()
        ));
        List<BidResponseDTO> bids = bidMapper.bidEntityListToBidResponseDTOList(bidEntityPage.getContent());
        if (bids.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionCode.ERR003_PRODUCT_NOT_FOUND.getMessage(), productId));
        }
        return new CollectionResponseDTO<>(bids, bidEntityPage.getTotalElements());
    }

    @Override
    public CollectionResponseDTO<BidResponseDTO> findByBidderEmailPaged(String bidderEmail, PageRequestDTO page) {
        log.info("Finding bids by bidder email with pagination: {}", bidderEmail);
        Page<BidEntity> bidEntityPage = bidRepository.findByBidderEmail(bidderEmail, PageRequest.of(
                page.getPageNumber(),
                page.getPageSize()
        ));
        List<BidResponseDTO> bids = bidMapper.bidEntityListToBidResponseDTOList(bidEntityPage.getContent());
        if (bids.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionCode.ERR002_EMAIL_NOT_FOUND.getMessage(), bidderEmail));
        }

        return new CollectionResponseDTO<>(bids, bidEntityPage.getTotalElements());
    }
}
