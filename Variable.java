package project;

import java.util.ArrayList;

public class Variable {
	public static int numberOfDecisions;
	public static int [] decisionSet;
	public static int[][] dataSet;
	public int numberOfAttributes;
	public int columnNumber;
	public ArrayList [] dataRows;
	public String variableName;
	public Attribute [] attributes;
	public ArrayList <Integer> availableRows;
	public double avgChildEntropy = 0;
	public double entireEntropy;
	public double informationGain;
	
	public Variable (int column, String name, int numberOfAttributes, String[] attributeList, ArrayList <Integer> availableRows) {
		this.columnNumber = column;
		this.variableName = name;
		this.numberOfAttributes = numberOfAttributes;
		this.availableRows = availableRows;
		
		this.dataRows = new ArrayList [numberOfAttributes];
		this.attributes = new Attribute [numberOfAttributes];
		makeAttributeList (attributeList);
		calculateChildEntropy();
		calculateEntropyAndInformationGain();
	}

	private void calculateChildEntropy() {
		int totalData = availableRows.size();
		for (int i=0; i<numberOfAttributes; i++) {
			this.avgChildEntropy += ((double)dataRows[i].size() / totalData) * attributes[i].entropy;
		}
	}

	private void makeAttributeList (String[] attributeList) {
		dataForAttributeList();
		for (int i=0; i<numberOfAttributes; i++) {
			attributes[i] = new Attribute(dataRows[i], attributeList[i]);
		}
	}

	private void dataForAttributeList() {
		for (int i=0; i<numberOfAttributes; i++) {
			dataRows[i] = new ArrayList <Integer> ();
		}
		
		for (int i=0; i<availableRows.size(); i++) {
			int dataIndex = availableRows.get(i);
			int att = dataSet[dataIndex][columnNumber];
			dataRows[att].add(dataIndex);
		}
	}
	
	private void calculateEntropyAndInformationGain() {
		int [] numberOfCollection = new int [numberOfDecisions]; //i.e. how many win or lose contains
		int totalData = availableRows.size();
		double probablity;
		int index;
		
		for (int i=0; i<availableRows.size(); i++) {
			index = availableRows.get(i);
			numberOfCollection[decisionSet[index]]++;
		}
		for (int i=0; i<numberOfDecisions; i++) {
			probablity = (double)numberOfCollection[i]/totalData;
			if (probablity == 0.00) this.entireEntropy += 0;
			else {
				this.entireEntropy -= probablity * (Math.log(probablity) / Math.log(numberOfDecisions));
			}
		}
		
		this.informationGain = this.entireEntropy-this.avgChildEntropy;
	}
}
