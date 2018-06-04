package data;

import java.util.HashSet;
import java.util.Set;

public class Job {

	private int job;
	private Set<Machine> machines;
	
	public Job(int job) {
		this.job = job;
		machines = new HashSet<>();
	}

	public int getJob() {
		return job;
	}
	
	public void addMachine(Machine m) {
		machines.add(m);
	}
	
	public void printMachines() {
		System.out.print("J"+job+" : ");
		for (Machine m : machines)
			System.out.print("M"+m.getMachine()+", ");
		System.out.println();
	}
	
}
