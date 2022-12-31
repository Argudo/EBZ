package es.uca.iw.ebz.controller;

import javax.swing.plaf.PanelUI;

public class TransaccionTarjeta {
    private String paymentStatus;
    private String cardNumber;
    private String cardholderName;
    private int expirationMonth;
    private int expirationYear;
    private String csc;
    private String type;
    private String shop;
    private Float value;
    private String id;
    private int securityToken;

    public TransaccionTarjeta() {}

    public TransaccionTarjeta(String paymentStatus, String cardNumber, String cardholderName, int expirationMonth, int expirationYear, String csc, String type, String shop, Float value, String id, int securityToken) {
        this.paymentStatus = paymentStatus;
        this.cardNumber = cardNumber;
        this.cardholderName = cardholderName;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.csc = csc;
        this.type = type;
        this.shop = shop;
        this.value = value;
        this.id = id;
        this.securityToken = securityToken;
    }

    //setters
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }
    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }
    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }
    public void setCsc(String csc) {
        this.csc = csc;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setShop(String shop) {
        this.shop = shop;
    }
    public void setValue(Float value) {
        this.value = value;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setSecurityToken(int securityToken) {
        this.securityToken = securityToken;
    }

    //getters
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public String getCardholderName() {
        return cardholderName;
    }
    public int getExpirationMonth() {
        return expirationMonth;
    }
    public int getExpirationYear() {
        return expirationYear;
    }
    public String getCsc() {
        return csc;
    }
    public String getType() {
        return type;
    }
    public String getShop() {
        return shop;
    }
    public Float getValue() {
        return value;
    }
    public String getId() {
        return id;
    }
    public int getSecurityToken() {
        return securityToken;
    }
}

