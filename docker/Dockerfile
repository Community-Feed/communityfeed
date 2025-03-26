# 1단계: 빌드용 이미지
FROM gradle:8.4.0-jdk17 AS builder
WORKDIR /app

# Gradle Wrapper 복사 (캐시 활용 목적)
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./

# 의존성 캐시만 먼저 다운 (옵션)
RUN ./gradlew build -x test || true

# 전체 프로젝트 복사 후 빌드
COPY . .
RUN ./gradlew clean build -x test

# 2단계: 실행용 이미지
FROM eclipse-temurin:17
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 외부 포트 노출
EXPOSE 8081

# 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]
