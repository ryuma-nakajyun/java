// https://dexall.co.jp/articles/?p=1061 → 参考
// https://qiita.com/studio_meowtoon/items/4d11e94a2389758759cd → 参考

import java.util.ArrayList;
import java.util.List;

public class OutPrint {

    public static void main(String[] args) {

        // 1,北海道
        Pref hokkaidoRegionRep = new Pref();
        hokkaidoRegionRep.setPrefCode("1");
        hokkaidoRegionRep.setPrefName("北海道");
        hokkaidoRegionRep.setCityName("札幌市");
        hokkaidoRegionRep.setLatitude("43°03′52″");
        hokkaidoRegionRep.setLongitude("141°20′49″");

        // 13,東京都
        Pref kantoRegionRep = new Pref();
        kantoRegionRep.setPrefCode("13");
        kantoRegionRep.setPrefName("東京都");
        kantoRegionRep.setCityName("新宿区");
        kantoRegionRep.setLatitude("35°41′22");
        kantoRegionRep.setLongitude("139°41′30″");

        // 47,沖縄県
        Pref kyushuRegionRep = new Pref();
        kyushuRegionRep.setPrefCode("47");
        kyushuRegionRep.setPrefName("沖縄県");
        kyushuRegionRep.setCityName("那覇市");
        kyushuRegionRep.setLatitude("26°12′45″");
        kyushuRegionRep.setLongitude("127°40′51″");

        // 1,北海道
        System.out.println(hokkaidoRegionRep.getPrefCode());
        System.out.println(hokkaidoRegionRep.getPrefName());
        System.out.println(hokkaidoRegionRep.getCityName());
        System.out.println(hokkaidoRegionRep.getLatitude());
        System.out.println(hokkaidoRegionRep.getLongitude());

        // 13,東京都
        System.out.println(kantoRegionRep.getPrefCode());
        System.out.println(kantoRegionRep.getPrefName());
        System.out.println(kantoRegionRep.getCityName());
        System.out.println(kantoRegionRep.getLatitude());
        System.out.println(kantoRegionRep.getLongitude());

        // 47,沖縄県
        System.out.println(kyushuRegionRep.getPrefCode());
        System.out.println(kyushuRegionRep.getPrefName());
        System.out.println(kyushuRegionRep.getCityName());
        System.out.println(kyushuRegionRep.getLatitude());
        System.out.println(kyushuRegionRep.getLongitude());
    }
}
