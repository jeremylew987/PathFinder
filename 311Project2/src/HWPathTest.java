// import java.io.BufferedReader;
// import java.io.FileInputStream;
// import java.io.FileNotFoundException;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.io.PrintStream;
// import java.util.concurrent.Callable;
// import java.util.concurrent.ExecutionException;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import java.util.concurrent.Future;
// import java.util.concurrent.TimeUnit;
// import java.util.concurrent.TimeoutException;

/*
  re-write a parameterized
  test function
  - test function: probably lambda expressions
  - test id
  - test input: instance of domain object
  - test output: instance of solution object
  - test points
 */

import java.util.ArrayList;

public class HWPathTest  {

    public static int testMinHeapExact(int tid, int points) throws Exception {

    	System.out.print("Test " + tid + ": " + points + "pts ");    	
    	int[] ans = {1, 4, 2, 7, 5, 6, 3};
    	MinHeap mH = new MinHeap();
    	try {
    		mH.add(7, 7);
    		mH.add(6, 6);
    		mH.add(5, 5);
    		mH.add(4, 4);
    		mH.add(3, 3);
    		mH.add(2, 2);
    		mH.add(1, 1);
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}	
    	ArrayList<Integer> elements = mH.getHeap();
    	for (int i=0; i<ans.length; i++) {
    		if (elements.get(i) != ans[i]) {
    			System.out.println("Test fails: key at loc " + i + " should be " + ans[i] + ". Solution says " + elements.get(i));
    			return 0;
    		}
    	}
    	System.out.println("Heap test with different values\n");
    	return points;
    }
    	


