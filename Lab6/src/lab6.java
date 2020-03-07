import java.io.*;
import java.util.*;


public class lab6 {
	public static void main(String[] args) throws IOException{
		//initialize variables for statistic keeping
		int numCacheAddresses = 0;
		int cache1Hits = 0;
		int cache2Hits = 0;
		int cache3Hits = 0;
		int cache4Hits = 0;
		int cache5Hits = 0;
		int cache6Hits = 0;
		int cache7Hits = 0;
		
		//instantiate the 7 caches
		DirectMappedCache DMcache_2kB_1word = new DirectMappedCache(2, 1);
		DirectMappedCache DMcache_2kB_2word = new DirectMappedCache(2, 2);
		DirectMappedCache DMcache_2kB_4word = new DirectMappedCache(2, 4);
		AssociativeCache Acache_2kB_2way_1word = new AssociativeCache(2, 2, 1);
		AssociativeCache Acache_2kB_4way_1word = new AssociativeCache(2, 4, 1);
		AssociativeCache Acache_2kB_4way_4word = new AssociativeCache(2, 4, 4);
		DirectMappedCache DMcache_4kB_1word = new DirectMappedCache(4, 1);
		
		//logic
		File inputFile = new File(args[0]);
		Scanner sc = new Scanner(inputFile);
		String thisLine, address;
		//while(sc.hasNextLine()) {
			thisLine = sc.nextLine();
			address = thisLine.substring(1,thisLine.length()).trim();
			System.out.println(hexToBinary(address));
		//}
		
	}	
	
	
	private static String hexToBinary(String hexValue) {
		String binValue = "";
		char hexElement;
		for (int i=0; i<8; i++) {
			hexElement = hexValue.charAt(i);
			switch (hexElement) {
				case ('0'):
					binValue += "0000";
					break;
				case ('1'):
					binValue += "0001";
					break;
				case ('2'):
					binValue += "0010";
					break;
				case ('3'):
					binValue += "0011";
					break;
				case ('4'):
					binValue += "0100";
					break;
				case ('5'):
					binValue += "0101";
					break;
				case ('6'):
					binValue += "0110";
					break;
				case ('7'):
					binValue += "0111";
					break;
				case ('8'):
					binValue += "1000";
					break;
				case ('9'):
					binValue += "1001";
					break;
				case ('a'):
					binValue += "1010";
					break;
				case ('b'):
					binValue += "1011";
					break;
				case ('c'):
					binValue += "1100";
					break;
				case ('d'):
					binValue += "1101";
					break;
				case ('e'):
					binValue += "1110";
					break;
				case ('f'):
					binValue += "1111";
					break;
			}
		}

		return binValue;
	}
	
}
