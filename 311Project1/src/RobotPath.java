// import directives

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//
// primary class for this file
public class RobotPath {
// attributes
	
	private int start[] = new int[2];
	private int dest[] = new int[2];
	private int col;
	private int row;
	private int obs[][];
	private String grid[][];
	private Graph g;
	
	public RobotPath() {
		
		
	}
	
	public RobotPath(String filename) {
		try{readInput(filename);} catch (IOException e) {
			e.printStackTrace();}
	}
	
public void readInput(String filename) throws FileNotFoundException {
// implementation for reading file with grid information
	try {File file = new File(filename); //makes file an object
	Scanner text = new Scanner(file);
	
	//Rows and Cols
	text.useDelimiter(" ");
	String curr = text.next();//gets rid of nrows
	curr = text.next();//should be number
	row = Integer.parseInt(curr);
	curr = text.next();//gets rid of ncols
	text.useDelimiter("\n");
	curr = text.next();//should be number
	col = Integer.parseInt(curr.substring(1));// the space will need to be cut off due to the delimiter change
	
	//start
	text.useDelimiter(" ");
	curr = text.next();//gets rid of start
	curr = text.next();//should be number
	start[0]= Integer.parseInt(curr);
	text.useDelimiter("\n");
	curr = text.next();//should be number
	start[1] = Integer.parseInt(curr.substring(1));// the space will need to be cut off due to the delimiter change
	
	
	//dest
	text.useDelimiter(" ");
	curr = text.next();//gets rid of start
	curr = text.next();//should be number
	dest[0]= Integer.parseInt(curr);
	text.useDelimiter("\n");
	curr = text.next();//should be number
	dest[1] = Integer.parseInt(curr.substring(1));// the space will need to be cut off due to the delimiter change
	
	
	//obs
	ArrayList<int[]> obslist = new ArrayList<int[]>();
	curr = text.next();//get rid of obsticles word
	while(text.hasNext()) {
		curr = text.next();
		int split  = curr.indexOf(" ");
		int point[] = new int[2];
		

		point[0] = Integer.parseInt(curr.substring(0,split));
		point[1] = Integer.parseInt(curr.substring(split+1));
		obslist.add(point);
	}
	obs = new int[obslist.size()][2];
	for(int i  = 0; i<obslist.size();i++) {
		obs[i] = obslist.get(i);
	}
	
	
	//grid
	grid = new String[row][col];
	for(int i = 0;i<row;i++) {
		for (int j = 0; j<col;j++)
			grid[i][j] = "0";
		
	}
	for(int[] a : obs) {
		if(a[0] < 0 || a[0] > row || a[1] < 0 || a[1] > col) {//checks bounds to prevent errors
			System.out.println("Obstacle out of bounds");
			System.exit(1);
		}
		
		grid[a[0]][a[1]] = "*";
	}
	if(start[0] < 0 || start[0] > row || start[1] < 0 || start[1] > col) {//checks bounds to prevent errors
		System.out.println("Start out of bounds");
		System.exit(1);
	}
	if(grid[start[0]][start[1]].equals("*")) {
		System.out.println("Start on obstacle");
		System.exit(1);
	}
	grid[start[0]][start[1]] = "S";
	if(dest[0] < 0 || dest[0] > row || dest[1] < 0 || dest[1] > col) {//checks bounds to prevent errors
		System.out.println("Start out of bounds");
		System.exit(1);
	}
	if(grid[dest[0]][dest[1]].equals("*")) {
		System.out.println("Destination on obstacle");
		System.exit(1);
	}
	grid[dest[0]][dest[1]] = "D";
	
	
	
	
	
	//graph
	g = new Graph(row*col,grid);
	
	
	
	
	
	}catch (FileNotFoundException ex) {
	    System.out.println();
	    System.out.println("\nCannot locate the input file " + "'" + filename + "'" + "on your computer - please try again.");
	  
	}
	

	
	
	
	
	
}
public void planShortest() {

	int startVal = start[0]*col + start[1];
	bfs(g,startVal);
	
	
	
}
public void quickPlan() {
// uses the grid data for finding plan as per specs
	g.unvisitAll();
	g.unparentAll();
	
	int startVal = start[0]*col + start[1];
	g.getVertices()[startVal].setLayer(0);
	dfs(startVal);
}
public void output() {
// invoked after planShortest or quickPlan
	int destVal = dest[0]*col + dest[1];
	
	String Ngrid[][] = new String[col][row];
for(int i = 0;i<row;i++) {
		
		for (int j = 0; j<col;j++)
			Ngrid[i][j] = grid[i][j];
	}
	
	updateGrid(destVal,Ngrid);
for(int i = 0;i<row;i++) {
		
		for (int j = 0; j<col;j++)
			System.out.printf("%5s", Ngrid[i][j]);
		System.out.println();
	}
	
	
	
	
	
	
	
}
// Any other helper methods
void bfs(Graph g,int index) {//will find paths from a starting index
	g.unvisitAll();
	g.unparentAll();
	LinkedList<Integer> queue = new LinkedList<Integer>();
	g.getVertices()[index].visit();
	g.getVertices()[index].setLayer(0);

	queue.add(index);
	while(queue.size() > 0) {
		
		index = queue.poll();
		for(Edge e : g.getVertices()[index].edges()) {
			if(!(e.getTo().isVisited())) {
				e.getTo().visit();//set vertex as visited
				queue.add(e.getTo().index());
				e.getTo().parents().add(index);
				e.getTo().setLayer(g.getVertices()[index].layer()+1);
			}
			else if(e.getTo().parents().size()>0&&g.getVertices()[e.getTo().parents().get(0)].layer() == g.getVertices()[index].layer()) {
				//if the vertex we are going to has a parent that is in the same layer as the current one then it can also be 
				//in a fastest path so we add it to parents
				e.getTo().parents().add(index);
			}
		}
		
		
	}
	
	
}
boolean dfs(int index) {
	if(index == dest[0]*col + dest[1]) {
		return true;
	}
		
	g.getVertices()[index].visit();
	int yc = dest[0];//goal y
	int xc = dest[1];//goal x
	ArrayList<double[]> dist = new ArrayList<double[]>();
	for(Edge e : g.getVertices()[index].edges()) {
		if(!(e.getTo().isVisited())) {
		int i = e.getTo().index();
		int y = i/col;//new y
		int x = i%col;//new x
		double arr[] = {dist(xc,yc,x,y),i};
		dist.add(arr);
		}
	}
	while(!dist.isEmpty()) {
		double d[] = getMin(dist);
		g.getVertices()[(int)d[1]].parents().add(index);
		g.getVertices()[(int)d[1]].setLayer(g.getVertices()[index].layer()+1);
		if(dfs((int)d[1]))return true;
	}
	return false;
	
	
}
double[] getMin(ArrayList<double[]> d) {
	int index = 0;
	for(int i =0; i<d.size();i++) {
		if(d.get(index)[0]>d.get(i)[0])
			index = i;
	}
	return d.remove(index);
}
	

void updateGrid(int index,String[][] grid) {
	
	
	int yc = index/col;//current y
	int xc = index%col;//current x
	for(Integer i : g.getVertices()[index].parents()) {
		int y = i/col;
		int x = i%col;
		if(i == start[0]*col + start[1])//return if looking at start value
			return;
		if(grid[y][x].equals("0"))
			grid[y][x] = "";
		if(y>yc && !(grid[y][x].contains("n")) ) grid[y][x]  += "n";
		else if(y<yc && !(grid[y][x].contains("s"))) grid[y][x]  += "s";
		else if(x>xc && !(grid[y][x].contains("w"))) grid[y][x]  += "w";
		else if(x<xc && !(grid[y][x].contains("e"))) grid[y][x]  += "e";
		updateGrid(i,grid);
		}
}
double dist(int x1, int y1, int x2, int y2) {
	
	return Math.sqrt((double)(Math.pow(x1-x2,2) + Math.pow(y1-y2,2)));
}


	
	
	
	
}






