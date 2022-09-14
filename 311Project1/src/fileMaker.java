import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//class provided by @Charles Yang on piazza


public class fileMaker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{System.out.println(GenerateRandomGrid(10,10,50));
		
		
		}catch (IOException e) {
			e.printStackTrace();}
		
	}
	public static String GenerateRandomGrid(int nRows, int nCols, int maxObstacles) throws IOException {
		// Setup
		File f = new File("Grid" + nRows + "R" + nCols + "C" + ".txt");
		FileWriter writer = new FileWriter(f);
		StringBuilder out = new StringBuilder();
		Random random = new Random();

		out.append("nrows "); out.append(nRows);
		out.append(' ');
		out.append("ncols "); out.append(nCols);
		out.append('\n');

		// Define start and dest
		int startR = random.nextInt(nRows);
		int startC = random.nextInt(nCols);
		int destR = random.nextInt(nRows);
		int destC = random.nextInt(nCols);
		while (destR == startR && destC == startC) {
		destR = random.nextInt();
		destC = random.nextInt();
		}

		        // Define obstacles
		 ArrayList<int[]> obstacles = new ArrayList<>();
		int r, c;
		for (int i = 0; i < maxObstacles; i++) {
		r = random.nextInt(nRows);
		c = random.nextInt(nCols);
		 while ((r == startR && c == startC) || (r == destR && c == destC)) {
		 r = random.nextInt(nRows);
		 c = random.nextInt(nCols);
		 }
		obstacles.add(new int[]{r, c});
		 }

		// Compile file text
		out.append("start "); out.append(startR); out.append(' '); out.append(startC);
		 out.append('\n');

		 out.append("dest "); out.append(destR); out.append(' '); out.append(destC);
		 out.append('\n');

		 out.append("obstacles");

		 for (int[] o : obstacles) {
		 out.append('\n');
		 out.append(o[0]); out.append(' '); out.append(o[1]);
		 }

		System.out.println(out);
		        // Output to file
 writer.write(out.toString());
	 writer.close();

	 return f.getName();
		 }

}
