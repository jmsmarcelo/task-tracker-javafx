@echo off

for /d %%F in (javafx-sdk*) do (
    set "JAVA_FX_FOLDER=%%F"
    goto :found
)
echo You need the JavaFX SDK to compile this project
goto :end

:found
if not exist bin (
    mkdir bin
)
echo compiling .java files...
javac --module-path %JAVA_FX_FOLDER%\lib --add-modules javafx.controls -d bin src\*.java
if errorlevel 1 (
    echo compilation failed
    goto :end
)
echo compilation successful

:end
pause