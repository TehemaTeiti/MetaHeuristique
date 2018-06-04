package data;

public class Operation implements Comparable<Operation>{

	private Machine machine;
	private int job;
	private int op;
	private int allowableStarting; // heure de depart autorisee (allowable starting)
	private int earliestStarting; // heure d'achevement le plus tot (earlist completion time)
	private int processingTime; // temps de traitement avec la machine (processing time)
	private Operation pred;

	public Operation(int job, int op) {
		this.job = job;
		this.op = op;
		this.machine = null;
		this.allowableStarting = 0;
		this.earliestStarting = 0;
		this.processingTime = 0;
		this.pred = null;
	}

	@Override
	public int compareTo(Operation o) {
		return Integer.compare(earliestStarting, o.earliestStarting);
	}
	
	public Machine getMachine() {
		return machine;
	}

	public void setPred(Operation op) {
		this.pred = op;
	}
	
	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public int getJob() {
		return job;
	}

	public void setJob(int job) {
		this.job = job;
	}

	public int getOp() {
		return op;
	}

	public void setOp(int op) {
		this.op = op;
	}
	
	public int getAllowableStarting() {
		if (pred == null)
			return allowableStarting;
		else {
			return getEarliestCompletionPred();
		}
			
	}

	public void setAllowableStarting(int allowableStarting) {
		this.allowableStarting = allowableStarting;
	}

	public int getEarliestStarting() {
		return earliestStarting;
	}

	public void setEarliestStarting(int earliestStarting) {
		this.earliestStarting = earliestStarting;
	}

	public int getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(int processingTime) {
		this.processingTime = processingTime;
	}
	
	public int getEarliestCompletion() {
		return this.earliestStarting + this.processingTime;
	}

	/**
	 * @return t de taille 2 avec t[0] = earliestStarting et t[1] = earliestCompletion
	 */
	public int[] getEarliestStartingTimeAndCompletion() {
		int[] ret = {earliestStarting, getEarliestCompletion()};
		return ret;
	}
	
	@Override
	public String toString() {
		if (machine == null)
			return "J" + job + "#Op" + op;
		else
			return "J" + job + "#Op" + op + " (" + machine + ")";
	}

	public int getEarliestCompletionPred() {
		if (pred != null)
			return pred.getEarliestCompletion();
		else
			return 0;
	}
	
}
