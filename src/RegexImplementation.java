import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * An implementation using regular expressions as a baseline for comparison. Pretty slow. Estimated time: 40 minutes.
 */
public class RegexImplementation {
    public static void main(String[] args) throws FileNotFoundException {
        // Read in data
        ArrayList<String> lines = new ArrayList<>();
        FileIterator fileIterator = new FileIterator("bin/all_reviews_content_only.csv");
        // Limit number of lines read in
        int howMany = 10000;
        for (int i = 0; i < howMany && fileIterator.hasNext(); i++)
            lines.add(fileIterator.next());

        // Read in abbreviations
        HashMap<String, String> abbrev = new HashMap<>();
        fileIterator = new FileIterator("bin/abbreviations.csv");
        while (fileIterator.hasNext()) {
            String[] line = fileIterator.next().split(",");
            abbrev.put(line[0].substring(1,line[0].length()-1),line[1].substring(1,line[1].length()-1));
        }

        // Replace abbreviations
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bin/regex_output.csv"))){
            for (int i = 0; i < lines.size(); i++) {
                for (String key : abbrev.keySet()) {
                    Pattern pattern = Pattern.compile("([^\\w]|^)(" + key + ")(?=[^\\w]|$)");
                    lines.set(i, pattern.matcher(lines.get(i)).replaceAll("$1" + abbrev.get(key)));
                }
                writer.write(lines.get(i) + '\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}