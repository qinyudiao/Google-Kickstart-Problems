import java.io.BufferedReader;
import java.io.InputStreamReader;

class P1_LongestArithmetic {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int numTests = Integer.parseInt(br.readLine());
		int[][] testCases = new int[numTests][];
		for (int i = 0; i < numTests; i++) {
			int n = Integer.parseInt(br.readLine());
			int[] nums = new int[n];
			String[] numStrs = br.readLine().split(" ");
			for (int j = 0; j < n; j++) {
				nums[j] = Integer.parseInt(numStrs[j]);
			}
			testCases[i] = nums;
		}
		br.close();

		for (int i = 0; i < numTests; i++) {
			System.out.println(new StringBuilder().append("Case #").append(i + 1).append(": ")
					.append(longestArithmetic(testCases[i])));
		}
	}

	private static int longestArithmetic(int[] nums) {
		int maxLength = 2;
		int prevDiff = nums[1] - nums[0];
		int startIndex = 0;
		for (int i = 2; i < nums.length; i++) {
			int diff = nums[i] - nums[i - 1];
			if (diff != prevDiff) {
				prevDiff = diff;
				int length = i - startIndex;
				if (length > maxLength) {
					System.out.println(diff);
					maxLength = length;
				}
				startIndex = i - 1;
			}
		}

		maxLength = Math.max(maxLength, nums.length - startIndex);

		return maxLength;
	}
}

//7
//7
//10 7 4 6 8 10 11
//4
//9 7 5 3
//9
//5 5 4 5 5 5 4 5 6
//10
//5 4 3 2 1 2 3 4 5 6
//4
//3 6 9 12
//5
//9 4 7 2 10
//7
//20 1 15 3 10 5 8
