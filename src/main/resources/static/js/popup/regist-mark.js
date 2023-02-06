
/*
    일정 :
    Member.class의
    String gender; // enum으로 만들기
    다대일 Mark-Member 관계 설정하기
    설정 후, meet-map.html의
    <top th:replace="~{#{topHtml} :: top('test','1000','로그인','회원가입')}"></top>
    파라미터로 넣기.
    test 코드 작성 후 bindingResult 사용해보기

    수정 목록 :
        top 템플릿 조각 추가 및 적용.
*/


function windowClose() {
    window.close();
}

/**
 * 입력 부분 유효성 검사
 */
function validate() {
    // isEmpty 검사
    if (jQuery.isEmptyObject($('#username').val())) {
        alert("이름을 입력해주세요.");
        return false;
    } else if (jQuery.isEmptyObject($("input[name=gender]:checked").val())) {
        alert("성별을 입력해주세요.");
        return false;
    } else if (jQuery.isEmptyObject($("input[name=age]:checked").val())) {
        alert("나이대를 입력해주세요.");
        return false;
    } else if (jQuery.isEmptyObject($('#character').val())) {
        alert("성격을 입력해주세요.");
        return false;
    } else if (jQuery.isEmptyObject($('#place').val())) {
        alert("장소를 입력해주세요.");
        return false;
    } else if (jQuery.isEmptyObject($('#startTime').val())) {
        alert("도착 시간을 입력해주세요.");
        return false;
    } else if (jQuery.isEmptyObject($('#endTime').val())) {
        alert("떠나는 시간을 입력해주세요.");
        return false;
    }

    // 값 유효성 검사
    if ($('#contents').val().length >= 200) {
        alert("하고 싶은 말은 200자 이내로 써주세요.");
        return false;
    } else if ($('#startTime').val() == $('#endTime').val()) {
        alert("도착 시간과 떠날 시간이 같으면 안됩니다.")
        return false;
    }
}

/**
 * 부모창에 있는 map에 mark 삽입 및
 * DB에 mark 객체 저장
 */
function insertMark() {
    if (validate() == false) {
        return false;
    }

    let lat = opener.document.getElementById('markLocationLat').value;
    let lng = opener.document.getElementById('markLocationLng').value;

    var params = {
        username: $("#username").val(),
        gender: $("input[name=gender]:checked").val(),
        age: $("input[name=age]:checked").val(),
        character: $("#character").val(),
        place: $("#place").val(),
        lat: lat,
        lng: lng,
        startTime: $("#startTime").val(),
        endTime: $("#endTime").val(),
        contents: $("#contents").val()
    }

    // Controller에서 Mark 객체 DB에 insert
    $.ajax({
        type: "post",
        url: "/insert-mark",
        async: false,
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify(params),
        success: function (data, status) {
            window.opener.insertMark(data);
            window.close();
        },
        error: function (status) {
            alert(status);
            window.close();
        }
    });
}


/**
 * MDTimePicker 시간 선택 초기화
 * 사이트 참조 : https://www.jqueryscript.net/time-clock/Material-Time-Picker-Plugin-jQuery-MDTimePicker.html
 */
function initMdTimePicker() {

    //MDTimePicker 옵션 및 기본값
    $('#startTime').mdtimepicker({

        // format of the time value (data-time attribute)
        timeFormat: 'hh:mm:ss.000',

        // format of the input value
        format: 'h:mm tt',

        // theme of the timepicker
        // 'red', 'purple', 'indigo', 'teal', 'green', 'dark'
        theme: 'blue',

        // determines if input is readonly
        readOnly: true,

        // determines if display value has zero padding for hour value less than 10 (i.e. 05:30 PM); 24-hour format has padding by default
        hourPadding: false,

        // determines if clear button is visible
        clearBtn: false
    });

    $('#endTime').mdtimepicker({

        // format of the time value (data-time attribute)
        timeFormat: 'hh:mm:ss.000',

        // format of the input value
        format: 'h:mm tt',

        // theme of the timepicker
        // 'red', 'purple', 'indigo', 'teal', 'green', 'dark'
        theme: 'blue',

        // determines if input is readonly
        readOnly: true,

        // determines if display value has zero padding for hour value less than 10 (i.e. 05:30 PM); 24-hour format has padding by default
        hourPadding: false,

        // determines if clear button is visible
        clearBtn: false
    });

    // timepicker 변경될 때마다 이벤트
    $('#startTime').mdtimepicker().on('timechanged', function(e){
        console.log("changedStartTime = " + e.value);
        // console.log(e.time);
    });
    $('#endTime').mdtimepicker().on('timechanged', function(e){
        console.log("changedEndTime = " + e.value);
        // console.log(e.time);
    });
}


