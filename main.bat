@ECHO OFF
REM batch file that builds the application and deploys the app container
ECHO Generating jar
IF EXIST .\app.jar DEL .\app.jar
call gradle build
COPY .\build\libs\posts-0.0.1-SNAPSHOT.jar .\
RENAME .\posts-0.0.1-SNAPSHOT.jar app.jar
ECHO Building image/container
call docker-compose -f deploy.yml build
ECHO Starting container
REM add -d to run it detached
call docker-compose -f deploy.yml up
