package application;

import java.util.Collections;
import java.util.LinkedList;

import data.Data;
import data.Solution;

/**
 * Cette classe met en place l'algorithme de recherche local Tabu search.
 * 
 * @author Tehema
 *
 */
public class TabuSearch {

	private LinkedList<Solution> tabuList;
	private Solution currentSolution;
	private Data data;
	private int maxT;

	public TabuSearch(Data data, int maxT) {
		this.data = data;
		this.maxT = maxT;
	}

	public Solution launch(int maxTSIterSize, Solution solution) {
		int TSIter = 0;

		// initialise parametres de TS
		tabuList = new LinkedList<>();
		currentSolution = solution;

		// tant que critere d'arret non atteint
		while (TSIter < maxTSIterSize) {

			// step 3 : genere le voisinage
			LinkedList<Solution> neighborhood = neighborhoodSolution(currentSolution);
			Collections.sort(neighborhood);
			int i = 0;

			// choisi le meilleur voisin qui ne soit pas dans la liste tabu
			if (!neighborhood.isEmpty()) {
				do {
					currentSolution = neighborhood.get(i);
					i++;
				} while(tabuList.contains(currentSolution) && i < neighborhood.size());
			}

			// mise a jour de la liste tabu 
			if (tabuList.size() >= maxT)
				tabuList.removeFirst();
			tabuList.add(currentSolution);
			TSIter++;
		}

		return currentSolution;
	}

	private LinkedList<Solution> neighborhoodSolution(Solution solution) {
		LinkedList<Solution> neighborhood = new LinkedList<>();
		LinkedList<int[]> OSs = new LinkedList<>();
		LinkedList<int[]> MAs = new LinkedList<>();

		// voisinage sur OS : on applique des swaps
		for (int i = 0 ; i < solution.OS.length-1 ; i+=2) {
			for(int j = i+1; j < solution.OS.length; j+=2) {
				OSs.add(swap(solution.OS, i, j));
			}
		}

		// voisinage sur MA
		/*for (int i = 0 ; i < solution.MA.length ; i++) {
			int[] pos = {i};
			MAs.add(GeneticOperators.mutationMA(solution.MA, pos, data));
		}*/

		// generation du voisinage et evaluation
		for (int[] OS : OSs) {
			Solution sol = new Solution(OS, solution.MA.clone(), data);
			sol.cout = new Evaluator().evaluate(sol);
			neighborhood.add(sol);
		}

		/*for (int[] MA : MAs) {
			Solution sol = new Solution(solution.OS.clone(), MA, data);
			sol.cout = new Evaluator().evaluate(sol);
			neighborhood.add(sol);
		}*/

		return neighborhood;
	}


	/**
	 * Effectue un swap dans le vecteur OS entre les positions i et j.
	 * Retourne le vecteur obtenu.
	 * @param OS
	 * @param i
	 * @param j
	 * @return
	 */
	private static int[] swap(int[] OS, int i, int j) {
		int[] ret = OS.clone();
		ret[i] = OS[j];
		ret[j] = OS[i];
		return ret;
	}

}
