package application;

import data.Data;
import data.Machine;
import data.Solution;
import utils.ConvertToGanttLatex;

public class Main {
	
	/* ********** PARAMETRE POUR DONNEES DU PROBLEME ********* */
	public static final int NB_JOB = 3;
	public static final int NB_OP = 8;
	public static final int NB_MACHINE = 8;
	public static final int MAX_COUT = 10;
	
	/* ********** PARAMETRES POUR ALGORITHME HYBRIDE ********* */
	public static final int POP_SIZE = 100; // taille de la population
	public static final int MAX_GEN = 50; // nombre total de generation
	public static final int MAX_STAGNANT_STEP = 20; // le nombre max d'etapes autorisees sans amelioration
	public static final float PR = 0.1f; // probabilite de reproduction
	public static final float PC = 0.5f; // probabilite de crossover
	public static final float PM = 0.1f; // probabilite de mutation
	public static final int MAXT = 9; // taille de la liste tabu
	
	public static void main(String[] args) {
		// data problem
		Data data = Data.randomData(NB_JOB, NB_OP , NB_MACHINE, MAX_COUT);
		// hybrid algorithm
		HybridAlgorithm ha = new HybridAlgorithm(data);
		
		// démarrage calcul du temps d'exécution
		long startTime = System.nanoTime();
		
		Solution sol = ha.launch();
		
		// fin calcul du temps d'exécution
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1000000;
		
		// info
		System.out.println("n x m : " + data.getNbOp() + " x " + data.getNbMachine());
		ha.printInfo();
		System.out.println("Temps de calcul = " + duration + "ms");
		
		// generation du Gantt en latex dans le terminal
		/*Evaluator eval = new Evaluator();
		eval.evaluate(sol);
		Machine[] machines = eval.getMachines();
		ConvertToGanttLatex converter = new ConvertToGanttLatex(machines, sol.cout);
		converter.launch();*/
	}

	
}
