import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Algorithm1 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        algorithm1("bin/all_reviews_content_only.csv");

        long endTime = System.currentTimeMillis();
        System.out.println("Total Time: " + (endTime - startTime) + "ms");
    }

    public static void algorithm1(String filename) throws IOException {
        // Read in abbreviations. Stored in an array of arraylists where each index of the array is a different letter that the abbreviation starts with
        BufferedReader abbrevReader = new BufferedReader(new FileReader("bin/abbreviations.csv"));
        ArrayList<String[]>[] abbreviations = new ArrayList[26];
        for (int i = 0; i < abbreviations.length; i++)
            abbreviations[i] = new ArrayList<>();

        String line;
        while ((line = abbrevReader.readLine()) != null) {
            String[] pieces = line.replaceAll("\"","").split(",");
            pieces[0] = pieces[0].toUpperCase();
            char firstChar = pieces[0].charAt(0);
            abbreviations[firstChar - 'A'].add(pieces);
        }
        abbrevReader.close();

        // Read in all words from the data file
        Iterator<String> wordIterator = new WordIterator(filename);
        ArrayList<String> words = new ArrayList<>(100000000);
        while (wordIterator.hasNext()) {
            words.add(wordIterator.next());
        }
        wordIterator = null;
        words.trimToSize();
        System.gc();

        long startTime = System.currentTimeMillis();

        // Main portion of algorithm
        ListIterator<String> wordsIterator = words.listIterator();
        outerLoop: while (wordsIterator.hasNext()) {
            String word = wordsIterator.next();
            String uppercaseWord = word.toUpperCase();
            int firstChar = uppercaseWord.charAt(0) - 'A';
            if (0 <= firstChar && firstChar < 26) {
                for (String[] abbrev : abbreviations[firstChar]) {
                    if (uppercaseWord.equals(abbrev[0])) {
                        wordsIterator.set(abbrev[1]);
                        continue outerLoop;
                    }
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time (Algorithm 1): " + (endTime - startTime) + "ms");
    }
}
