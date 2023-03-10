
/**
 * 저장된 마커 map에 표시 등
 * map 기능 설정
 */
function init(untilTime){
    // 이전에 추가됐던 마커 초기화 - 조건으로 재검색시 필요함
    if (layers.length !== 0) {
        for (const layer of layers) {
            layer.remove();
        }
        layers = [];
    }
    // Controller에서 MarkDto 객체 가져와 map에 marker 삽입
    $.ajax({
        type : "post",
        url: "/get-marks",
        async: false,
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify(untilTime),
        success : function (data, status) {
            for (const markDto of data) {
                if (markDto.participant == null) {
                    markDto.participant = 0;
                }
                insertMark(markDto);
            }
            // alert(status);
        },
        error : function (status) {
            console.log("오류 =" + JSON.stringify(status));
            // alert(status);
        }
    });

    map.on('click', onMapClick);
}

/**
 * map에 마크 삽입
 */
function insertMark(markDto) {
    const markLocation = [markDto.lat, markDto.lng];
    const layer = L.marker(markLocation).addTo(map);
    layers.push(layer);
    if (markDto.participant == null) {
        markDto.participant = 0;
    }

    layer.bindPopup(
        `<b>이름 : ${markDto.username}</b><br>
        <b>성별 : ${markDto.gender}</b><br>
        <b>나이 : ${markDto.ageRange}</b><br>
        <b>성격 : ${markDto.character}</b><br>
        <b>장소 : ${markDto.place}</b><br>
        <b>머무는 시간 :</b><br>
        &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
        <b> ${markDto.startYMD} ${markDto.startTime} 부터</b><br>
        &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
        <b>${markDto.endYMD} ${markDto.endTime} 까지</b><br>
        <b>내용 :</b><br>
        <div style="width: 220px; word-break: break-all; border: ridge;"><b>${markDto.contents}</b></div>
        <b>올 수도 있는 사람: ${markDto.participant} 명</b><br>
        <input type="hidden" value="${markDto.id}"/>
        <input type='button' value='참석' onclick="participate(${markDto.id})"/>
        <input type='button' value='삭제' onclick="deleteMark(${markDto.id})"/>`).openPopup();

}

/**
 * map에 mark 삭제
 */
function deleteMark(markId) {
    const boolean = confirm("삭제하시겠습니까?");

    if (boolean) {
        $.ajax({
            type: "post",
            url: "/delete-mark",
            async: false,
            contentType: 'application/json',
            dataType: "json",
            data: JSON.stringify(markId),
            success: function (data, status) {
                location.href = "/";
            },
            error: function (status) {
                alert("해당 마크를 삭제할 권한이 없습니다.");
            }
        });

    }

}

/**
 * 올 수도 있는 사람 인원 추가
 */
function participate(markId) {
    const boolean = confirm("참석하시겠습니까?");

    if (boolean) {
        $.ajax({
            type: "post",
            url: "/update-mark",
            async: false,
            contentType: 'application/json',
            dataType: "json",
            data: JSON.stringify(markId),
            success: function (data, status) {
                init();
            },
            error: function (status) {
                alert(status);
            }
        });
    }

}


/**
 * map 클릭시 팝업창 생성
 * map 클릭시 생성되는 팝업창 속성설정
 */
function onMapClick(e) {
    /*
    팝업창에서 등록시 mark를 생성하려는 의도 때문에 markLocation 정보를
    <input type="hidden" id="markLocationLat" name="markLocationLat" value="">
    <input type="hidden" id="markLocationLng" name="markLocationLng" value=""> 에 넣는 코드
    참고 : 위처럼 Lat Lng 따로 구분해 input 태그를 둘로 만든 이유 :
         markLocation = [e.latlng.lat, e.latlng.lng]; 로 둘을 합쳐서
         <input type="hidden" id="markLocation" name="markLocation" value="">에
         $('#markLocation').val(markLocation); 으로 값을 넣은 후
         popup 창에서
         let location = opener.document.getElementById('markLocation').value;
         로 가져와 window.opener.insertMark(location); 에 넣으면 문자열 형식이기 때문에 정상 작동 안함.
     */
    $('#markLocationLat').val(e.latlng.lat);
    $('#markLocationLng').val(e.latlng.lng);

    //팝업창 생성
    const url = "//" + location.host + "/regist-mark"; // localhost 에서 실행시 "//" + location.host 는 https://localhost:8080
    const popupWidth = 875;
    const popupHeight = 775;
    const popupX = (window.screen.width / 2) - (popupWidth / 2);
    const popupY = (window.screen.height / 2) - (popupHeight / 2);
    const target = "createLocation";
    const features = "width=" + popupWidth + ", height=" + popupHeight + ", left=" + popupX + ", top=" + popupY;
    window.open(url, target, features);
}

/**
 * navigator.geolocation.getCurrentPosition(success, error); 에서 쓰이는 함수
 */
function success(location) {
    let latitude = location.coords.latitude; // 위도
    let longitude = location.coords.longitude; // 경도
    let mapLocation = [latitude, longitude]

    // map 생성 - leaflet 라이브러리
    // 함수로 묶으면 더 깔끔하려나..싶으나 일단 보류했음
    map = selectMap.setView(mapLocation,17); // .setView([위도, 경도], 초기 줌레벨)
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
}

/**
 * navigator.geolocation.getCurrentPosition(success, error); 에서 쓰이는 함수
 */
function error() {
    alert("현재 위치를 확인할 수 없습니다.")
}

let mapLocation = [37.55559194373907,126.9370528594173];

// map 생성 - leaflet 라이브러리
// 함수로 묶으면 더 깔끔하려나..싶으나 일단 보류했음
let layers = [];
let selectMap = L.map("mapid");
let map = selectMap.setView(mapLocation,17); // .setView([위도, 경도], 초기 줌레벨)
L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map);



$(document).ready(function () {
    // ↓ user 위치 받아옴. 브라우저에서 위치 좌표 success에서 리턴해 줌.
    navigator.geolocation.getCurrentPosition(success, error, {enableHighAccuracy: true});
    init();
});








