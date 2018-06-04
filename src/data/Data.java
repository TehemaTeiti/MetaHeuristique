package data;

import java.util.Arrays;

public class Data {

	public int[] OPJ; // nb d'op par job
	public int[][] COM; // cout d'une op associée à une machine
	
	public Data(int[] OPJ, int[][] COM) {
		this.OPJ = OPJ;
		this.COM = COM;
	}

	/**
	 * @return un exemple de données du problèmes
	 */
	public static Data createDefaultData() {
		int[] OPJ = {3,3,2};
		int[][] COM = {
				{3,0,0},	
				{0,2,4},	
				{0,5,5},	
				{0,4,0},
				{2,0,0},
				{2,4,2},	
				{2,0,2},	
				{0,3,5}	
		};
		
		return new Data(OPJ, COM);
	}
	
	public int getNbOp() {
		int s = 0;
		for (int nb_op : OPJ) {
			s += nb_op;
		}
		return s;
	}
	
	public int getNbMachine() {
		return COM[0].length;
	}
	
	public static Data randomData(int nbJob, int nbOp, int nb_machine, int coutMax) {
		int rdMachine = (int) (Math.random() * nb_machine) + 1;
		int[] nb_job = new int[nbJob];
		int rep = (int) Math.ceil((double) nbOp/ (double) nbJob);
		int count = nbOp;

		
		// génération du nb d'op par job
		for (int i = 0; i < nbJob; i++) {
			nb_job[i] = (count - rep >= 0) ? rep : count;
			count -= rep;
		}
		
		// génération de la matrice d'association op/machine
		int[][] cout_machine = new int[nbOp][nb_machine];
		for (int i = 0; i < cout_machine.length; i++) {
			for (int j = 0; j < cout_machine[i].length; j++) {
				cout_machine[i][j] = (int) (Math.random() * (coutMax+1));
			}
		}
		
		return new Data(nb_job, cout_machine);
	}
	
}
