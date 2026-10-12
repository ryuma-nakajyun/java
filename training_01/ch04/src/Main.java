import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @author R.Nakajyun
 *
 * @version 1.0
 */
public class Main {

    /** 日時フォーマット */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * mainメソッド
     *
     * @param args 起動引数
     */
    public static void main(String[] args) {

        try {
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            // 開始ログ
            getLog("INFO", "AP-0001", "処理開始:" + methodName);

            // 入力チェック
            if (args.length != 2) {
                throw new IllegalArgumentException("引数は2件指定してください。");
            }

            // 業務処理
            for (String str : args) {
                getLog("DEBUG", "AP-xxxx", methodName + "出力値:" + str);
            }

            // 終了ログ
            getLog("INFO", "AP-0005", "処理終了:" + methodName);

        } catch (Exception e) {
            getLog("ERROR", "AP-9999", "システムエラー発生 : " + e.getClass().getSimpleName() + " : " + e.getMessage());
            // System.exit(1);
        }
    }

    /**
     * ログ出力
     *
     * @param level     ログレベル
     * @param messageId メッセージID
     * @param message   メッセージ
     */
    private static void getLog(
            String level,
            String messageId,
            String message) {

        String timestamp = LocalDateTime.now().format(FORMATTER);

        System.out.printf(
                "%s [%s] [%s] %s%n",
                timestamp,
                level,
                messageId,
                message);
    }
}
