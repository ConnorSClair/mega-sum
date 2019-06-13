package com.connor.hpc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;

/**
 * Hello world!
 */
public final class App {
    String name;
    String pathName;
    Path path;
    int lines;
    File file;
    ArrayList<Integer> data;

    private App() {
        this.name = "temp.txt";
        this.pathName = "mega-sum/target/" + name;
        this.path = FileSystems.getDefault().getPath("mega-sum/target/", name);
        this.lines = 30;
    }

    private void generateTestFile(boolean overwrite) {
        if (!Files.exists(path) || overwrite) {
            Generate.newFile(lines, pathName);
        }
        file = new File(pathName);
        Generate.viewFile(pathName);
    }

    /**
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        // todo input from user
        App app = new App();
        app.generateTestFile(true);
        app.readAndStoreData();
        app.timeSolve();
        // write concurrent application to open file
        // threadPoolExecutor
        // thread(section) reads part of file
        // returns result?
        // return result to an array of size of threads

    }

    private ArrayList<Integer> readAndStoreData() {
        data = new ArrayList<>();
        try {
            FileReader inFile = new FileReader(file);
            BufferedReader inStream = new BufferedReader(inFile);
            String inString;
            while ((inString = inStream.readLine()) != null) {
                data.add(Integer.parseInt(inString));
            }
        } catch (IOException e) {
            // do nothing
        }
        return data;
    }

    private void timeSolve() {
        long result;
        for (int i = 0; i < 2; i++) {
            Instant beforeSolve = Instant.now();
            if (i == 0) {
                result = Solve.solve(data);
            } else {
                result = QuickSolve.quickSolve(data,2);
            }
            Instant afterSolve = Instant.now();
            long ms = (afterSolve.toEpochMilli() - beforeSolve.toEpochMilli());
            System.out.println(String.format("%d ms", ms));
            System.out.println(String.format("Solved! result = %d", result));
        }
        
        
    }
}
