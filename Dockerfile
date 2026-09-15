# --- Build stage ---
# Uses the full JDK because compiling needs javac, not just a runtime.
# This stage is thrown away after the build - only the JAR it produces
# gets copied into the final image.
FROM eclipse-temurin:26-jdk AS build

WORKDIR /app

# Copy the Gradle wrapper and build files first, then download dependencies.
# Docker caches this layer, so as long as build.gradle doesn't change,
# "docker build" won't re-download all dependencies on every source change.
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon || true

# Now copy the actual source code and build the runnable JAR.
# --no-daemon: don't keep a background Gradle process alive in the container.
COPY src ./src
RUN ./gradlew clean bootJar -x test --no-daemon

# --- Run stage ---
# Uses a JRE-only image (no compiler, no build tools) - smaller and
# more secure since it has less attack surface than the JDK image.
FROM eclipse-temurin:26-jre AS run

WORKDIR /app

# Only the built JAR is copied over from the build stage - none of the
# source code, Gradle wrapper, or downloaded build dependencies end up
# in the final image.
COPY --from=build /app/build/libs/*.jar app.jar

# The app listens on 8080 (see application.properties / Spring Boot default).
EXPOSE 8080

# Run the Spring Boot app as the container's main process.
ENTRYPOINT ["java", "-jar", "app.jar"]
