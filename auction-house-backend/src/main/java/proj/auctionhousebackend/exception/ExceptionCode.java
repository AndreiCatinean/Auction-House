package proj.auctionhousebackend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    ERR001_USER_NOT_FOUND("UserInfo with ID %s not found"),
    ERR002_EMAIL_NOT_FOUND("Email %s not found"),
    ERR099_INVALID_CREDENTIALS("Invalid credentials."),
    ERR003_PRODUCT_NOT_FOUND("Product with ID %s not found"),
    ERR004_CATEGORY_NOT_FOUND("Category with ID %s not found"),
    ERR005_TRANSACTION_NOT_FOUND("Transaction with ID %s not found"),
    ERR006_BID_NOT_FOUND("Bid with ID %s not found");

    private final String message;
}