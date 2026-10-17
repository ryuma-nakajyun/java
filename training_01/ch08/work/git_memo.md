# Git Push エラー（non-fast-forward）トラブルシューティング
現象
git push
実行時
! [rejected] main -> main (non-fast-forward)
error: failed to push some refs

## 原因
GitHub側の main に、自分のローカルに存在しないコミットがある。
GitHub
A - B - C
Local
A - B - D

この状態で、git push、すると拒否される。

## 現在の状態確認
git status
git log --oneline --graph --all -10
git remote -v

ケース1：未コミットの変更がある
エラー
cannot pull with rebase:
You have unstaged changes

対処
stashして退避
git stash

確認
git stash list

リモートを取り込む
git pull --rebase origin main

成功例
Successfully rebased and updated refs/heads/main.

退避した変更を戻す
git stash pop
コミット
git add .
git commit -m "修正"

Push
git push origin main

ケース2：ローカル変更を先にコミットする
git add .
git commit -m "作業中"
git pull --rebase origin main
git push origin main

ケース3：GitHub側を無視して上書き
⚠️ リモート履歴を破壊する
git push --force origin main
または
git push -f origin main

## よく使うコマンド一覧
| コマンド    | 意味      |
| ------- | --------- | 
| git status | 状態確認      |
| git fetch origin　リモート取得のみ
| git pull origin main　取得＋マージ
| git pull --rebase origin main　取得＋リベース
| git stash　退避
| git stash list　退避一覧
| git stash pop　退避復元
| git push origin main　Push


個人開発でのおすすめ手順
GitHubへ反映する前は毎回
git add .
git commit -m "修正"
git pull --rebase origin main
git push origin main

この流れを習慣にすると、今回のような non-fast-forward エラーはかなり減ります。
