@echo off
REM EndsAndBends - Launcher for Windows (French)
REM Requires Java 21 or higher

REM If you want to force a specific Java version, uncomment the 2 lines below
REM and adjust the path to your Java installation
REM set "JAVA_HOME=C:\Program Files\Java\jdk-21"
REM set "PATH=%JAVA_HOME%\bin;%PATH%"

java -jar EndsAndBends.jar -nl fr
if errorlevel 1 (
    echo.
    echo ERREUR: Echec du lancement d'EndsAndBends
    echo Assurez-vous que Java 21 ou superieur est installe
    pause
)
