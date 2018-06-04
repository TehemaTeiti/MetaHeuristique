package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import data.Data;
import data.Solution;

class SolutionTest {

	private Solution solution1;
	private Solution solution2;
	private Data data;
	
	@BeforeEach
	void setUp() throws Exception {
		data = Data.createDefaultData();
	}

	@Test
	void testEquals() {
		int[] OS1 = {1,1,1,2,2,2,3,3};
		int[] OS2 = {1,1,1,2,2,2,3,3};
		int[] MA1 = {1,2,2,2,1,3,1,2};
		int[] MA2 = {1,2,2,2,1,3,1,2};
		solution1 = new Solution(OS1, MA1, data);
		solution2 = new Solution(OS2, MA2, data);
		
		assertEquals(solution1, solution2);
	}

	@Test
	void testNotEqualsOnOS() {
		int[] OS1 = {1,1,2,1,2,2,3,3};
		int[] OS2 = {1,1,1,2,2,2,3,3};
		int[] MA1 = {1,2,2,2,1,3,1,2};
		int[] MA2 = {1,2,2,2,1,3,1,2};
		solution1 = new Solution(OS1, MA1, data);
		solution2 = new Solution(OS2, MA2, data);
		
		assertNotEquals(solution1, solution2);
	}

	@Test
	void testNotEqualsOnMA() {
		int[] OS1 = {1,1,1,2,2,2,3,3};
		int[] OS2 = {1,1,1,2,2,2,3,3};
		int[] MA1 = {1,2,2,2,1,3,1,2};
		int[] MA2 = {1,3,2,2,1,3,1,2};
		solution1 = new Solution(OS1, MA1, data);
		solution2 = new Solution(OS2, MA2, data);
		
		assertNotEquals(solution1, solution2);
	}

	@Test
	void testNotEqualsOnBoth() {
		int[] OS1 = {1,1,1,2,2,2,3,3};
		int[] OS2 = {2,1,1,1,2,2,3,3};
		int[] MA1 = {1,2,2,2,1,3,1,2};
		int[] MA2 = {1,3,2,2,1,3,1,2};
		solution1 = new Solution(OS1, MA1, data);
		solution2 = new Solution(OS2, MA2, data);
		
		assertNotEquals(solution1, solution2);
	}
	
	

}
