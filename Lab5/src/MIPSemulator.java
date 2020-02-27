import java.util.HashMap;
import java.util.Map;

public class MIPSemulator {
	int[] dataMemory;
	Map<String, Integer> registers;
	int jumpDestination, branchDestination;
	
	public MIPSemulator()
	{
		dataMemory = new int[8192];
		registers = initializeRegisters();
		jumpDestination = 0;
		branchDestination = 0;
	}
	
	public Map<String, Integer> initializeRegisters() {
		Map<String, Integer> registers = new HashMap<String, Integer>();
		registers.put("pc", 0);
		registers.put("$0", 0);
		registers.put("$zero", 0);
		registers.put("$v0", 0); 
		registers.put("$v1", 0); 
		registers.put("$a0", 0); 
		registers.put("$a1", 0); 
		registers.put("$a2", 0); 
		registers.put("$a3", 0); 
		registers.put("$t0", 0); 
		registers.put("$t1", 0); 
		registers.put("$t2", 0); 
		registers.put("$t3", 0); 
		registers.put("$t4", 0); 
		registers.put("$t5", 0); 
		registers.put("$t6", 0); 
		registers.put("$t7", 0);
		registers.put("$s0", 0); 
		registers.put("$s1", 0); 
		registers.put("$s2", 0); 
		registers.put("$s3", 0); 
		registers.put("$s4", 0); 
		registers.put("$s5", 0); 
		registers.put("$s6", 0); 
		registers.put("$s7", 0); 
		registers.put("$t8", 0); 
		registers.put("$t9", 0); 
		registers.put("$sp", 0); 
		registers.put("$ra", 0);
		return registers;
	}
	
	public Map<String, Integer> getRegister(){
		return this.registers;
	}
	
	public int getRegisterValue(String key){
		return this.registers.get(key);
	}
	
	public void setRegisters(String key, int value) {
		this.registers.put(key, value);
	}

	public int getDataMemoryValue(int index) {
		return this.dataMemory[index];
	}
	
	public void setDataMemory(int value, int index) {
		if (index == -3) {
			System.out.println(registers.get("$ra"));
		}
		this.dataMemory[index] = value;
	}
}
