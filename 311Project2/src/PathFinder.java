import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;




public class PathFinder {
private int numVertex;
private int numEdge;
private Graph layout;

public PathFinder() {}

public void readInput(String filename) throws FileNotFoundException {
	try {File file = new File(filename); //makes file an object
	Scanner text = new Scanner(file);
	
	
	numVertex = text.nextInt();
	numEdge = text.nextInt();
	
	//for loop for vertex
	ArrayList<int[]> list = new ArrayList<int[]>();
	for(int i = 0; i<numVertex;i++) {
		
		int v[] = new int[3];
		v[0] = text.nextInt();
		v[1] = text.nextInt();
		v[2] = text.nextInt();
		
		list.add(v);
	}
	
	//for loop for edges
	ArrayList<int[]> list2 = new ArrayList<int[]>();
for(int i = 0; i<numEdge;i++) {
	int e[] = new int[2];
	e[0] = text.nextInt();
	e[1] = text.nextInt();
	list2.add(e);
	
	}
	
layout = new Graph(numVertex, list, list2);


	
	
	
	
	}
	catch (FileNotFoundException ex) {
	    System.out.println();
	    System.out.println("\nCannot locate the input file " + "'" + filename + "'" + "on your computer - please try again.");
	  
	}
	
	
	
	
}
public static double distance(int x1, int y1, int x2, int y2) {
return Math.sqrt((x1 - x2)* (x1 - x2) + (y1 - y2) * (y1 - y2));
}
public double[] shortestPathDistances(int s) {
	double d[] = new double[numVertex];
	int cur = s;
	layout.unvisitAll();
	for(int i = 0; i<numVertex;i++) {
		d[i] = Double.MAX_VALUE;
	}
	d[s] = 0;
	layout.getVertices()[s].visit();
	MinHeap pQ = new MinHeap();
	for(int i = 0; i<numVertex; i++) {
		pQ.add(i, d[i]);
	}
	while(!pQ.isEmpty()&&(pQ.getRootValue() != Double.MAX_VALUE)) {
		cur = pQ.extractMin();
		layout.getVertices()[cur].visit();
		for(Edge e : layout.getVertices()[cur].edges()) {
			if(e.getTo().isVisited())continue;
			int index = e.getTo().index();
			double weight = e.getWeight();
			d[index] = Math.min(d[index], d[cur] + weight);
			pQ.updateKey(index, d[index]);
			
			
		}
	}
	for(int i = 0; i<numVertex;i++) {
		if(d[i] == Double.MAX_VALUE) d[i] = -1;
	}
	
	
	
	
	return d;
	
}
public int noOfShortestPaths(int s, int dest) {
	double d[] = new double[numVertex];
	int paths[]  = new int[numVertex];
	int cur = s;
	layout.unvisitAll();
	for(int i = 0; i<numVertex;i++) {
		d[i] = Double.MAX_VALUE;
		paths[i] = 0;
	}
	paths[s] = 1;
	d[s] = 0;
	layout.getVertices()[s].visit();
	MinHeap pQ = new MinHeap();
	for(int i = 0; i<numVertex; i++) {
		pQ.add(i, d[i]);
	}
	while(!pQ.isEmpty()&&(pQ.getRootValue() != Double.MAX_VALUE)) {
		cur = pQ.extractMin();
		layout.getVertices()[cur].visit();
		for(Edge e : layout.getVertices()[cur].edges()) {
			int index = e.getTo().index();
			double weight = e.getWeight();
			if(e.getTo().isVisited()) {
			if(d[cur] + weight == d[index]) {
				paths[index] +=paths[cur];
			}
			}
			else {
				if(d[cur] + weight == d[index]) {
					paths[index] +=paths[cur];
				}
				else if(d[cur] + weight < d[index]){
					paths[index] =paths[cur];

				}
			d[index] = Math.min(d[index], d[cur] + weight);
			pQ.updateKey(index, d[index]);
			}
			
		}
	}
	
	
	
	
	return paths[dest];
}
public ArrayList<Integer> fromSrcToDest(int s, int dest, int A, int B){
	ArrayList<Integer> r = new ArrayList<Integer>();
	double d[] = new double[numVertex];
	int parent[] = new int[numVertex];
	int cur = s;
	layout.unvisitAll();
	for(int i = 0; i<numVertex;i++) {
		d[i] = Double.MAX_VALUE;
		parent[i] = -1; 
	}
	d[s] = 0;
	layout.getVertices()[s].visit();
	MinHeap pQ = new MinHeap();
	for(int i = 0; i<numVertex; i++) {
		pQ.add(i, d[i]);
	}
	while(!pQ.isEmpty()&&(pQ.getRootValue() != Double.MAX_VALUE)) {
		cur = pQ.extractMin();
		layout.getVertices()[cur].visit();
		for(Edge e : layout.getVertices()[cur].edges()) {
			if(e.getTo().isVisited())continue;
			int index = e.getTo().index();
			double weight = e.getWeight();
			if(A*(d[cur] + weight) + B*(distance(e.getTo().point()[0], e.getTo().point()[1], layout.getVertices()[dest].point()[0], layout.getVertices()[dest].point()[1])
					- distance(e.getFrom().point()[0], e.getFrom().point()[1], layout.getVertices()[dest].point()[0], layout.getVertices()[dest].point()[1])) < d[index]) {
				parent[index] = cur;//Updated TODO
				
			}
			
			
			d[index] = Math.min(d[index], A*(d[cur] + weight) + B*(distance(e.getTo().point()[0], e.getTo().point()[1], layout.getVertices()[dest].point()[0], layout.getVertices()[dest].point()[1])
					- distance(e.getFrom().point()[0], e.getFrom().point()[1], layout.getVertices()[dest].point()[0], layout.getVertices()[dest].point()[1])));
			pQ.updateKey(index, d[index]);//Updated TODO
			
			
		}
	}
	for(int i = 0; i<numVertex;i++) {
		if(d[i] == Double.MAX_VALUE) d[i] = -1;
	}
	
	int par = dest;
	r.add(dest);
	while(parent[par] != -1) {
		r.add(0,parent[par]);
		par = parent[par];
	}
	
	
	
	
	
	
	
	
	
	
	return r;
}
public ArrayList<Integer> fromSrcToDestVia(int s,  int dest,ArrayList<Integer> inter, int A, int B){
	ArrayList<Integer> r = new ArrayList<Integer>();
	r.addAll(fromSrcToDest(s,inter.get(0),A,B));
	for(int i = 0; i < inter.size()-1;i++) {
		ArrayList<Integer> temp = fromSrcToDest(inter.get(i),inter.get(i+1),A,B);
		temp.remove(0);
		r.addAll(temp);
	}
	ArrayList<Integer> temp = fromSrcToDest(inter.get(inter.size()-1),dest,A,B);
	temp.remove(0);
	r.addAll(temp);
	
	
	return r;
}

public int[] minCostReachabilityFromSrc(int s) {
	double d[] = new double[numVertex];
	int parent[] = new int[numVertex];
	int cur = s;
	layout.unvisitAll();
	for(int i = 0; i<numVertex;i++) {
		d[i] = Double.MAX_VALUE;
		parent[i] = -1;
	}
	d[s] = 0;
	parent[s] = s;//Added TODO
	layout.getVertices()[s].visit();
	MinHeap pQ = new MinHeap();
	for(int i = 0; i<numVertex; i++) {
		pQ.add(i, d[i]);
	}
	while(!pQ.isEmpty()&&(pQ.getRootValue() != Double.MAX_VALUE)) {
		cur = pQ.extractMin();
		layout.getVertices()[cur].visit();
		for(Edge e : layout.getVertices()[cur].edges()) {
			if(e.getTo().isVisited())continue;
			int index = e.getTo().index();
			double weight = e.getWeight();
			if( weight < d[index]) {
				parent[index] = cur;
			}
			
			d[index] = Math.min(d[index], weight);
			pQ.updateKey(index, d[index]);
			
			
		}
	}
	return parent;
	
	
}
public double minCostOfReachabilityFromSrc(int s) {
	double d[] = new double[numVertex];
	int cur = s;
	layout.unvisitAll();
	for(int i = 0; i<numVertex;i++) {
		d[i] = Double.MAX_VALUE;
		
	}
	d[s] = 0;
	layout.getVertices()[s].visit();
	MinHeap pQ = new MinHeap();
	for(int i = 0; i<numVertex; i++) {
		pQ.add(i, d[i]);
	}
	while(!pQ.isEmpty()&&(pQ.getRootValue() != Double.MAX_VALUE)) {
		cur = pQ.extractMin();
		layout.getVertices()[cur].visit();
		for(Edge e : layout.getVertices()[cur].edges()) {
			if(e.getTo().isVisited())continue;
			int index = e.getTo().index();
			double weight = e.getWeight();
			
			d[index] = Math.min(d[index], weight);
			pQ.updateKey(index, d[index]);
			
			
		}
	}
	double r = 0;
	for(int i = 0; i<numVertex;i++) {
		if(!( d[i] == Double.MAX_VALUE)) {
			r += d[i];
		}
	}
	return r;
	
}
public boolean isFullReachableFromSrc(int s) {
	layout.unvisitAll();
	layout.unparentAll();
	LinkedList<Integer> queue = new LinkedList<Integer>();
	layout.getVertices()[s].visit();
	layout.getVertices()[s].setLayer(0);

	queue.add(s);
	while(queue.size() > 0) {
		
		s = queue.poll();
		for(Edge e : layout.getVertices()[s].edges()) {
			if(!(e.getTo().isVisited())) {
				e.getTo().visit();//set vertex as visited
				queue.add(e.getTo().index());
				
				e.getTo().setLayer(layout.getVertices()[s].layer()+1);
			}
			
		}
		
		
	}
	for(Vertex v : layout.getVertices()) {
		if(!(v.isVisited())) {
			return false;
		}
	}
	return true;
	
	
	
}
	
	
	
}


 class MinHeap{
	static int maxheaplength = 10000;
	double values[];
	int heap[];
	
	int size;
	
	
	public MinHeap() {
		size = 0;
		values = new double[maxheaplength];
		heap = new int[maxheaplength];
	}
	void add(int k, double v) {
		values[k] = v;
		if(size == 0)
			heap[0] = k;
		else {
			int i = size;
			heap[size] = k;
			while(values[k] <=values[heap[(i-1)/2]]) {
				if(values[k] ==values[heap[(i-1)/2]]) {
					if(k<heap[(i-1)/2])swap(i,(i-1)/2);
				}
				else swap(i,(i-1)/2);
				if(i==0)break;
				i = (i-1)/2;
			}
		}
		
		size++;
		
	}
	public void updateKey(int i, double v) {
		values[i] = v;
		int hIndex = 0;
		for(int j =0; j<size;j++) {
			if(heap[j] == i)hIndex = j;
		}
		while(values[i] <=values[heap[(hIndex-1)/2]]) {
			if(values[i] ==values[heap[(hIndex-1)/2]]) {
				if(i<heap[(hIndex-1)/2])swap(hIndex,(hIndex-1)/2);
			}
			else swap(hIndex,(hIndex-1)/2);
			if(hIndex==0)break;
			hIndex = (hIndex-1)/2;
		}
		
		
	}
	public int extractMin() {
		int e = heap[0];
		swap(0,size-1);
		size--;
		heapDown();
		
		return e;
	}
	public void heapDown() {
		int i = 0;
		while(i<size) {
	
			int left = 2*i + 1;
			int right = 2*i + 2;
			if(left>=size)return;
			else if(right>=size) {
				if(values[heap[i]]>=values[heap[left]]) {
					if(values[heap[i]]==values[heap[left]]) {
						if(heap[i] > heap[left])swap(i,left);
					}
					else
						swap(i,left);
					i = left;
				}
				else break;
			}
			else {
				double minC = Math.min(values[heap[left]], values[heap[right]]);
				if(minC == values[heap[left]]) {
					if(values[heap[i]]>=values[heap[left]]) {
						if(values[heap[i]]==values[heap[left]]) {
							if(heap[i] > heap[left])swap(i,left);
						}
						else
							swap(i,left);
						i = left;
					}
					else break;
				}
				else {
					if(values[heap[i]]>=values[heap[right]]) {
						if(values[heap[i]]==values[heap[right]]) {
							if(heap[i] > heap[right])swap(i,right);
						}
						else
							swap(i,right);
						i = right;
					}
					else break;
				}
				
			}
		}
	}
	
	
	public void swap(int p1, int p2) {
		int temp = heap[p1];
		heap[p1] = heap[p2];
		heap[p2] = temp;
	}
	
	public ArrayList<Integer> getHeap(){
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i =0; i<size;i++) {
			list.add(heap[i]);
		}
		return list;
	}
	public boolean isEmpty() {
		return (size == 0);
	}
	public double getRootValue() {
		return values[heap[0]];
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
   
   double getWeight();
   
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
   Graph(int vertexCount,ArrayList<int[]> l1, ArrayList<int[]> l2)
     throws IllegalArgumentException
   {
     if (0 > vertexCount)
     {
       throw new IllegalArgumentException(String.valueOf(vertexCount));
     }


     this.vertices = new Vertex[vertexCount];
     for(int i = 0;i<vertexCount;i++) {
    	 int cur[] = l1.get(i);
    	 int cur2[] = {cur[1],cur[2]};
    	 
    	 
    	 this.vertices[cur[0]] = new GraphVertex(cur[0], cur2);
     }
     for(int i = 0; i< l2.size();i++) {
    	 int vs[] = l2.get(i);
    	 int v1 = vs[0];
    	 int v2 = vs[1];
    	 int p1[]  = vertices[v1].point();
    	 int p2[]  = vertices[v2].point();
    	 int x1 = p1[0];
    	 int y1 = p1[1];
    	 int x2 = p2[0];
    	 int y2 = p2[1];
    	 double dist = PathFinder.distance(x1, y1, x2, y2);
    	 addEdge(dist,v1,v2);
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
   addEdge(double weight, int fromIdx, int toIdx)
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
 	  GraphEdge fromTo = new GraphEdge(weight, vertices[fromIdx], vertices[toIdx]);
 	 //makes the new edge from toIdx to fromIdx vertex
	  GraphEdge ToFrom = new GraphEdge(weight, vertices[toIdx], vertices[fromIdx]);
 	  //adds edge to each vertex's edge collection in the direction
 	  vertices[fromIdx].edges().add(fromTo);
 	  vertices[toIdx].edges().add(ToFrom);
 	  
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
     

     private int point[] = new int[2];


     /**
      * Initializes the vertex.
      *
      * @param index
      *   the index of the vertex within the vertices array
      */
     public
     GraphVertex(int index, int point[])
     {
       this.visited = false;
       this.index = index;
       this.edges = new ArrayList<>();
       this.point  = point;
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
       return "v[" + point[0] +"," + point[1] + ","+index+","+layer+"]: " + edges().toString();
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
     
     private double weight;


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
     GraphEdge(double weight, Vertex from, Vertex to)
     {
      
       
       this.weight = weight;
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
     public double getWeight() {return weight;}
     public
     String
     toString()
     {
       return "("+from.index()  +")--"+weight+"-->("+to.index() +")";
     }

   
   }
 }



