import java.io.*;
import java.util.*;
 
public class lab4 {
	public static void main(String[] args) throws IOException{
		File inputFile = new File(args[0]);										//open and read file
		Scanner scanner1 = new Scanner(inputFile);			
		MIPSemulator emulator = new MIPSemulator();
		CPU cpu = new CPU();
		Map<String,Integer> labels = storeLabelAddresses(scanner1);				//first pass, create a hashmap of labels and line 
		Scanner scanner2 = new Scanner(inputFile);								//scanner to reread file for second pass
		ArrayList<String> instructions = instructionArray(scanner2);
		if (args.length == 2) {
			File script = new File(args[1]);
			processScriptInputs(script, labels, instructions, emulator, cpu);
		} else {
			processInteractiveInputs(labels, instructions, emulator, cpu);
		}							
	}
	
	public static ArrayList<String> instructionArray(Scanner sc) {
		ArrayList<String> instructions = new ArrayList<String>(); 
		while(sc.hasNextLine()) {
			String thisLine = sc.nextLine().trim();
			if (thisLine.length() != 0) {
				if (thisLine.charAt(0) != '#') {
					int h=0;
					int i=0;
					while(thisLine.charAt(i+1) != ' ' && thisLine.charAt(i+1) != '\t' && thisLine.charAt(i+1) != '$' && i+2 < thisLine.length()) {
						if(thisLine.charAt(i) == ':') {
							h=i+1;
						}
						i++;
					}
					boolean hasInstruction = true;
					if (thisLine.charAt(i) == ':' || thisLine.charAt(i+1) == ':') {
						if(thisLine.length() > i+2) {
							thisLine = thisLine.substring(i+1,thisLine.length()).trim();
							i=0;
							while(thisLine.charAt(i+1) != ' ') {
								i++;
							}
						} else {
							hasInstruction = false;
						}	
					}
					if (hasInstruction) {
						int endIndex = thisLine.length();
						for (int k = 0; k < thisLine.length(); k++) {
							if (thisLine.charAt(k) == '#'){
								endIndex = k;
							}
						}
						instructions.add(thisLine.substring(0,endIndex).trim());
					}
				}	
			}	
		}		
		return instructions;
	}

	public static Map<String,Integer> storeLabelAddresses(Scanner sc){
		Map<String,Integer> labels = new HashMap<String,Integer>();
		int line = 0;
		while(sc.hasNextLine()){
			String thisLine = sc.nextLine().trim();
			for (int i = 0; i < thisLine.length(); i++) {
				if (thisLine.charAt(i) == ':') {
					labels.put(thisLine.substring(0,i),line);
					break;
				}
			}
			if (thisLine.length() != 0 && thisLine.trim().charAt(0) != '#') {
				line++;
			}	
		}
		return labels;
	}
	
	public static void processScriptInputs(File script, Map<String,Integer> labels, ArrayList<String> instructions, MIPSemulator emulator, CPU cpu) throws FileNotFoundException {
		Scanner sc = new Scanner(script);
		if (sc.hasNext()) {
			int PCline = 0;
			String inputLine;
			do {	
				inputLine = sc.nextLine().trim();
				System.out.println("mips> " + inputLine);
				PCline = processInput(PCline,inputLine,instructions,labels,emulator, cpu);
			} while(sc.hasNext() && PCline != -1);
		}
		sc.close();
	}
	
