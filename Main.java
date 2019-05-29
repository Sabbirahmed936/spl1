package project;

import java.io.File;

import javax.swing.JOptionPane;

public class Main {
	public static void main( String[] args ) {
		String fileName = JOptionPane.showInputDialog("Enter File Name/Path:");
		File file = new File(fileName);
		while (!file.exists()) {
			JOptionPane.showMessageDialog(null, "File doesn't exist.");
			fileName = JOptionPane.showInputDialog("Enter file name:");
			file = new File(fileName);
		}
		String algo = JOptionPane.showInputDialog("Enter Algorithm DT/NB:");
		while (!(algo.equals("DT")||algo.equals("NB"))) {
			JOptionPane.showMessageDialog(null, "Algorithm doesn't exist.");
			algo = JOptionPane.showInputDialog("Enter Algorithm DT/NB:");
		}
		if(algo.equals("DT")) {
			Tree myTree = new Tree (new Data(fileName));
			new SelectionPage(myTree, fileName);
		}
		else {
			NaiveBayes nb = new NaiveBayes(file);
			nb.test();
			nb.run();
		}
		
	}
	
	
}
