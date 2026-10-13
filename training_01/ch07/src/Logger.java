import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ログ出力クラス
 *
 * @author R.Nakajyun
 * @version 1.0
 */
public final class Logger {

        // ログメッセージ日時フォーマット
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        // DEBUG[開発：true, 本番：false]
        private static final boolean DEBUG_MODE = true;

        // ログレベル
        // DEBUG
        private static final String DEBUG = "DEBUG";

        // INFO
        private static final String INFO = "INFO";

        // WARN
        private static final String WARN = "WARN";

        // ERROR
        private static final String ERROR = "ERROR";

        /**
         * コンストラクタ
         */
        private Logger() {
        }

        /**
         * DEBUGログ出力メソッド
         *
         * @param traceId    TraceID
         * @param messageId  メッセージID
         * @param className  クラス名
         * @param methodName メソッド名
         * @param message    出力メッセージ
         */
        public static void debug(
                        String traceId,
                        String messageId,
                        String className,
                        String methodName,
                        String message) {

                write(
                                DEBUG,
                                traceId,
                                messageId,
                                className,
                                methodName,
                                message,
                                null);
        }

        /**
         * INFOログ出力メソッド
         *
         * @param traceId    TraceID
         * @param messageId  メッセージID
         * @param className  クラス名
         * @param methodName メソッド名
         * @param message    出力メッセージ
         */
        public static void info(
                        String traceId,
                        String messageId,
                        String className,
                        String methodName,
                        String message) {

                write(
                                INFO,
                                traceId,
                                messageId,
                                className,
                                methodName,
                                message,
                                null);
        }

        /**
         * WARNログ出力メソッド
         *
         * @param traceId    TraceID
         * @param messageId  メッセージID
         * @param className  クラス名
         * @param methodName メソッド名
         * @param message    出力メッセージ
         */
        public static void warn(
                        String traceId,
                        String messageId,
                        String className,
                        String methodName,
                        String message) {

                write(
                                WARN,
                                traceId,
                                messageId,
                                className,
                                methodName,
                                message,
                                null);
        }

        /**
         * ERRORログ出力メソッド
         *
         * @param traceId    TraceID
         * @param messageId  メッセージID
         * @param className  クラス名
         * @param methodName メソッド名
         * @param message    出力メッセージ
         * @param exception  例外
         */
        public static void error(
                        String traceId,
                        String messageId,
                        String className,
                        String methodName,
                        String message,
                        Exception exception) {

                write(
                                ERROR,
                                traceId,
                                messageId,
                                className,
                                methodName,
                                message,
                                exception);
        }

        /**
         * ログ出力処理メソッド
         *
         * @param level      ログレベル
         * @param traceId    TraceID
         * @param messageId  メッセージID
         * @param className  クラス名
         * @param methodName メソッド名
         * @param message    メッセージ
         * @param exception  例外
         */
        private static void write(
                        String level,
                        String traceId,
                        String messageId,
                        String className,
                        String methodName,
                        String message,
                        Exception exception) {

                String timestamp = LocalDateTime.now().format(FORMATTER);
                String threadName = Thread.currentThread().getName();
                String logMessage = String.format(
                                "%s [%s] [TRACE=%s] [THREAD=%s] [MSG=%s] [%s#%s] %s",
                                timestamp,
                                level,
                                traceId,
                                threadName,
                                messageId,
                                className,
                                methodName,
                                message);

                System.out.println(logMessage);

                if (exception != null) {
                        exception.printStackTrace(System.out);
                }
        }
}
