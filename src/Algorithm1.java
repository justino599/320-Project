import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Algorithm1 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        algorithm1("bin/all_reviews_content_only.csv");
        long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + "ms");
    }

    public static void algorithm1(String filename) throws IOException {
        // Read in abbreviations. Stored in an array of arraylists where each index of the array is a different letter that the abbreviation starts with
        BufferedReader abbrevReader = new BufferedReader(new FileReader("bin/abbreviations.csv"));
        ArrayList<String[]>[] abbreviations = new ArrayList[26];
        for (int i = 0; i < abbreviations.length; i++)
            abbreviations[i] = new ArrayList<>();

        String line;
        while ((line = abbrevReader.readLine()) != null) {
            String[] pieces = line.replaceAll("\"", "").split(",");
            pieces[0] = pieces[0].toUpperCase();
            char firstChar = pieces[0].charAt(0);
            abbreviations[firstChar - 'A'].add(pieces);
        }
        abbrevReader.close();

        // Read in all words from the data file
        Iterator<String> wordIterator = new WordIterator(filename);
        ArrayList<String> input = new ArrayList<>(110000000);
        while (wordIterator.hasNext())
            input.add(wordIterator.next());

        wordIterator = null;
        System.gc();

        // Main portion of algorithm

//        outerLoop: while (wordIterator.hasNext()) {
//            String word = wordIterator.next();
//        int limit = input.size();
        // int limit=600000;

        System.out.println(input.size());
        long[][] runtimes = new long[100][10];
        for(int limit = 1000000; limit < 100000000; limit += 1000000) {
            System.out.println("Current Iteration: " + limit);
            for (int j = 0; j < 10; j++) {
                long start = System.currentTimeMillis();
                ArrayList<String> output = new ArrayList<>(110000000);
                outerLoop:
                for (int i = 0; i < limit; i++) {
                    String word = input.get(i);
                    String uppercaseWord = word.toUpperCase();
                    int firstChar = uppercaseWord.charAt(0) - 'A';
                    if (0 <= firstChar && firstChar < 26) {
                        for (String[] abbrev : abbreviations[firstChar]) {
                            if (uppercaseWord.equals(abbrev[0])) {
                                output.add(abbrev[1]);
                                continue outerLoop;
                            }
                        }
                    }
                    output.add(word);
                    long end = System.currentTimeMillis();
                    runtimes[(limit - 1000000)/1000000][j] = (end - start);
                    System.out.println(end - start);
                }
            }
        }
        for (long[] runtime : runtimes)
            System.out.println(Arrays.toString(runtime));
    }
}
