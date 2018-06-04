package test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import application.GeneticOperators;
import data.Data;

class GeneticOperatorsTest {
	
	@Test
	void test_OS_crossoverPOX() {
		int[] P1 = {1,3,1,2,2,3};
		int[] P2 = {3,2,1,2,3,1};
		int[] O1 = P1.clone();
		int[] O2 = P2.clone();
		LinkedList<Integer> J1 = new LinkedList<>();
		LinkedList<Integer> J2 = new LinkedList<>();
		J1.add(2);
		J2.add(1);
		J2.add(3);

		int[] O1_expected = {3,1,3,2,2,1};
		int[] O2_expected = {1,2,3,2,1,3};
		
		GeneticOperators.crossoverOS_POX(P1, P2, O1, O2, J1, J2);

		assertArrayEquals(O1, O1_expected);
		assertArrayEquals(O2, O2_expected);
	}
	
	@Test
	void test_OS_crossoverJBX() {
		int[] P1 = {1,3,1,2,2,3};
		int[] P2 = {3,2,1,2,3,1};
		int[] O1 = P1.clone();
		int[] O2 = P2.clone();
		LinkedList<Integer> J1 = new LinkedList<>();
		LinkedList<Integer> J2 = new LinkedList<>();
		J1.add(2);
		J2.add(1);
		J2.add(3);
		
		int[] O1_expected = {3,1,3,2,2,1};
		int[] O2_expected = {3,2,1,2,3,1};
		
		GeneticOperators.crossoverOS_JBX(P1, P2, O1, O2, J1, J2);
		
		assertArrayEquals(O1, O1_expected);
		assertArrayEquals(O2, O2_expected);
	}

	@Test
	void test_MA_crossover() {
		int[] P1 = {2,1,3,1,2,2};
		int[] P2 = {1,3,2,3,3,1};
		int[] O1 = P1.clone();
		int[] O2 = P1.clone();
		int pos1 = 1;
		int pos2 = 4;

		int[] O1_expected = {2,1,2,3,3,2};
		int[] O2_expected = {1,3,3,1,2,1};
		
		GeneticOperators.crossoverMA(P1, P2, O1, O2, pos1, pos2);
		
		assertArrayEquals(O1, O1_expected);
		assertArrayEquals(O2, O2_expected);
	}

	@Test
	void test_OS_swappingMutation() {
		int[] P = {1,3,1,2,2,3};
		int[] O = P.clone();
		int pos1 = 1;
		int pos2 = 4;

		int[] O_expected = {1,2,1,2,3,3};
		GeneticOperators.swappingMutationOS(P, O, pos1, pos2);
		
		assertArrayEquals(O, O_expected);
	}
	
	@Test
	void test_OS_neighborhoodMutation() {
		int[] P = {1,3,1,2,2,3};
		int[] O;
		int pos1 = 0;
		int pos2 = 3;
		int pos3 = 5;
		boolean b = false;
		
		int[][] O_expected = {
				{1,3,1,3,2,2},
				{2,3,1,1,2,3},
				{2,3,1,3,2,1},
				{3,3,1,2,2,1},
				{3,3,1,1,2,2}
		};
		
		O = GeneticOperators.neighborhoodMutationOS(P, pos1, pos2, pos3);
		
		for (int[] el : O_expected) {
			if (Arrays.equals(el, O)) {
				b = true;
				break;
			}
		}
		
		assertTrue(b);
	}

	@Test
	void test_MA_mutation() {
		int[] nb_jobs = {2,2,2};
		int[][] cout_machine_op = {
				{5,4,4},
				{4,2,3},
				{2,2,1},
				{3,4,3},
				{5,4,5},
				{2,2,0}
		};
		Data data = new Data(nb_jobs, cout_machine_op);
		
		int[] P = {2,1,3,1,2,2};
		int[] O;
		int pos[] = {0,3,5};
		
		int[] O_expected = {3,1,3,2,2,1};
		O = GeneticOperators.mutationMA(P, pos, data);
		
		assertArrayEquals(O, O_expected);
		
		
	}
}
