import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;


public class Algorithm2 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        algorithm2("bin/one_giant_string.txt");

        long endTime = System.currentTimeMillis();
        System.out.println("Total Time: " + (endTime - startTime) + "ms");
    }

    public static void algorithm2(String filename) throws IOException {
        // Load abbreviations into a HashMap
        HashMap<String, String> abbreviations = new HashMap<>(1000);
        BufferedReader abbrevReader = new BufferedReader(new FileReader("bin/abbreviations.csv"));
        String line;
        while ((line = abbrevReader.readLine()) != null) {
            String[] pieces = line.replaceAll("\"", "").split(",");
            abbreviations.put(pieces[0].toUpperCase(), pieces[1]);
        }
        abbrevReader.close();

        // Load words into an ArrayList
        WordIterator wordIterator = new WordIterator(filename);
        ArrayList<String> words = new ArrayList<>(100000000);
        while (wordIterator.hasNext()) {
            words.add(wordIterator.next());
        }
        wordIterator = null;
        System.gc();

        long[][] runtimes = new long[100][10];
        for(int limit = 1000000; limit <= 100000000; limit += 1000000) {
            System.out.println("Current Iteration: " + limit);
            for (int j = 0; j < 10; j++) {
                long startTime = System.currentTimeMillis();

                // Main portion of algorithm
                ListIterator<String> wordsIterator = words.listIterator();
                int i = 0;
                while (i < limit) {
                    String replacement = abbreviations.get(wordsIterator.next().toUpperCase());
                    if (replacement != null)
                        wordsIterator.set(replacement);
                    i++;
                }

                long endTime = System.currentTimeMillis();
                runtimes[(limit - 1000000) / 1000000][j] = (endTime - startTime);
            }
        }
        System.out.println("Data: ");
        for(int limit = 1000000; limit < 100000000; limit += 1000000) {
            System.out.print(limit + ", ");
        }
        System.out.println();
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + ", ");
            for (int j = 0; j < 100; j++)
                System.out.print(runtimes[j][i-1] + ", ");
            System.out.println();
        }
        System.out.println("We done");
    }
}
