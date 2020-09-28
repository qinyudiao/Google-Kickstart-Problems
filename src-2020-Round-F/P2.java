import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

class P2 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int numTests = Integer.parseInt(br.readLine());
		int[][][] testCasesIntevals = new int[numTests][][];
		int[] testCasesLimits = new int[numTests];
		for (int i = 0; i < numTests; i++) {
			String[] line1 = br.readLine().split(" ");
			int n = Integer.parseInt(line1[0]);
			testCasesLimits[i] = Integer.parseInt(line1[1]);
			int[][] intervals = new int[n][];
			for (int j = 0; j < n; j++) {
				int[] timePoints = new int[2];
				String[] numStrs = br.readLine().split(" ");
				for (int k = 0; k < 2; k++) {
					timePoints[k] = Integer.parseInt(numStrs[k]);
				}
				intervals[j] = timePoints;
			}
			testCasesIntevals[i] = intervals;
		}
		br.close();

		for (int i = 0; i < numTests; i++) {
			StringBuilder sb = new StringBuilder().append("Case #").append(i + 1).append(": ")
					.append(minDeployments(testCasesIntevals[i], testCasesLimits[i]));
			System.out.println(sb);
		}
	}

	private static int minDeployments(int[][] intervals, int limit) {
		Arrays.sort(intervals, (interval1, interval2) -> {
			if (interval1[0] < interval2[0])
				return -1;
			else
				return 1;
		});

		int expireTime = 0;
		int min = 0;
		for (int[] interval : intervals) {
			if (expireTime < interval[0]) {
				min++;
				expireTime = interval[0] - 1 + limit;
			}
			while (expireTime < interval[1] - 1) {
				min++;
				expireTime += limit;
			}
		}
		return min;
	}
}

//5
//3 5
//1 5
//10 11
//8 9
//3 2
//1 2
//3 5
//13 14
//3 4
//1 5
//10 11
//8 9
//3 3
//1 5
//10 11
//8 9
//3 1
//1 5
//10 11
//8 9
