package es.uca.iw.ebz.controller;

public class TransaccionMovimiento {
    private String transactionStatus;

    private String transactionType;

    private String concept;

    private String iban;

    private int value;

    private String issuer;

    private String id;

    public TransaccionMovimiento(String transactionStatus, String transactionType, String concept, String iban, int value, String issuer, String id) {
        this.transactionStatus = transactionStatus;
        this.transactionType = transactionType;
        this.concept = concept;
        this.iban = iban;
        this.value = value;
        this.issuer = issuer;
        this.id = id;
    }
    public  TransaccionMovimiento() {}

    //setters
    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public void setId(String id) {
        this.id = id;
    }

    //getters
    public String getTransactionStatus() {
        return transactionStatus;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getConcept() {
        return concept;
    }

    public String getIban() {
        return iban;
    }

    public int getValue() {
        return value;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getId() {
        return id;
    }
}
