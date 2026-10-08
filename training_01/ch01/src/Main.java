/**
 * @author R.Nakajyun
 * 
 * @version 1.0
 */
public class Main {
    /**
     * mainメソッド
     * 指定した文字列を標準出力する。
     * 
     * @param args 配列
     * @return -
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("引数が異常のため異常終了");
        }

        for (String str : args) {
            System.out.println(str);
        }
    }
}
