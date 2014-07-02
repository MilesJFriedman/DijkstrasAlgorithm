package homework4Dijkstras;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * ShortestPath.java
 * 
 * @author Miles 
 * @version 7/1/14
 */
public class ShortestPath {

	
	public static void main(String[] args) throws Exception {
		//Place input file in the file reader here:
		Scanner input = new Scanner(new FileReader("C:\\Users\\Miles\\workspace\\COP3503 - Computer Science II\\src\\homework4Dijkstras\\input3.txt"));
	
		int numOfMaps = input.nextInt();
		int m = 0;
		
		//for each test case...
		for (m = 0; m < numOfMaps; m++) {
			
			int numOfIntersections = input.nextInt();
		
			//create an adjacency matrix to hold the edge weights (or distance between points)
			double[][] adjMatrix = new double[numOfIntersections][numOfIntersections];
			
			//for the next k lines of the input file up to the number of intersections...
			for (int k = 0; k < numOfIntersections; k++) {
				//fill the adjacency matrix based on information given in the input file.
				for (int i = 0; i < numOfIntersections; i++) {
					adjMatrix[k][i] = input.nextFloat();
				}
			}

			int startPoint = input.nextInt();
			int endPoint = input.nextInt();
			
			//Create arrays to hold the path estimates and current parents of the shortest path.
			double[] pathEstimates = new double[numOfIntersections];
			int[] parentIntersections = new int[numOfIntersections];
			
			//Initialize the path estimates and parentIntersections. At the start each path
			//estimate will be infinity and each parentIntersection will be -1 as there is no path
			//built yet.
			for (int i = 0; i < numOfIntersections; i++) {
				pathEstimates[i] = Double.MAX_VALUE;
				parentIntersections[i] = -1;
			}
			
			//Create an linkedList to hold the set which will contain the shortest path.
			LinkedList<Integer> shortestPath = new LinkedList<Integer>();
			
			
			dijkstrasAlgorithm (m, adjMatrix, numOfIntersections, startPoint, endPoint, pathEstimates, parentIntersections, shortestPath);

			//Print out the desired output:
			System.out.printf("\nMap #%d \nThe shortest distance between %d and %d is %.2f\n", m+1, startPoint, endPoint, pathEstimates[endPoint]);
			//Print out the shortest path
			System.out.printf("The shortest path from %d to %d is ", startPoint, endPoint);
			while (shortestPath.isEmpty() == false) {
				System.out.print(shortestPath.removeFirst());
				if (shortestPath.isEmpty() == false)
					System.out.print("->");
			}
			
			System.out.println();
		
		}
			
			
	}
	
	public static void dijkstrasAlgorithm (int m, double[][] adjMatrix, int maxIndex, int startPoint, int endPoint, double[] pathEstimates, int[] parents, LinkedList<Integer> shortestPath) {
		//Create a linked list that holds each intersection
		LinkedList<Integer> remainingPoints = new LinkedList<Integer>();
		
		//fill the linked list with all of the intersection points
		for (int i = 0; i < maxIndex; i++) {
			remainingPoints.add(i);
		}
		
		remainingPoints.remove(Integer.valueOf(startPoint));
		
		//loop through each index of the start points adjacency array and update the path estimates.
		for (int i = 0; i < maxIndex; i++) {
			//if the path estimate is greater than the distance found from the startingPoint to the
			//i'th point, update the pathEstimate for that point and update the parent of the point.
			if ((adjMatrix[startPoint][i] < pathEstimates[i]) && (pathEstimates[i] != 0)) {
				pathEstimates[i] = adjMatrix[startPoint][i];
				parents[i] = startPoint;
			}
		}
		
		//Create a variable to represent the next intersection to branch from. 
		int next = -1;
		
		//This will be the intersection that has the shortest path from the last tested 
		//intersection (that is not 0).
		for (int i = 0; i < maxIndex; i++) {
			if (pathEstimates[i] != 0) {
				if (next == -1)
					next = i;
				else if (pathEstimates[i] < next)
					next = i;
			}
		}
		
		remainingPoints.remove(Integer.valueOf(next));
		
		//repeat a similar process to the one above stopping once the endPoint has been found.
		while (remainingPoints.contains(endPoint)) {
			
			//loop through each index of the start points adjacency array and update the path estimates.
			for (int i = 0; i < maxIndex; i++) {
				//if the path estimate is greater than the distance found from the next Point to the
				//i'th point, update the pathEstimate for that point and update the parent of the point.
				if ((pathEstimates[i] != 0 && adjMatrix[next][i] != 0) && (adjMatrix[next][i]+pathEstimates[next] < pathEstimates[i])) {
					pathEstimates[i] = adjMatrix[next][i] + pathEstimates[next];
					parents[i] = next;
				}
			}
			
			//reset next. 
			next = -1;
			
			//This will be the intersection that has the shortest path from the last tested 
			//intersection (that is not 0).
			for (int i = 0; i < maxIndex; i++) {
				if (pathEstimates[i] != 0 && remainingPoints.contains(i)) {
					if (next == -1)
						next = i;
					else if (pathEstimates[i] < next)
						next = i;
				}
			}
			
			remainingPoints.remove(Integer.valueOf(next));
		
		}
		
		//build the shortest path from the endPoint to the startPoint
		shortestPath.addFirst(endPoint);
		int nextPoint = endPoint;
		while (shortestPath.contains(startPoint) == false) {
			shortestPath.addFirst(parents[nextPoint]);
			nextPoint = parents[nextPoint];
		}
		
	}
	
	//Outputs a display of the adjacency matrix (for testing purposes)
	public static void displayMatrix (double[][] adjMatrix, int maxIndex) {
		for (int i = 0; i < maxIndex; i++) {
			for (int j = 0; j < maxIndex; j++) {
				System.out.print(adjMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}

}
