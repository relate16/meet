function changeProfileImg() {
    const formData = new FormData();
    const profileImgFile = $('#storeFilename')[0].files;

    // 파일 저장 로직 짠 후 아래 로직 실현 (프로필 이미지 클라이언트에게 변경되게 보여주는 로직임)
    const storeFilename = $('#storeFilename').val();
    $('#profileImg').src(storeFilename);
}