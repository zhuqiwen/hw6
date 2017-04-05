/**
 * Problem 10.62(a): Let A be an n-by-n matrix of zeros and ones. A submatrix
 * S of A is any group of contiguous entries that forms a square. Design an
 * O(n^2) algorithm that determines the size of the largest submatrix of ones
 * in A.
 */

/**
 * Created by Qiwen Zhu on 4/3/17.
 *
 * To find out the biggest subsquare, we need to keep a count of continuous ones.
 * This means, for row and column, if the count meets a zero, the count should be reset to zero;
 * 	and if the ones keep running, for row or column, we just add 1 to previous count.
 *
 * There might be multiple subsquares, and thus, multiple counts. At last, we need to find the biggest counts.
 *
 * Now the question is how / where we should keep the count / counts.
 *
 * A score bord of n * n size can be used to keep these counts.
 *
 * The idea behind this algorithm is the right-lower corner any subsquare can be used to store the count for current subsquare.
 *
 * Now, we just need to go through every element and
 * 	if an element in mat is 1, its corresponding position on the score board should be filled min(diagnol, left, down) + 1
 * 	this will ensure that only when all three neighbors are equal and not zero, the count represents the size.
 *
 * 	if an element in mat is 0, we just set its corresponding position on the score board to be 0, equivalent to reset a count.
 *
 * Above needs nested for-loop (2-layer), thus its worst running time is n^2.
 */




public class LargestSquareSubmatrix {

	/**
	 * Returns the size (i.e., side length) of the largest square of ones found
	 * within the first n rows and first n columns of mat. Assume n > 0 and
	 * n <= mat.length.
	 */
	public static int largestSubSquare(int[][] mat, int n) {
		assert n > 0 && n <= mat.length;

		int[][] scoreBoard = new int[n][n];
		for(int i = 0; i < n; i++)
		{
			scoreBoard[0][i] = mat[0][i];
			scoreBoard[i][0] = mat[i][0];
		}

		for(int i = 1; i < n; i++)
		{
			for(int j = 1; j < n; j++)
			{
				if(mat[i][j] == 1)
				{
					scoreBoard[i][j] = Math.min(Math.min(scoreBoard[i][j - 1], scoreBoard[i - 1][j]), scoreBoard[i - 1][j - 1]) + 1;
				}

				if(mat[i][j] == 0)
				{
					scoreBoard[i][j] = 0;
				}
			}
		}

//		for(int[] i : scoreBoard)
//		{
//			for(int j : i)
//			{
//				System.out.print(j + ",");
//			}
//			System.out.println();
//		}

		int max = 0;
		for(int[] i : scoreBoard)
		{
			for(int j : i)
			{
				System.out.print(j + ",");
				max = Math.max(max, j);
			}
			System.out.println();
		}




		return max;
	}

	public static void main(String... args) {
		int[][] mat = {
				{ 1, 0, 1, 1, 1, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 1, 0, 0 },
				{ 0, 0, 1, 1, 1, 0, 0, 0 },
				{ 0, 0, 1, 1, 1, 0, 1, 0 },
				{ 0, 0, 1, 1, 1, 1, 1, 1 },
				{ 0, 1, 0, 1, 1, 1, 1, 0 },
				{ 0, 1, 0, 1, 1, 1, 1, 0 },
				{ 0, 0, 0, 1, 1, 1, 1, 0 },
		};

		assert 1 == largestSubSquare(mat, 1);
		assert 1 == largestSubSquare(mat, 2);
		assert 1 == largestSubSquare(mat, 3);
		assert 2 == largestSubSquare(mat, 4);
		assert 3 == largestSubSquare(mat, 5);
		assert 3 == largestSubSquare(mat, 6);
		assert 3 == largestSubSquare(mat, 7);
		assert 4 == largestSubSquare(mat, 8);
	}
}