	public static int testMinHeap(int tid, int k, int points) throws Exception {

		System.out.print("Test " + tid + ": " + points + "pts ");

		MinHeap mH = new MinHeap();
		try {
		for (int i=1; i<5000; i++) {
			mH.add(5000-i, 0);
			mH.add(5000+i, 0);
		}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


		ArrayList<Integer> elements = mH.getHeap();
		boolean flag = true;
		int i;
		
		for (i=0; i<50 && flag; i++) {
		    for (int j=k*i+1; j<=k*i+k && j<51; j++) {
			if (elements.get(i) < elements.get(j));
			else {
			    System.out.println(elements.get(i) + " at index " + i + " is parent of " + elements.get(j) + " at index " + j + ": Incorrect Heap Property");
			    flag = false;
			    break;
			}
		    }
		}
		if (i<50)	{
			System.out.println("Heap addition incorrect\n");
			return 0;
		}
		else {
			System.out.println("Heap test with same values\n");
			return points;
		}
		
	}
	
	  // dist2All
	public static int testDist2All(int tid, PathFinder pF, int src, int k, double[] ans, int points) {
		System.out.print("Test " + tid + ": " + points + "pts ");
			int p = 0;
			boolean correct = false;
			try {
				double[] stdAns = pF.shortestPathDistances(src);
				int i=0; 
				for (i=0; i<ans.length; i++) {
					if (Math.round(stdAns[i]) == Math.round(ans[i]));
					else  {
						System.out.println("At index:" + i + " Expected (Rounded): " + Math.round(ans[i]) + " Produced: " + Math.round(stdAns[i]));
						break;
					}
				}
				if (i==ans.length) correct = true;
			} catch (Exception e) {
				System.out.println("Distances from: " + "src=" + src + "\n");
				System.out.println(e.getMessage());
			}
			
			if  (correct) {
				p = points;
				System.out.println("Distances from: " + "src=" + src  + "\n");
			}
			else 
				System.out.println("Distances from: " + "src=" + src + ": incorrect\n");
			return p;
		}
		
    	
 

    // fromSrcToDest
	public static int testPath2Dest(int tid, PathFinder pF, int src, int dest, int A, int B, ArrayList<Integer> ans, int points) {
		System.out.print("Test " + tid + ": " + points + "pts ");
		int p = 0;
		boolean correct = false;
		try {
			ArrayList<Integer> stdAns = pF.fromSrcToDest(src, dest, A, B);
			if (stdAns.size() == ans.size()) {
				int n = ans.size();
				int i;
				for (i=0; i<n; i++) {
					if (stdAns.get(i).equals(ans.get(i)));
					else {
						System.out.println("At index:" + i + " Expected: " + ans.get(i) + " Produced: " + stdAns.get(i));
						break;
					}
				}
				if (i==n) correct = true;
			}
 		}	catch (Exception e) {
			System.out.println("fromSrcToDest: " + "src= " + src + " dest= " + dest + " A= " + A + " B= " + B);
			System.out.println(e.getMessage());
		}
		if (correct) {
			p = points;
			System.out.println("fromSrcToDest: " + "src= " + src + " dest= " + dest + " A= " + A + " B= " + B + "\n");
		}
		else {
			System.out.println("fromSrcToDest: " + "src= " + src + " dest= " + dest + " A= " + A + " B= " + B + ": incorrect\n");
		}
		return p;
	}
	
	public static int testPath2DestVia(int tid, PathFinder pF, int src, int dest, ArrayList<Integer> via, 
			                           int A, int B, ArrayList<Integer> ans, int points) {
		System.out.print("Test " + tid + ": " + points + "pts ");
		int p = 0;
		boolean correct = false;
		try {
			ArrayList<Integer> stdAns = pF.fromSrcToDestVia(src, dest, via, A, B);
			if (stdAns.size() == ans.size()) {
				int n = ans.size();
				int i;
				for (i=0; i<n; i++) {
					if (stdAns.get(i).equals(ans.get(i)));
					else {
						System.out.println("At index:" + i + " Expected: " + ans.get(i) + " Produced: " + stdAns.get(i));
						break;
					}
				}
				if (i==n) correct = true;
			}
 		}	catch (Exception e) {
			System.out.println("fromSrcToDestVia: " + "src= " + src + " dest= " + dest + " A= " + A + " B= " + B);
			System.out.println(e.getMessage());
		}
		if (correct) {
			p = points;
			System.out.println("fromSrcToDestVia: " + "src= " + src + " dest= " + dest + " A= " + A + " B= " + B + "\n");
		}
		else {
			System.out.println("fromSrcToDestVia: " + "src= " + src + " dest= " + dest + " A= " + A + " B= " + B + ": incorrect\n");
		}
		return p;
	}
	

	
  
    // noOfMPaths2Dest
	public static int testNoOfPaths2Dest(int tid, PathFinder pF, int src, int dest, int k, int ans, int points) {
		System.out.print("Test " + tid + ": " + points + "pts ");
		int p = 0;
		boolean correct = false;
		try {
			int stdAns = pF.noOfShortestPaths(src, dest);
			if (stdAns == ans) 
				correct = true;
			else 
				System.out.println("Expected: " + ans + " Produced: " + stdAns);
		} catch (Exception e) {
			System.out.println("noOfShortestPaths: " + "src= " + src + " dest= " + dest);
			System.out.println(e.getMessage());
		}
		if (correct) {
			p = points;
			System.out.println("noOfShortestPaths: " + "src= " + src + " dest= " + dest + "\n");
		}
		else 
			System.out.println("noOfShortestPaths: " + "src= " + src + " dest= " + dest + ": Incorrect\n");
		return p;
	}
	

   
    // Reachability Tree
	public static int testReachabilityFromSource(int tid, PathFinder pF, int src, int k, int[] ans, int points) {
		System.out.print("Test " + tid + ": " + points + "pts ");
		int p = 0;
		boolean correct = false;
		try {
			int[] stdAns = pF.minCostReachabilityFromSrc(src);			
			int i, j;
			for (i=0; i<ans.length; i++) {
			    j = stdAns[i];
			    if (ans[i] == j || ans[j] == i);
			    else {
				System.out.println("Road between " + i + " and " + j + " is incorrectly included");
				break;
			    }
			}
			if (i==ans.length)
				correct = true;
		} catch (Exception e) {
			System.out.println("ReachabilityFromSource: " + "src= " + src);
			System.out.println(e.getMessage());
		}
		if (correct) {
			p = points;
			System.out.println("ReachabilityFromSource: " + "src=" + src + "\n");
		}
		else 
			System.out.println("ReachabilityFromSource: " + "src=" + src  + "Incorrect\n");
		return p;
	}
	
    // Cost of reachability tree
	public static int testCostFromSource(int tid, PathFinder pF, int src, int k, double ans, int points) {
		System.out.print("Test " + tid + ": " + points + "pts ");
		int p = 0;
		boolean correct = false;
		try {
			double stdAns = pF.minCostOfReachabilityFromSrc(src);

			if (Math.round(stdAns) == Math.round(ans))
				correct = true;
			else 
				System.out.println("Expected (Rounded): " + Math.round(ans) + " Produced: " +  Math.round(stdAns));
	
		} catch (Exception e) {
			System.out.println("CostFromSource: " + "src= " + src + "\n");
			System.out.println(e.getMessage());
		}
		if (correct) {
			p = points;
			System.out.println("CostFromSource: " + "src=" + src + "\n");
		}
		else
			System.out.println("CostFromSource: " + "src=" + src + ": " + "Incorrect\n");
		return p;
	}
	
    
    // v1 is slower than v2
	public static int testv1v2(int tid, PathFinder pF, int points) {
		System.out.print("Test " + tid + ": " + points + "pts ");
		int p = 0;
		boolean correct = false;
		long start, finish, timev1 = 0, timev2 = 0;
		try {
			start = System.currentTimeMillis();
			for (int i=0; i<100; i++)
			    pF.fromSrcToDest(0, 39998, 1, 0);
			finish = System.currentTimeMillis();
			timev1 = (finish - start)/100;
		
			start = System.currentTimeMillis();
			for (int i=0; i<100; i++)
			    pF.fromSrcToDest(0, 39998, 1, 1);
			finish = System.currentTimeMillis();
			timev2 = (finish - start)/100;
			if (10* timev2 < timev1) {
				correct = true;
	System.out.println("Expected a difference in time by factor of 10. Produced: (1, 0)-Time " + timev1 + " (1, 1)-Time " + timev2);
			}
			else {
				System.out.println("Expected a difference in time by factor of 10. Produced: (1, 0)-Time " + timev1 + " (1, 1)-Time " + timev2);
			}
		} catch (Exception e) {
			System.out.print("testing time difference betwee usage of parameter (1, 0) and (1, 1)");
			System.out.println(e.getMessage());
		}
		
		if (correct) {
			System.out.println("(1, 1) execution time faster than (1, 0)\n");
			p = points;
		}
		else 
			System.out.println("(1, 1) execution time faster than (1, 0): Test fails \n");
		return p;
	
	}
    
	public static int points = 0;
	public static int cnt = 1;
	public static String fName;
    // main method	
	public static void main(String[] args)  throws Exception {
	    
		try {
	        TBlock timeoutBlock = new TBlock(2000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	try {
	            		points = points + testMinHeap(cnt, 2, 15);
				
	            	} catch (Exception e) {}
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		 
		try {
	        TBlock timeoutBlock = new TBlock(2000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	try {
	            		points = points + testMinHeapExact(cnt, 15);
				
	            	} catch (Exception e) {}
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		
		PathFinder pF = new PathFinder();
		
		fName = "sample3.txt";
		System.out.println("\n***************************");
		System.out.println("Tests with file: " + fName);
		System.out.println("***************************\n");
		pF.readInput(fName);

		System.out.println("Testing Mincost Reachability from Source\n");
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testCostFromSource(cnt, pF, 0, 2, 1655, 5);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testCostFromSource(cnt, pF, 7, 2, 1655, 5);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		
		int[] treeAns0 = {0, 0, 1, 2, 3, 1,  5, 6};
		int[] treeAns7 = {1, 5, 1, 2, 3, 6, 7, 7};
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testReachabilityFromSource(cnt, pF, 0, 2, treeAns0, 10);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testReachabilityFromSource(cnt, pF, 7, 2, treeAns7, 10);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;		
		
		fName = "sample.txt";
		System.out.println("\n***************************");
		System.out.println("Tests with file: " + fName);
		System.out.println("***************************\n");
		
		
		pF.readInput(fName);
		

		double[] distAns = {0, 12, 34, 41, 54, 59, 54, 44, 29, 23, 43, 34, 45, 52, 52, 72, 63, 115};
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testDist2All(cnt, pF, 0, 0, distAns, 10);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;

		System.out.println("Testing Path from Source to Destination");	
		ArrayList<Integer> pAns1 = new ArrayList<>();
		pAns1.add(1);
		pAns1.add(2);
		pAns1.add(3);
		pAns1.add(4);
		pAns1.add(5);
		pAns1.add(6);
		pAns1.add(7);
		pAns1.add(11);
		pAns1.add(12);
		pAns1.add(14);
		pAns1.add(15);
		pAns1.add(17);
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testPath2Dest(cnt, pF, 1, 17, 0, 1, pAns1, 10);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		
		ArrayList<Integer> pAns2 = new ArrayList<>();
		pAns2.add(1);
		pAns2.add(0);
		pAns2.add(9);
		pAns2.add(8);
		pAns2.add(11);
		pAns2.add(12);
		pAns2.add(14);
		pAns2.add(15);
		pAns2.add(17);
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testPath2Dest(cnt, pF, 1, 17, 1, 1, pAns2, 10);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		
		cnt++;
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testPath2Dest(cnt, pF, 1, 17, 1, 0, pAns2, 10);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		
		ArrayList<Integer> pAns3 = new ArrayList<>();
		pAns3.add(9);
		pAns3.add(8);
		pAns3.add(11);
		pAns3.add(12);
		pAns3.add(14);
		pAns3.add(15);
		pAns3.add(17);
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testPath2Dest(cnt, pF, 9, 17, 1, 0, pAns3, 10);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testPath2Dest(cnt, pF, 9, 17, 1, 1, pAns3, 10);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testPath2Dest(cnt, pF, 9, 17, 0, 1, pAns3, 10);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		
		ArrayList<Integer> via = new ArrayList<>();
		via.add(7);
		via.add(14);
		via.add(17);
		
		ArrayList<Integer> via1 = new ArrayList<>();
		via1.add(2); 
		via1.add(3); 
		via1.add(4); 
		via1.add(5); 
		via1.add(6); 
		via1.add(7); 
		via1.add(11); 
		via1.add(12); 
		via1.add(14); 
		via1.add(15); 
		via1.add(17); 
		via1.add(15); 
		via1.add(14); 
		via1.add(12); 
		via1.add(11); 
		via1.add(8);
		via1.add(9); 
		via1.add(2); 
		via1.add(3); 

		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testPath2DestVia(cnt, pF, 2, 3, via, 1, 0, via1, 10);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testPath2DestVia(cnt, pF, 2, 3, via, 1, 1, via1, 10);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		
		ArrayList<Integer> via2 = new ArrayList<>();
		via2.add(2);
		via2.add(9);
		via2.add(8);
		via2.add(7);
		via2.add(11);
		via2.add(12);
		via2.add(14);
		via2.add(15);
		via2.add(17);
		via2.add(15);
		via2.add(14);
		via2.add(12);
		via2.add(10);
		via2.add(9);
		via2.add(2);
		via2.add(3);
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testPath2DestVia(cnt, pF, 2, 3, via, 0, 1, via2, 10);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		

   		boolean flag = true;  // not a good idea - should remove
		


		fName = "sample200-200.txt";
		System.out.println("\n***************************");
		System.out.println("Tests with file: " + fName);
		System.out.println("***************************\n");
		try {
		    pF.readInput(fName);
		} catch (Exception e) {
		    System.out.println("Cannot read file input");
		    System.out.println(e.getMessage());
		    flag = false;
		}

		if (flag) {
		ArrayList<Integer> pAns4 =  new ArrayList<>();
		pAns4.add(0);
		pAns4.add(1);
		pAns4.add(2);
		pAns4.add(3);
		pAns4.add(4);
		pAns4.add(5);
		pAns4.add(6);
		pAns4.add(7);
		pAns4.add(8);
		pAns4.add(9);
		pAns4.add(10);
		pAns4.add(210);
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testPath2Dest(cnt, pF, 0, 210, 1, 0, pAns4, 5);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testPath2Dest(cnt, pF, 0, 210, 1, 1, pAns4, 5);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testPath2Dest(cnt, pF, 0, 210, 0, 1, pAns4, 5);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		
		
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testNoOfPaths2Dest(cnt, pF, 0, 700, 0, 176851, 25);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		
		System.out.println("\n***************************");
		System.out.println("Total Points before extra credits: " + points + " out of 200");
		
		System.out.println("\n***************************");
		System.out.println("Extra Credit");
		System.out.println("***************************\n");
		
		try {
	        TBlock timeoutBlock = new TBlock(1000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	            	points = points + testNoOfPaths2Dest(cnt, pF, 0, 39998, 0, 1, 15);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	        System.out.println(e.getMessage());
	    }
		
		cnt++;
		
		
		try {
	        TBlock timeoutBlock = new TBlock(20000);//set timeout in milliseconds
	        Runnable block=new Runnable() {
	            @Override
	            public void run() {
	                points = points + testv1v2(cnt, pF, 25);
	            }
	        };
	        timeoutBlock.addBlock(block);// execute the runnable block 
	    } catch (Throwable e) {
	    	System.out.println(e.getMessage());
	        //catch the exception here . Which is block didn't execute within the time limit
	    }

		
		cnt++;
		}
		
		System.out.println("\n****************************");
		System.out.println("Total Points: " + points);
		System.out.println("***************************\n");
		}
				
}


// from stackoverflow
// https://stackoverflow.com/questions/5715235/java-set-timeout-on-a-certain-block-of-code/19183452

class TBlock {

	 private final long timeoutMilliSeconds;
	    private long timeoutInteval=100;

	    public TBlock(long timeoutMilliSeconds){
	        this.timeoutMilliSeconds=timeoutMilliSeconds;
	    }

	    public void addBlock(Runnable runnable) throws Throwable{
	        long collectIntervals=0;
	        Thread timeoutWorker=new Thread(runnable);
	        timeoutWorker.start();
	        do{ 
	            if(collectIntervals>=this.timeoutMilliSeconds){
	                timeoutWorker.stop();
	                throw new Exception("<<<<<<<<<<****>>>>>>>>>>> Timeout Block Execution Time Exceeded In "+timeoutMilliSeconds+" Milli Seconds. Thread Block Terminated.\n");
	            }
	            collectIntervals+=timeoutInteval;           
	            Thread.sleep(timeoutInteval);

	        }while(timeoutWorker.isAlive());
	        //System.out.println("<<<<<<<<<<####>>>>>>>>>>> Executed Within "+collectIntervals+" Milli Seconds.");
	    }

	    /**
	     * @return the timeoutInteval
	     */
	    public long getTimeoutInteval() {
	        return timeoutInteval;
	    }


	    /**
	     * @param timeoutInteval the timeoutInteval to set
	     */
	    public void setTimeoutInteval(long timeoutInteval) {
	        this.timeoutInteval = timeoutInteval;
	    }
}
