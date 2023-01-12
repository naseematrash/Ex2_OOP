package Ex2_1;


import java.util.Scanner;

public class LineCounterThread implements Runnable{

    int lines;
    String fn;


    public LineCounterThread(String fn) {
        this.fn = fn;
    }

    /**
     * it counts the number of lines the file has
     * */
    public void run() {
        Scanner scanner = new Scanner(fn);
        while (scanner.hasNextLine()) {
            lines++;
            scanner.nextLine();
        }
        scanner.close();
    }

}
