package com.example.meet.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id
    @GeneratedValue
    private Long id;

    private String pg; // 상점 ID 예) pg : 'kcp'

    private String payMethod;

    private String itemName; // 상품 이름

    private Integer amount; // 충전 결제 금액

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

//    private String buyerEmail;
//    private String buyerName; // 구매자(결제자)
//    private String buyerTel;
//    private String buyerAddr; // 구매자 주소
//    private String buyerPostcode;

    private boolean success;

    private String errorMsg;

    private String merchantUid;

    private String impUid;

    public Payment(String pg, String payMethod, String itemName, Integer amount, Member member,
                   boolean success, String errorMsg, String merchantUid, String impUid) {
        this.pg = pg;
        this.payMethod = payMethod;
        this.itemName = itemName;
        this.amount = amount;
        this.member = member;
        this.success = success;
        this.errorMsg = errorMsg;
        this.merchantUid = merchantUid;
        this.impUid = impUid;
    }
}
