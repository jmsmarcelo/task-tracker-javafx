#!/bin/bash

if [ ! -d bin ]; then
    mkdir bin
fi

echo "compiling .java files..."
javac -d bin src/*.java

if [ $? -ne 0 ]; then
    echo "compilation failed"
    exit 1
fi

echo "compilation successful"

read -p "press Enter to exit..."