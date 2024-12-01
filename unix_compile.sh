#!/bin/bash

for folder in javafx-sdk*; do
    if [ -d "$folder" ]; then
        JAVA_FX_FOLDER="$folder"
        break
    fi
done

if [ -z "$JAVA_FX_FOLDER" ]; then
    echo "You need the JavaFX SDK to compile this project"
    exit 1
fi

if [ ! -d bin ]; then
    mkdir bin
fi

echo "Compiling .java files..."
javac --module-path "$JAVA_FX_FOLDER/lib" --add-modules javafx.controls -d bin src/*.java
if [ $? -ne 0 ]; then
    echo "Compilation failed"
    exit 1
fi

echo "Compilation successful"