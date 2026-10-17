#!/bin/bash

# コンパイル
#javac -d bin src/Main.java
javac -d bin src/*.java

# 直前の実行結果の判定
if [ $? -ne 0 ]; then
    echo "[ERROR] Java compile failed."
    exit 1
fi
echo "[INFO] Compile successful."

# 実行
java -cp bin Main /home/ryuma/dev/java/training_01/ch07/data/pref.csv

# 直前の実行結果の判定
if [ $? -ne 0 ]; then
    echo "[ERROR] Application execution failed."
    exit 1
fi
echo "[INFO] Application completed successfully."
