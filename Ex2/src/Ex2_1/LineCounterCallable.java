package Ex2_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class LineCounterCallable implements Callable<Integer> {
    String fileName;

    public LineCounterCallable(String fileName) {
        this.fileName = fileName;
    }

    /**
     * The call method
     * @return  number of lines read from the file
     * @throws  Exception if there is any error
     * */
    public Integer call() throws Exception {
        int lines = 0;
        Scanner scanner = new Scanner(fileName);
        while (scanner.hasNextLine()) {
            lines++;
            scanner.nextLine();
        }
        return lines;
    }
}
