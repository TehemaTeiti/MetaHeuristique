package data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Cette classe represente une solution a l'aide des deux vecteurs OS et MA.
 * Elle implemente l'interface Comparable afin de pouvoir etre compare avec d'autres solutions.
 * Les solutions sont comparees selon leur cout max.
 * Initialement, le cout vaut la valeur maximale pour un entier. Elle doit etre mise a jour avec
 * la methode evaluate() de la classe Evaluator.
 * 
 * @author Tehema
 *
 */
public class Solution implements Comparable<Solution> {

	public int[] OS;
	public int[] MA;
	public Data data;
	public int cout = Integer.MAX_VALUE;
	
	public Solution(int[] OS, int[] MA, Data data) {
		this.OS = OS;
		this.MA = MA;
		this.data = data;
	}
	
	/**
	 * Génère un vecteur OS aléatoire à partir des données d'un problème
	 * @param data
	 * @return
	 */
	public static int[] randomOS(Data data) {
		int[] OS;
		int nb_act_total = 0;
		List<Integer> list = new LinkedList<>();
		
		// calcul nb activites total
		for (int i=0 ; i < data.OPJ.length ; i++) {
			nb_act_total += data.OPJ[i];
			for (int j=0 ; j < data.OPJ[i] ; j++) {
				list.add(i+1);
			}
		}
		OS = new int[nb_act_total];
		
		// génération aléatoire de OS
		for (int i=0 ; i < OS.length ; i++) {
			int rand = (int)(Math.random()*list.size() + 1);
			OS[i] = list.remove(rand-1);
		}
				
		return OS;
	}
	
	/**
	 * Génère un vecteur MA aléatoire à partir des données d'un problème
	 * @param data
	 * @return
	 */
	public static int[] randomMA(Data data) {
		int[] MA;
		int nb_act_total = 0;
		List<Integer> list = new LinkedList<>();
		
		// calcul nb activites total
		for (int i=0 ; i < data.OPJ.length ; i++) {
			nb_act_total += data.OPJ[i];
			for (int j=0 ; j < data.OPJ[i] ; j++) {
				list.add(i+1);
			}
		}
		
		MA = new int[nb_act_total];
		
		// génération aléatoire de MA
		for (int i=0 ; i < data.COM.length ; i++) {
			int[] ligne = data.COM[i];
			int el, rand = 0;
			List<Integer> tmp = Arrays.stream(ligne).boxed().collect(Collectors.toList());
			do {
				rand = (int)(Math.random()*tmp.size() + 1);
				el = tmp.get(rand-1);
			} while (el == 0);
			MA[i] = rand;
		}
		
		return MA;
	}
	
	/**
	 * Retourne une solution aleatoire a partir des donnees d'un probleme.
	 * @param data les donnees du probleme
	 * @return une nouvelle solution aleatoire
	 */
	public static Solution randomSolution(Data data) {
		int[] OS = randomOS(data);
		int[] MA = randomMA(data);
		
		return new Solution(OS, MA, data);
	}
	
	@Override
	public int compareTo(Solution o) {
		return Integer.compare(this.cout, o.cout);
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Solution))
			return false;
		else {
			Solution sol = (Solution) obj;
			return (Arrays.equals(this.OS, sol.OS) && Arrays.equals(this.MA, sol.MA)); 
		}
	}
	
	public void printOS() {
		System.out.println("OS");
		for (int i : OS) {
			System.out.print(i + " -> ");
		}
	}
	
	public void printMA() {
		System.out.println("MA");
		for (int i=0 ; i < data.OPJ.length ; i++) {
			System.out.println("J" + (i+1) + " : ");
			for (int j=0 ; j < data.OPJ[i] ; j++) {
				System.out.println("\tOp" + (j+1) + " -> M" + MA[i*data.OPJ.length+j]);
			}
		}
	}
	
}
