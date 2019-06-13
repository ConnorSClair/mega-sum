package com.connor.hpc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.awt.Desktop;

public class Generate {

    // static function. Build a massive text file and spit it out 
    // N lines, M=1 ints for now
    public static void newFile(int lines,String pathName) {
        FileWriter fileWriter;
        Random random = new Random(1000);
        int number;
        try {
            fileWriter = new FileWriter(pathName);
            for (int i = 0; i < lines; i++) {
                number = random.nextInt(30) + 50000;
                fileWriter.write(String.format("%d\n",number));
            }
            fileWriter.close();
            
        } catch (IOException e) {
            System.err.println(String.format("could not write to file %s",pathName));
        }   
    }

    public static void viewFile(String pathName) {
        // todo: super lazy and no error checking
        File file = new File(pathName);
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            System.err.println("Couldn't find file / couldn't open file / couldn't access desktop");
        }
    }
}