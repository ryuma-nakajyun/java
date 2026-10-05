// https://dexall.co.jp/articles/?p=1061 → 参考
// https://qiita.com/studio_meowtoon/items/4d11e94a2389758759cd → 参考

/**
 * @author R.Nakajyun
 * 
 * @version 1.0
 */
public class HelloWorld {
    /**
     * mainメソッド
     * 指定した文字列を標準出力する。
     * 
     * @param args 配列
     * @return -
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("引数異常");
        }

        for (String str : args) {
            System.out.println(str);
        }
    }
}
