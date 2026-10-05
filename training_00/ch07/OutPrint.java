// https://dexall.co.jp/articles/?p=1061 → 参考
// https://qiita.com/studio_meowtoon/items/4d11e94a2389758759cd → 参考

import java.util.ArrayList;
import java.util.List;

/**
 * @author R.Nakajyun
 * @version 1.0
 */
public class OutPrint {

    /**
     * mainメソッド
     * 指定した各都道府県の下記リストの項目を設定する。
     * ・自治体コード
     * ・都道府県名
     * ・市区名
     * ・緯度(北緯)[60進数]
     * ・経度(東経)[60進数]
     * 
     * @param args 配列
     * @return -
     */
    public static void main(String[] args) {

        List<Pref> prefList = new ArrayList<>();

        // 1,北海道
        Pref hokkaidoRegionRep = new Pref();
        hokkaidoRegionRep.setPrefCode("1");
        hokkaidoRegionRep.setPrefName("北海道");
        hokkaidoRegionRep.setCityName("札幌市");
        hokkaidoRegionRep.setLatitude("43°03′52″");
        hokkaidoRegionRep.setLongitude("141°20′49″");
        prefList.add(hokkaidoRegionRep);

        // 13,東京都
        Pref kantoRegionRep = new Pref();
        kantoRegionRep.setPrefCode("13");
        kantoRegionRep.setPrefName("東京都");
        kantoRegionRep.setCityName("新宿区");
        kantoRegionRep.setLatitude("35°41′22");
        kantoRegionRep.setLongitude("139°41′30″");
        prefList.add(kantoRegionRep);

        // 47,沖縄県
        Pref kyushuRegionRep = new Pref();
        kyushuRegionRep.setPrefCode("47");
        kyushuRegionRep.setPrefName("沖縄県");
        kyushuRegionRep.setCityName("那覇市");
        kyushuRegionRep.setLatitude("26°12′45″");
        kyushuRegionRep.setLongitude("127°40′51″");
        prefList.add(kyushuRegionRep);

        outPrint(prefList);
    }

    /**
     * outPrintメソッド
     * 指定した各都道府県の下記リストの項目を標準出力する。
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
        for (Pref prefObj : prefList) {
            System.out.println(prefObj.getPrefCode());
            System.out.println(prefObj.getPrefName());
            System.out.println(prefObj.getCityName());
            System.out.println(prefObj.getLatitude());
            System.out.println(prefObj.getLongitude());
        }
}
