package MultiFastaParser;


import com.sun.tools.javac.util.StringUtils;

import javax.swing.plaf.SeparatorUI;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by JKuebler on 24/10/16.
 */
public class CommandLine {

    public BufferedReader buffer = null;
    public BufferedWriter writer = null;

    public void displayAlignment(ArrayList<Sequence> sequences, int breakpoint) {

        try {

            converter(sequences,breakpoint);
            writer = new BufferedWriter(new OutputStreamWriter(System.out));

            String formatFirstBlock = "%27s %" + breakpoint + "s";
            String formatSecondBlock = "%27s %" +  (sequences.get(0).sequence.length()-breakpoint) + "s";

            writer.write(String.format(formatFirstBlock, "1", Integer.toString(breakpoint)) + "\n");
            writer.write(converter(sequences,breakpoint)[0]);
            writer.write('\n');
            writer.write(String.format(formatSecondBlock, Integer.toString(breakpoint+1), sequences.get(0).sequence.length()+1 + "\n"));
            writer.write(converter(sequences,breakpoint)[1]);
            writer.write('\n');
            writer.write("Number of Sequences: "  + sequences.size() + '\n');
            writer.write("Shortest length: " + (getShortLong(sequences)[0]+1) + " (excluding '-'s: " + (getShortLong(sequences)[2])  + ")" + '\n');
            writer.write("Average length: " + (getAverage(sequences)+1) + " (excluding '-'s: " + (getAverageEx(sequences)) + ")" + '\n');
            writer.write("Longest length: " + (getShortLong(sequences)[1]+1) + " (excluding '-'s: " + (getShortLong(sequences)[3])  + ")" + '\n');
            writer.write("Counts: A: " + countChar(sequences, 'A') + ", C: " + countChar(sequences, 'C') + ", G: " + countChar(sequences, 'G') + ", U: " + countChar(sequences, 'U') + ", '-': " + countChar(sequences, '-') );
            writer.flush();



        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public String[] converter(ArrayList<Sequence> sequences, int breakpoint) {

        String[] arr = new String[2];
        String formatSeq = "%-25s %-10s";
        String firstBlock = "";
        String secondBlock = "";

        for (int i = 0; i < sequences.size(); i++) {
            firstBlock+= String.format(formatSeq, sequences.get(i).name, sequences.get(i).sequence.substring(0,breakpoint) + "\n");
            secondBlock+= String.format(formatSeq, sequences.get(i).name, sequences.get(i).sequence.substring(breakpoint+1,sequences.get(i).sequence.length()) + "\n");
        }

        arr[0] = firstBlock;
        arr[1] = secondBlock;

        return arr;
    }

    public int[] getShortLong(ArrayList<Sequence> sequences) {
        int curShortLength = sequences.get(0).sequence.length();
        int curLongLength = sequences.get(0).sequence.length();
        int[] shortLong = new int[4];

        for (int i = 0; i < sequences.size() ; i++) {
            if (sequences.get(i).sequence.length() < curShortLength) {
                curShortLength = sequences.get(i).sequence.length();
            }
            if (sequences.get(i).sequence.length() > curShortLength) {
                curLongLength = sequences.get(i).sequence.length();
            }
        }

        int sumChars = 0;
        int curLongEx = 0;
        int curShortEx = 999999;

        for (int i = 0; i < sequences.size() ; i++) {
            for (int j = 0; j < sequences.get(i).sequence.length(); j++) {
                if (sequences.get(i).sequence.charAt(j) != '-') {
                    sumChars++;
                }
            }

            if (sumChars > curLongEx) {
                curLongEx = sumChars;
            } else if (sumChars < curShortEx) {
                curShortEx = sumChars;
            }

            sumChars = 0;

        }

        shortLong[0] = curShortLength;
        shortLong[1] = curLongLength;
        shortLong[2] = curShortEx;
        shortLong[3] = curLongEx;

        return shortLong;
    }

    public int getAverage(ArrayList<Sequence> sequences) {
        int averageLength = 0;
        int sum = 0;

        for (int i = 0; i < sequences.size() ; i++) {
            sum+= sequences.get(i).sequence.length();
        }

        averageLength = sum/sequences.size();

        return averageLength;
    }


    public int getAverageEx(ArrayList<Sequence> sequences) {
        int aveLength = 0;
        int sumChars = 0;

        for (int i = 0; i < sequences.size() ; i++) {
            for (int j = 0; j < sequences.get(i).sequence.length(); j++) {
                if (sequences.get(i).sequence.charAt(j) != '-') {
                    sumChars++;
                }
            }

        }

        aveLength = sumChars/sequences.size();

        return aveLength;
    }

    public int countChar(ArrayList<Sequence> sequences, char letter) {
        int amount = 0;

        for (int i = 0; i < sequences.size() ; i++) {
            for (int j = 0; j < sequences.get(i).sequence.length(); j++) {
                if (sequences.get(i).sequence.charAt(j) == letter) {
                    amount++;
                }
            }
        }

        return amount;
    }

    //parses file into String
    public String readFile(String filepath) {

        String line = null;
        String fileString = "";

        try {
            buffer = new BufferedReader((new FileReader(filepath)));


            while ((line=buffer.readLine())!=null)
            {
                line+= "\n";
                fileString+=line;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileString;

    }

    //get names and actual sequences out of file and assign it into an Arraylist of Sequence Objects
    public ArrayList<Sequence> getSequences(String parsedFile) {

        ArrayList<Sequence> seqObj = new ArrayList<>();

        String[] lineArray = parsedFile.split("\n");
        String curSeq = "";
        Sequence curSequence = new Sequence();

        for (int i = 0; i < lineArray.length; i++) {
            if (lineArray[i].startsWith(">")) {
                if (curSequence.name != null) {
                    curSequence.sequence = curSeq;
                    curSeq = "";
                    seqObj.add(curSequence);
                }
                curSequence = new Sequence();
                curSequence.name = lineArray[i];
            } else {
                curSeq = curSeq + lineArray[i];
            }
        }

        curSequence.sequence = curSeq;
        seqObj.add(curSequence);

        return seqObj;

    }










}
