import java.util.Scanner;

public class printElems {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String l = scan.nextLine();
        l = l.substring(1,l.length()-1);
        String[] elems = l.split(", ");
        for (int i = 0; i < elems.length; i++) {
            int elem = Integer.parseInt(elems[i]);
            if (elem < 1000)
                System.out.println(elem);
        }
        scan.close();
    }
}
