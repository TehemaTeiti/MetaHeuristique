package utils;

import data.Machine;
import data.Operation;

public class ConvertToGanttLatex {

	private Machine[] machines;
	private int coutMax;
	private String s;
	
	public ConvertToGanttLatex(Machine[] machines, int coutMax) {
		this.machines = machines;
		this.coutMax = coutMax;
	}
	
	public void launch() {
		// debut
		s = "\\begin{ganttchart}[inline]{1}{"+ coutMax + "}\n";
		s += "\\gantttitle{Gantt machine}{" + coutMax + "} \\\\\n";
		s += "\\gantttitlelist{1,...," + coutMax + "}{1} \\\\\n";
		
		// corps
		for (Machine m : machines) {
			s += addMachine(m.getMachine());
			
			for (Operation op : m.getOps_placed()) {
				s += "\n";
				s += addOp(op.getJob(), op.getOp(), op.getEarliestStarting()+1, op.getEarliestCompletion());
			}
			s += "\\\\\n";
		}
		
		// fin
		s += "\\end{ganttchart}";
		System.out.println(s);
	}
	
	
	public static String addMachine(int m) {
		String s = "";
		 s += "\\ganttbar{M"+ m +"}{0}{0}";
		 return s;
	}
	
	public static String addOp(int job, int op, int deb, int fin) {
		String s = "";
		 s += "\\ganttbar{Op"+ job + "," + op +"}{" + deb + "}{" + fin + "}";
		 return s;
	}
	
	public static void perf(int n, int m, int cout, int nbIter, long duration) {
		System.out.println("$"+ n +" \\times "+ m +"$ & " + cout + " & "+ nbIter +" & " + duration + "\\\\");
	}
	
}
