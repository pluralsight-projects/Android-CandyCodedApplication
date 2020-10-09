FROM thyrlian/android-sdk:2.6

ENTRYPOINT ["bash"]

# We need permision to the /opt/android-sdk before we define /src/app as our working directory
RUN groupadd projects && useradd --no-create-home -g projects projects && \
  chown -R projects:projects /opt

WORKDIR /src/app

COPY . .

RUN \
  chown -R projects:projects /src/app && \
  mkdir -p /home/projects && \
  chown -R projects:projects /home/projects

USER projects

# If we don't exclude the tests, they get run during the build
# We want all the dependencies installed (and even an initial build)
# But we don't wanna run the tests since they fail initially
RUN ["./gradlew", "build", "-x", "test"]
