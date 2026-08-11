# Java Essential × Data Lab

Java 필수 개념 퀴즈와 Generic/Lambda 기반 ETL 코드 미션을 제공하는 최소 Spring Boot 웹 앱입니다. 회원별 답안과 통과 여부를 DB에 저장합니다.

## 제공 기능

- Java 필수 개념 퀴즈 8개: 실행 흐름, 메서드, 객체, 컬렉션, 제네릭, 람다, 예외, Spring 계층
- ETL 코드 미션 5개: `filter`, `map`, `groupingBy`, `max`, 제네릭 결과 타입
- 회원 아이디 중복 확인 및 DB unique 제약
- BCrypt 비밀번호 해시 저장과 로그인
- 사용자별 답안, 통과 여부, 수정 시각 저장
- 코드 자동 임시 저장(입력 후 650ms) 및 단계별 채점
- H2 로컬 DB / PostgreSQL 배포 DB 지원

> 코드 미션 채점은 브라우저에서 필수 문법과 구조를 검사하는 학습용 채점입니다. 제출된 Java 코드를 서버에서 실행하지 않으므로 원격 코드 실행 위험이 없습니다. 실제 컴파일 채점기는 별도의 격리 실행 환경이 필요합니다.

## 구조

```text
java-quiz-web/
├── pom.xml                         # Maven 의존성
├── Dockerfile                      # 배포용 컨테이너
├── compose.yaml                    # 앱 + PostgreSQL 로컬 실행
└── src/
    ├── main/java/com/skala/quiz/
    │   ├── JavaQuizApplication.java
    │   ├── api/LearningApiController.java
    │   ├── domain/Learner.java
    │   ├── domain/Progress.java
    │   └── repository/...          # DB 접근
    └── main/resources/
        ├── application.properties  # DB/서버 환경변수
        └── static/
            ├── index.html          # 화면 구조
            ├── css/app.css         # 디자인
            └── js/app.js           # 퀴즈·채점·API 호출
```

브라우저 요청 흐름:

```text
HTML/CSS/JS → /api → Controller → Repository → H2 또는 PostgreSQL
```

DB 관계:

```text
learners 1 ─── N progress
```

`progress`에는 `(learner_id, question_key)` unique 제약이 있어 한 회원의 한 문제에는 하나의 최신 답안만 존재합니다.

## 가장 빠른 실행

필요 환경: Java 21, Maven 3.9+

```bash
cd java-quiz-web
mvn spring-boot:run
```

브라우저에서 <http://localhost:8080>을 엽니다. 데이터는 `java-quiz-web/data/`의 H2 파일에 유지됩니다.

테스트:

```bash
mvn test
```

초기화가 필요하면 서버를 종료한 뒤 `data/` 폴더를 삭제합니다. 이 작업은 모든 로컬 회원과 답안을 삭제합니다.

## PostgreSQL로 실행

Docker가 있다면:

```bash
docker compose up --build
```

앱만 직접 실행하고 외부 PostgreSQL을 연결하려면 다음 환경변수를 설정합니다.

| 환경변수 | 예시 | 설명 |
|---|---|---|
| `PORT` | `8080` | 서버 포트 |
| `DB_URL` | `jdbc:postgresql://host:5432/javaquiz` | JDBC 연결 주소 |
| `DB_USERNAME` | `javaquiz` | DB 사용자 |
| `DB_PASSWORD` | `change-me` | DB 비밀번호 |
| `H2_CONSOLE` | `false` | 배포 환경에서는 false |

## 웹 배포 최소 절차

Docker를 지원하는 호스팅 서비스와 PostgreSQL DB를 준비합니다.

1. 이 프로젝트를 Git 저장소에 push합니다.
2. 배포 서비스의 root directory를 `java-quiz-web`으로 지정합니다.
3. Dockerfile 배포를 선택합니다.
4. PostgreSQL을 만들고 위의 `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`를 설정합니다.
5. `H2_CONSOLE=false`를 설정하고 배포합니다.
6. 배포 URL에서 가입 → 퀴즈 풀이 → 로그아웃/재로그인 후 답안 복원을 확인합니다.

H2는 단일 인스턴스 로컬 학습에는 충분하지만 컨테이너가 교체될 수 있는 웹 배포 환경에는 적합하지 않습니다. 배포 시에는 PostgreSQL을 사용하세요.

## API

| Method | URL | 기능 |
|---|---|---|
| `GET` | `/api/users/check?username=...` | 아이디 중복 확인 |
| `POST` | `/api/users` | 회원 가입 |
| `POST` | `/api/sessions` | 로그인 및 접근 토큰 발급 |
| `GET` | `/api/progress` | 내 답안 전체 조회 |
| `PUT` | `/api/progress/{questionKey}` | 내 답안 저장 |

가입/로그인 요청:

```json
{"username":"data_rookie","password":"secret123"}
```

진행 API는 로그인 응답의 토큰을 `X-Access-Token` 헤더로 전송합니다. 프런트엔드는 이를 자동 처리합니다.

## 학습할 때 볼 파일 순서

1. `static/index.html`: 화면에 어떤 영역이 있는지
2. `static/js/app.js`: 클릭 → 채점 → API 저장 흐름
3. `LearningApiController.java`: HTTP 요청을 Java가 받는 방식
4. `Learner.java`, `Progress.java`: Java 객체가 DB 테이블이 되는 방식
5. Repository: SQL을 직접 쓰지 않고 기본 저장/조회하는 방식

처음부터 모든 코드를 작성할 필요는 없습니다. 각 파일이 맡은 역할과 `브라우저 → API → DB → API → 브라우저` 흐름을 설명하는 것을 첫 목표로 삼으세요.
