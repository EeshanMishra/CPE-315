
public class DirectMappedCache {
	int[] cache;
	int size; 
	int cacheSize;
	int blockSize;
	
	public DirectMappedCache(int size, int blockSize) {
		this.size = size;
		this.cacheSize = (size * 256);
		this.cache = new int[cacheSize];
		this.blockSize = blockSize;
	}
	
	public boolean searchCache(String address) {
		int wordOffsetMSB = 2;
		int indexMSB = 0;
		if (this.blockSize == 1) {
			wordOffsetMSB += 0;
		} else if (this.blockSize == 2) {
			wordOffsetMSB += 1;
		} else {
			wordOffsetMSB += 2;
		}
		if (this.size == 2) {		//2kB
			indexMSB = wordOffsetMSB + 9;
		} else if (this.size == 4) {
			indexMSB = wordOffsetMSB + 10;
		}
		int tag = binaryToDecimal(address.substring(0,32-indexMSB));
		int index = binaryToDecimal(address.substring(32-indexMSB,32-wordOffsetMSB));
		//index = Math.floorDiv(index,blockSize);
		//System.out.println("tag: " + address.substring(0,32-indexMSB) + " = " + tag);
		//System.out.println("index: " + address.substring(32-indexMSB,32-wordOffsetMSB) + " = " + index);
		if (this.cache[index] == tag) {
			return true;
		} 
		this.cache[index] = tag;
		for (int m=0; m<this.blockSize; m++) {
			this.cache[index - (index % this.blockSize) + m] = tag;
			//System.out.println(index - (index % blockSize) + m);
		}
		return false;
	}
	
	private int binaryToDecimal(String binVal) {
		int decVal = 0;
		for (int i=0; i<binVal.length(); i++) {
			if (binVal.charAt(i) == '1') {
				decVal += (int)Math.pow(2, binVal.length() - (1+i));
			}
		}
		return decVal;
	}
}
