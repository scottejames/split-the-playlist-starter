import java.io.IOException;
import java.util.Arrays;

/** Demo runner — runs minLongestSide on one example case and prints the result. */
public class Main {
    public static void main(String[] args) throws IOException {
        TestData.TestCase c = TestData.loadCase(TestData.TEST_DATA_DIR + "/medium/01_trap_at_scale.txt");

        System.out.println("Case: " + c.name);
        System.out.println("Songs: " + Arrays.toString(c.songs));
        System.out.println("Sides (d): " + c.d);

        long result = SplitPlaylist.minLongestSide(c.songs, c.d);
        System.out.println("Minimum possible longest side: " + result);
    }
}
