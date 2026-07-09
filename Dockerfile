# 1. 빌드된 jar 파일을 가볍게 실행할 수 있는 JDK 21 경량화 가상 환경을 가져옵니다.
FROM alpine:3.20

# 2. 자바 21 실행 환경(JRE)을 가상 환경 내부에 설치합니다.
RUN apk add --no-cache openjdk21-jre

# 3. 컨테이너 내부에서 작업할 기본 디렉토리를 생성하고 이동합니다.
WORKDIR /app

# 4. Gradle 빌드로 생성된 진짜 jar 파일 실물을 컨테이너 내부로 복사합니다.
# 💡 AlgoSync 프로젝트 빌드 시 생성되는 jar 파일 매칭
COPY build/libs/*-SNAPSHOT.jar app.jar

# 5. 컨테이너가 가동될 때 스프링 부트를 자동으로 실행하는 명령어입니다.
ENTRYPOINT ["java", "-jar", "app.jar"]