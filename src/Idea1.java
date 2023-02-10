import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Idea1 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        // Read in abbreviations. Stored in an array of arraylists where each index of the array is a different letter that the abbreviation starts with
        BufferedReader abbrevReader = new BufferedReader(new FileReader("bin/abbreviations.csv"));
        ArrayList<String[]>[] abbreviations = new ArrayList[26];
        for (int i = 0; i < abbreviations.length; i++)
            abbreviations[i] = new ArrayList<>();

        String line;
        while ((line = abbrevReader.readLine()) != null) {
            String[] pieces = line.replaceAll("\"","").split(",");
            char firstChar = pieces[0].charAt(0);
            abbreviations[firstChar - 'A'].add(pieces);
        }

        // Main portion of algorithm
        Iterator<String> words = new WordIterator("bin/all_reviews_content_only.csv");
//        Iterator<String> words = new WordIterator("bin/one_giant_string.txt");
        ArrayList<String> output = new ArrayList<>();
        int replacementsMade = 0;
        outerLoop: while (words.hasNext()) {
            String word = words.next();
            String uppercaseWord = word.toUpperCase();
            int charIdx = uppercaseWord.charAt(0) - 'A';
            if (0 <= charIdx && charIdx < 26) {
                for (String[] abbrev : abbreviations[charIdx]) {
                    if (uppercaseWord.equals(abbrev[0])) {
                        output.add(abbrev[1]);
                        replacementsMade++;
                        continue outerLoop;
                    }
                }
            }
            output.add(word);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + "ms");
        System.out.println("Replacements made: " + replacementsMade);
        System.out.println("Output size: " + output.size());
    }
}