interface
Visitable
{

  boolean isVisited();

  
  void setVisited(boolean visited);

  
  default void visit()
  {
    this.setVisited(true);
  }

 
  default void unvisit()
  {
    this.setVisited(false);
  }
}








interface
Vertex
  extends Visitable
{
 
  int index();//used to get index


  Collection<Edge> edges();//returns collection
  
  String data();//store data for the grid at the end
 int point[][] = {};//values for the grid
 int[] point();//return point
 ArrayList<Integer> parents();//return parents to be able to add to it outside of the class
 int layer();//check layer for finding multiple shortest paths
 void setLayer(int l);//sets layer




 
  @Override
  void setVisited(boolean visited);


}



interface
Edge
{
 

 

 
  Vertex
  getFrom();

  Vertex
  getTo();
  
  String toString();
}







/**
 * A graph used to generate a perfect hash table.
 *
 * @author Jeremy Lewis
 */

class
Graph
{
  /**
   * The vertices of the graph.
   */
  private
  final
  Vertex[]
  vertices;


  /**
   * Initializes the graph to have the given number of vertices.
   *
   * @param vertexCount
   *   the number of vertices in the graph
   *
   * @throws IllegalArgumentException
   *   if {@code vertexCount} is negative
   */
  public
  Graph(int vertexCount,String[][] grid)
    throws IllegalArgumentException
  {
    if (0 > vertexCount)
    {
      throw new IllegalArgumentException(String.valueOf(vertexCount));
    }


    this.vertices = new Vertex[vertexCount];
    int count = 0;
    for(int i =0;i<grid.length;i++) {
    	for(int j =0; j<grid[i].length;j++) {
    		int point[] = {i,j};
    		vertices[count] = new GraphVertex(count, grid[i][j], point);
    		count++;
    	}
    }
    for(Vertex v : vertices) {
    	if(v.data().equals("*"))//skip * since they dont connect to anything
    		continue;
    	int loc[] = v.point();
    	int height = grid.length;
    	int length = grid[1].length;
    	if(loc[1]-1>-1 && !(vertices[(loc[0]*length) + loc[1]-1].data().equals("*"))) {//left
    		addEdge(v.index(),vertices[(loc[0]*length) + loc[1]-1].index());
    	}
    	if(loc[1]+1<length && !(vertices[(loc[0]*length) + loc[1]+1].data().equals("*"))) {//right
    		addEdge(v.index(),vertices[(loc[0]*length) + loc[1]+1].index());
    	}
    	if(loc[0]-1>-1 && !(vertices[((loc[0]-1)*length) + loc[1]].data().equals("*"))) {//up
    		addEdge(v.index(),vertices[((loc[0]-1)*length) + loc[1]].index());
    	}
    	if(loc[0]+1<height && !(vertices[((loc[0]+1)*length) + loc[1]].data().equals("*"))) {//down
    		addEdge(v.index(),vertices[((loc[0]+1)*length) + loc[1]].index());
    	}
    }
  
  
  }




  /**
   * Returns the array of vertices used by this graph.
   *
   * @return
   *   the array of vertices used by this graph
   */
  public
  Vertex[]
  getVertices()
  {
    /*
     * For grading.
     * Do not change this method.
     */

    return vertices;
  }

  /**
   * Adds an undirected edge between the two indicated vertices.
   *
   * @param fromIdx
   *   the index of the from vertex
   * @param toIdx
   *   the index of the to vertex
   * @param index
   *   the index of the data of the edge to add
   * @param word
   *   the data of the edge to add
   *
   * @throws IndexOutOfBoundsException
   *   if either of {@code fromIdx} and {@code toIdx} are invalid vertex
   *   indices
   * @throws NullPointerException
   *   if {@code word} is {@code null}
   */
  public
  void
  addEdge(int fromIdx, int toIdx)
    throws IndexOutOfBoundsException,
           NullPointerException
  {
   
	 
	  if(fromIdx < 0 || fromIdx >= vertices.length) {
		  throw new IndexOutOfBoundsException();
	  }
	  if(toIdx < 0 || toIdx >= vertices.length) {
		  throw new IndexOutOfBoundsException();
	  }
	  //makes the new edge from fromIdx to toIdx vertex
	  GraphEdge fromTo = new GraphEdge( vertices[fromIdx], vertices[toIdx]);
	
	  //adds edge to each vertex's edge collection in the direction
	  vertices[fromIdx].edges().add(fromTo);
	  
  }

  /**
   * Marks all vertices and edges within the graph as unvisited.
   */
  public
  void
  unvisitAll()
  {
    
	for (Vertex v : vertices) {
		v.setVisited(false);//will set the vertex and its edges to unvisited
		
		
	}
  }
  public void unparentAll() {
	  for (Vertex v : vertices) {
			v.parents().clear();//will clear the parents
			
			
		}
	  
  }

 


   
  /**
   * Returns true if and only if this graph contains a cycle.
   *
   * @return
   *   whether this graph contains a cycle
   */


  @Override
  public
  String
  toString()
  {
   String toreturn = "";
   for(Vertex v: vertices)
	  toreturn += v.toString() + "\n";
	  return toreturn;
  }


  private
  class
  GraphVertex
    implements Vertex
  {
    /**
     * Whether this vertex is marked as visited.
     */
    private boolean visited;

    /**
     * The index of this vertex within the vertices array.
     */
    private final int index;

    /**
     * Outgoing edges from this vertex.
     */
    private final Collection<Edge> edges;
    private ArrayList<Integer> parents = new ArrayList<Integer>();
    private int layer = -1;//default value
    
    private String data;
    private int point[] = new int[2];


    /**
     * Initializes the vertex.
     *
     * @param index
     *   the index of the vertex within the vertices array
     */
    public
    GraphVertex(int index,String data, int point[])
    {
      this.visited = false;
      this.index = index;
      this.edges = new ArrayList<>();
      this.point  = point;
      this.data = data;
    }


    @Override
    public
    boolean
    isVisited()
    {
      return visited;
    }

    @Override
    public
    void
    setVisited(boolean visited)
    {
     
    	this.visited = visited;//just changes the variable
    	

      /*
       * Don't forget to handle the special false case.
       */
    }
    public ArrayList<Integer> parents(){return parents;}//use later to add parents
    public int layer() {return layer;}
    public void setLayer(int l) {layer = l;}
    @Override
    public
    int
    index()
    {
      return index;
    }
    @Override
    public String data() {return data;}
    public int[] point() {return point;}
    @Override
    public
    Collection<Edge>
    edges()
    {
      return edges;
    }

   

   

    @Override
    public
    String
    toString()
    {
      return "v[" + point[0] +"," + point[1] + ","+data+","+layer+"]: " + edges().toString();
    }
  }

  private
  class
  GraphEdge
    implements Edge
  {
  
    /**
     * The index of the data of this edge.
     */
    
 
    

    /**
     * The vertex from which this edge is outgoing.
     */
    private
    final
    Vertex
    from;

    /**
     * The vertex to which this edge is incoming.
     */
    private
    final
    Vertex
    to;


    /**
     * Initializes the edge.
     *
     * @param index
     *   the index of the data of the edge
     * @param data
     *   the data of the edge
     * @param from
     *   the vertex from which the edge is outgoing
     * @param to
     *   the vertex to which the edge is incoming
     */
    public
    GraphEdge(Vertex from, Vertex to)
    {
     
      
      
      this.from = from;
      this.to = to;
    }


  




   




    @Override
    public
    Vertex
    getFrom()
    {
      return from;
    }

    @Override
    public
    Vertex
    getTo()
    {
      return to;
    }
    public
    String
    toString()
    {
      return "("+from.point()[0] +"," + from.point()[1] +")--->("+to.point()[0] +"," + to.point()[1]+")";
    }

  
  }
}




