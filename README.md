![image](https://user-images.githubusercontent.com/100582309/197399779-697672ed-0bdf-4b94-9211-a988991e5e85.png)

<h3 align="center"> 위치, 사용자, 게시글 기반 대용량 데이터 검색 서비스 ChadDa입니다. </h2>

<br><br>

# 💡 **프로젝트 소개**

### 팀 노션
ChadDa 서비스의 상세 내용 및 개발 일지를 보고 싶다면?<br>
팀 워크스페이스 바로가기 👉🏻 **[[Notion] 🔍 ChadDa](https://chadda.notion.site/ChadDa-14a7282da6954f529572a538953664d0)**

<br>

### 구현 기능

1. 회원 관련
    - 회원가입
    - 로그인
    - 로그아웃
2. 검색
    - 위치 검색
    - 게시글 검색
    - 유저 검색
3. 상세 검색/조회
    - 위치 정보가 포함된 게시글 검색
    - 게시글을 작성한 유저의 프로필 조회
    - 유저가 작성한 게시글들 조회

<br>

### 사용한 데이터

- 약 1,300만 건의 게시글 데이터
- 약 200만 건의 유저 데이터
- 약 75만 건의 위치 데이터


<br><br>


# 💡 **프로젝트 설명 및 주요 기능**


> 대용량의 위치, 사용자, 게시글 기반 데이터를 빠르고 안정적으로 검색할 수 있도록 하는 플랫폼
> 

### 🔹 Fulltext Index를 활용한 빠른 대용량 텍스트 검색

      -  ngram + 쿼리문을 활용하여 연관 키워드 중 정확도 순으로 정렬하여 출력

      -  방대한 데이터 양으로, `%LIKE%` 쿼리문으로는 속도 및 기술적인 한계가 존재

      -  동일한 환경(데이터 8만개, limit 10)에서 테스트시 `Fulltext Search (ngram)`가 `%LIKE%` 대비 검색 속도 230% 향상

### 🔹 Redis를 활용한 캐싱

      -  서비스의 규모가 커짐에 따라 사용자 정보를 불러올 때 매번 DB에 접속하는 방식에서 성능적인 이슈 발생 가능성 ↑

      -  업데이트가 자주 일어나지 않고 반복적으로 동일한 결과를 출력하는 경우가 많음 → 메모리에 저장(캐싱)해두면 DB의 부하는 감소하고,  속도는 향상됨 

      -  Local Cache 와 Global Cache 중 서버 증설의 확장성을 위해 Global Cache 를 사용하기로 결정

### 🔹 Kafka로 이벤트 기반 데이터 처리 방식 도입

      -  차후 안정성 향상을 위하여 DB replication 필요

      -  차후 MSA 도입을 대비하여 이벤트 단위로 동작할 수 있는 EDM을 구현해보고자 함

      -  메시지 큐 RabbitMQ와 비교 후, 대용량 트래픽 처리에 유리한 Kafka로 결정

      -  실무 MSA 구조에서 거의 필수적으로 볼 수 있는 로그 저장용 kafka를 구현하여 Kafka 메시지를 각각 MySQL, Elastic Search에 로깅하는 방식으로 구현

### 🔹 ELK stack을 활용한 로깅 및 모니터링

      -  개발자라면 실제 운영을 위해 반드시 마주치고 익숙해져야만 하는 로깅에 대하여 구현, 경험해보고자 함

      -  데이터 보관 및 분석 역할을 담당하는 Elastic Search는 거의 실시간(Real-Time)에 가깝게 데이터를 처리할 수 있음

      -  가장 널리 쓰이고 있고 편리한 로깅 기술 스택인 ELK를 채택하여 로그 수집 및 모니터링 진행

### 🔹 Database Lock을 활용한 동시성 제어

      -  Optimistic Lock을 활용하여 동시성 컨트롤

      -  여러 방식의 테스트를 통해 동시성 제어 및 데이터 충돌 문제가 자주 일어나지 않도록 설정

### 🔹 Github Actions + Docker, Docker-compose를 활용한 CI/CD

      -  배포 자동화를 통해 효율적인 협업 및 작업 환경을 구축하기 위함

      -  간단한 CI/CD기능을 사용할 예정이라 Github Actions 로 결정

      -  ELK (Elastic Search, Logstash, Kibana)는 모두 연관되어 동작해야 함 → docker-compose로 묶어 구축함

<br><br><br>


# 아키텍쳐
![image](https://user-images.githubusercontent.com/100582309/198054245-6e4565ed-35b5-4164-80d5-dddff0cfd0fe.png)

<br><br><br>

# 기술스택
|분류|기술|
| :-: |:- |
|Language|<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white">|
|Framework|<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"> <img src="https://img.shields.io/badge/Springboot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white">|
|Build Tool|<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">|
|DB|<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">|
|Server|<img src="https://img.shields.io/badge/aws ec2-232F3E?style=for-the-badge&logo=AmazonAWS&logoColor=white">|
|CI/CD|<img src="https://img.shields.io/badge/GitHub Actions-2088FF?style=for-the-badge&logo=GitHub Actions&logoColor=white"> <img src="https://img.shields.io/badge/docker-00A6E4?style=for-the-badge&logo=docker&logoColor=white">|
|Caching|<img src="https://img.shields.io/badge/redis-B71C1C?style=for-the-badge&logo=redis&logoColor=white">|
|Logging|<img src="https://img.shields.io/badge/kafka-3498DB?style=for-the-badge&logo=apachekafka&logoColor=white"> <img src="https://img.shields.io/badge/elasticsearch-E5A505?style=for-the-badge&logo=elasticsearch&logoColor=white"> <img src="https://img.shields.io/badge/logstash-1ABC9C?style=for-the-badge&logo=logstash&logoColor=white&textColor=white"> <img src="https://img.shields.io/badge/kibana-6DB33F?style=for-the-badge&logo=kibana&logoColor=white">|


<br><br><br>


# 설계
<details>
    <summary> <b>📕 DB 설계 (ERD)</b> </summary>
    <img src="https://user-images.githubusercontent.com/100582309/198495585-852ef4e6-70cb-44ae-b3db-9ee42214750f.png">
    <div markdown="1">
        
> 자세히 보러 가기 👉🏻 [**[Notion] 📕 DB 설계**](https://www.notion.so/chadda/DB-f384089335da42ee88687ddb49ff60ee)
>
        
<br>
        
</div>
</details>
<details>
    <summary> <b>📝 API 설계</b> </summary>
    <img src="https://user-images.githubusercontent.com/100582309/198497364-2869032e-b679-46e1-989f-7c9ffb763fda.png">
    <div markdown="1">
        
> 자세히 보러 가기 👉🏻 [**[Notion] 📝 API 설계**](https://www.notion.so/chadda/API-5c17ce874b52451dbc0dbe9dce9b6420)
>
        
<br>
        
</div>
</details>

<br><br><br>


# 트러블 슈팅
<details>
    <summary> <b> 검색 성능 개선</b> </summary>
    <div markdown="1">

- **도입 이유 및 문제 상황**
       
  : 방대한 데이터 양으로, `%LIKE%` 쿼리문으로는 속도 및 기술적인 한계가 존재함
        
- **해결방안 고민**
  - Fulltext index 
  - Fulltext index with parser ngram
        
- **의견 결정**
   * 게시글에 들어가는 키워드 검색, 위치에 들어가는 키워드 검색 등 검색 결과의 퀄리티를 위하여 Fulltext Index with parser ngram이 더 좋겠다고 판단
   * 성능 테스트 결과 큰 차이가 없을 것으로 판단
        
    ⇒ ngram + 쿼리문을 활용하여 연관 키워드 중 정확도 순으로 정렬하여 출력하기로 결정
        
<br>
        
> 자세히 보러 가기 👉🏻 [**[Notion] 🚀 검색 성능 개선**](https://www.notion.so/chadda/dd456aa3a0d2434e93bda3437e08280c)
> 
<br>
</div>
</details>
<details>
    <summary> <b> 부하 테스트(feat. JMeter, Ngrinder)</b> </summary>
    <br>
    <div markdown="1">
        
> 자세히 보러 가기 👉🏻 [**[Notion] 🦾 부하 테스트**](https://www.notion.so/chadda/feat-JMeter-080f19c0ec924f6e85fa5044ae336592)
>
        
<br>
        
</div>
</details>
<details>
    <summary> <b> 캐싱 (feat. Redis)</b> </summary>
    <br>
    <div markdown="1">
        
> 자세히 보러 가기 👉🏻 [**[Notion] 💾 캐싱**](https://www.notion.so/chadda/feat-Redis-9eaf78a765474c4cb831330c57f7ef8e)
>
        
<br>
        
</div>
</details>
<details>
    <summary> <b> 동시성 제어(feat. DB, Redis)</b> </summary>
    <br>
    <div markdown="1">
        
> 자세히 보러 가기 👉🏻 [**[Notion] 🔐 동시성 제어**](https://www.notion.so/chadda/feat-DB-Lock-a105ee025b3a49ee83a8d28ee7c3c60f)
>
        
<br>
        
</div>
</details>
<details>
    <summary> <b> 로깅 (feat. Kafka, ELK)</b> </summary>
    <br>
    <div markdown="1">
        
> 자세히 보러 가기 👉🏻 [**[Notion] 🧾 로깅**](https://www.notion.so/chadda/feat-AOP-Kafka-ELK-4c5ede1ae12541fdab278b17c7a5a04f)
>
        
<br>
        
</div>
</details>

<br><br><br>

# 프로젝트 관리
<details>
    <summary> <b>CI/CD</b> </summary>
    <br>
    <div markdown="1">
        
> 자세히 보러 가기 👉🏻 [**[Notion] 🐳 CI/CD**](https://www.notion.so/chadda/CI-CD-feat-Github-Actions-Docker-f645688df08047788ecdd9b2dc01b6a2)
>
        
<br>
        
</div>
</details>
<details>
    <summary> <b>Conventions</b> </summary>
    <br>
    <div markdown="1">
        
> 자세히 보러 가기 👉🏻 [**[Notion] ✨ Conventions**](https://www.notion.so/chadda/Conventions-03cb79380ea6484cb7c4a49d24b62771)
>
        
<br>
        
</div>
</details>

<br><br><br>


# 팀원

|이름|포지션|분담|@ Email|Github|
|------|------|------|------|------|
|이혜민|BackEnd|검색(쿼리 최적화)<br>부하 테스트<br>캐싱<br/>|hmlee5938@gmail.com|https://github.com/hm5938|
|조성윤|BackEnd|검색(쿼리 최적화)<br>부하 테스트<br>동시성 제어|syzzang21c@gmail.com|https://github.com/Cho-El|
|송민진|BackEnd|검색(쿼리 최적화)<br>부하 테스트<br>로깅|m_6595@naver.com|https://github.com/deingvelop|

