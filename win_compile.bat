@echo off

if not exist bin (
    mkdir bin
)

echo compiling .java files...
javac -d bin src\*.java

if errorlevel 1 (
    echo compilation failed
    pause
    exit /b
)

echo compilation successful
pause