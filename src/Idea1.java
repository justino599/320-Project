import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Idea1 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        idea1("bin/one_giant_string.txt");
        long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + "ms");
    }

    public static ArrayList<String> idea1(String filename) throws IOException {
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

        // Read in words
        Iterator<String> wordIterator = new WordIterator(filename);
        ArrayList<String> words = new ArrayList<>(100000000);
        while (wordIterator.hasNext()) {
            words.add(wordIterator.next());
        }
        wordIterator = null;
        System.gc();

        long startTime = System.currentTimeMillis();

        // Main portion of algorithm
        ArrayList<String> output = new ArrayList<>();
//        outerLoop: while (words.hasNext()) {
//            String word = words.next();
        outerLoop: for (String word : words) {
            String uppercaseWord = word.toUpperCase();
            int charIdx = uppercaseWord.charAt(0) - 'A';
            if (0 <= charIdx && charIdx < 26) {
                for (String[] abbrev : abbreviations[charIdx]) {
                    if (uppercaseWord.equals(abbrev[0])) {
                        output.add(abbrev[1]);
                        continue outerLoop;
                    }
                }
            }
            output.add(word);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time (idea1): " + (endTime - startTime) + "ms");

        return output;
    }
}
