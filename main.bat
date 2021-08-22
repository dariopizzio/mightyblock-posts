@ECHO OFF
REM batch file that builds and deploys the app container
ECHO Building image/container
call docker-compose -f deploy.yml build
ECHO Starting container
REM add -d to run it detached
call docker-compose -f deploy.yml up
