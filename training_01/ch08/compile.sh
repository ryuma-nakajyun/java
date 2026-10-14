#!/bin/bash

echo "$(date '+%Y-%m-%d %H:%M:%S.%3N') [INFO] コンパイル開始."

javac -d bin src/*.java

if [ $? -ne 0 ]; then
    echo "$(date '+%Y-%m-%d %H:%M:%S.%3N') [ERROR] Java コンパイル失敗."
    exit 1
fi

echo "$(date '+%Y-%m-%d %H:%M:%S.%3N') [INFO] コンパイル成功."
