package manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
    public static void assertTrue(boolean bool, String s) {
        if (!bool) {
            IO.println("assertion failed: " + s);
            GameManager.endWithError("assertion failed");
        }
    }

    public static void assertTrue(boolean bool) {
        assertTrue(bool, "(unfilled assertion)");
    }

    public static <T> ArrayList<T> deepCopy(List<T> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);

            return new ArrayList<>((List<T>) in.readObject());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static int randint(int num1, int num2) {
        Random r = new Random();
        return num1 + r.nextInt(num2 - num1 + 1);
    }
}
