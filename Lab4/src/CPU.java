import java.util.HashMap;

public class CPU {
	HashMap<String, String> intermediateRegs;
	int numInstructions;
	int numCycles;
	int numStalls;
	boolean useAfterLoadFlag;
	boolean takenBranchFlag;
	boolean jumpFlag;
	int branchCtr, jumpCtr, useAfterLoadCtr;
	
	public CPU() {
		intermediateRegs = initializeIntermediateRegs();
		numCycles = 0;
		numInstructions = 0;
		numStalls = 0;
		useAfterLoadFlag = false;
		takenBranchFlag = false;
		jumpFlag = false;
		branchCtr = 0;
		jumpCtr = 0;
		useAfterLoadCtr = 0;
	}
	
	public HashMap<String, String> initializeIntermediateRegs() {
		HashMap<String, String> intermediateRegs = new HashMap<>();
		intermediateRegs.put("IF/ID reg", "empty");
		intermediateRegs.put("ID/EXE reg", "empty");
		intermediateRegs.put("EXE/MEM reg", "empty");
		intermediateRegs.put("MEM/WB reg", "empty");
		return intermediateRegs;
	}
	
	public String printCPI() {
		double CPI = (double)this.numCycles/this.numInstructions;
		String CPIstr = String.format("%.3f", CPI);
		return "CPI = " + CPIstr + "\tCycles = " + this.numCycles + "\tInstructions = " + this.numInstructions;
	}
	
	public void stall1() {
		this.intermediateRegs.put("MEM/WB reg", this.intermediateRegs.get("EXE/MEM reg"));
		this.intermediateRegs.put("EXE/MEM reg", this.intermediateRegs.get("ID/EXE reg"));
		this.intermediateRegs.put("ID/EXE reg", "stall");
		this.useAfterLoadCtr = 0;
	}
	
	public void squash3() {
		this.intermediateRegs.put("IF/ID reg", "squash");
		this.intermediateRegs.put("ID/EXE reg", "squash");
		this.intermediateRegs.put("EXE/MEM reg", "squash");
		this.intermediateRegs.put("MEM/WB reg", this.intermediateRegs.get("EXE/MEM reg"));
		this.branchCtr = 0;
		this.takenBranchFlag = false;
	}
}
