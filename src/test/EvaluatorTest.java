package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.Evaluator;
import data.Data;
import data.Solution;

class EvaluatorTest {

	private Solution solution;
	private Evaluator evaluator;
	
	@BeforeEach
	void setUp() throws Exception {
		int[] nb_job = {3,3,2};
		int[][] cout_machine_op = {
				{3,0,0},	
				{0,2,4},	
				{0,5,5},	
				{0,4,0},
				{2,0,0},
				{2,4,2},	
				{2,0,2},	
				{0,3,5}	
		};
		
		Data data = new Data(nb_job, cout_machine_op);
		int[] OS = {1,1,1,2,2,2,3,3};
		int[] MA = {1,2,2,2,1,3,3,2};
		solution = new Solution(OS, MA, data);
		evaluator = new Evaluator();
	}

	@AfterEach
	void tearDown() throws Exception {
		solution = null;
	}
	
	@Test
	void testEvaluate() {
		int cout = evaluator.evaluate(solution);
		int cout_expected = 17;
		assertEquals(cout, cout_expected);
	}

}
