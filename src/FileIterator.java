import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class FileIterator implements Iterator<String> {
    private final BufferedReader bufferedReader;
    private String nextLine;

    public FileIterator(String filename) throws FileNotFoundException {
        bufferedReader = new BufferedReader(new FileReader(filename));
        nextLine = getNextString();
    }

    @Override
    public boolean hasNext() {
        return nextLine != null;
    }

    @Override
    public String next() {
        String out = nextLine;
        nextLine = getNextString();
        return out;
    }

    @SuppressWarnings("StringConcatenationInLoop")
    private String getNextString() {
        try {
            String s = bufferedReader.readLine();
            if (s == null)
                return null;
            while (!s.endsWith("\""))
                s += '\n' + bufferedReader.readLine();
            if (s.length() > 2)
                return s.substring(1, s.length() - 1);
            else
                return getNextString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}