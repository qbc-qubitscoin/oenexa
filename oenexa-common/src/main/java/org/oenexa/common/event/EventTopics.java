package org.oenexa.common.event;

public final class EventTopics {
    private EventTopics() {}

    public static final String USER_REGISTERED = "USER_REGISTERED";
    public static final String USER_LOGIN = "USER_LOGIN";
    public static final String KYC_SUBMITTED = "KYC_SUBMITTED";
    public static final String KYC_APPROVED = "KYC_APPROVED";
    public static final String WALLET_DEPOSIT = "WALLET_DEPOSIT";
    public static final String WALLET_WITHDRAWAL = "WALLET_WITHDRAWAL";
    public static final String TRANSFER_INITIATED = "TRANSFER_INITIATED";
    public static final String TRANSFER_COMPLETED = "TRANSFER_COMPLETED";
    public static final String ORDER_CREATED = "ORDER_CREATED";
    public static final String ORDER_FILLED = "ORDER_FILLED";
    public static final String TRADE_EXECUTED = "TRADE_EXECUTED";
    public static final String PAYMENT_PROCESSED = "PAYMENT_PROCESSED";
    public static final String SECURITY_ALERT = "SECURITY_ALERT";
    public static final String FRAUD_DETECTED = "FRAUD_DETECTED";
}
