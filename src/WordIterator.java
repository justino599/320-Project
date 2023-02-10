import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class WordIterator implements Iterator<String> {
    private final byte[] bytes;
    private int nextIndex;
    private boolean currentlyReadingWord;

    public WordIterator(String filename) throws IOException {
        bytes = new BufferedInputStream(new FileInputStream(filename)).readAllBytes();
        currentlyReadingWord = Character.isLetter(bytes[0]);
    }
    @Override
    public boolean hasNext() {
        return nextIndex < bytes.length;
    }

    /**
     * @return the next "word" in the file. A word is either a sequence of only letters or a sequence of only non-letters (the characters between words)
     */
    @Override
    public String next() {
        StringBuilder word = new StringBuilder();

        while (hasNext()) {
            char c = (char) bytes[nextIndex++];
            if (currentlyReadingWord != (currentlyReadingWord = Character.isLetter(c))) {
                nextIndex--;
                return word.toString();
            } else {
                word.append(c);
            }
        }

        return word.toString();
    }
}