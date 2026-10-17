#!/bin/bash

# IFファイルの連携パスを作成.(IFファイル=pref.xxx)
export JAVA_DATA_FILE_PATH="/home/ryuma/dev/java/training_01/ch08/data/"
source ~/.bashrc
PREF_FILE="${JAVA_DATA_FILE_PATH}pref.csv"

# ファイルの属性情報は変化しない？
mkdir -p "/home/ryuma/dev/java/training_01/ch08/logs/$(date +%Y%m%d)"

# if [ ! -d "$TODAY_DIR" ]; then
#     mkdir -p "$TODAY_DIR"
#     echo "$(date '+%Y-%m-%d %H:%M:%S.%3N') [INFO] 作成: $TODAY_DIR"
# else
#     echo "$(date '+%Y-%m-%d %H:%M:%S.%3N') [INFO] 既存: $TODAY_DIR"
# fi

echo "$(date '+%Y-%m-%d %H:%M:%S.%3N') [INFO] アプリケーション実行開始:引数=$PREF_FILE"
java -cp bin Main "$PREF_FILE"

if [ $? -ne 0 ]; then
    echo "$(date '+%Y-%m-%d %H:%M:%S.%3N') [ERROR] アプリケーション実行失敗."
    exit 1
fi

echo "$(date '+%Y-%m-%d %H:%M:%S.%3N') [INFO] アプリケーション実行終了."
