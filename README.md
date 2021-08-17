
# **Direction Search** : 🚑신호 체계 기반 긴급 차량 교통 안내 앱🚒
### 🛠OPEN INFRA DEVELOPMENT COMPETITION🛠 | Team:ESC
<br/>

## **📝 프로젝트 소개**

사용자를 **긴급 출동 차량**과 **일반 차량** 두 분류로 나누어 **긴급 출동 차량**에는 교통 신호 정보를 기반으로 출발지로부터 요구조자의 위치까지의 **최단 경로**를 계산해 경로 안내를 제공, 신속하게 현장에 도착할 수 있도록 하는 서비스를 구현하였다. 또한 **일반 차량**에게는 경로 안내를 동일하게 제공하되 경로 안내를 제공받는 사용자의 위치와 긴급 출동 차량의 위치를 실시간으로 계산해 경로가 겹치거나 긴급 차량과의 거리가 가까운 경우 일반 차량에 **알림**도 함께 제공할 수 있도록 하는 서비스를 구현하여 두 분류의 차량이 기능을 제공받는 앱서비스을 개발하였다.

- 메인화면
<img src="https://user-images.githubusercontent.com/70185844/129472308-959ad539-0711-4041-b6d5-a5e710ae4702.jpeg" width="30%" height="30%">

- 응급차량/일반운전자로 접속

<img src="https://user-images.githubusercontent.com/70185844/129472306-4d2b8140-e18a-46d6-8d05-cbf6e634e749.jpeg" width="30%" height="30%"> <img src="https://user-images.githubusercontent.com/70185844/129472305-e2a0eab7-7b79-40b9-bb2c-386961f3d289.jpeg" width="30%" height="30%"> 

- 길 안내 화면
<img src="https://user-images.githubusercontent.com/70185844/129472309-791e83a8-a1e4-4f56-9c1b-073209a2bd53.jpeg" width="30%" height="30%">
<br/>

## **📚 Tech Stack**
```
frontend : React Native, Expo
backend : Spring
database : MySQL
model(NAVER Api) : Geocoding, Directions 5
api documentation/test : postman
cloud : NAVER CLOUD PLATFORM
```
| frontend                                                                                                                                                                                                                   | backend                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   | etc                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://img.shields.io/badge/React%20Native-61DAFB?style=flat-square&logo=React&logoColor=white"> <img src="https://img.shields.io/badge/Expo-000020?style=flat-square&logo=Expo&logoColor=white"> <br/> <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=JavaScript&logoColor=white"> | <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white"> <img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"> <br/> <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"> <img src="https://img.shields.io/badge/NAVER%20API-03C75A?style=flat-square&logo=Naver&logoColor=white"> | <img src="https://img.shields.io/badge/NAVER%20CLOUD%20PLATFORM-03C75A?style=flat-square&logo=Naver&logoColor=white"> <br/> <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=Postman&logoColor=white"> <img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"> <br/> <img src="https://img.shields.io/badge/Visual%20Studio%20Code-007ACC?style=flat-square&logo=Visual%20Studio%20Code&logoColor=white">
<br/>

## **💻  설치**

### a. Expo 설치
[공식 사이트](https://docs.expo.dev)의 가이드를 따라서 Expo를 설치한다.

### b. 이 Repository를 Clone 한다.
```shell
git clone https://github.com/OIDC-ESC/Direction_search
```
<br/>

## **💻  실행**

### a. 다음 내용을 넣어 **putty** 실행
```shell
ip: 175.106.99.29
id: root
pw: D5Y+MRn3Hcyd
```

### b. putty에 localtunnel 실행
```shell
$ lt --port 5000 --subdomain oidcesc --print-requests
```

### c. 새로운 putty창에 서버 구동
```shell
$ java -jar /var/www/Direction_search/backend/target/backend-0.0.1-SNAPSHOT.jar
```

### d. **Direction_search/frontend**에서 다음을 실행

```shell
npm run ios
npm run android
```
혹은
```shell
npm start
```
입려 후 expo 사이트를 통해 **Emulator(Android)/Simulator(ios)/Expo App**을 이용해 앱 실행
<br/>

## **🔒Team:ESC Member**

name | role | github |
--- | --- | --- | 
김유정 | AI, Backend | [@kimyu0218](https://github.com/kimyu0218) |
박소현 | AI, Backend | [@Sohyun-Dev](https://github.com/Sohyun-Dev)|
김태영 | AI, Frontend | [@EHOia](https://github.com/EHOia)|
주효정 | AI, Frontend | [@jhj2713](https://github.com/jhj2713)|

</br></br></br>
