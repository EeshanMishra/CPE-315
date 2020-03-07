
public class DirectMappedCache {
	int[] cache;
	
	public DirectMappedCache(int size, int blockSize) {
		int cacheSize = size * 1024;
		cache = new int[cacheSize];
	}
	
	public boolean searchCache(String address) {
		
		
		
		return false;
	}
	
	private int binaryToDecimal(String binVal) {
		int decVal = 0;
		for (int i=0; i<binVal.length(); i++) {
			if (binVal.charAt(i) == '1') {
				decVal += (int)Math.pow(2, i);
			}
		}
		return decVal;
	}
	
	
}
