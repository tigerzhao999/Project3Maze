import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ASCIIReader {
    /**
     * read maze converts a maze from the "+ - |" representation to one which is represented with bits
     *   2 2
     *   + +-+
     *   |   |
     *   +-+ +
     *   | |
     *   + +-+
     *
     *
     * @param fileName the file name to be converted
     * @return Maze representation with bits representing the walls
     */
    public static int[][] readMaze(String fileName) throws IOException {
        Scanner sc = new Scanner(new File(fileName));
        String[] xyString = sc.nextLine().split(" ");
        int x = Integer.parseInt(xyString[0]);
        int y = Integer.parseInt(xyString[1]);
        int[][] bitMaze = new int[x][y];
        for (int i = 0; i < y; i++) {
            // draw the north edge
            String edgeString = sc.nextLine();
            for (int j = 0; j < x; j++) {
                if(edgeString.charAt(1 + (j * 2)) == ' '){
                    bitMaze[i][j] = bitMaze[i][j] | DIR.N.bit;
                    if(i - 1 >= 0){
                        bitMaze[i - 1][j] = bitMaze[i - 1][j] | DIR.N.opposite.bit;
                    }
                }
            }
            // read the side to side edges
            String wallString = sc.nextLine();
            for (int j = 0; j < x; j++) {
                if(wallString.charAt(j * 2) == ' ') {
                    bitMaze[i][j] = bitMaze[i][j] | DIR.W.bit;
                    if(j - 1 >= 0){
                        bitMaze[i][j - 1] = bitMaze[i][j - 1] | DIR.W.opposite.bit;
                    }

                }
            }
        }
        return bitMaze;
    }
}
