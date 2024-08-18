package proj.auctionhousebackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import proj.auctionhousebackend.mapper.*;
import proj.auctionhousebackend.repository.*;
import proj.auctionhousebackend.service.bid.BidService;
import proj.auctionhousebackend.service.bid.BidServiceBean;
import proj.auctionhousebackend.service.category.CategoryService;
import proj.auctionhousebackend.service.category.CategoryServiceBean;
import proj.auctionhousebackend.service.favoriteOffer.FavoriteOfferService;
import proj.auctionhousebackend.service.favoriteOffer.FavoriteOfferServiceBean;
import proj.auctionhousebackend.service.mail.MailService;
import proj.auctionhousebackend.service.mail.SyncMailServiceBean;
import proj.auctionhousebackend.service.product.ProductService;
import proj.auctionhousebackend.service.product.ProductServiceBean;
import proj.auctionhousebackend.service.transaction.TransactionService;
import proj.auctionhousebackend.service.transaction.TransactionServiceBean;
import proj.auctionhousebackend.service.userInfo.UserInfoService;
import proj.auctionhousebackend.service.userInfo.UserInfoServiceBean;

@Configuration
public class Config {

    @Bean
    public UserInfoService userInfoServiceBean(UserInfoRepository userInfoRepository, UserInfoMapper userInfoMapper, @Value("${spring.application.name:BACKEND}") String applicationName) {
        return new UserInfoServiceBean(userInfoRepository, userInfoMapper, applicationName);
    }

    @Bean
    public ProductService productServiceBean(ProductRepository productRepository, ProductMapper productMapper, @Value("${spring.application.name:BACKEND}") String applicationName) {
        return new ProductServiceBean(productRepository, productMapper, applicationName);
    }

    @Bean
    public CategoryService categoryServiceBean(CategoryRepository categoryRepository, CategoryMapper categoryMapper, @Value("${spring.application.name:BACKEND}") String applicationName) {
        return new CategoryServiceBean(categoryRepository, categoryMapper);
    }

    @Bean
    public FavoriteOfferService favoriteOfferServiceBean(FavoriteOfferRepository favoriteOfferRepository, FavoriteOfferMapper favoriteOfferMapper, @Value("${spring.application.name:BACKEND}") String applicationName) {
        return new FavoriteOfferServiceBean(favoriteOfferRepository, favoriteOfferMapper);
    }

    @Bean
    public BidService bidServiceBean(BidRepository bidRepository,ProductRepository productRepository, BidMapper bidMapper, @Value("${spring.application.name:BACKEND}") String applicationName) {
        return new BidServiceBean(bidRepository,productRepository, bidMapper);
    }

    @Bean
    public TransactionService transactionServiceBean(TransactionRepository transactionRepository, TransactionMapper transactionMapper, @Value("${spring.application.name:BACKEND}") String applicationName) {
        return new TransactionServiceBean(transactionRepository, transactionMapper);
    }

    @Bean
    public MailService syncMailServiceBean(
            @Value("${mail-sender-app.url}") String url,
            RestTemplateBuilder restTemplateBuilder
    ) {
        return new SyncMailServiceBean(url, restTemplateBuilder.build());
    }
}