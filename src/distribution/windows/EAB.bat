@echo off
REM EndsAndBends - Launcher for Windows
REM Requires Java 21 or higher

REM If you want to force a specific Java version, uncomment the 2 lines below
REM and adjust the path to your Java installation
REM set "JAVA_HOME=C:\Program Files\Java\jdk-21"
REM set "PATH=%JAVA_HOME%\bin;%PATH%"

java -jar EndsAndBends.jar
if errorlevel 1 (
    echo.
    echo ERROR: Failed to launch EndsAndBends
    echo Make sure Java 21 or higher is installed
    pause
)
