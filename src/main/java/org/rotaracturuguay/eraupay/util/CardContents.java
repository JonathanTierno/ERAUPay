package org.rotaracturuguay.eraupay.util;

public class CardContents {
    private Long idCard;
    private Long idUser;
    private Long creationTimestamp;
    private Double balance;

    private CardContents() {
    }

    public static CardContents build() { return new CardContents(); }

    public CardContents setIdCard(Long idCard) {
        this.idCard = idCard;
        return this;
    }

    public CardContents setIdUser(Long idUser) {
        this.idUser = idUser;
        return this;
    }

    public CardContents setCreationTimestamp(Long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
        return this;
    }

    public CardContents setBalance(Double balance) {
        this.balance = balance;
        return this;
    }

    public Long getIdCard() {
        return idCard;
    }

    public Long getIdUser() {
        return idUser;
    }

    public Long getCreationTimestamp() {
        return creationTimestamp;
    }

    public Double getBalance() {
        return balance;
    }
}
