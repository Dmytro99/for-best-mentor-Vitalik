
package concurency;

import java.io.*;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.currentThread;


public class multithreadingJoin {

    public static void writeToFileJoin()
            throws IOException {
        String path = "D:\\Java\\tree.txt";
        String pathDir = "D:\\Move";

        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(printDirectoryTree(new File(pathDir)));
        fileWriter.close();
    }

    public static String printDirectoryTree(File folder) {
        int indent = 0;
        StringBuilder sb = new StringBuilder();

        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        printDirectoryTree(folder, indent, sb);
        System.out.println(sb.toString());
        return sb.toString();
    }

    private static void printDirectoryTree(File folder, int indent,
                                           StringBuilder sb) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        String separator = System.getProperty("line.separator");
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(folder.getName());
        sb.append("/");
        sb.append(separator);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isDirectory()) {
                Thread t = new Thread(() -> printDirectoryTree(file, indent + 1, sb));
                t.start();
                try {
                    t.join();
                } catch (Exception e) {
                    System.out.println("Interrupted");
                }
            }
        }
    }


    private static String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("|  ");
        }
        return sb.toString();
    }
}







