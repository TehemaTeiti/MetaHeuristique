package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.Utils;

class UtilMethodsTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testMaxTab() {
		int[] t = {1,2,3,4,5,50,1,0,-1,6};
		int max = Utils.max(t);
		int max_expected = 50;
		assertEquals(max, max_expected);
	}

	@Test
	void testRandomSplitJob() {
		int nb_jobs = 3;
		boolean b = false;
		LinkedList<Integer>[] jobs = Utils.randomSplitJob(nb_jobs);

		// test si tous les jobs existent dans un des 2 sets (et non les 2 en meme temps)
		for (int i = 1 ; i <= nb_jobs ; i++) {
			if ((jobs[0].contains(i)) ^ (jobs[1].contains(i))) {
				b = true;
			}
			else {
				b = false;
				break;
			}
		}
		
		// test uniquement si les 2 ensembles de jobs ne sont pas vides et que leur taille cumulee est bien egale au nombre de jobs
		assertFalse(jobs[0].isEmpty());
		assertFalse(jobs[1].isEmpty());
		assertEquals((jobs[0].size() + jobs[1].size()), nb_jobs);
		assertTrue(b);
	}

	@Test
	void testRandomCrossoverMA() {
		int n = 6;
		int pos[] = Utils.randomCrossoverMA(n);
		
		for (int i = 0; i < 50; i++) {
			pos = Utils.randomCrossoverMA(n);
			assertEquals(pos.length, 2);
			assertNotEquals(pos[0], pos[1]);
			assertTrue(pos[0] >= 0);
			assertTrue(pos[0] < n);
			assertTrue(pos[1] >= 0);
			assertTrue(pos[1] < n);
			assertTrue(pos[0] < pos[1]);
		}
	}
	
	@Test
	void testAddNextEmpty() {
		int[] t = {1,2,-1,5,6,-1,8};
		int[] v = {4,6,7};
		int[] t_expected = {1,2,4,5,6,6,8};
		
		for (int i : v)
			Utils.addNextEmpty(t, i);
		
		assertArrayEquals(t, t_expected);
	}
	
	

}
