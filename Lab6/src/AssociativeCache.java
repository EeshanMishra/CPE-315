
public class AssociativeCache {
	int waySize, associativity, blockSize, size;
	int[][] ways, LRDarray;
	
	public AssociativeCache(int size, int associativity, int blockSize) {
		this.size = size;
		this.waySize = (size * 256)/associativity;
		this.associativity = associativity;
		this.blockSize = blockSize;
		this.LRDarray = new int[this.waySize][this.associativity];
		this.ways = new int[this.waySize][this.associativity];
	}
	
	public boolean searchCache(String address, int lineNum) {
		int wordOffsetMSB = 2;
		int indexMSB = 0;
		if (this.blockSize == 1) {
			wordOffsetMSB += 0;
		} else if (this.blockSize == 2) {
			wordOffsetMSB += 1;
		} else {
			wordOffsetMSB += 2;
		}
		indexMSB = wordOffsetMSB + 9 - (this.associativity/2);
		int tag = binaryToDecimal(address.substring(0,32-indexMSB));
		int index = binaryToDecimal(address.substring(32-indexMSB,32-wordOffsetMSB));
		index = index % this.waySize;
		for (int i=0; i<this.associativity; i++) {
			if (this.ways[index][i] == tag) {
				LRDarray[index][i] = lineNum;
				return true;
			}	
		}
		int lowestVal = 5000000;
		int lowestIndex = 0;
		for (int i=0; i<this.associativity; i++) {
			if (this.LRDarray[index][i] == 0) {
				LRDarray[index][i] = lineNum;
				for (int m=0; m<this.blockSize; m++) {
					this.ways[index - (index % this.blockSize) + m][i] = tag;
				}
				return false;
			} else {
				if (this.LRDarray[index][i] < lowestVal) {
					lowestVal = this.LRDarray[index][i];
					lowestIndex = i;
				}
			}
		}
		for (int m=0; m<this.blockSize; m++) {
			this.ways[index - (index % this.blockSize) + m][lowestIndex] = tag;
		}
		this.LRDarray[index][lowestIndex] = lineNum;
		return false;
	}
	
	
	private int binaryToDecimal(String binVal) {
		//System.out.println(binVal);
		int decVal = 0;
		for (int i=0; i<binVal.length(); i++) {
			if (binVal.charAt(i) == '1') {
				decVal += (int)Math.pow(2, binVal.length() - (1+i));
			}
		}
		return decVal;
	}
}
