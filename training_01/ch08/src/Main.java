import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author R.Nakajyun
 *
 * @version 1.0
 */
public class Main {

    // トレースID
    private static final String TRACE_ID = "TRC-" + System.currentTimeMillis();

    // クラス名
    private static final String CLASS_NAME = Main.class.getSimpleName();

    // 現在のメソッド
    private static final int METHOD_NUM = 1; // TODO:AIより、これは廃止にすべき。理由はまだ追えていない

    /**
     * mainメソッド
     *
     * @param args 起動引数
     */
    public static void main(String[] args) {

        String methodName = null;

        try {
            methodName = Thread.currentThread().getStackTrace()[METHOD_NUM].getMethodName();
            Logger.info(TRACE_ID, "AP-0001", CLASS_NAME, methodName, "処理開始");

            // 引数チェック
            if (args.length != 1) {
                Logger.warn(TRACE_ID, "AP-1001", CLASS_NAME, methodName, "引数チェックNG args.length=" + args.length);
                throw new IllegalArgumentException("引数チェックNG args.length=" + args.length);
            }

            String prefFile = args[0];
            Logger.debug(TRACE_ID, "AP-0002", CLASS_NAME, methodName, "入力ファイル=" + prefFile);

            outPrint(inputPrefData(prefFile));

            Logger.info(TRACE_ID, "AP-0005", CLASS_NAME, methodName, "処理終了");

        } catch (IllegalArgumentException e) {
            Logger.error(TRACE_ID, "AP-9001", CLASS_NAME, methodName, "入力チェックエラー", e);
        } catch (Exception e) {
            Logger.error(TRACE_ID, "AP-9999", CLASS_NAME, methodName, "予期しないエラー", e);
        }
    }

