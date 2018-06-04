package application;

import java.util.Arrays;
import java.util.LinkedList;

import data.Data;
import data.Population;
import data.Solution;
import utils.Utils;

/**
 * Cette classe permet d'executer l'algorithme hybride.
 * @author Tehema
 *
 */
public class HybridAlgorithm {

	private Data data;
	private int popSize; // taille de la population
	private int maxGen; // nombre total de generation
	private int maxStagnantStep; // le nombre max d'etapes autorisees sans amelioration
	private float pr; // probabilite de reproduction
	private float pc; // probabilite de crossover
	private float pm; // probabilite de mutation
	private int maxTSIterSize; // max d'iteration de TS
	private int maxT; // taille de la liste tabu
	private int gen;
	private Solution bestSolution;
	
	public HybridAlgorithm(Data data) {
		this.data = data;
		popSize = Main.POP_SIZE;
		maxGen = Main.MAX_GEN;
		maxStagnantStep = Main.MAX_STAGNANT_STEP;
		pr = Main.PR;
		pc = Main.PC;
		pm = Main.PM;
		maxT = Main.MAXT;
	}

	public Solution launch() {
		// step 1 : set parameter of HA
		gen = 0;
		int stagnantStep = 0;
		int prev_cout = Integer.MAX_VALUE;

		// step 2 : initialize the population and set Gen=1
		Population population = new Population(data);
		population.generateInitialPopulation(popSize);
		
		while (true) {
			maxTSIterSize = (int) (800 * ((float)gen / (float) maxGen));
			// step 3 : evaluate every individual in the population by the objectives
			Evaluator evaluator = new Evaluator();
			for (Solution solution : population.solutions) {
				solution.cout = evaluator.evaluate(solution); 
			}
			
			// met a jour la variable verifiant le nombre d'etapes sans ameliorations
			if (population.bestSolution().cout >= prev_cout) {
				stagnantStep++;
			}
			else {
				stagnantStep = 0;
			}
			prev_cout = population.bestSolution().cout;
			
			// step 4 : is the termination criteria satisfied ? (if yes, go to step 7)
			if (!(gen < maxGen && stagnantStep < maxStagnantStep))
				break;
		
			// -- step 5 : generate new population --
	
			// step 5.1 : use genetic operator to generate new population
			LinkedList<Solution> solutionGeneretad = newIndividuals(population);
	
			// step 5.2 : apply the guided LS algorithm (TS)
			for (Solution solution : solutionGeneretad) {
				TabuSearch tabu = new TabuSearch(data, maxT);
				solution = tabu.launch(maxTSIterSize, solution);
			}
	
			// step 6 : set Gen = Gen + 1 and go to step 3
			population = addNewInidividualInPopulation(solutionGeneretad, population);
			gen++;
		}
		
		// 7 : output the best solution
		bestSolution = population.bestSolution();
		Evaluator e = new Evaluator();
		e.evaluate(bestSolution);
		
		return bestSolution;
	}

	private Population addNewInidividualInPopulation(LinkedList<Solution> solutionGeneretad, Population population) {
		int n = solutionGeneretad.size();
		
		// suppression des n pires individus de la population
		population.deleteWorstInidividual(n);
		
		// ajout des nouveaux individus dans la population
		population.addNewIndividual(solutionGeneretad);
		
		return population;
	}

	/**
	 * Genere une nouvelle population a l'aide des operateurs genetiques.
	 * @param population
	 * @return
	 */
	private LinkedList<Solution> newIndividuals(Population population) {
		// selection
		LinkedList<Solution> solutionSelected = GeneticOperators.elitistSelection(population, pr, popSize);
		LinkedList<Solution> solutionGenerated = new LinkedList<>();
		int nbCrossover = (int) (solutionSelected.size() * pc); 
		int nbMutation = (int) (solutionSelected.size() * pm);
		
		
		// crossover
		for (int i = 0; i < nbCrossover; i++) {
			// selection aleatoire de 2 parents
			int rdParent1 = (int) (Math.random() * solutionSelected.size());
			int rdParent2;
			do {
				rdParent2 = (int) (Math.random() * solutionSelected.size());
			} while (rdParent2 == rdParent1);
			Solution parent1 = solutionSelected.get(rdParent1);
			Solution parent2 = solutionSelected.get(rdParent2);
			int nb_job = parent1.data.OPJ.length;
			
			int[] OS1 = parent1.OS.clone();
			int[] OS2 = parent1.OS.clone();
			int[] MA1 = parent1.MA.clone();
			int[] MA2 = parent1.MA.clone();
			LinkedList<Integer>[] jobs = Utils.randomSplitJob(nb_job);
			
			// une chance sur deux de realiser le crossover POX sur OS
			int rd = (int) (Math.random() * 2);
			if (rd == 0) {
				GeneticOperators.crossoverOS_POX(parent1.OS, parent2.OS, OS1, OS2, jobs[0], jobs[1]);
			}
			else {
				GeneticOperators.crossoverOS_JBX(parent1.OS, parent2.OS, OS1, OS2, jobs[0], jobs[1]);
			}
			
			// crossover sur MA
			int[] pos = Utils.randomCrossoverMA(MA1.length);
			GeneticOperators.crossoverMA(parent1.MA, parent2.MA, MA1, MA2, pos[0], pos[1]);
			
			// evaluation et ajout des solutions fils
			Solution offspring1 = new Solution(OS1, MA1, data);
			Solution offspring2 = new Solution(OS2, MA2, data);
			Evaluator evaluator = new Evaluator();
			offspring1.cout = evaluator.evaluate(offspring1);
			offspring2.cout = evaluator.evaluate(offspring2);
			solutionGenerated.add(offspring1);
			solutionGenerated.add(offspring2);
			
		}
		
		// mutation
		for (int i = 0; i < nbMutation; i++) {
			// selection aleatoire d'un parent
			int rdParent = (int) (Math.random() * solutionSelected.size());
			Solution parent = solutionSelected.get(rdParent);
			
			int[] OS = parent.OS.clone();
			int[] MA = parent.MA.clone();
			
			// une chance sur deux de realiser le swapping mutation de OS
			int rd = (int) (Math.random() * 2);
			if (rd == 0) {
				int[] pos = Utils.randomSwappingMutation(OS.length);
				GeneticOperators.swappingMutationOS(parent.OS, OS, pos[0], pos[1]);
			}
			else {
				int[] pos = Utils.randomNeighborhoodMutation(OS.length);
				OS = GeneticOperators.neighborhoodMutationOS(parent.OS, pos[0], pos[1], pos[2]);		
			}
			
			// mutation sur MA
			int[] pos = Utils.randomMutationMA(parent.MA.length, parent.MA.length/2);
			MA = GeneticOperators.mutationMA(parent.MA, pos, data);
			
			// evaluation et ajout de la solution fils
			Solution offspring = new Solution(OS, MA, data);
			Evaluator evaluator = new Evaluator();
			offspring.cout = evaluator.evaluate(offspring);
			solutionGenerated.add(offspring);
		}
		
		return solutionGenerated;
	}
	
	public int getNbGen() {
		return gen;
	}
	
	public void printInfo() {
		System.out.println("Nombre de generations : " + gen);
		System.out.println("Meilleure solution : ");
		System.out.println("\tOS = " + Arrays.toString(bestSolution.OS));
		System.out.println("\tMA = " + Arrays.toString(bestSolution.MA));
		System.out.println("\tCout = " + bestSolution.cout);
	}
	
}
