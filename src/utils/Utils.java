package utils;

import java.util.LinkedList;

/**
 * Cette classe contient uniquement des methodes statiques.
 * Elle regroupe des methodes utilitaires divers.
 * @author Tehema
 *
 */
public class Utils {

	/**
	 * Ajoute la valeur v dans le tableau t au premier indice i ou t[i] < 0.
	 * On considere qu'une case du tableau est vide si sa valeur est negative.
	 * S'il n'y a pas de place, la valeur n'est pas ajoute. 
	 * @param t un tableau d'entier
	 * @param v la valeur a insere
	 */
	public static void addNextEmpty(int[] t, int v) {
		for (int i=0 ; i < t.length ; i++)
			if (t[i] < 0) {
				t[i] = v;
				break;
			}
		
	}

	/**
	 * Renvoie une tableau d'entier de taille 2.
	 * Chaque entier est determine aleatoirement dans une plage compris dans l'intervalle [0;n[
	 * Le premier entier est strictement inferieur au deuxieme entier
	 * @param n la valeur maximale d'un entier retourne
	 * @return un tableau d'entier de taille 2
	 */
	public static int[] randomCrossoverMA(int n) {
		int pos1;
		int pos2;
		pos1 = (int) (Math.random() * (n-1));
		do {
			pos2 = (int) (Math.random() * n);
		} while(pos2 <= pos1);
		
		int[] pos = {pos1, pos2};
		return pos;
	}
	
	/**
	 * Renvoie une tableau d'entier de taille 2.
	 * Chaque entier est determine aleatoirement dans une plage compris dans l'intervalle [0;n[
	 * Les deux entiers sont differents.
	 * @param n la valeur maximale d'un entier retourne
	 * @return un tableau d'entier de taille 2
	 */
	public static int[] randomSwappingMutation(int n) {
		int pos1 = (int) (Math.random() * n);
		int pos2;
		do {
			pos2 = (int) (Math.random() * n);
		} while (pos2 == pos1);
		
		int[] pos = {pos1, pos2};
		return pos;
	}

	/**
	 * Renvoie une tableau d'entier de taille 3.
	 * Chaque entier est determine aleatoirement dans une plage compris dans l'intervalle [0;n[
	 * Les trois entiers sont differents.
	 * @param n la valeur maximale d'un entier retourne
	 * @return un tableau d'entier de taille 3
	 */
	public static int[] randomNeighborhoodMutation(int n) {
		int pos[] = new int[3];
		pos[0] = (int) (Math.random() * n);
		do {
			pos[1] = (int) (Math.random() * n);
		} while (pos[1] == pos[0]);
		
		do {
			pos[2] = (int) (Math.random() * n);
		} while (pos[2] == pos[0] || pos[2] == pos[1]);
		
		return pos;
	}
	
	/**
	 * @param n
	 * @param r
	 * @return un tableau d'entier de taille r
	 */
	public static int[] randomMutationMA(int n, int r) {
		int pos[] = new int[r];
		LinkedList<Integer> l = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			l.add(i);
		}
		
		for (int i = 0; i < pos.length; i++) {
			int rd = (int) (Math.random() * l.size());
			pos[i] = l.remove(rd);
		}
		
		return pos;
	}
	
	/**
	 * Divise les n jobs en 2 ensembles de jobs aleatoirement. Un ensemble contient au moins 1 job.
	 * Les deux ensembles sont strictements differents et tous les jobs sont repartis entre ces 2 ensembles.
	 * @param n le nombre de jobs
	 * @return un tableau de deux listes d'entiers. Chaque liste est un ensemble de jobs.
	 */
	public static LinkedList<Integer>[] randomSplitJob(int n) {
		int rd_split_job = (int) (Math.random() * (n-1)) + 1; // choisi un entier aleatoire dans [1 ; n]
		int rd;
		LinkedList<Integer> J1 = Utils.create_jobs(n);
		LinkedList<Integer> J2 = new LinkedList<>();
		
		// generation aleatoire de J1 et J2
		for (int i=0 ; i < rd_split_job ; i++) {
			rd = (int) (Math.random() * J1.size());
			J2.add(J1.remove(rd));
		}
		
		LinkedList<Integer>[] jobs = new LinkedList[2];
		jobs[0] = J1;
		jobs[1] = J2;
		
		return jobs;
	}

	/**
	 * Creer un tableau d'entiers de taille n.
	 * Le ieme element vaut i+1.
	 * @param n
	 * @return
	 */
	public static LinkedList<Integer> create_jobs(int n) {
		LinkedList<Integer> jobs = new LinkedList<>();
		for (int i = 0 ; i < n ; i++)
			jobs.add(i+1);
		return jobs;
	}

	/**
	 * Retourne le plus grand entier d'un tableau d'entiers
	 * @param t tableau d'entiers
	 * @return le max de t
	 */
	public static int max(int[] t) {
		int max = 0;
		for (int i : t)
			max = Math.max(i, max);
		return max;
	}

}
