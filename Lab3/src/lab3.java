import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class lab3 {
	public static void main(String[] args) throws FileNotFoundException{
		File inputFile = new File(args[0]);					//open and read file
		Scanner scanner1 = new Scanner(inputFile);			
		Map<String, String> registerLUT = createLUT();		//create look up table mapping registers to integer values
		Map<String,Integer> labels = storeLabelAddresses(scanner1);	//first pass, create a hashmap of labels and line 
		Scanner scanner2 = new Scanner(inputFile);					//scanner to reread file for second pass
		generateBinary(scanner2,labels, registerLUT);							//second pass, print out op-codes
	}

	public static Map<String,Integer> storeLabelAddresses(Scanner sc){
		Map<String,Integer> labels = new HashMap<String,Integer>();
		int line = 0;
		while(sc.hasNextLine()){
			boolean hasLabel = false;
			String thisLine = sc.nextLine().trim();
			for (int i = 0; i < thisLine.length(); i++) {
				if (thisLine.charAt(i) == ':') {
					hasLabel = true;
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
	
	public static void generateBinary(Scanner sc, Map<String,Integer> labels, Map<String,String> registerLUT) {
		boolean quit = false;
		int currLine = 0;
		while(sc.hasNextLine() && !quit) {
			String thisLine = sc.nextLine().trim();
			//System.out.println(thisLine);
			String bits = "";
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
						//System.out.println("here");
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
						currLine++;
						String firstWord = thisLine.substring(h,i+1);
						String[] registers = thisLine.substring(i+1,thisLine.length()).split(",");
						for (int n=0; n<registers.length; n++) {
							registers[n] = registers[n].trim();
							if (n+1 == registers.length) {
								for (int m=0; m<registers[n].length(); m++) {
									if(registers[n].charAt(m) == '#') {
										registers[n] = registers[n].substring(0,m).trim();
									}
								}
							}
						}
						switch (firstWord){
							case("and"):
								bits += "000000 ";
								bits += registerLUT.get(registers[1]) + " ";
								bits += registerLUT.get(registers[2]) + " ";
								bits += registerLUT.get(registers[0]) + " ";
								bits += "00000 ";
								bits += "100100";
								break;
							case("or"):
								bits += "000000 ";
								bits += registerLUT.get(registers[1]) + " ";
								bits += registerLUT.get(registers[2]) + " ";
								bits += registerLUT.get(registers[0]) + " ";
								bits += "00000 ";
								bits += "100101";
								break; 
							case("add"):
								bits += "000000 ";
								bits += registerLUT.get(registers[1]) + " ";
								bits += registerLUT.get(registers[2]) + " ";
								bits += registerLUT.get(registers[0]) + " ";
								bits += "00000 ";
								bits += "100000";
								break;
							case("addi"):
								bits += "001000 ";
								bits += registerLUT.get(registers[1]) + " ";
								bits += registerLUT.get(registers[0]) + " ";
								bits += decimalToBinary(registers[2], 16);
								break;
							case("sll"):
								bits += "000000 ";
								bits += "00000 ";
								bits += registerLUT.get(registers[1]) + " ";
								bits += registerLUT.get(registers[0]) + " ";
								bits += decimalToBinary(registers[2], 5) + " ";
								bits += "000000";	
								break;
							case("sub"):
								bits += "000000 ";
								bits += registerLUT.get(registers[1]) + " ";
								bits += registerLUT.get(registers[2]) + " ";
								bits += registerLUT.get(registers[0]) + " ";
								bits += "00000 ";
								bits += "100010";
								break;
							case("slt"):
								bits += "000000 ";
								bits += registerLUT.get(registers[1]) + " ";
								bits += registerLUT.get(registers[2]) + " ";
								bits += registerLUT.get(registers[0]) + " ";
								bits += "00000 ";
								bits += "101010";
								break;
							case("beq"):
								bits += "000100 ";
								bits += registerLUT.get(registers[0]) + " ";
								bits += registerLUT.get(registers[1]) + " ";
								bits += getBranchOffset(labels.get(registers[2]), currLine, 16);
								break;
							case("bne"):
								bits += "000101 ";
								bits += registerLUT.get(registers[0]) + " ";
								bits += registerLUT.get(registers[1]) + " ";
								bits += getBranchOffset(labels.get(registers[2]), currLine, 16);
								break;
							case("lw"):
								bits += "100011 ";
								bits += registerLUT.get(registers[1].substring(2,5)) + " ";
								bits += registerLUT.get(registers[0]) + " ";
								bits += decimalToBinary(registers[1].substring(0,registers[1].indexOf('(')), 16);
								break;
							case("sw"):
								bits += "101011 ";
								bits += registerLUT.get(registers[1].substring(2,5)) + " ";
								bits += registerLUT.get(registers[0]) + " ";
								bits += decimalToBinary(registers[1].substring(0,registers[1].indexOf('(')), 16);
								break;
							case("j"):
								bits += "000010 ";
								bits += getBranchOffset(labels.get(registers[0]), 0, 26);
								break;
							case("jr"):
								bits += "000000 ";
								bits += registerLUT.get(registers[0]) + " ";
								bits += "000000000000000 001000";
								break;
							case("jal"):
								bits += "000011 ";
								bits += getBranchOffset(labels.get(registers[0]), 0, 26);
								break;
							default:
								bits += "invalid instruction: " + firstWord;
								quit = true;
						}
						System.out.println(bits);
					}
				}	
			}	
		}
	}
	
	public static String getBranchOffset(int labelLineNum, int currLineNum, int numBits) {
		int offset = labelLineNum-currLineNum;
		String binaryString = "";
		String binaryOffset = Integer.toBinaryString(offset);
		if (binaryOffset.length() > numBits) {
			binaryOffset = binaryOffset.substring(binaryOffset.length()-numBits, binaryOffset.length());
		} else {
			int numZeroes = numBits - binaryOffset.length();
			for (int i=0; i<numZeroes; i++) {
				binaryString += '0';
			}
		}
		binaryString += binaryOffset;
		return binaryString;
	}
	
	public static String decimalToBinary(String value, int numBits){
		String binaryString = "";
		String binaryRepresentation = Integer.toBinaryString(Integer.parseInt(value));
		if (binaryRepresentation.length()>numBits) {
			binaryRepresentation = binaryRepresentation.substring(binaryRepresentation.length()-numBits, binaryRepresentation.length());
		} else {
			int numZeroes = numBits - binaryRepresentation.length();
			for (int i=0; i<numZeroes; i++) {
				binaryString += '0';
			}
		}	
		binaryString += binaryRepresentation;
		return binaryString;
	}
	
	//TO DO: change LUT to map register name to contents
	public static Map<String, String> createLUT() {
		Map<String, String> registerLUT = new HashMap<String, String>();
		registerLUT.put("$0", "00000");
		registerLUT.put("$zero", "00000");
		registerLUT.put("$v0", "00010"); 
		registerLUT.put("$v1", "00011"); 
		registerLUT.put("$a0", "00100"); 
		registerLUT.put("$a1", "00101"); 
		registerLUT.put("$a2", "00110"); 
		registerLUT.put("$a3", "00111"); 
		registerLUT.put("$t0", "01000"); 
		registerLUT.put("$t1", "01001"); 
		registerLUT.put("$t2", "01010"); 
		registerLUT.put("$t3", "01011"); 
		registerLUT.put("$t4", "01100"); 
		registerLUT.put("$t5", "01101"); 
		registerLUT.put("$t6", "01110"); 
		registerLUT.put("$t7", "01111");
		registerLUT.put("$s0", "10000"); 
		registerLUT.put("$s1", "10001"); 
		registerLUT.put("$s2", "10010"); 
		registerLUT.put("$s3", "10011"); 
		registerLUT.put("$s4", "10100"); 
		registerLUT.put("$s5", "10101"); 
		registerLUT.put("$s6", "10110"); 
		registerLUT.put("$s7", "10111"); 
		registerLUT.put("$t8", "11000"); 
		registerLUT.put("$t9", "11001"); 
		registerLUT.put("$sp", "11101"); 
		registerLUT.put("$ra", "11111");
		return registerLUT;
	}
}





