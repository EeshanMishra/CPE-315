public class BranchPredictor {
	int[] predictionArray, GHR;
	int numPredictions, numCorrectPredictions;
	
	public BranchPredictor(int GHRsize) {
		numPredictions = 0;
		numCorrectPredictions = 0;		
		predictionArray = new int[(int)Math.pow(2, GHRsize)];
		GHR = new int[GHRsize];
	}
	
	public boolean getPrediction() {
		int GHRindex = getPredictionArrayIndex(this.GHR);
		if (this.predictionArray[GHRindex] <= 1) {
			return false;
		} else {
			return true;
		}
	}
	
	public void updatePrediction(boolean branchTaken) {		
		int GHRindex = getPredictionArrayIndex(this.GHR);
		if (branchTaken){
			if(this.predictionArray[GHRindex] < 3) {
				this.predictionArray[GHRindex]++;
			}
		} else {
			if(this.predictionArray[GHRindex] > 0) {
				this.predictionArray[GHRindex]--;
			}
		}
		for (int i=this.GHR.length-1; i>0; i--){
			this.GHR[i] = this.GHR[i-1];
		}
		if (branchTaken) {
			this.GHR[0] = 1;
		} else {
			this.GHR[0] = 0;
		}
		return;
	}
	
	public static int getPredictionArrayIndex(int[] GHR) {
		int index = 0;
		for (int i=0; i<GHR.length; i++) {
			if (GHR[i] == 1) {
				index += (int)Math.pow(2,i);
			}
		}
		return index;
	}
}


