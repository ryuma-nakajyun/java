https://dexall.co.jp/articles/?p=1061
https://qiita.com/studio_meowtoon/items/4d11e94a2389758759cd

実務推奨
理由：判定対象のコマンドと判定処理を一体化できるから
TODO:一旦表層の理解でとめる
if ! javac Main.java; then



実際には、
AP-0001 処理開始
は人間向け、
event=process_start
は機械向けです。
最近のシステムでは両方持つこともあります。

例えば対応表を作るとこうなります。
共通処理
| ログID    | 従来ログ      | 構造化ログ                        |
| ------- | --------- | ---------------------------- |
| AP-0001 | 処理開始      | event=process\_start         |
| AP-0002 | 処理終了      | event=process\_end           |
| AP-0003 | パラメータ取得開始 | event=parameter\_read\_start |
| AP-0004 | パラメータ取得終了 | event=parameter\_read\_end   |
| AP-0005 | 処理件数出力    | event=count\_output          |
| AP-0006 | 処理時間出力    | event=elapsed\_time\_output  |

入力チェック
| ログID    | 従来ログ     | 構造化ログ                        |
| ------- | -------- | ---------------------------- |
| AP-1001 | 入力チェック開始 | event=validation\_start      |
| AP-1002 | 入力チェック終了 | event=validation\_end        |
| AP-1003 | 必須チェック開始 | event=required\_check\_start |
| AP-1004 | 必須チェック終了 | event=required\_check\_end   |
| AP-1005 | 桁数チェック開始 | event=length\_check\_start   |
| AP-1006 | 桁数チェック終了 | event=length\_check\_end     |

業務処理
| ログID    | 従来ログ    | 構造化ログ                   |
| ------- | ------- | ----------------------- |
| AP-2001 | 業務処理開始  | event=business\_start   |
| AP-2002 | 業務処理終了  | event=business\_end     |
| AP-2003 | データ編集開始 | event=data\_edit\_start |
| AP-2004 | データ編集終了 | event=data\_edit\_end   |
| AP-2005 | データ集計開始 | event=aggregate\_start  |
| AP-2006 | データ集計終了 | event=aggregate\_end    |

ファイル処理
| ログID    | 従来ログ     | 構造化ログ                     |
| ------- | -------- | ------------------------- |
| AP-3001 | ファイル読込開始 | event=file\_read\_start   |
| AP-3002 | ファイル読込終了 | event=file\_read\_end     |
| AP-3003 | ファイル出力開始 | event=file\_write\_start  |
| AP-3004 | ファイル出力終了 | event=file\_write\_end    |
| AP-3005 | ファイル削除開始 | event=file\_delete\_start |
| AP-3006 | ファイル削除終了 | event=file\_delete\_end   |

DB処理
| ログID    | 従来ログ       | 構造化ログ                     |
| ------- | ---------- | ------------------------- |
| AP-4001 | DB接続開始     | event=db\_connect\_start  |
| AP-4002 | DB接続終了     | event=db\_connect\_end    |
| AP-4003 | SQL実行開始    | event=sql\_execute\_start |
| AP-4004 | SQL実行終了    | event=sql\_execute\_end   |
| AP-4005 | COMMIT開始   | event=commit\_start       |
| AP-4006 | COMMIT終了   | event=commit\_end         |
| AP-4007 | ROLLBACK開始 | event=rollback\_start     |
| AP-4008 | ROLLBACK終了 | event=rollback\_end       |

外部IF
| ログID    | 従来ログ    | 構造化ログ                          |
| ------- | ------- | ------------------------------ |
| AP-5001 | API接続開始 | event=api\_connect\_start      |
| AP-5002 | API接続終了 | event=api\_connect\_end        |
| AP-5003 | 電文送信開始  | event=request\_send\_start     |
| AP-5004 | 電文送信終了  | event=request\_send\_end       |
| AP-5005 | 電文受信開始  | event=response\_receive\_start |
| AP-5006 | 電文受信終了  | event=response\_receive\_end   |

業務エラー
| ログID    | 従来ログ    | 構造化ログ                       |
| ------- | ------- | --------------------------- |
| AP-9001 | パラメータ不正 | event=parameter\_error      |
| AP-9002 | 入力値不正   | event=validation\_error     |
| AP-9003 | データ不存在  | event=data\_not\_found      |
| AP-9004 | 件数超過    | event=count\_over\_limit    |
| AP-9005 | 業務ルール違反 | event=business\_rule\_error |

システムエラー
| ログID    | 従来ログ       | 構造化ログ                       |
| ------- | ---------- | --------------------------- |
| AP-9901 | DB接続エラー    | event=db\_connect\_error    |
| AP-9902 | SQL実行エラー   | event=sql\_execute\_error   |
| AP-9903 | ファイルI/Oエラー | event=file\_io\_error       |
| AP-9904 | API接続エラー   | event=api\_connect\_error   |
| AP-9905 | タイムアウト     | event=timeout\_error        |
| AP-9999 | 想定外例外      | event=unexpected\_exception |

