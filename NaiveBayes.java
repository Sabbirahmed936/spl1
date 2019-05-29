package project;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JOptionPane;
import java.util.Scanner;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Collections;



public class NaiveBayes {
	int noOfFeatures = 0;
	int noOfLabels = 0;
	ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>(); 
	ArrayList<String> labels = new ArrayList<String>();
	HashMap<String,Label> grp = new HashMap<String,Label>();

	
	public NaiveBayes(File file) {
		readData(file);
	}
	
	private void readData(File file) {
		Scanner in = null;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		String[] temporaryArray;
		while (in.hasNextLine()) {
			line = in.nextLine();
			if (line.equals("@data"))
				break;
		} 
		while (in.hasNextLine()) {
			line = in.nextLine();

			if (line.equals("") || line.charAt(0) == '%')
				continue;
			temporaryArray=line.split(",");
			ArrayList<String> sample = new ArrayList<String>(Arrays.asList(temporaryArray));
			data.add(sample);
		}
	}
	
	public void train(ArrayList<ArrayList<String>> data) {
		noOfFeatures = data.get(0).size() - 1;
		System.out.println(noOfFeatures);
		for(ArrayList<String> sample:data) {
			labels.add(sample.get(noOfFeatures));
		}
		for(String c:labels) grp.put(c,new Label(c,noOfFeatures));
		for(ArrayList<String> sample:data) {
			Label g = grp.get(sample.get(noOfFeatures));
			g.addSample(sample);
		}
		for(String c:labels) grp.get(c).postProcess();
//		for(String c:labels) {
//			Label g = grp.get(c);
//			System.out.println(c+","+g.total);
//		}
	}
	
	public String predict(ArrayList<String> sample) {
		double mxP = -10000.0;
		String cls = null;
		for(String c:labels) {
			Label g = grp.get(c);
			double p = g.calcProb(sample);
			if(p>mxP) {
				mxP = p;
				cls = c;
			}
		}
		return cls;
	}
	
	public void test() {
		Collections.shuffle(data);
		int N = data.size();
		int trainSize = (int)(N*.8);
		int testSize = N -trainSize;
		ArrayList<ArrayList<String>> trainData = new ArrayList<ArrayList<String>>(); 
		ArrayList<ArrayList<String>> testData = new ArrayList<ArrayList<String>>(); 
		for(int i=0;i<trainSize;i++) trainData.add(data.get(i));
		for(int i=trainSize;i<N;i++) testData.add(data.get(i));
		train(trainData);
		double acc = 0;
		for(ArrayList<String>sample:testData) {
			String cls = predict(sample);
			String actual = sample.get(noOfFeatures);
			//System.out.println(cls+","+actual+","+sample);
			if(cls.equals(actual)) acc++;
		}
		System.out.println("Accuracy is:"+(acc/testSize));
	}
	public void run() {
		ArrayList<String> sample = new ArrayList<String>();
		Scanner in = new Scanner(System.in);
		for(int i=0;i<noOfFeatures;i++) {
			sample.add(in.nextLine());
		}
		System.out.println("Prediction:"+predict(sample));
	}
}
