import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    // 日時フォーマット
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * ログ出力
     *
     * @param level     ログレベル
     * @param messageId メッセージID
     * @param message   メッセージ
     */
    public static void writeOutPutLog(
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
