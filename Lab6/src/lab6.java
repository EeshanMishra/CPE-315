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
		while(sc.hasNextLine()) {
		//for (int i=0; i<1; i++) {
			thisLine = sc.nextLine();
			address = thisLine.substring(1,thisLine.length()).trim();
			address = hexToBinary(address);
			if (DMcache_2kB_1word.searchCache(address)) {
				cache1Hits++;
			}
			if (DMcache_2kB_2word.searchCache(address)) {
				cache2Hits++;
			}
			if (DMcache_2kB_4word.searchCache(address)) {
				cache3Hits++;
			}
			if (Acache_2kB_2way_1word.searchCache(address, numCacheAddresses)) {
				cache4Hits++;
			}
			if (Acache_2kB_4way_1word.searchCache(address, numCacheAddresses)) {
				cache5Hits++;
			}
			if (Acache_2kB_4way_4word.searchCache(address, numCacheAddresses)) {
				cache6Hits++;
			}
			if (DMcache_4kB_1word.searchCache(address)) {
				cache7Hits++;
			}
			numCacheAddresses++;
		}
		//System.out.println(numCacheAddresses);
		double cache1Percentage = (double)(cache1Hits*100)/numCacheAddresses;
		double cache2Percentage = (double)(cache2Hits*100)/numCacheAddresses;
		double cache3Percentage = (double)(cache3Hits*100)/numCacheAddresses;
		double cache4Percentage = (double)(cache4Hits*100)/numCacheAddresses;
		double cache5Percentage = (double)(cache5Hits*100)/numCacheAddresses;
		double cache6Percentage = (double)(cache6Hits*100)/numCacheAddresses;
		double cache7Percentage = (double)(cache7Hits*100)/numCacheAddresses;
		System.out.println("Cache #1\n" + 
						   "Cache size: 2048B\tAssociativity: 1\tBlock size: 1\n" +
						   "Hits: " + cache1Hits + "\tHit Rate: " + String.format("%.2f", cache1Percentage) + "%\n" +
						   "---------------------------");
		System.out.println("Cache #2\n" + 
				           "Cache size: 2048B\tAssociativity: 1\tBlock size: 2\n" +
				           "Hits: " + cache2Hits + "\tHit Rate: " + String.format("%.2f", cache2Percentage) + "%\n" +
				   		   "---------------------------");
		System.out.println("Cache #3\n" + 
				   		   "Cache size: 2048B\tAssociativity: 1\tBlock size: 4\n" +
				   		   "Hits: " + cache3Hits + "\tHit Rate: " + String.format("%.2f", cache3Percentage) + "%\n" +
				   		   "---------------------------");		
		System.out.println("Cache #4\n" + 
				   		   "Cache size: 2048B\tAssociativity: 2\tBlock size: 1\n" +
				   		   "Hits: " + cache4Hits + "\tHit Rate: " + String.format("%.2f", cache4Percentage) + "%\n" +
				   	       "---------------------------");		
		System.out.println("Cache #5\n" + 
         				   "Cache size: 2048B\tAssociativity: 4\tBlock size: 1\n" +
         				   "Hits: " + cache5Hits + "\tHit Rate: " + String.format("%.2f", cache5Percentage) + "%\n" +
				   		   "---------------------------");		
		System.out.println("Cache #6\n" + 
						   "Cache size: 2048B\tAssociativity: 4\tBlock size: 4\n" +
						   "Hits: " + cache6Hits + "\tHit Rate: " + String.format("%.2f", cache6Percentage) + "%\n" +
				           "---------------------------");		
		System.out.println("Cache #7\n" + 
				           "Cache size: 4096B\tAssociativity: 1\tBlock size: 1\n" +
				           "Hits: " + cache7Hits + "\tHit Rate: " + String.format("%.2f", cache7Percentage) + "%\n" +
				           "---------------------------");		
		System.out.println(cache2Hits);
		System.out.println(cache3Hits);
		System.out.println(cache4Hits);
		System.out.println(cache5Hits);
		System.out.println(cache6Hits);
		System.out.println(cache7Hits);
		sc.close();
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
