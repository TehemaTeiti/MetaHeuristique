package application;

import java.util.Arrays;

import data.Job;
import data.Machine;
import data.Operation;
import data.Solution;

/**
 * Cette classe permet de calculer le cout maximal d'une solution dans un probleme donne.
 * Apres une execution de la methode evaluate(), elle instancie et enregistre les jobs, 
 * les operations et les machines utilises dans le calcul.
 * @author Tehema
 *
 */
public class Evaluator {

	private Machine[] machines;
	private Operation[][] ops;
	private Job[] jobs;
	
	public Machine[] getMachines() {
		return machines;
	}

	public Operation[][] getOps() {
		return ops;
	}

	public Job[] getJobs() {
		return jobs;
	}

	/**
	 * Evalue le coût maximal de la solution
	 * @param sol la solution dont on doit calculer le cout maximal
	 * @return le cout maximal
	 */
	public int evaluate(Solution sol) {
		int nb_job = sol.data.OPJ.length;
		int nb_machine = sol.data.COM[0].length;
		
		jobs = new Job[nb_job];
		ops = new Operation[nb_job][];
		for (int i = 0; i < ops.length; i++) {
			ops[i] = new Operation[sol.data.OPJ[i]];
		}
		
		// creation des operations
		for (int i = 0; i < ops.length; i++) {
			for (int j = 0; j < ops[i].length; j++) {
				ops[i][j] = new Operation(i+1, j+1);
			}
		}
		
		// creation des jobs
		for (int i = 0; i < jobs.length; i++) {
			jobs[i] = new Job(i+1);
		}
		
		// association op/machine
		int[] tmp = new int[nb_job];
		machines = new Machine[nb_machine];
		Arrays.fill(tmp, 0);
		for (int i = 0; i < machines.length; i++) {
			machines[i] = new Machine(i+1);
		}
		
		// pour chaque operation
		for (int i = 0; i < sol.OS.length; i++) {
			int job = sol.OS[i];
			int op_id = ++tmp[job-1];
			int machine_id = getMachine(job-1, op_id-1, sol.MA, sol.data.OPJ); // recupere la machine associee a l'op [job, op_id] depuis MA 
			int cout_op = getCoutMachine(job-1, op_id-1, machine_id-1, sol.data.COM, sol.data.OPJ);
			Job j = jobs[job-1];
			Machine m = machines[machine_id-1];
			Operation op = ops[job-1][op_id-1];
			if (op_id > 1)
				op.setPred(ops[job-1][op_id-2]);

			// etape 1 : pour chaque op, determiner sa machine
			op.setMachine(m);
			op.setProcessingTime(cout_op);
			
			// etape 2 : pour chaque machine, determiner son ensemble d'operation
			m.addOperation(op);
			
			// etape 3 : pour chaque job, determiner son ensemble de machine
			j.addMachine(m); 
		}
		
		// etape 4 : pour chaque op, determiner son heure de depart autorise
		for (int i = 0; i < ops.length; i++) {
			for (int j = 0; j < ops[i].length; j++) {
				Operation op = ops[i][j];
				if (j == 0) {
					op.setEarliestStarting(0);
					op.setAllowableStarting(0);
				}
				else {
					Operation prec = ops[i][j-1];
					op.setEarliestStarting(prec.getEarliestCompletion());
					op.setAllowableStarting(prec.getEarliestCompletion());
				}
			}
		}
		
		// etape 5 : verifier le temps d'inactivite d'une machine pour chaque operation
		Arrays.fill(tmp, 0);
		
		for (int job : sol.OS) {
			int op_id = ++tmp[job-1];
			Operation op = ops[job-1][op_id-1];
			int machine_id = getMachine(job-1, op_id-1, sol.MA, sol.data.OPJ);
			Machine m = machines[machine_id-1];
			m.checkIdleArea(op);
		}
		
		// etape 6 : deja fait methode getEarliestCompletion dans Operation

		// etape 7 : ok => methode getEarliestStartingTimeAndCompletion dans Operation
		int coutTotal = 0;
		
		for (Operation[] t_op : ops)
			for (Operation op : t_op)
				coutTotal = Math.max(coutTotal, op.getEarliestCompletion());
		
		return coutTotal;
	}
	
	private static int getCoutMachine(int job, int op_id, int machine_id, int[][] cout_machine_op, int[] nb_op_job) {
		int indice_job = getIndiceJob(job, nb_op_job);
		return cout_machine_op[indice_job + op_id][machine_id];
	}
	
	private static int getMachine(int job, int op_id, int[] MA, int[] nb_op_job) {
		int indice_job = getIndiceJob(job, nb_op_job);
		return MA[indice_job + op_id];
	}
	
	private static int getIndiceJob(int job, int[] nb_op_job) {
		if (job == 0)
			return 0;
		else if (job == 1)
			return nb_op_job[0];
		else
			return nb_op_job[job-1] + getIndiceJob(job-1, nb_op_job);
	}
	
}
