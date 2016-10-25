package MultiFastaParser;


import com.sun.tools.javac.util.StringUtils;

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
            writer.write("Number of Sequences: "  + sequences.size());

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

    public int countChar(char letter) {
        int amount = 0;
        

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

    public ArrayList<Sequence> getNames(String parsedFile) {

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
