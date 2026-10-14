#!/bin/bash

CSV_FILE=${1:-"/home/ryuma/dev/java/training_01/ch08/data/pref.csv"}

echo "$(date '+%Y-%m-%d %H:%M:%S.%3N') [INFO] アプリケーション実行開始."

java -cp bin Main "$CSV_FILE"

if [ $? -ne 0 ]; then
    echo "$(date '+%Y-%m-%d %H:%M:%S.%3N') [ERROR] アプリケーション実行失敗."
    exit 1
fi

echo "$(date '+%Y-%m-%d %H:%M:%S.%3N') [INFO] アプリケーション実行終了."
