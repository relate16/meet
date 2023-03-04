
// IMP 객체 초기화 코드. 주의 : 한 번만 호출되게 설정할 것
var IMP = window.IMP; // 생략 가능
IMP.init("imp67011510"); // 예: imp00000000a

var today = new Date();
var hours = today.getHours(); // 시
var minutes = today.getMinutes();  // 분
var seconds = today.getSeconds();  // 초
var milliseconds = today.getMilliseconds();
var makeMerchantUid = hours +  minutes + seconds + milliseconds;

function requestPay() {
    const memberId = $("#id").val();
    const pg = 'kcp';
    const merchantUid = 'IMP' + makeMerchantUid;
    const itemName = '캐시';
    const chargeAmount = $("#chargeAmount").val();
    const buyerEmail = 'Iamport@chai.finance';
    const buyerName = $("#username").val();
    const buyerTel = '010-1234-5678';
    const buyerAddr = '서울특별시 강남구 삼성동';
    const buyerPostcode = '123-456';


    IMP.request_pay({
        pg : pg,
        pay_method : 'card',
        merchant_uid: merchantUid,
        name : itemName, // rsp에 없음, payment에서 사용
        amount : chargeAmount, // rsp에 없음, payment에서 사용
        buyer_email : buyerEmail, // rsp에 없지만 아직 member에서 안씀 필요 없음
        buyer_name : buyerName, // rsp에 없음, payment에서 사용
        buyer_tel : buyerTel, // rsp에 없지만 아직 member에서 안씀 필요 없음
        buyer_addr : buyerAddr, // rsp에 없지만 아직 member에서 안씀 필요 없음
        buyer_postcode : buyerPostcode // rsp에 없지만 아직 member에서 안씀 필요 없음
    }, function (rsp) { // callback
        // rsp 객체 예 :
        // {error_msg: "사용자 결제 취소", imp_uid: "imp_284..", merchant_uid: "IMP849",
        // pay_method: "card", pg_provider: "kcp", pg_type: "payment", success: false}
        if (rsp.success) {
            console.log("결제 성공");
            requestCharge(rsp, memberId, itemName, buyerName, chargeAmount);
        } else {
            console.log("결제 실패");
        }
    });
}

function requestCharge(rsp, memberId, itemName, buyerName, chargeAmount) {
    const params = {
        pg: rsp.pg,
        payMethod: rsp.pay_method,
        itemName: itemName,
        amount: chargeAmount,
        memberId: memberId,
        username: buyerName,
        success: rsp.success,
        errorMsg: rsp.error_msg,
        merchantUid: rsp.merchant_uid,
        impUid: rsp.imp_uid
    }
    $.ajax({
        type: "post",
        url: "/payment/chargeCash",
        async: false,
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data, status) {
            location.href = "/";
        },
        error: function (status) {
            alert("결제는 됐으나 충전에 실패했습니다. 너무 죄송합니다. 관리자에게 문의해주세요.");
            location.href = "/";
        }
    });


}


