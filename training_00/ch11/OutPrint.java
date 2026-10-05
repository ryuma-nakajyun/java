import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 参考：https://codingls.com/java/2308/ 【Java】CSVファイルを読み込む方法｜split(“,”)の落とし穴とライブラリの使い分け

/**
 * @author R.Nakajyun
 * @version 1.0
 */
public class OutPrint {

    /**
     * mainメソッド
     * 指定した文字列を標準出力する。
     * 
     * @param args 配列
     * @return -
     */
    public static void main(String[] args) {

        //  引数チェック
        if (args.length != 1) {
            System.out.println("ファイル名を指定してください。");
            return;
        }
        
        String fileName = args[0];
        System.out.println("指定されたファイル: " + fileName);

        try {
            List<Pref> prefList = inputPrefData(fileName);
            outPrint(prefList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * inputPrefDataメソッド
     * 指定したファイルから項目を都道府県リストに追加
     * 
     * @param fileName ファイルパス
     * @return prefList
     */
    private static List<Pref> inputPrefData(String fileName) throws IOException {
        //  引数チェック
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("引数不正：" + fileName);
        }

        File file = new File(fileName);
        // ファイル読み込みチェック
        if (!file.exists()) {
            throw new FileNotFoundException("ファイルが存在しません：" + fileName);
        }

        // ファイルの中身をチェック
        if (file.length() == 0) {
            throw new IOException("ファイルの中身が空です：" + file); // 権限とかの原因？
        }

        String line = null;
        // ヘッダ判定用変数[true:ヘッダ未判定、false:ヘッダ判定済み]
        boolean isCsvHeader = true;

        // ファイル読み込みの初回判定を実行する
        // ファイルの先頭行はヘッダ行のためスキップする
        // csvファイルから読み込んだ、値を格納するPrefListを用意(都道府県リスト)
        List<Pref> prefList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                if (isCsvHeader) {
                    isCsvHeader = false;
                    continue;
                }

                // 行をカラムで「,」分割して各フィールドに設定
                // PrefListに各フィールドを設定する
                // -1 で末尾の空欄も保持
                // カラム数のチェック
                String[] prefCols = line.split(",", -1);
                if (prefCols.length != 5) {
                    throw new IOException("CSVフォーマット不正 : " + line);
                }

                Pref prefObj = new Pref();
                prefObj.setPrefCode(prefCols[0]); // 都道府県コード
                prefObj.setPrefName(prefCols[1]); // 都道府県名
                prefObj.setCityName(prefCols[2]); // 市区名
                prefObj.setLatitude(prefCols[3]); // 緯度（北緯）[60進数]
                prefObj.setLongitude(prefCols[4]); // 経度（東経）[60進数]

                // 都道府県リストに都道府県インスタンスを追加
                prefList.add(prefObj);
            }
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
     * @param prefList リスト
     * @return -
     */
    private static void outPrint(List<Pref> prefList) {

        // 引数チェック
        if (prefList.isEmpty()) {
            throw new IllegalArgumentException("データが0件です");
        }

        for (Pref pref : prefList) {
            System.out.println("都道府県コード : " + pref.getPrefCode());
            System.out.println("都道府県名 : " + pref.getPrefName());
            System.out.println("市区名 : " + pref.getCityName());
            System.out.println("緯度（北緯）[60進数] : " + pref.getLatitude());
            System.out.println("経度（東経）[60進数] : " + pref.getLongitude());
            System.out.println(""); // 視認性のため改行
        }
    }
}
