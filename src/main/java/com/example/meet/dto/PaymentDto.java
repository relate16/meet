package com.example.meet.dto;

import com.example.meet.entity.Member;
import lombok.*;

@Getter
@Setter
@ToString(exclude = "member")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentDto {
    private Long id;

    private String pg; // 상점 ID 예) pg : 'kcp'

    private String payMethod;

    private String itemName; // 상품 이름

    private Integer amount; // 충전 결제 금액

    private Long memberId;

    private String username;

//    private String buyerEmail;
//    private String buyerName; // 구매자(결제자)
//    private String buyerTel;
//    private String buyerAddr; // 구매자 주소
//    private String buyerPostcode;

    private boolean success;

    private String errorMsg;

    private String merchantUid;

    private String impUid;
}
