package Ex2_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LineCounterThread implements Runnable{
    String fileName;
    int lines;

    public LineCounterThread(String fileName) {
        this.fileName = fileName;
    }

    /**
     * it counts the number of lines the file has
     * */
    public void run() {
        Scanner scanner = new Scanner(fileName);
        while (scanner.hasNextLine()) {
            lines++;
            scanner.nextLine();
        }
        scanner.close();
    }

}
