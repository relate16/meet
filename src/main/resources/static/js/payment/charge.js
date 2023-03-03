
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
    const username = $("#username").val();
    const chargeAmount = $("#chargeAmount").val();
    const memberId = $("#id").val();

    IMP.request_pay({
        pg : 'kcp',
        pay_method : 'card',
        merchant_uid: "IMP" + makeMerchantUid,
        name : '캐시',
        amount : chargeAmount,
        buyer_email : 'Iamport@chai.finance',
        buyer_name : username,
        buyer_tel : '010-1234-5678',
        buyer_addr : '서울특별시 강남구 삼성동',
        buyer_postcode : '123-456'
    }, function (rsp) { // callback
        if (rsp.success) {
            console.log(rsp);
            requestCharge(rsp.merchant_uid, memberId, username);
        } else {
            console.log(rsp);
        }
    });
}

function requestCharge(merchant_uid, memberId, username) {
    const params = {
        merchantUid: merchant_uid,
        memberId: memberId,
        username: username
    }
    $.ajax({
        type: "post",
        url: "/payment/chargeCash",
        async: false,
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify(params)
    });


}


