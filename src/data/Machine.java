package data;

import static java.lang.Math.max;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Machine {

	private int machine;
	private int count;
	private List<Operation> ops;
	private List<Operation> ops_placed;
	private int[] t = {0, Integer.MAX_VALUE} ;
	
	public Machine(int machine) {
		this.machine = machine;
		ops = new LinkedList<>();
		ops_placed = new LinkedList<>();
		this.count = 0;
	}

	public List<Operation> getOps_placed() {
		return ops_placed;
	}
	
	public int getMachine() {
		return machine;
	}

	public void setMachine(int machine) {
		this.machine = machine;
	}

	public boolean addOperation(Operation op) {
		return ops.add(op);
	}

	public void checkIdleArea(Operation op) {
		boolean trouve = false;

		while(!trouve) {
			// cherche si l'operation rentre dans l'intervalle [t[0] ; t[1]]
			boolean b = max(t[0], op.getAllowableStarting()) + op.getProcessingTime() <= t[1];
			if (b) {
				op.setEarliestStarting(max(t[0], op.getAllowableStarting()));
				ops_placed.add(op);
				count = 0;
				t[0] = 0;
				Collections.sort(ops_placed);
				t[1] = ops_placed.get(0).getEarliestStarting();
				trouve = true;
			}
			else {
				count++;
				nextIntervalle(t);
			}
		}
	}

	private boolean nextIntervalle(int[] t) {
		if (count < ops_placed.size()) {
			Operation op = ops_placed.get(count);
			t[1] = op.getEarliestStarting();

			if (count > 0) {
				Operation op_prec = ops_placed.get(count-1);
				t[0] = op_prec.getEarliestCompletion();
			}
			else {
				t[0] = 0;
			}
			return true;
		}
		else {
			t[0] = ops_placed.get(count - 1).getEarliestCompletion();
			t[1] = Integer.MAX_VALUE;
			return false;
		}
	}

	public void printOperations() {
		System.out.print("M"+machine+" : ");
		for (Operation op : ops)
			System.out.print("J"+op.getJob()+"#Op"+op.getOp()+", ");
		System.out.println();
	}

	public void printGantt() {
		System.out.println("M" + machine);
		for (Operation op : ops) {
			System.out.println(op + " : AS = " + op.getAllowableStarting()
			+ ", Cout = " + op.getProcessingTime()
			+ ", S = " + op.getEarliestStarting()
			+ ", Fin = " + op.getEarliestCompletion());
		}
	}

	@Override
	public String toString() {
		return "M" + machine;
	}
}