    /**
     * inputPrefDataメソッド
     * 指定したファイルから項目を都道府県リストに追加
     * 
     * @param fileName ファイルフルパス(ファイル名含む)
     * @return prefList
     */
    private static List<Pref> inputPrefData(
            String fileName) throws IOException {

        String methodName = Thread.currentThread().getStackTrace()[METHOD_NUM].getMethodName();
        // Logger.info(TRACE_ID, "AP-0001", CLASS_NAME, methodName, "処理開始");

        // 引数チェック
        // 引数がnull または 空のチェック
        if (fileName == null || fileName.isBlank()) {
            Logger.warn(TRACE_ID, "AP-1001", CLASS_NAME, methodName, "引数チェックNG fileName=" + fileName);
            throw new IllegalArgumentException("引数が不正です。" + fileName);
        }

        // ファイル読み込みチェックを実行して、
        // ファイルが存在無しの場合または権限チェックで異常の場合は、異常とする。
        File file = new File(fileName);
        if (!file.exists()) {
            Logger.warn(TRACE_ID, "AP-1001", CLASS_NAME, methodName, "ファイルが存在しません。fileName=" + fileName);
            throw new FileNotFoundException("ファイルが存在しません。fileName=" + fileName);
        }

        String csvRecord = null;
        // ヘッダ判定用変数[true:ヘッダ未判定,false:ヘッダ判定済み]
        boolean isCsvHeader = true;

        // ファイル読み込みのヘッダ行の判定を実行して、カラム名の場合はスキップする
        List<Pref> prefList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((csvRecord = br.readLine()) != null) {
                if (isCsvHeader) {
                    isCsvHeader = false;
                    continue;
                }

                // -1 で末尾の空欄も保持
                // カラム数のチェック
                String[] prefCols = csvRecord.split(",", -1);
                if (prefCols.length != CsvConst.COLUMN_COUNT) {
                    Logger.warn(TRACE_ID, "AP-1001", CLASS_NAME, methodName, "CSVフォーマット不正:csvRecord=" + csvRecord);
                    throw new IOException("CSVフォーマット不正:csvRecord=" + csvRecord);
                }

                prefList.add(createPref(prefCols));
            }

            // Logger.info(TRACE_ID, "AP-0005", CLASS_NAME, methodName, "処理終了");

            return prefList;
        }
    }

    /**
     * outPrintメソッド
     * 各都道府県の下記リストの項目を標準出力する。
     * ・自治体コード
     * ・都道府県名
     * ・市区名
     * ・緯度(北緯)[60進数]
     * ・経度(東経)[60進数]
     * 
     * @param prefList 都道府県リスト
     * @return -
     */
    private static void outPrint(
            List<Pref> prefList) throws IllegalArgumentException {

        String methodName = Thread.currentThread().getStackTrace()[METHOD_NUM].getMethodName();
        // Logger.info(TRACE_ID, "AP-0001", CLASS_NAME, methodName, "処理開始");

        // 引数チェック
        if (prefList.isEmpty()) {
            Logger.warn(TRACE_ID, "AP-1001", CLASS_NAME, methodName, "引数チェックNG prefList.size=" + prefList.size());
            throw new IllegalArgumentException("データが0件です。prefList.size=" + prefList.size());
            // TODO:場合によりけり、データが0件の場合でも処理継続も有かも。 なにが0件なのか？
        }

        int debugCount = 1;
        Logger.debug(TRACE_ID, "AP-xxxx", CLASS_NAME, methodName, "都道府県情報出力開始");
        for (Pref pref : prefList) {
            Logger.debug(TRACE_ID, "AP-xxxx", CLASS_NAME, methodName, "カウント:" + debugCount  + "回目");
            Logger.debug(TRACE_ID, "AP-xxxx", CLASS_NAME, methodName, "都道府県コード:" + String.format("%02d", Integer.parseInt(pref.getPrefCode())));
            Logger.debug(TRACE_ID, "AP-xxxx", CLASS_NAME, methodName, "都道府県名:" + pref.getPrefName());
            Logger.debug(TRACE_ID, "AP-xxxx", CLASS_NAME, methodName, "市区名:" + pref.getCityName());
            Logger.debug(TRACE_ID, "AP-xxxx", CLASS_NAME, methodName, "緯度[10進数]:" +  dmsToDecimal(pref.getLatitude()));
            Logger.debug(TRACE_ID, "AP-xxxx", CLASS_NAME, methodName, "経度[10進数]:" + dmsToDecimal(pref.getLongitude()));
            debugCount++;
        }
        Logger.debug(TRACE_ID, "AP-xxxx", CLASS_NAME, methodName, "都道府県情報出力終了");
        
    // Logger.info(TRACE_ID, "AP-0005", CLASS_NAME, methodName, "処理終了");
    }

    /**
     * createPrefメソッド
     * 各都道府県の下記リストの項目を作成する。
     * ・自治体コード
     * ・都道府県名
     * ・市区名
     * ・緯度(北緯)[60進数]
     * ・経度(東経)[60進数]
     * 
     * @param cols 都道府県配列
     * @return pref
     */
    private static Pref createPref(
        String[] cols) {

        String methodName = Thread.currentThread().getStackTrace()[METHOD_NUM].getMethodName();
        // Logger.info(TRACE_ID, "AP-0001", CLASS_NAME, methodName, "処理開始");

        // TODO: インデックスよりも連想配列もいいね。
        // →AI回答→あまりこの場合はあまりよろしくないらしい。
        Pref pref = new Pref();
        pref.setPrefCode(cols[CsvConst.PREF_CODE]); // 自治体コード
        pref.setPrefName(cols[CsvConst.PREF_NAME]);// 都道府県名
        pref.setCityName(cols[CsvConst.CITY_NAME]);// 市区名
        pref.setLatitude(cols[CsvConst.LATITUDE]);// 緯度（北緯）[60進数]
        pref.setLongitude(cols[CsvConst.LONGITUDE]);// 経度（東経）[60進数]

        // Logger.info(TRACE_ID, "AP-0005", CLASS_NAME, methodName, "処理終了");

        return pref;
    }

    /**
     * dmsToDecimalメソッド
     * 60進数の座標を10進数に変換する
     * @param dms 座標
     * @return degree
     */
    private static double dmsToDecimal(
        String dms) {

        String methodName = Thread.currentThread().getStackTrace()[METHOD_NUM].getMethodName();
        // Logger.info(TRACE_ID, "AP-0001", CLASS_NAME, methodName, "処理開始");

        String[] parts = dms
                .replace("°", " ")
                .replace("′", " ")
                .replace("″", "")
                .trim()
                .split("\\s+");

        // 文字列が含まれているかチェックが必要かね
        int degree = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        int second = Integer.parseInt(parts[2]);

            // Logger.info(TRACE_ID, "AP-0005", CLASS_NAME, methodName, "処理終了");

        double ret = degree
                + (double) minute / 60
                + (double) second / 3600;

        return ret;
    }
}
