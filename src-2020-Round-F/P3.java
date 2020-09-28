import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class P3 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int numTests = Integer.parseInt(br.readLine());
		int[][][] testCasesObstacles = new int[numTests][][];
		int[] testCasesLengths = new int[numTests];
		int[][] testCasesStartA = new int[numTests][];
		int[][] testCasesStartB = new int[numTests][];
		for (int i = 0; i < numTests; i++) {
			String[] line1 = br.readLine().split(" ");
			testCasesLengths[i] = Integer.parseInt(line1[0]);
			int[] startA = {Integer.parseInt(line1[1]), Integer.parseInt(line1[2])};
			int[] startB = {Integer.parseInt(line1[3]), Integer.parseInt(line1[4])};
			testCasesStartA[i] = startA;
			testCasesStartB[i] = startB;
			int numObstacles = Integer.parseInt(line1[5]);
			int[][] obstacles = new int[numObstacles][];
			for (int j = 0; j < numObstacles; j++) {
				String[] numStrs = br.readLine().split(" ");
				int[] point = {Integer.parseInt(numStrs[0]), Integer.parseInt(numStrs[1])};
				obstacles[j] = point;
			}
			testCasesObstacles[i] = obstacles;
		}
		br.close();

		for (int i = 0; i < numTests; i++) {
			StringBuilder sb = new StringBuilder().append("Case #").append(i + 1).append(": ")
					.append(paintDuelScore(testCasesLengths[i], testCasesStartA[i], testCasesStartB[i], testCasesObstacles[i]));
			System.out.println(sb);
		}
	}

	private static int paintDuelScore(int length, int[] startPointA, int[] startPointB, int[][] obstacles) {
		List<List<Integer>> obstacleList = new ArrayList<>();
 		for(int[] obstacle : obstacles) {
 			List<Integer> obstaclePoint = new ArrayList<>();
 			obstaclePoint.add(obstacle[0]);
 			obstaclePoint.add(obstacle[1]);
			obstacleList.add(obstaclePoint);
		}
		
 		if(obstacleList.size() == size(length) - 2)
 			return 0;
 		
 		List<List<Integer>> copiedObstacles = obstacleList != null ? copiedList(obstacleList) : new ArrayList<>();
		copiedObstacles.add(Arrays.asList(startPointA[0], startPointA[1]));
		copiedObstacles.add(Arrays.asList(startPointB[0], startPointB[1]));
 		
		int row = startPointA[0];
		int column = startPointA[1];
//		System.out.println(row + ", " + column);
 		List<Integer> leftPoint = Arrays.asList(row, column - 1);
 		List<Integer> rightPoint = Arrays.asList(row, column + 1);
 		List<Integer> topBottom = (row - column) % 2 == 0 ? Arrays.asList(row + 1, column + 1) : Arrays.asList(row - 1, column - 1); // even: bottom, odd: top
		
		boolean leftA = isAvailable(length, leftPoint, copiedObstacles);
		boolean rightA = isAvailable(length, rightPoint, copiedObstacles);
		boolean tbA = isAvailable(length, topBottom, copiedObstacles);
		
		List<Integer> pointB = Arrays.asList(startPointB[0], startPointB[1]);
		if(!(leftA || rightA || tbA))
			return berth(length, null, pointB, copiedObstacles, 0);
		
		int max = Integer.MIN_VALUE;
				
		if(leftA) {
			max = Math.max(max, berth(length, leftPoint, pointB, copiedObstacles, 1));
		}
		if(rightA) {
			max = Math.max(max, berth(length, rightPoint, pointB, copiedObstacles, 1));
		}
		if(tbA) {
			max = Math.max(max, berth(length, topBottom, pointB, copiedObstacles, 1));
		}
		
		return max;
	}
	
	private static int alma(int length, List<Integer> startPointA, List<Integer> startPointB, List<List<Integer>> obstacles, int score) {
		
		int row = startPointA.get(0);
		int column = startPointA.get(1);
		
		List<List<Integer>> copiedObstacles = copiedList(obstacles);
		if(startPointB != null)
			copiedObstacles.add(startPointB);
		
 		List<Integer> leftPoint = Arrays.asList(row, column - 1);
 		List<Integer> rightPoint = Arrays.asList(row, column + 1);
 		List<Integer> topBottom = (row - column) % 2 == 0 ? Arrays.asList(row + 1, column + 1) : Arrays.asList(row - 1, column - 1); // even: bottom, odd: top
		
		boolean leftA = isAvailable(length, leftPoint, copiedObstacles);
		boolean rightA = isAvailable(length, rightPoint, copiedObstacles);
		boolean tbA = isAvailable(length, topBottom, copiedObstacles);
		if(!(leftA || rightA || tbA)) {
			if(startPointB == null)
				return score;
			else
				return berth(length, null, startPointB, copiedObstacles, score);
		}
		
		int max = score;
				
		if(leftA) {
			max = startPointB == null ? Math.max(max, alma(length, leftPoint, null, copiedObstacles, score + 1)) : Math.max(max, berth(length, leftPoint, startPointB, copiedObstacles, score + 1));
		}
		if(rightA) {
			max = startPointB == null ? Math.max(max, alma(length, rightPoint, null, copiedObstacles, score + 1)) : Math.max(max, berth(length, rightPoint, startPointB, copiedObstacles, score + 1));
		}
		if(tbA) {
			max = startPointB == null ? Math.max(max, alma(length, topBottom, null, copiedObstacles, score + 1)) : Math.max(max, berth(length, topBottom, startPointB, copiedObstacles, score + 1));
		}
		
		return max;
	}
	
	private static int berth(int length, List<Integer> startPointA, List<Integer> startPointB, List<List<Integer>> obstacles, int score) {
		int row = startPointB.get(0);
		int column = startPointB.get(1);
		
		List<List<Integer>> copiedObstacles = copiedList(obstacles);
		if(startPointA != null)
			copiedObstacles.add(startPointA);
		
 		List<Integer> leftPoint = Arrays.asList(row, column - 1);
 		List<Integer> rightPoint = Arrays.asList(row, column + 1);
 		List<Integer> topBottom = (row - column) % 2 == 0 ? Arrays.asList(row + 1, column + 1) : Arrays.asList(row - 1, column - 1); // even: bottom, odd: top
		
		boolean leftA = isAvailable(length, leftPoint, copiedObstacles);
		boolean rightA = isAvailable(length, rightPoint, copiedObstacles);
		boolean tbA = isAvailable(length, topBottom, copiedObstacles);
//		System.out.println("here " + leftA + rightA + tbA);
//		System.out.println("rightPoint " + rightPoint);
//		System.out.println("copiedObstacles " + copiedObstacles);
		if(!(leftA || rightA || tbA)) {

//			System.out.println("here " + score);
			if(startPointA == null)
				return score;
			else
				return alma(length, startPointA, null, copiedObstacles, score);
		}
		
		int min = score;
				
		if(leftA) {
			min = startPointA == null ? Math.min(min, berth(length, null, leftPoint, copiedObstacles, score - 1)) : Math.min(min, alma(length, startPointA, leftPoint, copiedObstacles, score - 1));
		}
		if(rightA) {
			min = startPointA == null ? Math.min(min, berth(length, null, rightPoint, copiedObstacles, score - 1)) : Math.min(min, alma(length, startPointA, rightPoint, copiedObstacles, score - 1));
		}
		if(tbA) {
			min = startPointA == null ? Math.min(min, berth(length, null, topBottom, copiedObstacles, score - 1)) : Math.min(min, alma(length, startPointA, topBottom, copiedObstacles, score - 1));
		}
				
		return min;
	}
	
	private static List<List<Integer>> copiedList(List<List<Integer>> toCopy) {
		List<List<Integer>> copiedList = new ArrayList<>();
		for(List<Integer> toCopyPoint : toCopy) {
			List<Integer> obstaclePoint = new ArrayList<>(2);
 			obstaclePoint.add(toCopyPoint.get(0));
 			obstaclePoint.add(toCopyPoint.get(1));
			copiedList.add(obstaclePoint);
		}
		return copiedList;
	}
	
	private static boolean isAvailable(int length, List<Integer> point, List<List<Integer>> obstacles) {
		int row = point.get(0);
		int column = point.get(1);
		if(row < 1 || row > length || column < 1 || column > rowSize(row))
			return false;
		for(List<Integer> obstacle : obstacles) {
			if(point.get(0) == obstacle.get(0) && point.get(1) == obstacle.get(1))
				return false;
		}
		return true;
	}
	
	private static int size(int length) {
		switch(length) {
			case 2 :
				return 4;
			case 3 :
				return 8;
			case 4 :
				return 15;
			case 5 :
				return 24;
			case 6 :
				return 35;
		}
		return 0;
	}
	
	private static int rowSize(int length) {
		switch(length) {
			case 2 :
				return 3;
			case 3 :
				return 5;
			case 4 :
				return 7;
			case 5 :
				return 9;
			case 6 :
				return 11;
		}
		return 1;
	}
}

/*
2
2 1 1 2 1 0
2 2 2 1 1 2
2 1
2 3
*/

/*
2
3 3 4 2 1 2
2 3
3 1
3 3 2 2 3 2
2 1
3 1
*/
