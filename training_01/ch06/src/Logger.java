import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ログ出力クラス
 *
 * @author R.Nakajyun
 * @version 1.0
 */
public final class Logger {

        /** 日時フォーマット */
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm:ss.SSS");

        /** ログレベル */
        private static final String INFO = "INFO";
        private static final String WARN = "WARN";
        private static final String ERROR = "ERROR";

        /**
         * コンストラクタ
         */
        private Logger() {
        }

        /**
         * INFOログ出力
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
         * WARNログ出力
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
         * ERRORログ出力
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
         * ログ出力処理
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