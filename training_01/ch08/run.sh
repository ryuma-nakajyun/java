#!/bin/bash

# コンパイル
echo "[INFO] コンパイル開始."
javac -d bin src/*.java

# 直前の実行結果の判定
if [ $? -ne 0 ]; then
    echo "[ERROR] Java コンパイル失敗."
    exit 1
fi
echo "[INFO] コンパイル成功."

# 実行
# TODO:ファイルパスは環境変数などにすべき
echo "[INFO] アプリケーション実行開始."
java -cp bin Main /home/ryuma/dev/java/training_01/ch07/data/pref.csv

# 直前の実行結果の判定
if [ $? -ne 0 ]; then
    echo "[ERROR] アプリケーション実行失敗."
    exit 1
fi
echo "[INFO] アプリケーション実行終了."
