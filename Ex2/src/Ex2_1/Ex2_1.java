package Ex2_1;

import java.io.*;
import java.util.Random;
import java.util.concurrent.*;

public class Ex2_1 {

    /**
     * Creates text files with random number of lines.
     * @param n Number of files to create.
     * @param seed  starting seed for number of lines.
     * @param bound max number of lines in every file.
     * @return an array with the names of the files created.
     */
    public static String[] createTextFiles(int n, int seed, int bound) {

        String[] filenames = new String[n];
        Random random = new Random(seed);

        for (int i = 0; i < n; i++) {
            int numlines = random.nextInt(bound);
            String fileName = "file" + i + ".txt";
            filenames[i] = fileName;

            try {
                FileWriter writing = new FileWriter(fileName) ;
                for (int j = 0; j < numlines; j++) {
                    writing.write("hello world\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filenames;

    }

    /**
     * Reads the number of lines of given array of text files
     * @param filenames array that contains the file names.
     * @return number of total lines read.
     */
    public static int getNumOfLines(String[] filenames) {

        int numoflines = 0;

        for (String fileName : filenames) {

            try {
                int lines = 0;
                BufferedReader br = new BufferedReader(new FileReader(fileName)) ;

                while (br.readLine() != null) {
                    lines++;
                }
                numoflines += lines;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return numoflines;

    }

    /**
     * Reads the number of lines of given array of text files using threads
     * @param filenames array that contains the file names.
     * @return number of total lines read.
     */
    public int getNumOfLinesThreads(String[] filenames) {

        int numoflines = 0;
        LineCounterThread[] threadcount = new LineCounterThread[filenames.length];
        Thread[] threads = new Thread[filenames.length];
        for (int i = 0; i < filenames.length; i++) {
            threadcount[i] = new LineCounterThread(filenames[i]);
            threads[i] = new Thread(threadcount[i]);
            threads[i].start();

        }

        for (int i = 0; i < filenames.length; i++) {
            try {
                threads[i].join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            numoflines =numoflines+ threadcount[i].lines;
        }
        return numoflines;
    }

    /**
     * Reads the number of lines of given array of text files,using a thread pool
     * @param filenames array that contains the file names.
     * @return number of total lines read.
     * @throws ExecutionException , InterruptedException
     */
    public int getNumOfLinesThreadPool(String[] filenames) throws ExecutionException, InterruptedException {

        int numOfCores = Runtime.getRuntime().availableProcessors();
        int corePoolSize = numOfCores/2;
        int maxPoolSize = numOfCores-1;
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(corePoolSize,maxPoolSize, 500, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(filenames.length));
        int numoflines = 0;
        @SuppressWarnings("unchecked")
        Future<Integer>[] dawait = new Future[filenames.length];
        for (int i = 0; i < filenames.length; i++) {
            LineCounterCallable a = new LineCounterCallable(filenames[i]);
            dawait[i] = threadPool.submit(a);
        }

        for (int i = 0; i < filenames.length; i++) {
            numoflines =numoflines+ dawait[i].get();
        }
        threadPool.shutdown();
        return numoflines;
    }


    public static void main(String[] args) throws Exception {
        Ex2_1 n = new Ex2_1();
        String[] filenames = Ex2_1.createTextFiles(100, 2, 100);

        long startTime = System.nanoTime();
        int numoflines = n.getNumOfLinesThreadPool(filenames);
        long endTime = System.nanoTime();
        long time = endTime - startTime;
        System.out.println("Threadspool : " + time + " nanoseconds");

        startTime = System.nanoTime();
        numoflines = Ex2_1.getNumOfLines(filenames);
        endTime = System.nanoTime();
        time = endTime - startTime;
        System.out.println("normal : " + time + " nanoseconds");



        startTime = System.nanoTime();
        numoflines = n.getNumOfLinesThreads(filenames);
        endTime = System.nanoTime();
        time = endTime - startTime;
        System.out.println("Threads : " + time + " nanoseconds");
    }

}
