package project;

import java.util.ArrayList;
import java.util.HashMap;


public class Label {
	HashMap<Integer,HashMap<String,Double>> probs = new HashMap<Integer,HashMap<String,Double>>();
	String cls;
	int total = 0;
	int noOfFeatures;
	public Label(String cls,int noOfFeatures) {
		this.cls = cls;
		this.noOfFeatures = noOfFeatures;
		for(int i=0;i<noOfFeatures;i++) probs.put(i,new HashMap<String,Double>());
	}
	public void addSample(ArrayList<String> sample) {
		this.total+=1;
		for(int i=0;i<sample.size()-1;i++) {
			HashMap<String,Double> feat= probs.get(i);
			String val = sample.get(i);
			if (!feat.containsKey(val)) {
				feat.put(val,0.0);
			}
			feat.put(val,feat.get(val)+1.0);
		}
	}
	public void postProcess() {
		for(int f:probs.keySet()) {
			HashMap<String,Double> feat= probs.get(f);
			for(String v:feat.keySet()) {
				feat.put(v,feat.get(v)/this.total);
			}
		}
	}
	public double calcProb(ArrayList<String> sample) {
		double pr = 1.0;
		for(int f=0;f<noOfFeatures;f++) {
			String val = sample.get(f);
			HashMap<String,Double> feat= probs.get(f);
			if(feat.containsKey(val)) pr = pr*feat.get(val);
			else return 0.0;
		}
		return pr;
	}

	
	
}
