function changeProfileImg() {

    const uploadFilename = $('#uploadFilename')[0].files[0].name; // 클라이언트 업로드 파일명
    // https://2ham-s.tistory.com/307 참고 (formData에 이미지 담기)

    const formData = new FormData();
    const file = $('#uploadFilename')[0].files[0];
    formData.append("profileImgFile", file);

    $.ajax({
        url: '/my-info/temp-profile-img',
        contentType: false,
        processData: false,
        data: formData,
        type: 'post',
        success: function (data) {
            console.log("success=" + JSON.stringify(data));
            location.href = "/my-info?storeFilename=" + data.storeFilename + "&uploadFilename=" + uploadFilename;
        },
        error: function (e) {
            console.log("error=" + e);
        }
    });
}

/**
 * my-info.html '수정' 버튼 누를 시 form 내용 전송 함수
 */
function submitForm() {

    const username = $('#username').val();
    const age = $('#age').val();
    const gender = $('#gender').val();
    const cash = $('#cash').val();
    const uploadFilename =
        new URLSearchParams(location.search).get('uploadFilename');
    // 클라이언트 업로드 파일명, URLSearchParams 클래스를 이용해 url 쿼리스트링에서 값 가져옴

    const storeFilename = new URLSearchParams(location.search).get('storeFilename');

    const params = {
        username: username,
        age: age,
        gender: gender,
        cash: cash,
        uploadFilename: uploadFilename,
        storeFilename: storeFilename,
    };

    $.ajax({
        url: '/my-info/update-profile-img',
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify(params),
        type: 'post',
        success: function (data) {
            console.log("success=" + JSON.stringify(data));
            location.href = "/my-info";
       },
        error: function (e) {
            console.log("error=" + e);
        }
    });
}