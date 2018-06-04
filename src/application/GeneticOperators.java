package application;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import data.Data;
import data.Population;
import data.Solution;
import utils.Utils;

/**
 * Cette classe a pour but de fournir les methodes liees aux operateurs genetiques.
 * Toutes les methodes sont statiques.
 * @author Tehema
 *
 */
public class GeneticOperators {

	/**
	 * Selectionne les meilleurs individus d'une population (au minimum 2); 
	 * @param population
	 * @param pr
	 * @param popSize
	 */
	public static LinkedList<Solution> elitistSelection(Population population, float pr, int popSize) {
		LinkedList<Solution> solutionsSelected = new LinkedList<>();
		int n = (int) (pr * popSize);
		n = Math.max(n, 2);
		
		// selectionne les meilleurs individus
		Collections.sort(population.solutions);
		for (int i = 0 ; i < n ; i++) {
			solutionsSelected.add(population.solutions.get(i));
		}
		
		return solutionsSelected;
	}
	
	public static LinkedList<Solution> tournamentSelection() {
		LinkedList<Solution> solutionsSelected = new LinkedList<>();
		
		// TODO
		
		return solutionsSelected;
	}
	
	/**
	 * Operation de crossover pour le vecteur OS.
	 * Se base sur la methode POX.
	 * Les parametres O1 et O2 sont modifies.
	 * @param P1_tmp
	 * @param P2_tmp
	 * @param O1
	 * @param O2
	 */
	public static void crossoverOS_POX(int[] P1, int[] P2, int[] O1, int[] O2, LinkedList<Integer> J1, LinkedList<Integer> J2) {
		int[] P1_tmp = P1.clone();
		int[] P2_tmp = P2.clone();

		Arrays.fill(O1, -1);
		Arrays.fill(O2, -1);
		
		// remplissage de O1 et O2 en fonction de J1
		for (int i=0 ; i < P1_tmp.length ; i++) {
			if (J1.contains(P1_tmp[i])) {
				O1[i] = P1_tmp[i];
				P1_tmp[i] = -1;
			}
			
			if (J1.contains(P2_tmp[i])) {
				O2[i] = P2_tmp[i];
				P2_tmp[i] = -1;
			}
		}
		
		// remplissage de O1 et O2 en fonction du reste
		for (int i=0 ; i < P1_tmp.length ; i++) {
			if (P1_tmp[i] != -1) {
				Utils.addNextEmpty(O2, P1_tmp[i]);
				P1_tmp[i] = -1;
			}
			
			if (P2_tmp[i] != -1) {
				Utils.addNextEmpty(O1, P2_tmp[i]);
				P2_tmp[i] = -1;
			}
		}
		
	}
	
	/**
	 * Operation de crossover pour le vecteur OS.
	 * Se base sur la methode JBX.
	 * Les parametres O1 et O2 sont modifies.
	 * @param P1_tmp
	 * @param P2_tmp
	 * @param O1
	 * @param O2
	 */
	public static void crossoverOS_JBX(int[] P1, int[] P2, int[] O1, int[] O2, LinkedList<Integer> J1, LinkedList<Integer> J2) {
		int[] P1_tmp = P1.clone();
		int[] P2_tmp = P2.clone();
		
		Arrays.fill(O1, -1);
		Arrays.fill(O2, -1);
		
		// remplissage de O1 et O2 en fonction de J1 et J2
		for (int i=0 ; i < P1_tmp.length ; i++) {
			if (J1.contains(P1_tmp[i])) {
				O1[i] = P1_tmp[i];
			}
			
			if (J2.contains(P2_tmp[i])) {
				O2[i] = P2_tmp[i];
			}
		}
		
		// remplissage de O1 et O2 en fonction du reste
		for (int i=0 ; i < P1_tmp.length ; i++) {
			if (J1.contains(P1_tmp[i])) {
				Utils.addNextEmpty(O2, P1_tmp[i]);
			}
			
			if (J2.contains(P2_tmp[i])) {
				Utils.addNextEmpty(O1, P2_tmp[i]);
			}
		}
	}
	
	/**
	 * Swapping mutation.
	 * @param OS
	 */
	public static void swappingMutationOS(int[] P, int[] O, int pos1, int pos2) {
		O[pos1] = P[pos2];
		O[pos2] = P[pos1];
	}
	
	
	public static int[] neighborhoodMutationOS(int[] P, int pos1, int pos2, int pos3) {
		LinkedList<int[]> OS_generated = new LinkedList<>();
		int[] OS = P.clone();
		
		// generation de tous les voisins de P
		int[] pos = {P[pos1], P[pos2], P[pos3]};
		for (int i=0 ; i < pos.length ; i++) {
			for (int j=0 ; j < pos.length ; j++) {
				for (int k=0 ; k < pos.length ; k++) {
					if (i!=j && j!=k && k!=i) {
						OS[pos1] = pos[i];
						OS[pos2] = pos[j];
						OS[pos3] = pos[k];
						OS_generated.add(OS.clone());
					}
				}
			}
		}

		// selection aleatoire d'un voisin
		int rd = (int) (Math.random() * OS_generated.size());
		return OS_generated.get(rd);
	}
	
	public static void crossoverMA(int[] P1, int[] P2, int[] O1, int[] O2, int pos1, int pos2) {
		// selection aleatoire des 2 points de crossovers pour MA
		Arrays.fill(O1, -1);
		Arrays.fill(O2, -1);
		
		// remplissage de O1 et O2 en fonction de pos1
		for (int i = 0; i <= pos1 ; i++) {
			O1[i] = P1[i];
			O2[i] = P2[i];
		}
		
		for (int i = pos1+1; i <= pos2 ; i++) {
			O1[i] = P2[i];
			O2[i] = P1[i];
		}
		
		for (int i = pos2+1; i < P1.length ; i++) {
			O1[i] = P1[i];
			O2[i] = P2[i];
		}
	}
	
	
	public static int[] mutationMA(int[] P, int[] pos, Data data) {
		int[] O = P.clone();
		
		// pour chaque position de pos, on selectionne une nouvelle machine dans O
		for (int i : pos) {
			int old_machine = P[i]; // recupere la machine actuelle
			int machine = old_machine-1;
			do {
				machine = (machine + 1) % (data.COM[i].length);
			} while(data.COM[i][machine] <= 0);
			machine++;
			O[i] = machine;
		}
		
		return O;
	}

}

