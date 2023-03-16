function changeProfileImg() {
    const formData = new FormData();
    const profileImgFile = $('#uploadFilename')[0];
    // const profileImgFile = $('#uploadFilename')[0].files;
    const uploadFilename = $('#uploadFilename')[0].name; // 클라이언트 업로드 파일명
    // .files + for(const element of profileImgFile) .. 을 사용하지 않고
    // 그냥 const profileImgFile = $('#storeFilename')[0]
    // formData.append(profileImgFile) 하면 될듯 일단 profileImgFile 콘솔 값 확인 후 고쳐보기
    // https://2ham-s.tistory.com/307 참고 (formData에 이미지 담기)

    console.log("profileImgFIle=" + JSON.stringify(profileImgFile));

    formData.append("profileImg", profileImgFile);
    console.log("username = " + $('#username').val());
    console.log("uploadFilename = " + uploadFilename);
    console.log("formData = " + formData);
    // for (const element of profileImgFile) {
    //     formData.append("profileImg", element);
    // }

    const params = {
        username: $('#username').val(),
        uploadFilename: uploadFilename,
        profileImgFile: formData
    };

    $.ajax({
        url: '/my-info/updateProfileImg',
        data: params,
        cache: false,
        contentType: false,
        processData: false,
        type: 'post',
        success: function (data) {
            console.log(data);
            // 파일 저장 로직 짠 후 아래 로직 실현 (프로필 이미지 클라이언트에게 변경되게 보여주는 로직임)
            // const storeFilename = $('#storeFilename').val();
            // const storeFilename = data.;
            // $('#profileImg').attr("src", storeFilename);
        },
        error: function (e) {
            console.log(e);
        }
    })
}