package cs434_NaiveBayesClassifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Preprocess {
	
	private ArrayList<String> features;
	
	public Preprocess(){
		features = new ArrayList<String>();
	}
	/*
	 * Adds every word contained in the given file to the features set
	 */
	public void addFeatures(String fileName){
		//read line from file
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       // process the line
		    	ArrayList<String> words = stripPunctuation(line);
		    	//add each word from line to features set
		    	for(int i=0; i<words.size()-1;i++){	//last word is class label
		    		if(!features.contains(words.get(i))){
		    			features.add(words.get(i));
		    		}
		    	}		    	
		    }
		    //sort features set
		    Collections.sort(features);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * processes each line of text in the input file
	 */
	public ArrayList<ArrayList<Integer>> processFile(String inputFile){
		
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		//read line
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       // process the line
		    	ArrayList<String> words = stripPunctuation(line);
		    	if(words.size()!=0){
		    		//initialize arraylist of examples
			    	list.add(new ArrayList<Integer>());
			    	for(int i=0; i<=features.size();i++){	//last word is class label
			    		if(i == features.size()){
			    			//add last element in words (which is class label) to last
			    			//example added to list
			    			list.get(list.size()-1).add(Integer.parseInt(words.get(words.size()-1)));
			    		}else{
			    			//check if read example contains ith feature
			    			if(words.contains(features.get(i))){
			    				list.get(list.size()-1).add(1);
			    			}else{
			    				list.get(list.size()-1).add(0);
			    			}
			    		}
			    	}	
		    	}
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	/*
	 * writes the processed data to file
	 */
	public void writePreprocessedData(String src,ArrayList<ArrayList<Integer>> data){
		PrintWriter pw = null;
		try{
			File file= new File(src);
			FileWriter fw = new FileWriter(file, true);
			pw= new PrintWriter(fw);
			for(int i=-1; i<data.size();i++){
				for(int j=0; j<= features.size();j++){
					if(i==-1){
						//writes header
						if(j==features.size()){
							pw.print("ClassLabel");
						}else{
							pw.print(features.get(j)+",");
						}
					}else{
						pw.print(data.get(i).get(j)+",");
					}
				}
				pw.print("\n");
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			if(pw != null){
				pw.close();
			}
		}
	}
	/*
	 * strips punctuation from given string and returns an array containing each 
	 * word from the given string
	 */
	private ArrayList<String> stripPunctuation(String line){
		ArrayList<String> words = new ArrayList<String>();
		String word="";
		for(int i=0; i< line.length();i++){
			if(validChar(line.charAt(i))){
				word+=line.charAt(i);
			}else if(line.charAt(i)!='\'' && word.length()!=0){
				//anything other than ' indicates the word ended
				words.add(word.toLowerCase());
				word="";
			}
		}	
		return words;
	}
	
	private boolean validChar(char c){
		//Valid chars are any capital or small letter or a number
		if((c <= 'Z' && c >='A')|| (c <='z' && c >= 'a') || (c<= '9' && c>='0')){
			return true;
		}else{
			return false;
		}
	}
	
	public ArrayList<String> getFeatures(){
		return features;
	}
	

}
