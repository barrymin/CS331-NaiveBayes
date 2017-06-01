package cs434_NaiveBayesClassifier;

import java.util.ArrayList;
import java.util.Collections;

public class NaiveBayesClassifier {
	private ArrayList<ArrayList<Integer>> matrix;
	private ArrayList<ArrayList<Double>>probConditionalClass0;
	private ArrayList<ArrayList<Double>>probConditionalClass1;
	private double probClass0;
	private double probClass1;
		
	public NaiveBayesClassifier(ArrayList<ArrayList<Integer>> matrix){
		this.matrix = matrix;			
		probConditionalClass0= new ArrayList<ArrayList<Double>>();
		probConditionalClass0.add(calcPriors(0,0));
		probConditionalClass0.add(calcPriors(1,0));
		probConditionalClass1= new ArrayList<ArrayList<Double>>();
		probConditionalClass1.add(calcPriors(0,1));
		probConditionalClass1.add(calcPriors(1,1));
		this.probClass0 = calcProbClassLabel(0);
		this.probClass1 = calcProbClassLabel(1);		
		
	}
	
	private double calcProbClassLabel(int classLabel){
		int nExamples = matrix.size();
		int nAttr = matrix.get(0).size();		
		ArrayList<Integer> indexes=new ArrayList<Integer>();
		
		for(int i=0;i<nExamples;i++){
			if(matrix.get(i).get(nAttr-1).equals(new Integer(classLabel))){
				indexes.add(i);				
			}
		}
		 
		return indexes.size()/(double)nExamples;
		
	}
	
	private ArrayList<Double> calcPriors(int value, int classLabel){
		int nExamples = matrix.size();
		int nAttr = matrix.get(0).size();
		int count = 0;
		ArrayList<Double> priors = new ArrayList<Double>(Collections.nCopies(nAttr-1, 1.0));
		
		for(int i=0;i<nExamples;i++){
			if(matrix.get(i).get(nAttr-1).equals(new Integer(classLabel))){
				count++;
				for(int j=0;j<(nAttr-2);j++){
					if(matrix.get(i).get(j).equals(new Integer(value))){
						priors.set(j, priors.get(j)+1);
					}								
				}
			}
		}
		for(int j=0;j<(nAttr-2);j++){
			priors.set(j, (priors.get(j))/(count+2));
			
		}
		return priors;
	}
	
	private int predictRow(ArrayList<Integer> row){
		//Class 0
		double sum=0;
		double value0;
		double value1;
		for(int j=0;j<row.size();j++){ //each example
			if(row.get(j).equals(new Integer(0))){
				sum = sum+Math.log(probConditionalClass0.get(0).get(j));
			}else sum = sum+Math.log(probConditionalClass0.get(1).get(j));
			
		}
		value0 = Math.log(probClass0)+sum;
		//Class 1
		sum=1;
		for(int j=0;j<row.size();j++){
			if(row.get(j).equals(new Integer(1))){
				sum = sum+Math.log(probConditionalClass1.get(1).get(j));
			}else sum = sum+Math.log(probConditionalClass1.get(0).get(j));
			
		}
		value1=Math.log(probClass1)+sum;
		
		return value0>value1?0:1;
	}
	
	public double predict(ArrayList<ArrayList<Integer>> matrix){
		int nExamples = matrix.size();
		int nAttr = matrix.get(0).size();
		double accuracy=0;
		int pred=0;
		ArrayList<Integer> aux = new ArrayList<Integer>();
		
		for(int i=0;i<nExamples;i++){
			aux = new ArrayList<Integer>(matrix.get(i));
			aux.remove(nAttr-1);
			pred = predictRow(aux);
			if(matrix.get(i).get(nAttr-1).equals(new Integer(pred))){
				accuracy++;			
			}
		}
		accuracy = Math.round((accuracy/(double)nExamples)*100.0)/100.0;//round to 2 decimal places
		return accuracy;
	}
}
