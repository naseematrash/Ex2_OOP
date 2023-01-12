package Ex2_1;

import java.util.Scanner;
import java.util.concurrent.Callable;

public class LineCounterCallable implements Callable<Integer> {
    String fn;

    public LineCounterCallable(String fn) {
        this.fn = fn;
    }

    /**
     * The call method
     * @return  number of lines read from the file
     * @throws  Exception if there is any error
     * */
    public Integer call() {

        Scanner scanner = new Scanner(fn);
        int count = 0;

        while (scanner.hasNextLine()) {
            count++;
            scanner.nextLine();
        }
        return count;
    }
}
