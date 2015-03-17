package com.elex.ssp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;



public class ElexAnalyzer extends Analyzer{

	private volatile boolean initialized = false;
	private StopAnalyzer analyzer;
	
	public ElexAnalyzer() throws IOException {
		super();
		
		//StopAnalyzer加载自定义停用词表
		Set<String> stop = new HashSet<String>();
		//不可以在一个analyzer里加载所有语种的停用词，因为对于欧洲语系而言，某种语言的某个停用词在另一种语言里却是有用的。
		String[] stopFiles = new String[]{"arabic.txt","catalan.txt","danish.txt",
				"dutch.txt","english.txt","french.txt","german.txt","hungarian.txt",
				"italian.txt","norwegian.txt","portuguese.txt","romanian.txt",
				"russian.txt","spanish.txt","swedish.txt","swedish.txt"};
		
		//String[] stopFiles = new String[]{"arabic.txt","english.txt","portuguese.txt","russian.txt",};
		BufferedReader reader = null;
		for (String file : stopFiles) {
			reader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("profile/"+file),"UTF-8"));
			String stopWord = "";
			while ((stopWord = reader.readLine()) != null) {
				stop.add(stopWord.trim().toLowerCase());			
			}
		}
		
		reader.close();		
		analyzer = new StopAnalyzer(Version.LUCENE_36, stop);
				
		//全部初始化完成
		initialized = true;
	}

	@Override
	public void close() {
		super.close();
	}

	
	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		if(!initialized)
			return null;
		List<String> tokens;
		try {
			tokens = tokenizeReader(reader);
			return new ElexTokenizer(tokens);
		} catch (IOException e) {
			System.out.println("com.elex.dmp.common.ElexAnalyzer.tokenStream() failed! because IOException");
		}
		return null;
		
	}
	
	/**
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public List<String> tokenizeReader(Reader reader) throws IOException {
	    List<String> result = new ArrayList<String>(1000);
	    TokenStream ts = analyzer.tokenStream(null, reader);
		CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
		while(ts.incrementToken()){
			if(!term.toString().matches("["+term.toString().substring(0, 1)+"]*") && term.toString().length()>2){
				result.add(term.toString());
			}
			
		}
	    return result;
	  }
	

}
