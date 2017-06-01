package cs434_NaiveBayesClassifier;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileHandler {
	
	public static void writeFile(String src, String message){
		PrintWriter pw = null;
		try{
			File file= new File(src);
			FileWriter fw = new FileWriter(file, true);
			pw= new PrintWriter(fw);
			pw.println(message);
		}catch(IOException e){
			e.printStackTrace();
		}finally {
			if(pw != null){
				pw.close();
			}
		}
	}
	
}
