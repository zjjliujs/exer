import java.util.Arrays;

public class SimpleObjRef {
    public static void main(String[] args) {
        String[] stringArray = { "Barbara", "James", "Mary", "John",
            "Patricia", "Robert", "Michael", "Linda" };
        System.out.println (Arrays.toString(stringArray));

        Arrays.sort(stringArray, String::compareToIgnoreCase);

        System.out.println (Arrays.toString(stringArray));
    }
}
