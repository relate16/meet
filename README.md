# 혹시 올 사람? 프로젝트
* 프로젝트 특징 :
  * 오늘, 그 다음날 한에서만 약속을 잡을 수 있고 참석할 수 있는 애플리케이션
    * 모임 가입을 하고 그 모임에 속하지 않고 문뜩 사람이 만나고 싶을 때 사용하는 애플리케이션
    * 그날 만남이 끝나면 연이 끊어지기 때문에, 지속적으로 연을 이어가고 싶으면 번호를 물어봐야 하는 강제성(?) 효과 기대

* 사용된 라이브러리 :
  * leaflet
  * MDTimePicker


* 추가된 기능 :
  * map 기능 구현
    * DB에 등록된 mark를 map에 표시
    * map 클릭시 등록 팝업창 생성 및 mark 등록시 DB에 저장 및 map에 추가
    * map에서 mark 클릭시 등록된내용 표시
    * 상황별 시간 세팅 로직 추가 :
      * 2023-01-20 10:00am이 현재 시간일 때
      * 머무는 시간을 9:00am ~ 7:00am 으로 설정하면
      * 2023-01-21 9:00am ~ 2023-01-22 7:00am 으로 저장
    * 위치기능 사용시 현재 위치 기준으로 map 보여짐 :
      * navigator.geolocation.getCurrentPosition(success, error) 사용
    * 마커 팝업 참석 기능 추가
    * 마커 팝업 삭제 기능 추가
    * 마커 조건 검색 추가 - querydsl 사용
    * DB에 등록된 mark의 떠나는 시간이 현재 시각보다 작으면 mark 삭제
  * 화면 기능 구현 
    * top 템플릿 조각 추가 및 적용
  * 회원 기능 구현
    * 회원가입 및 로그인 구현
    * 로그인이 필요한 페이지 적용
  * 결제 기능 추가
    * 회원 캐시 충전 기능 적용
    
    
### home 화면
![leaflet_home](https://user-images.githubusercontent.com/70901928/221106597-7a5f9d85-6767-4323-b75b-7196f9932271.png)

### 회원가입 화면
![leaflet_signup](https://user-images.githubusercontent.com/70901928/221106617-72787ffb-2eb4-47e7-9761-ada29f38153e.png)

### 로그인 화면
![leaflet_login](https://user-images.githubusercontent.com/70901928/221106621-fbc87461-b941-4bc9-894d-9d32d22bf4ff.png)

### map에서 임의 지역 클릭시 등록화면
![meetregist](https://user-images.githubusercontent.com/70901928/214245537-d3c104c6-77c2-4c8c-bf4e-63602445d4a6.png)

### marker 팝업창
![meetpopup](https://user-images.githubusercontent.com/70901928/214245512-6afd2f72-945d-4314-988c-5e36a1ed9452.png)

### marker 팝업창 참석 및 삭제 기능
![leaflet_participate](https://user-images.githubusercontent.com/70901928/221106651-699ed51c-f5c9-4b7b-b0f8-bdcfa2481782.png)

### marker 조건 검색 기능
![leaflet_search](https://user-images.githubusercontent.com/70901928/221106674-a3deddd8-4757-4c3c-8547-493d800658c2.png)

### 캐시 충전 결제 기능
![충전1](https://user-images.githubusercontent.com/70901928/222941409-a244479d-fe5e-48d1-ab10-e591b393cd80.png)
![충전2](https://user-images.githubusercontent.com/70901928/222941412-19b8f935-58ce-486e-be7a-025da5fe5db8.png)
![충전3](https://user-images.githubusercontent.com/70901928/222941414-273976cc-f699-4a92-90c1-a31a04f7c0a7.png)
![충전4](https://user-images.githubusercontent.com/70901928/222941416-96967530-1afd-4a3b-8eb0-00d0c98ac43f.png)



