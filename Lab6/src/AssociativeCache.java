
public class AssociativeCache {
	int waySize;
	int[] way1, way2, way3, way4;
	
	public AssociativeCache(int size, int associativity, int blockSize) {
		if (associativity == 2) {
			waySize = (size * 1024)/2;
			way1 = new int[waySize];
			way2 = new int[waySize];
		} else {
			waySize = (size * 1024)/4;
			way1 = new int[waySize];
			way2 = new int[waySize];
			way3 = new int[waySize];
			way4 = new int[waySize];
		}
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
