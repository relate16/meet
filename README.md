# 혹시 올 사람? 프로젝트
* 프로젝트 특징 :
  * 오늘, 그 다음날 한에서만 약속을 잡을 수 있고 참석할 수 있는 애플리케이션
    * 모임 가입을 하고 그 모임에 속하지 않고 문뜩 사람이 만나고 싶을 때 사용하는 애플리케이션
    * 그날 만나고 연이 끊어질 수 있기 때문에, 지속적으로 연을 이어가고 싶으면 번호를 물어봐야 하는 강제성(?) 효과 기대

* 사용된 라이브러리 :
  * leaflet
  * MDTimePicker


* 추가된 기능 :
  * 기본적인 기능 구현
    * DB에 등록된 mark를 map에 표시
    * map 클릭시 등록 팝업창 생성 및
    * 팝업창에서 mark 등록시 DB에 저장 및 map에 추가
    * map에서 mark 클릭시 등록된내용 표시
    * 상황별 시간 세팅 로직 추가 :
      * 2023-01-20 10:00am이 현재 시간일 때
      * 머무는 시간을 9:00am ~ 7:00am 으로 설정하면
      * 2023-01-21 9:00am ~ 2023-01-22 7:00am 으로 저장
    * 위치기능 사용시 현재 위치 기준으로 map 보여짐 :
      * navigator.geolocation.getCurrentPosition(success, error) 사용
    * mark 클릭시 뜨는 팝업에서 참석 누를 시, 올 수도 있는 사람 명 수 추가
    * DB에 등록된 mark의 떠나는 시간이 현재 시각보다 작으면 mark 삭제
    
### home화면
![meet](https://user-images.githubusercontent.com/70901928/214245481-b47c6f8d-1b22-4f75-91c1-fecb19884277.png)

### map에서 임의 지역 클릭시 등록화면
![meetregist](https://user-images.githubusercontent.com/70901928/214245537-d3c104c6-77c2-4c8c-bf4e-63602445d4a6.png)

### mark 팝업창
![meetpopup](https://user-images.githubusercontent.com/70901928/214245512-6afd2f72-945d-4314-988c-5e36a1ed9452.png)
### mark 팝업창 참석 클릭 후
![meetpopup2](https://user-images.githubusercontent.com/70901928/214245524-7d0439cd-ed4d-4162-8bd9-16e83a399354.png)


