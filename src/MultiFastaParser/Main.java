package MultiFastaParser;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        FastaTool f1 = new FastaTool();
        ArrayList<String> sequences = f1.getNames(f1.readFile("/Users/jonas/Desktop/data01.fna"));
        System.out.println(sequences.get(6));



    }
}
