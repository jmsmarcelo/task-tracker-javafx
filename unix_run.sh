#!/bin/bash

if [ ! -d bin ]; then
    mkdir bin

    echo "compiling .java files..."
    javac -d bin src/*.java

    if [ $? -ne 0 ]; then
        echo "compilation failed"
        exit 1
    fi

    echo "compilation successful"
fi

cd bin
clear
bash