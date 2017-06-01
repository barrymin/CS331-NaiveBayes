package cs434_NaiveBayesClassifier;

import java.util.ArrayList;

public class Main {
	
	public static void main(String [] args){
		double accTrain, accTest=0;
		Preprocess p = new Preprocess();
		p.addFeatures("training_text.txt");
		ArrayList<ArrayList<Integer>> trainData = p.processFile("training_text.txt");
		ArrayList<ArrayList<Integer>> testData = p.processFile("test_text.txt");
		p.writePreprocessedData("preprocessed_train.txt",trainData);
		p.writePreprocessedData("preprocessed_test.txt",testData);
		NaiveBayesClassifier nbc = new NaiveBayesClassifier(trainData);//Learns a model based on training data		
		accTrain=nbc.predict(trainData);//Prediction for the training data
		accTest=nbc.predict(testData);// Prediction for the test data
		String msg = "Training data: 'training_text.txt'\nTest data:"
				+ " 'text_text.txt'\n\nClassifier on training data: "+100*accTrain+"% accuracy.\n"
						+ "Classifier on test data: "+100*accTest+"% accuracy.";
		FileHandler.writeFile("results.txt",msg);// Create and write in results.txt
		System.out.println(msg);
	}
	
}
