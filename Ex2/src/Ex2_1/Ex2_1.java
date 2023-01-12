package Ex2_1;

import java.io.*;
import java.util.Random;
import java.util.concurrent.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class Ex2_1 {



    /**
     * Creates text files with random number of lines.
     * @param n Number of files to create.
     * @param seed  starting seed for number of lines.
     * @param bound max number of lines in every file.
     * @return an array with the names of the files created.
     */
    public static String[] createTextFiles(int n, int seed, int bound) {

        Random random = new Random(seed);
        String[] filenames = new String[n];


        for (int i = 0; i < n; i++) {
            String ftxt = "file" + i + ".txt";
            int numlines = random.nextInt(bound);

            filenames[i] = ftxt;

            try {

                String gnrattxt = Stream.generate(() -> "hello world\n").limit(numlines).collect(Collectors.joining());
                Files.write(Paths.get(ftxt), gnrattxt.getBytes());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filenames;

    }

    /**
     * Reads the number of lines of given array of text files
     * @param fileNames array that contains the file names.
     * @return number of total lines read.
     */
    public static int getNumOfLines(String[] fileNames) {

        int numoflines = 0;

        for (int i=0;i<fileNames.length;i++) {

            try {
                String ftxt= fileNames[i];
                int lines = 0;

                BufferedReader br = new BufferedReader(new FileReader(ftxt)) ;

                while (br.readLine() != null) {
                    lines++;
                }
                numoflines =numoflines+ lines;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return numoflines;

    }

    /**
     * Reads the number of lines of given array of text files using threads
     * @param fileNames array that contains the file names.
     * @return number of total lines read.
     */
    public int getNumOfLinesThreads(String[] fileNames) {

        Thread[] t = new Thread[fileNames.length];
        LineCounterThread[] threadcount = new LineCounterThread[fileNames.length];

        int numoflines = 0;

        for (int i = 0; i < fileNames.length; i++) {

            threadcount[i] = new LineCounterThread(fileNames[i]);
            t[i] = new Thread(threadcount[i]);
            t[i].start();

        }

        for (int i = 0; i < fileNames.length; i++) {

            try {
                t[i].join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            numoflines =numoflines+ threadcount[i].lines;
        }
        return numoflines;
    }

    /**
     * Reads the number of lines of given array of text files,using a thread pool
     * @param fileNames array that contains the file names.
     * @return number of total lines read.
     * @throws ExecutionException , InterruptedException
     */
    public int getNumOfLinesThreadPool(String[] fileNames) throws ExecutionException, InterruptedException {

        int numoflines = 0;
        int numOfCores = Runtime.getRuntime().availableProcessors();
        int maxPoolSize = numOfCores-1;
        int corePoolSize = numOfCores/2;

        ThreadPoolExecutor tp = new ThreadPoolExecutor(corePoolSize,maxPoolSize, 500, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(fileNames.length));

        @SuppressWarnings("unchecked")
        Future<Integer>[] f = new Future[fileNames.length];

        for (int i = 0; i < fileNames.length; i++) {

            LineCounterCallable a = new LineCounterCallable(fileNames[i]);
            f[i] = tp.submit(a);
        }

        for (int i = 0; i < fileNames.length; i++) {

            numoflines =numoflines+ f[i].get();
        }

        tp.shutdown();
        return numoflines;
    }


    public static void main(String[] args) throws Exception {

        Ex2_1 n = new Ex2_1();
        String[] fn = Ex2_1.createTextFiles(10, 2, 100);

        long start = System.nanoTime();
        int numoflines = n.getNumOfLinesThreadPool(fn);
        long end = System.nanoTime();
        long time = end - start;
        System.out.println("Threadspool : " + time + " nanoseconds");


        start = System.nanoTime();
        numoflines = n.getNumOfLinesThreads(fn);
        end = System.nanoTime();
        time = end - start;
        System.out.println("Threads : " + time + " nanoseconds");


        start = System.nanoTime();
        numoflines = Ex2_1.getNumOfLines(fn);
        end = System.nanoTime();
        time = end - start;
        System.out.println("normal : " + time + " nanoseconds");


    }

}
