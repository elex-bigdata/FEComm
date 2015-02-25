package com.elex.ssp;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class WordSeder {
	
	private static Analyzer analyzer;
	
	static{
		try {
			analyzer = new ElexAnalyzer();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	public static List<String> sed(String sent) throws IOException{
		StringReader sr = new StringReader(sent);
		String temp;
		List<String> result = new ArrayList<String>();
		TokenStream ts = analyzer.tokenStream("contents", sr);
		while (ts.incrementToken()) {
			temp = ts.getAttribute(CharTermAttribute.class).toString().trim();
			if (!temp.equals(" ")) {
				result.add(temp);
			}
		}
		return result;
		
	}
	
	

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		List<String> result = WordSeder.sed(args[0]);
		for(String b : result){
			System.out.println(b);
		}
		

	}

}
