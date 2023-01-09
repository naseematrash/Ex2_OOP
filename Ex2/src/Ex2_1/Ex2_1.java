package Ex2_1;

import java.io.*;
import java.util.Random;
import java.util.concurrent.*;

public class Ex2_1 {

    public static String[] createTextFiles(int n, int seed, int bound) {

        String[] fileNames = new String[n];
        Random random = new Random(seed);
        for (int i = 0; i < n; i++) {
            int numLines = random.nextInt(bound);
            String fileName = "file" + i + ".txt";
            fileNames[i] = fileName;
            try (FileWriter fw = new FileWriter(new File(fileName))) {
                for (int j = 0; j < numLines; j++) {
                    fw.write("hello world\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileNames;

    }


    public static int getNumOfLines(String[] fileNames) {

        int totalLines = 0;
        for (String fileName : fileNames) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                int lines = 0;
                while (br.readLine() != null) {
                    lines++;
                }
                totalLines += lines;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return totalLines;
    }


    public int getNumOfLinesThreads(String[] fileNames) {

        int totalLines = 0;
        LineCounterThread[] counters = new LineCounterThread[fileNames.length];
        Thread[] threads = new Thread[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            counters[i] = new LineCounterThread(fileNames[i]);
            threads[i] = new Thread(counters[i]);
            threads[i].start();

        }

        for (int i = 0; i < fileNames.length; i++) {
            try {
                threads[i].join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            totalLines += counters[i].lines;
        }
        return totalLines;
    }


    public int getNumOfLinesThreadPool(String[] fileNames) throws ExecutionException, InterruptedException {

        int numOfCores = Runtime.getRuntime().availableProcessors();
        int corePoolSize = numOfCores/2;
        int maxPoolSize = numOfCores-1;
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor
                (corePoolSize,maxPoolSize,
                        500, TimeUnit.MILLISECONDS,
                        new ArrayBlockingQueue(fileNames.length));
        int totalLines = 0;
        @SuppressWarnings("unchecked")
        Future<Integer>[] results = new Future[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            results[i] = threadPool.submit(new LineCounterCallable(fileNames[i]));
        }

        for (int i = 0; i < fileNames.length; i++) {
            totalLines += results[i].get();
        }
        threadPool.shutdown();
        return totalLines;
    }


    public static void main(String[] args) throws Exception {
        Ex2_1 n = new Ex2_1();
        String[] fileNames = Ex2_1.createTextFiles(20, 200, 100);

        long startTime = System.nanoTime();
        int totalLines = n.getNumOfLinesThreadPool(fileNames);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("sumLineCountsWithThreadspool runtime: " + duration + " nanoseconds");

        startTime = System.nanoTime();
        totalLines = Ex2_1.getNumOfLines(fileNames);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("sumLineCounts runtime: " + duration + " nanoseconds");



        startTime = System.nanoTime();
        totalLines = n.getNumOfLinesThreads(fileNames);
        endTime = System.nanoTime();
        duration = endTime - startTime;
        System.out.println("sumLineCountsWithThreads runtime: " + duration + " nanoseconds");
    }

}
