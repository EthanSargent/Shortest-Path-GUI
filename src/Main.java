
public class Main {

	public static int sum(int[] nums) {
		 int sum = 0;
		 for (int i = 0; i < nums.length; i++) {
		  sum = sum + nums[i];
		 }
		 return sum;
		}
	public static void main(String[] args) {
		(new Graph()).start(args);
	}
}
