package MultiFastaParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
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
    
    public void getNames(String parsedFile) {

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

    }

}
