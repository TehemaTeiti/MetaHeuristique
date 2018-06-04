package data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Population {

	public LinkedList<Solution> solutions;
	private Data data;
	
	public Population(Data data) {
		solutions = new LinkedList<>();
		this.data = data;
	}
	
	/**
	 * Initialise une population de taille n.
	 * La population est generee aleatoirement.
	 * @param n
	 */
	public void generateInitialPopulation(int n) {
		for (int i = 0; i < n; i++) {
			solutions.add(Solution.randomSolution(data));
		}
	}
	
	/**
	 * @return la (ou une) meilleure solution de la population
	 */
	public Solution bestSolution() {
		Collections.sort(solutions);
		return solutions.get(0);
	}
	
	/**
	 * Retire de la population les n pires individus
	 * @param n
	 */
	public void deleteWorstInidividual(int n) {
		Collections.sort(solutions);
		int i = 0;
		while (i < n && !solutions.isEmpty()) {
			solutions.removeLast();
			i++;
		}
	}
	
	/**
	 * Ajoute une liste de nouveaux individus dans la population
	 * @param offsprings la liste des individus a ajouter dans la population
	 */
	public void addNewIndividual(List<Solution> offsprings) {
		solutions.addAll(offsprings);
	}
	
}
