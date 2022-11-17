
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/*
 * recursive backtracking algorithm
 * shamelessly borrowed from the ruby at
 * http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
 */
public class MazeGenerator {
	//coordinates row x, column y
	private final int x;
	private final int y;
	//stores the cells
	private final int[][] maze;

	int visitedIndexDFS;
	int[][] visitedDFS;

	int visitedIndexBFS;
	int[][] visitedBFS;
	class CellIndex{
		int x;
		int y;
		public CellIndex(int nx, int ny){
			x = nx;
			y = ny;
		}

		@Override
		public String toString() {
			return "(" + x +","+ y +
					')';
		}
	}

	//Constructor
	public MazeGenerator(int x, int y) {
		this.x = x;
		this.y = y;
		maze = new int[this.x][this.y];
		generateMaze(0, 0);
	}

	public MazeGenerator(int[][] mazeArray){
		maze = mazeArray;
		this.y = mazeArray.length;
		this.x = mazeArray[0].length;
	}
	//prints the maze with the cells and walls removed 
	public void displayMaze() {
		for (int i = 0; i < y; i++) {
			// draw the north edge
			for (int j = 0; j < x; j++) {
				System.out.print((maze[i][j] & 1) == 0 ? "+---" : "+   ");
			}
			System.out.println("+");
			// draw the west edge
			for (int j = 0; j < x; j++) {
				System.out.print((maze[i][j] & 8) == 0 ? "|   " : "    ");
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j < x; j++) {
			System.out.print("+---");
		}
		System.out.println("+");
	}

	//prints the maze with the cells and walls removed
	public void displayMazePath(ArrayList<CellIndex> path) {
		for (int i = 0; i < y; i++) {
			// draw the north edge
			for (int j = 0; j < x; j++) {
				System.out.print((maze[i][j] & 1) == 0 ? "+---" : "+   ");
			}
			System.out.println("+");
			// draw the west edge
			for (int j = 0; j < x; j++) {
				//logic for drawing the #
				System.out.print((maze[i][j] & 8) == 0 ? "|" : " ");
				boolean inPath = false;
				for(CellIndex ci : path){
					if(ci.x == i && ci.y == j){
						inPath = true;
					}
				}
				if(inPath){
					System.out.print(" # ");
				}
				else{
					System.out.print("   ");
				}
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j < x; j++) {
			System.out.print("+---");
		}
		System.out.println("+");

		System.out.println(path.toString());
	}


	//prints the maze with the cells and walls removed
	public void displayMazeVisited() {
		for (int i = 0; i < y; i++) {
			// draw the north edge
			for (int j = 0; j < x; j++) {
				System.out.print((maze[i][j] & 1) == 0 ? "+---" : "+   ");
			}
			System.out.println("+");
			// draw the west edge
			for (int j = 0; j < x; j++) {
				System.out.print((maze[i][j] & 8) == 0 ? "|" : " ");
				if(visitedDFS[i][j] > 0){
					System.out.print(" " + (visitedDFS[i][j] - 1)% 10 + " ");
				}
				else{
					System.out.print("   ");
				}
			}
			System.out.println("|");
		}
		// draw the bottom line
		for (int j = 0; j < x; j++) {
			System.out.print("+---");
		}
		System.out.println("+");
	}



	//recursive perfect maze generator, using a modified DFS
	//(cx,cy) coordinates of current cell, and (nx,ny) coordinates of neighbor cell
	private void generateMaze(int cx, int cy) {
		DIR[] dirs = DIR.values();
		Collections.shuffle(Arrays.asList(dirs));
		for (DIR dir : dirs) {
			//find neighbor cell
			int nx = cx + dir.dx;
			int ny = cy + dir.dy;
			//if neighbor exists and not visited
			if (isValid(nx, ny) && (maze[nx][ny] == 0)) {
				//remove walls
				//update current cell using or (|) bit operations
				//example if a cell has north (1) and south (2) neighbor openings, maze holds 3
				//example if a cell has east (4) and west (8) neighbor openings, maze holds 12
				
				maze[cx][cy] |= dir.bit;
				//update neighbor cell
				maze[nx][ny] |= dir.opposite.bit;
				//recursive call to neighbor cell
				generateMaze(nx, ny);
			}
		}
	}
	//prints the value of maze array
	public void displayCells() {
		for (int i=0;i<x;i++) {
			for (int j=0;j<y;j++)
				System.out.print(" "+ maze[i][j] + ",");
			System.out.println();
			}
	}

	/**
	 * Checks for a valid cell indices
	 * @param nx x Coordinate of the cell
	 * @param ny y Coordinate of the cell
	 * @return
	 */
	private boolean isValid(int nx, int ny) {
		return (nx >= 0) && (nx < x) && (ny >= 0) && (ny < y);
	}

	// enum type for all directions 


	public void BFS(){
		visitedIndexDFS = 1;
		visitedDFS = new int[x][y];
		LinkedList<CellIndex> queue = new LinkedList<>();
		queue.addLast(new CellIndex(0,0));
		while(!queue.isEmpty()){
			int cx = queue.peek().x;
			int cy = queue.peek().y;
			DIR[] dirsBFS = {DIR.E, DIR.S, DIR.N, DIR.W};
			for(DIR dir : dirsBFS){
				if((maze[cx][cy] & dir.bit) != 0){
					//find neighbor cell
					int nx = cx + dir.dx;
					int ny = cy + dir.dy;
					if(visitedDFS[nx][ny] == 0){
						visitedDFS[nx][ny] = visitedIndexDFS++;
						queue.addLast(new CellIndex(nx, ny));
						ArrayList<CellIndex> path = DFSRunner(nx,ny);
					}
				}
			}
			//clear path cannidate.
			queue.remove();
		}
	}


	public ArrayList<CellIndex> DFS(){
		//make sure to subtract 1 when printing to differentiate visited from not visited (array initalizes as all 0s)
		visitedIndexDFS = 1;
		visitedDFS = new int[x][y];
		ArrayList<CellIndex> path = DFSRunner(0,0);
		displayMazeVisited();
		System.out.println("Visited Cells: " + (visitedIndexDFS - 1));
		displayMazePath(path);
		System.out.println("Path Length : " + (path.size()));
		return path;
	}
	private ArrayList<CellIndex> DFSRunner(int cx, int cy){
		visitedDFS[cx][cy] =  visitedIndexDFS++;
		if(cx == (x - 1) && cy == (y - 1)){
			ArrayList<CellIndex> path = new ArrayList<>();
			path.add(new CellIndex(cx,cy));
			return path;
		}
		DIR[] dirsDFS = {DIR.E, DIR.S, DIR.N, DIR.W};
		for(DIR dir : dirsDFS){
			if((maze[cx][cy] & dir.bit) != 0){
				//find neighbor cell
				int nx = cx + dir.dx;
				int ny = cy + dir.dy;
				if(isValid(nx,ny) && visitedDFS[nx][ny] == 0){
					ArrayList<CellIndex> path = DFSRunner(nx,ny);
					if(path != null){
						path.add(0,new CellIndex(cx,cy));
						return path;
					}
				}
			}
		}
		return null;
	}

	public static void main(String[] args) {

		
		MazeGenerator maze33 = new MazeGenerator(2, 2);
		
		maze33.displayCells();
	    maze33.displayMaze();
		maze33.DFS();
	    /*
	    MazeGenerator maze58 = new MazeGenerator(5, 8);
		
		maze58.displayCells();
	    maze58.displayMaze();
	    */
	    
	    //MazeGenerator maze55 = new MazeGenerator(5, 5);
		
		//maze55.displayCells();
	    //maze55.displayMaze();
	}





}