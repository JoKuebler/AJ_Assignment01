package MultiFastaParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by JKuebler on 24/10/16.
 */
public class FastaTool {

    public BufferedReader buffer = null;


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

    //TODO create arraylist of sequences
    public ArrayList<String> getNames(String parsedFile) {

        ArrayList<Sequence> seqObj = new ArrayList<>();
        

        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> sequences = new ArrayList<>();
        String[] lineArray = parsedFile.split("\n");
        String curSeq = "";

        for (int i = 0; i < lineArray.length; i++) {
            if (lineArray[i].startsWith(">")) {
                if (curSeq != "") {
                    sequences.add(curSeq);
                    curSeq = "";
                }
                names.add(lineArray[i]);
            } else {
                curSeq+=lineArray[i];
            }
        }

        sequences.add(curSeq);

        return sequences;
    }

}
