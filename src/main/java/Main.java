import java.util.Scanner;

public class Main {

    public static int j = 0;
    public static void main(String[] args) throws InterruptedException {
        while (j == 0) {
            CalculateOperation t = new CalculateOperation("CalculateOperation");
            t.start();
            t.join();
        }
    }


}
