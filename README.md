# 🤖 알봇 (AlgoSync)

<h3 align="center">프로그래머스 맞춤형 AI 알고리즘 코드 리뷰 및 학습 관리 서비스</h3>

<p align="center">
  <img src="https://img.shields.io/badge/Manifest--V3-blue?style=flat-for-the-badge" alt="Manifest V3" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-for-the-badge&logo=springboot" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/LangChain4j-0.x-orange?style=flat-for-the-badge" alt="LangChain4j" />
  <img src="https://img.shields.io/badge/AWS-EC2-FF9900?style=flat-for-the-badge&logo=amazonwebservices" alt="AWS EC2" />
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-for-the-badge&logo=postgresql" alt="PostgreSQL" />
</p>

---

## 🌟 프로젝트 소개
알봇(AlgoSync)은 프로그래머스(Programmers) 플랫폼에서 알고리즘 문제를 풀이하는 개발자들의 효율적인 학습을 돕기 위해 개발된 **크롬 익스텐션 & 백엔드 분석 서버 시스템**입니다. 

작성 중인 소스코드를 크롬 익스텐션이 안전하게 추출하여 백엔드로 전송하면, AI 분석 엔진이 코드의 **시간/공간 복잡도 분석** 및 **구체적인 리팩토링 방향성**을 제시하여 실시간 피드백을 제공합니다.

---

## 🛠️ 기술 스택 (Tech Stack)

| 구분 | 기술 스택 | 상세 활용 목적 |
| :--- | :--- | :--- |
| **Backend** | `Java`, `Spring Boot 3.x` | 백엔드 분석 서버 구축 및 API 비즈니스 로직 처리 |
| **Extension** | `JavaScript (ES6)`, `HTML5` | Chrome Manifest V3 기반 클라이언트, DOM 파싱 및 UI 구현 |
| **AI Orchestration** | `LangChain4j` | 이기종 LLM API(Gemini, Nvidia AI) 연결 추상화 및 Fallback 흐름 제어 |
| **Database** | `PostgreSQL` | 유저 데이터 및 문제 풀이 히스토리, AI 피드백 이력 저장 |
| **Infrastructure** | `AWS EC2` | 클라우드 환경 배포 및 운영 안정성 확보 |

---

## 🛠️ 시스템 아키텍처 (System Architecture)

```text
[ 프로그래머스 풀이 화면 ] 
         │ (DOM 구조 파싱 및 소스코드 추출 via inject.js)
         ▼
[ 알봇 크롬 익스텐션 (Manifest V3) ]
         │ 
         │ (HTTPS POST / JSON API 통신)
         ▼
[ AWS EC2 / Spring Boot 백엔드 서버 ] 
         │
         ├──► [ 1차 시도: Google AI API (Gemini) ] 
         │             │ (503 에러 발생 시 즉시 Failover 작동)
         │             ▼
         └──► [ Fallback: Nvidia AI API ]
         │
         ▼ (사용자 풀이 정보 및 AI 피드백 적재)
[ PostgreSQL 데이터베이스 ]
```

---

## 🚀 핵심 기능 (Key Features)

* **원클릭 소스코드 추출:** 번거로운 복사-붙여넣기 과정 없이, 프로그래머스 화면에서 아이콘 클릭 한 번으로 작성 중인 코드를 백엔드로 안전하게 전달합니다.
* **AI 기반 다차원 정밀 리뷰:** 단순한 동작 여부 판단을 넘어, 알고리즘 최적화를 위한 시간 복잡도 및 공간 복잡도 분석과 개선 방향성 가이드를 제시합니다.
* **학습 히스토리 추적:** 분석했던 알고리즘 문제 정보와 AI 피드백 내역을 데이터베이스에 적재하여, 사용자가 본인의 오답 및 발전 흐름을 지속적으로 모니터링할 수 있도록 돕습니다.

---

## 📂 디렉토리 구조 (Directory Structure)
```text
├── ext-client/          # Chrome Extension (Manifest V3 Client)
│   ├── manifest.json    # Extension 설정 파일 (권한 및 주입 규칙 정의)
│   ├── content.js       # 정답 팝업 확인 후 background.js에 전
│   ├── popup.html       # 알봇 도우미 팝업 UI Layout
│   ├── popup.js         # 사용자 이벤트 제어
│   ├── inject.js        # 프로그래머스 문제 영역 DOM 파서
│   ├── background.js    # AI api 동작
│   └── icons/           # 확장 프로그램 아이콘
│       └── png
└── server-backend/      # Spring Boot 백엔드 서버 시스템
    ├── src/
    │   ├── main/java/.../controller/  # 분석 요청 API 컨트롤러
    │   ├── main/java/.../dto/         # 이기종 LLM 바인딩을 위한 공통 DTO
    │   ├── main/java/.../mapper/      # DB 매핑을 위한 MyBatis Mapper 인터페이스
    │   ├── main/java/.../service/     # LangChain4j 연동 및 Failover 비즈니스 로직
    │   └── main/resources/
    │       ├── mapper/                # SQL 쿼리가 작성된 MyBatis XML 폴더
    │       │   └── *.xml              # 분석 이력 저장 및 조회용 mapper.xml 파일들
    │       └── application.yml        # DB 연결 및 MyBatis 경로 설정 파일
    └── build.gradle
```
