import java.io.IOException;

public class ARTester {
    public static void main(String[] args) throws IOException {
        String fileName = "./src/maze4.txt";
        int[][] art = ASCIIReader.readMaze(fileName);
        MazeGenerator mg = new MazeGenerator(art);
        mg.displayCells();
        mg.displayMaze();
        mg.DFS();
    }

}
