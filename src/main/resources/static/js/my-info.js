function changeProfileImg() {

    const uploadFilename = $('#uploadFilename')[0].files[0].name; // 클라이언트 업로드 파일명
    // .files + for(const element of profileImgFile) .. 을 사용하지 않고
    // 그냥 const profileImgFile = $('#storeFilename')[0]
    // formData.append(profileImgFile) 하면 될듯 일단 profileImgFile 콘솔 값 확인 후 고쳐보기
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

function submitForm() {

    // multipartFile 포함해 controller에 파라미터 넘기는 방법 : https://truecode-95.tistory.com/167

    const formData = new FormData();

    const username = $('#username').val();
    const age = $('#age').val();
    const gender = $('#gender').val();
    const cash = $('#cash').val();
    const uploadFilename = new URLSearchParams(location.search).get('uploadFilename');
    // 클라이언트 업로드 파일명, URLSearchParams 클래스를 이용해 url 쿼리스트링에서 값 가져옴
    const storeFilename = '$[[memberDto.profileImg.storeFilename]]';
    const profileImgFile = $('#uploadFilename')[0].files[0];
    // https://2ham-s.tistory.com/307 참고 (formData에 이미지 담기)


    const params = {
        username: username,
        age: age,
        gender: gender,
        cash: cash,
        uploadFilename: uploadFilename,
        storeFilename: storeFilename,
    };
    profileImgFile: formData

    $.ajax({
        url: '/my-info/update-profile-img',
        contentType: false,
        processData: false,
        data: formData,
        type: 'post',
        success: function (data) {
            console.log("success=" + JSON.stringify(data));
       },
        error: function (e) {
            console.log("error=" + e);
        }
    });
}