実務だと最終的にこういう形式になることが多いです。
Plain Text
2026-10-07 09:00:01.123
level=INFO
logId=AP-1001
event=validation_start
method=validateArgs
``
その他の行を表示する
Plain Text
2026-10-07 09:00:01.125
level=ERROR
logId=AP-9001
event=parameter_error
method=validateArgs
argLength=1
message=引数は2件必要です
その他の行を表示する

つまり、
Plain Text
logId
↓
人間が運用手順書を見るため
 
event
↓
ELK/OpenSearch/Splunkで検索するため
その他の行を表示する

という二段構えにするのが、昔ながらの業務システムと現代的な構造化ログの折衷案としてかなり実践的です。

この規約をそのまま Java の業務バッチで実装するなら、ログID + event + メタ情報を出す形がおすすめです。

例えば SLF4J を使う前提で設計します。
LogConstants.java
/**
 * ログID定義
 */
public final class LogConstants {

    private LogConstants() {
    }

    // 共通
    public static final String AP0001 = "AP-0001";
    public static final String AP0002 = "AP-0002";
    public static final String AP0003 = "AP-0003";
    public static final String AP0004 = "AP-0004";

    // 入力チェック
    public static final String AP1001 = "AP-1001";
    public static final String AP1002 = "AP-1002";

    // 業務処理
    public static final String AP2001 = "AP-2001";
    public static final String AP2002 = "AP-2002";

    // 業務エラー
    public static final String AP9001 = "AP-9001";

    // システムエラー
    public static final String AP9999 = "AP-9999";
}

Main.java
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger =
            LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        logger.info(
            "logId={} event=process_start class={} method={} args={}",
            LogConstants.AP0001,
            Main.class.getSimpleName(),
            "main",
            Arrays.toString(args));

        try {

            validateArgs(args);

            executeBusiness(args);

            long elapsed =
                    System.currentTimeMillis() - startTime;

            logger.info(
                "logId={} event=process_end class={} method={} elapsedMs={}",
                LogConstants.AP0002,
                Main.class.getSimpleName(),
                "main",
                elapsed);

        } catch (IllegalArgumentException e) {

            logger.error(
                "logId={} event=parameter_error class={} method={} message={}",
                LogConstants.AP9001,
                Main.class.getSimpleName(),
                "main",
                e.getMessage());

            System.exit(1);

        } catch (Exception e) {

            logger.error(
                "logId={} event=unexpected_exception class={} method={}",
                LogConstants.AP9999,
                Main.class.getSimpleName(),
                "main",
                e);

            System.exit(1);
        }
    }

    private static void validateArgs(String[] args) {

        logger.info(
            "logId={} event=validation_start method=validateArgs",
            LogConstants.AP1001);

        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "引数は2件指定してください。");
        }

        logger.info(
            "logId={} event=validation_end method=validateArgs result=success",
            LogConstants.AP1002);
    }

    private static void executeBusiness(String[] args) {

        logger.info(
            "logId={} event=business_start method=executeBusiness",
            LogConstants.AP2001);

        for (String arg : args) {

            logger.info(
                "event=output value={}",
                arg);

            System.out.println(arg);
        }

        logger.info(
            "logId={} event=business_end method=executeBusiness count={}",
            LogConstants.AP2002,
            args.length);
    }
}

出力例
2026-10-07 09:00:01.123 INFO
logId=AP-0001
event=process_start
class=Main
method=main
args=[123, abcde]

2026-10-07 09:00:01.124 INFO
logId=AP-1001
event=validation_start
method=validateArgs

2026-10-07 09:00:01.125 INFO
logId=AP-1002
event=validation_end
method=validateArgs
result=success

2026-10-07 09:00:01.126 INFO
logId=AP-2001
event=business_start
method=executeBusiness

2026-10-07 09:00:01.127 INFO
event=output
value=123

2026-10-07 09:00:01.128 INFO
event=output
value=abcde

2026-10-07 09:00:01.129 INFO
logId=AP-2002
event=business_end
method=executeBusiness
count=2

2026-10-07 09:00:01.130 INFO
logId=AP-0002
event=process_end
class=Main
method=main
elapsedMs=7

この形だと、
運用担当は AP-1001 で設計書を引ける
OpenSearch や Splunk は event=validation_start で検索できる
elapsedMs で性能分析できる
method= で処理経路を追える
ので、昔ながらのログID管理と現代的な構造化ログの両方のメリットを取れます。これは実際のJava業務システムでも十分通用する設計です。


ログIDだけだと都度設計書を参照する必要があります。 
一方で
event=validation_error
method=validateArgs
 
のように出力しておけば、
ログ検索基盤上で直接検索できます。 
そのため運用性を考えると
ログIDと構造化ログの併用が良いと考えました。
