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

    /**
     * mainメソッド
     *
     * @param args 起動引数
     */
    public static void main(String[] args) {

        try {
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            // 開始ログ
            Logger.writeOutPutLog("INFO", "AP-0001", "処理開始:" + methodName);

            // 入力チェック
            // 引数の数をチェック
            if (args.length != 1) {
                throw new IllegalArgumentException("引数が不正です。");
            }

            String prefFile = args[0];
            // ファイル名チェック
            // if (!prefFile.equals("pref.csv")) {
            //     throw new IllegalArgumentException("ファイル名が不正です。");
            // }
            outPrint(inputPrefData(prefFile));

            // 終了ログ
            Logger.writeOutPutLog("INFO", "AP-0005", "処理終了:" + methodName);

        } catch (Exception e) {
            Logger.writeOutPutLog("ERROR", "AP-9999", "システムエラー発生:" + e.getClass().getSimpleName() + " : " + e.getMessage());
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
            
            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            // 開始ログ
            Logger.writeOutPutLog("INFO", "AP-0001", "処理開始:" + methodName);


        // 引数チェック
        // 引数がnull または 空のチェック
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("引数不正：" + fileName);
        }

        File file = new File(fileName);
        // ファイル読み込みチェックを実行して、
        // ファイルが存在無しの場合または権限チェックで異常の場合は、異常とする。
        if (!file.exists()) {
            throw new FileNotFoundException("ファイルが存在しません：" + fileName);
        }

        // ファイルの中身をチェック
        if (file.length() == 0) {
            throw new IOException("ファイルの中身が空です：" + fileName);
        }

        String line = null;
        // ヘッダ判定用変数[true:ヘッダ未判定、false:ヘッダ判定済み]
        boolean isCsvHeader = true;

        // ファイル読み込みのヘッダ行の判定を実行して、カラム名の場合はスキップする
        List<Pref> prefList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                if (isCsvHeader) {
                    isCsvHeader = false;
                    continue;
                }

                // -1 で末尾の空欄も保持
                // カラム数のチェック
                String[] prefCols = line.split(",", -1);
                if (prefCols.length != 5) {
                    throw new IOException("CSVフォーマット不正:" + line);
                }

                // 都道府県リストに都道府県インスタンスを追加                 // TODO: インデックスよりも連想配列もいいね。
                Pref prefObj = new Pref();
                prefObj.setPrefCode(prefCols[0]); // 都道府県コード
                prefObj.setPrefName(prefCols[1]); // 都道府県名
                prefObj.setCityName(prefCols[2]); // 市区名
                prefObj.setLatitude(prefCols[3]); // 緯度（北緯）[60進数]
                prefObj.setLongitude(prefCols[4]); // 経度（東経）[60進数]
                prefList.add(prefObj);
            }

            // 終了ログ
            Logger.writeOutPutLog("INFO", "AP-0005", "処理終了:" + methodName);

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

            String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            // 開始ログ
            Logger.writeOutPutLog("INFO", "AP-0001", "処理開始:" + methodName);

        // 引数チェック
        if (prefList.isEmpty()) {
            throw new IllegalArgumentException("データが0件です"); // TODO:場合によりけり、データが0件の場合でも処理継続も有かも。
        }

        for (Pref pref : prefList) {
            Logger.writeOutPutLog("INFO", "AP-000X", "都道府県リスト出力開始");
            Logger.writeOutPutLog("DEBUG", "AP-xxxx", "都道府県コード:" + pref.getPrefCode());
            Logger.writeOutPutLog("DEBUG", "AP-xxxx", "都道府県名:" + pref.getPrefName());
            Logger.writeOutPutLog("DEBUG", "AP-xxxx", "市区名:" + pref.getCityName());
            Logger.writeOutPutLog("DEBUG", "AP-xxxx", "緯度（北緯）[60進数]:" + pref.getLatitude());
            Logger.writeOutPutLog("DEBUG", "AP-xxxx", "経度（東経）[60進数]:" + pref.getLongitude());
            Logger.writeOutPutLog("INFO", "AP-000X", "都道府県リスト出力終了");
        }
            
        // 終了ログ
        Logger.writeOutPutLog("INFO", "AP-0005", "処理終了:" + methodName);
    }
    
}