	public static void processInteractiveInputs(Map<String,Integer> labels, ArrayList<String> instructions, MIPSemulator emulator, CPU cpu) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
		int PCline = 0;
        while(PCline != -1) {
			System.out.print("mips> ");
	        String inputLine = reader.readLine().trim();
	        PCline = processInput(PCline,inputLine,instructions,labels,emulator, cpu);
		}
	}
	
	public static int processInput(int PCline, String inputLine, ArrayList<String> instructions, Map<String,Integer> labels, MIPSemulator emulator, CPU cpu) {
		switch(inputLine.charAt(0)) {
			case ('h'):
				System.out.println("h = show help\r\n" + 
								   "d = dump register state\r\n" + 
								   "p = show pipeline registers\r\n" + 	
						           "s = single step through the program (i.e. execute 1 instruction and stop)\r\n" + 
						           "s num = step through num instructions of the program\r\n" + 
						           "r = run until the program ends and display timing summary\r\n" + 
						           "m num1 num2 = display data memory from location num1 to num2\r\n" + 
						           "c = clear all registers, memory, and the program counter to 0\r\n" + 
						           "q = exit the program\r\n");
				break;
			case ('d'):
				System.out.println("\npc = " + emulator.getRegisterValue("pc"));
				System.out.println("$0 = " + emulator.getRegisterValue("$0") + "\t\t$v0 = " + emulator.getRegisterValue("$v0") + "\t\t$v1 = " +
							emulator.getRegisterValue("$v1") + "\t\ta0 = " + emulator.getRegisterValue("$a0"));
				System.out.println("$a1 = " + emulator.getRegisterValue("$a1") + "\t\t$a2 = " + emulator.getRegisterValue("$a2") + "\t\t$a3 = " +
							emulator.getRegisterValue("$a3") + "\t\tt0 = " + emulator.getRegisterValue("$t0"));
				System.out.println("$t1 = " + emulator.getRegisterValue("$t1") + "\t\t$t2 = " + emulator.getRegisterValue("$t2") + "\t\t$t3 = " +
							emulator.getRegisterValue("$t3") + "\t\tt4 = " + emulator.getRegisterValue("$t4"));
				System.out.println("$t5 = " + emulator.getRegisterValue("$t5") + "\t\t$t6 = " + emulator.getRegisterValue("$t6") + "\t\t$t7 = " +
							emulator.getRegisterValue("$t7") + "\t\ts0 = " + emulator.getRegisterValue("$s0"));
				System.out.println("$s1 = " + emulator.getRegisterValue("$s1") + "\t\t$s2 = " + emulator.getRegisterValue("$s2") + "\t\t$s3 = " +
							emulator.getRegisterValue("$s3") + "\t\ts4 = " + emulator.getRegisterValue("$s4"));
				System.out.println("$s5 = " + emulator.getRegisterValue("$s5") + "\t\t$s6 = " + emulator.getRegisterValue("$s6") + "\t\t$s7 = " +
							emulator.getRegisterValue("$s7") + "\t\tt8 = " + emulator.getRegisterValue("$t8"));
				System.out.println("$t9 = " + emulator.getRegisterValue("$t9") + "\t\t$sp = " + emulator.getRegisterValue("$sp") + "\t\t$ra = " +
							emulator.getRegisterValue("$ra") + "\n");
				break;
			case ('s'):
				if (inputLine.length() > 1) {
					int numSteps = Integer.parseInt(inputLine.substring(1,inputLine.length()).trim());
					for(int i=0;i<numSteps;i++) {
						if (PCline < instructions.size()) {
							PCline = processNextInstr(PCline, instructions, labels, emulator, cpu);
							System.out.println("\npc\tif/id\tid/exe\texe/mem\tmem/wb\n" + 
							           PCline + "\t" + 
							           cpu.intermediateRegs.get("IF/ID reg") + "\t" + 
							           cpu.intermediateRegs.get("ID/EXE reg") + "\t" +
							           cpu.intermediateRegs.get("EXE/MEM reg") + "\t" + 
							           cpu.intermediateRegs.get("MEM/WB reg") + "\n");	
						}	
					}
				} else {
					PCline = processNextInstr(PCline, instructions, labels, emulator, cpu);
					System.out.println("\npc\tif/id\tid/exe\texe/mem\tmem/wb\n" + 
					           PCline + "\t" + 
					           cpu.intermediateRegs.get("IF/ID reg") + "\t" + 
					           cpu.intermediateRegs.get("ID/EXE reg") + "\t" +
					           cpu.intermediateRegs.get("EXE/MEM reg") + "\t" + 
					           cpu.intermediateRegs.get("MEM/WB reg") + "\n");			
				}	
				break;
			case ('r'):
				while(PCline<instructions.size()) {
					PCline = processNextInstr(PCline, instructions, labels, emulator, cpu); 
				}
				System.out.println("\nProgram complete\n" + cpu.printCPI() + "\n");			
				break;
			case ('m'):
				String[] indeces = inputLine.substring(0,inputLine.length()).split(" ");
				int lowerBound = Integer.parseInt(indeces[1]);
				int upperBound = Integer.parseInt(indeces[2]);
				System.out.println("\n");
				for (int j=lowerBound; j<=upperBound; j++) {
					System.out.println("[" + j + "] = " + emulator.getDataMemoryValue(j));
				}
				System.out.println("\n");
				break;
			case ('p'):
				System.out.println("pc\tif/id\tid/exe\texe/mem\tmem/wb\n" + 
						           PCline + "\t" + 
						           cpu.intermediateRegs.get("IF/ID reg") + "\t" + 
						           cpu.intermediateRegs.get("ID/EXE reg") + "\t" +
						           cpu.intermediateRegs.get("EXE/MEM reg") + "\t" + 
						           cpu.intermediateRegs.get("MEM/WB reg"));
				break;
			case ('c'):
				System.out.println("\tSimulator reset\n");
				for (Map.Entry<String, Integer> entry : emulator.getRegister().entrySet())
				     entry.setValue(0);
				break;
			case ('q'):
				return -1;
			default:	
		}		
		emulator.setRegisters("pc", PCline); 
		return PCline;
	}
	
	public static int processNextInstr(int PCline, ArrayList<String> instructions, Map<String,Integer> labels, MIPSemulator emulator, CPU cpu) {
		cpu.numCycles++;
		if (cpu.jumpFlag){
			cpu.jumpCtr++;
			if (cpu.jumpCtr == 1) {
				executeInstruction(cpu, "squash");
				cpu.jumpCtr = 0;
				cpu.jumpFlag = false;
				//return PCline;
				return emulator.jumpDestination;	
			}
		}  
		if (cpu.takenBranchFlag) {
			cpu.branchCtr++;
			if (cpu.branchCtr == 3) {
				cpu.squash3();
				return emulator.branchDestination;
			}
		}
		if (cpu.useAfterLoadFlag) {
			if (cpu.useAfterLoadCtr == 1) {
				cpu.stall1();
				cpu.useAfterLoadFlag = false;
				return PCline;
			} else {
				cpu.useAfterLoadCtr++;	
			}
		}

		String thisLine = instructions.get(PCline);
		thisLine = thisLine.replace(",", "");
		String[] lineContents = thisLine.split(" ");
		executeInstruction(cpu, lineContents[0]);
		String[] memLocation;
		if (!cpu.jumpFlag && !cpu.takenBranchFlag) {
			switch (lineContents[0].trim()){
				case("and"):
					emulator.setRegisters(lineContents[1].trim(), emulator.getRegisterValue(lineContents[2].trim()) & emulator.getRegisterValue(lineContents[3].trim()));
					PCline++;
					break;
				case("or"):
					emulator.setRegisters(lineContents[1].trim(), emulator.getRegisterValue(lineContents[2].trim()) | emulator.getRegisterValue(lineContents[3].trim()));
					PCline++;
					break; 
				case("add"):
					emulator.setRegisters(lineContents[1].trim(), emulator.getRegisterValue(lineContents[2].trim()) + emulator.getRegisterValue(lineContents[3].trim()));
					PCline++;
					break;
				case("addi"):
					emulator.setRegisters(lineContents[1].trim(), emulator.getRegisterValue(lineContents[2].trim()) + Integer.parseInt(lineContents[3].trim()));
					PCline++;
					break;
				case("sll"):
					emulator.setRegisters(lineContents[1].trim(), emulator.getRegisterValue(lineContents[2].trim()) << Integer.parseInt(lineContents[3].trim()));
					PCline++;
					break;
				case("sub"):
					emulator.setRegisters(lineContents[1].trim(), emulator.getRegisterValue(lineContents[2].trim()) - emulator.getRegisterValue(lineContents[3].trim()));
					PCline++;
					break;
				case("slt"):
					if(emulator.getRegisterValue(lineContents[2].trim()) >= emulator.getRegisterValue(lineContents[3].trim())) {
						emulator.setRegisters(lineContents[1].trim(), 0);
					} else {
						emulator.setRegisters(lineContents[1].trim(), 1);
					}
					PCline++;
					break;
				case("beq"):
					if(emulator.getRegisterValue(lineContents[1].trim()) == emulator.getRegisterValue(lineContents[2].trim())) {
						cpu.takenBranchFlag = true;
						emulator.branchDestination = labels.get(lineContents[3].trim());
					}
					PCline++;
					break;
				case("bne"):
					if(emulator.getRegisterValue(lineContents[1].trim()) != emulator.getRegisterValue(lineContents[2].trim())) {
						cpu.takenBranchFlag = true;
						emulator.branchDestination = labels.get(lineContents[3].trim());
					}	
					PCline++;
					break;
				case("lw"):
					memLocation = lineContents[2].split("[(]");
					memLocation[1] = memLocation[1].substring(0,memLocation[1].length()-1);
					emulator.setRegisters(lineContents[1], emulator.getDataMemoryValue(emulator.getRegisterValue(memLocation[1])+Integer.parseInt(memLocation[0])));
					if(instructions.get(PCline+1).contains(lineContents[1]) && !instructions.get(PCline+1).contains("lw") && !instructions.get(PCline+1).contains("sw")) {
						cpu.useAfterLoadFlag = true;					
					}
					PCline++;
					break;
				case("sw"):
					memLocation = lineContents[2].split("[(]");
					memLocation[1] = memLocation[1].substring(0,memLocation[1].length()-1);
					emulator.setDataMemory(emulator.getRegisterValue(lineContents[1]), emulator.getRegisterValue(memLocation[1])+Integer.parseInt(memLocation[0]));	
					PCline++;
					break;
				case("j"):
					PCline++;
					emulator.jumpDestination = labels.get(lineContents[1]);
					cpu.jumpFlag = true;
					break;
				case("jr"):
					PCline++;
					emulator.jumpDestination = emulator.getRegisterValue(lineContents[1]);	
					cpu.jumpFlag = true;
					break;
				case("jal"):
					emulator.setRegisters("$ra", PCline+1);
					emulator.jumpDestination = labels.get(lineContents[1]);
					cpu.jumpFlag = true;
					PCline++;
					//clearNonPersistantRegisters(emulator);
					break;
				default:
					System.out.println("invalid instruction");
			}
		} else {
			PCline++;
		}
		cpu.numInstructions++;
		if (PCline == instructions.size()) {
			for (int i=0; i<4; i++) {
				cpu.numCycles++;
				executeInstruction(cpu, "empty");
			}
		}
		return PCline;
	}
	
	public static void executeInstruction(CPU cpu, String instruction) {
		cpu.intermediateRegs.put("MEM/WB reg", cpu.intermediateRegs.get("EXE/MEM reg"));
		cpu.intermediateRegs.put("EXE/MEM reg", cpu.intermediateRegs.get("ID/EXE reg"));
		cpu.intermediateRegs.put("ID/EXE reg", cpu.intermediateRegs.get("IF/ID reg"));
		cpu.intermediateRegs.put("IF/ID reg", instruction);
	}
		
	public static void clearNonPersistantRegisters(MIPSemulator emulator) {
		emulator.setRegisters("v0", 0);
		emulator.setRegisters("v1", 0);
		emulator.setRegisters("a0", 0);
		emulator.setRegisters("a1", 0);
		emulator.setRegisters("a2", 0);
		emulator.setRegisters("a3", 0);
		emulator.setRegisters("t0", 0);
		emulator.setRegisters("t1", 0);
		emulator.setRegisters("t2", 0);
		emulator.setRegisters("t3", 0);
		emulator.setRegisters("t4", 0);
		emulator.setRegisters("t5", 0);
		emulator.setRegisters("t6", 0);
		emulator.setRegisters("t7", 0);
		emulator.setRegisters("t8", 0);
		emulator.setRegisters("t9", 0);
	}
}

