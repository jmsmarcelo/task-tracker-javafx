@echo off

for /d %%F in (javafx-sdk*) do (
    set "JAVA_FX_FOLDER=%%F"
    goto :found
)
echo You need the JavaFX SDK to run this application
goto :end

:found
if not exist bin (
    mkdir bin
    echo compiling .java files...
    javac --module-path %JAVA_FX_FOLDER%\lib --add-modules javafx.controls -d bin src\*.java
    if errorlevel 1 (
        echo compilation failed
        goto :end
    )
    echo compilation successful
    pause
)
if not exist bin\TaskApp.class (
    echo compiling .java files...
    javac --module-path %JAVA_FX_FOLDER%\lib --add-modules javafx.controls -d bin src\*.java
    if errorlevel 1 (
        echo compilation failed
        goto :end
    )
    echo compilation successful
    pause
)
cd bin
start "" javaw --module-path ..\%JAVA_FX_FOLDER%\lib --add-modules javafx.controls TaskApp
exit

:end
pause