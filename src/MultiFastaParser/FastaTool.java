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

    public static void main(String[] args) {

        CommandLine c1 = new CommandLine();
        c1.displayAlignment(c1.getNames(c1.readFile("/Users/jonas/Desktop/data01.fna")),60);


    }


}
