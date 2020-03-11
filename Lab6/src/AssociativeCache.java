
public class AssociativeCache {
	int waySize, associativity, blockSize, size;
	int[][] ways, LRDarray;
	
	public AssociativeCache(int size, int associativity, int blockSize) {
		this.size = size;
		this.waySize = (size * 256)/(associativity*blockSize);
		this.associativity = associativity;
		this.blockSize = blockSize;
		this.LRDarray = new int[this.waySize][this.associativity];
		this.ways = new int[this.waySize][this.associativity];
	}
	
	public boolean searchCache(String address, int lineNum) {
		int offsetBits = 2;
		int indexSize = 0;
		if (this.blockSize == 2) {
			offsetBits += 1;				
			indexSize -= 1;
		} else if (this.blockSize == 4) {
			offsetBits += 2;
			indexSize -= 2;
		}
		if (this.associativity == 2) {
			indexSize -= 1;
		} else if (this.associativity == 4) {
			indexSize -= 2;
		}
		if (this.size == 2) {		//2kB
			indexSize += 9;
		} else if (this.size == 4) {	//4kB
			indexSize += 10;
		}
		int tagSize = 32 - indexSize - offsetBits; 
		int tag = binaryToDecimal(address.substring(0,tagSize));
		int index = binaryToDecimal(address.substring(tagSize,tagSize+indexSize));
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
				this.ways[index][i] = tag;
//				for (int m=0; m<this.blockSize; m++) {
//					this.ways[index - (index % this.blockSize) + m][i] = tag;
//				}
				return false;
			} else {
				if (this.LRDarray[index][i] < lowestVal) {
					lowestVal = this.LRDarray[index][i];
					lowestIndex = i;
				}
			}
		}
		this.ways[index][lowestIndex] = tag;
//		for (int m=0; m<this.blockSize; m++) {
//			this.ways[index - (index % this.blockSize) + m][lowestIndex] = tag;
//		}
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
