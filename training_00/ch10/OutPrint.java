import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OutPrint {

    public static void main(String[] args) {

        try (BufferedReader br = new BufferedReader(new FileReader("pref.csv"))) {

            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
