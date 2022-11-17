import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MazeTest {
    private MazeGenerator mg;
    @Test
    public void test55(){
        int[][] testArray55 = {
                {2, 6, 12, 10, 2},
                {3, 3, 2, 5, 11},
                {5, 9, 7, 8, 3},
                {6, 8, 7, 12, 9},
                {5, 12, 13, 12, 8}};
        mg = new MazeGenerator(testArray55);
    }

}

