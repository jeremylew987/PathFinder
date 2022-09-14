import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RobotPath a = new RobotPath();
		RobotPath b = new RobotPath();
		RobotPath c = new RobotPath();
		
		
		try{a.readInput("Grid0.txt");
		b.readInput("Grid1.txt");
		c.readInput("Grid15.txt");} catch (IOException e) {
			e.printStackTrace();
		}
		a.planShortest();
		a.output();
		System.out.println();
		a.quickPlan();
		a.output();
		System.out.println();
		b.planShortest();
		b.output();
		System.out.println();
		b.quickPlan();
		b.output();
		System.out.println();
		c.planShortest();
		c.output();
		System.out.println();
		c.quickPlan();
		//c.output();
		System.out.println();
	}

}
