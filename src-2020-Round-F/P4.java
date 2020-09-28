import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class P4 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int numTests = Integer.parseInt(br.readLine());
		int[][] testCasesTargets = new int[numTests][];
		int[] testCasesLimits = new int[numTests];
		for (int i = 0; i < numTests; i++) {
			String[] line1 = br.readLine().split(" ");
			int n = Integer.parseInt(line1[0]);
			testCasesLimits[i] = Integer.parseInt(line1[1]);
			int[] nums = new int[n];
			String[] numStrs = br.readLine().split(" ");
			for (int j = 0; j < n; j++) {
				nums[j] = Integer.parseInt(numStrs[j]);
			}
			testCasesTargets[i] = nums;
		}
		br.close();

		for (int i = 0; i < numTests; i++) {
			StringBuilder sb = new StringBuilder().append("Case #").append(i + 1).append(":");
			for(int num : getOrder(testCasesTargets[i], testCasesLimits[i])) {
				sb.append(" ");
				sb.append(num);
			}
			System.out.println(sb);
		}
	}

	private static List<Integer> getOrder(int[] nums, int limit) {
		Map<Integer, List<Integer>> map = new TreeMap<>();
		for(int i = 0; i < nums.length; i++) {
			int round = (nums[i] - 1) / limit;
			List<Integer> list = map.get(round);
			if(list == null)
				list = new ArrayList<>();
			list.add(i + 1);
			map.put(round, list);
		}

		List<Integer> result = new ArrayList<>();
		for(List<Integer> list : map.values()) {
			result.addAll(list);
		}

		return result;
	}
}
