package com.elex.ssp;

public class QueryUtils {
	
	 /** 
     * ASCII表中可见字符从!开始，偏移位值为33(Decimal) 
     */  
    static final char DBC_CHAR_START = 33; // 半角!  
  
    /** 
     * ASCII表中可见字符到~结束，偏移位值为126(Decimal) 
     */  
    static final char DBC_CHAR_END = 126; // 半角~  
  
    /** 
     * 全角对应于ASCII表的可见字符从！开始，偏移值为65281 
     */  
    static final char SBC_CHAR_START = 65281; // 全角！  
  
    /** 
     * 全角对应于ASCII表的可见字符到～结束，偏移值为65374 
     */  
    static final char SBC_CHAR_END = 65374; // 全角～  
  
    /** 
     * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移 
     */  
    static final int CONVERT_STEP = 65248; // 全角半角转换间隔  
  
    /** 
     * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理 
     */  
    static final char SBC_SPACE = 12288; // 全角空格 12288  
  
    /** 
     * 半角空格的值，在ASCII中为32(Decimal) 
     */  
    static final char DBC_SPACE = ' '; // 半角空格 

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String  a = "@throws NullPointerException, i,f <code>target</code> or　ｈｅｌｌｏ　        <code>replacement</code>；。，０　ｏｏｉｐ is <code>null</code>.";
		System.out.println(normalize(a));

	}
	
	public static String normalize(String query){
		if(query != null){
			if(!query.trim().equals("")){
				return qj2bj(query.trim().toLowerCase()).replaceAll("[\\pP|\\[|\\]|\\<|\\>|\\(|\\)|\\{|\\}|\\|\\/]", " ").replaceAll("[' ']+", " ");
			}else{
				return null;
			}
		}else{
			return null;
		}
				
		
	}
	
	 
	 public static String qj2bj(String src) {  
	        if (src == null) {  
	            return src;  
	        }  
	        StringBuilder buf = new StringBuilder(src.length());  
	        char[] ca = src.toCharArray();  
	        for (int i = 0; i < src.length(); i++) {  
	            if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内  
	                buf.append((char) (ca[i] - CONVERT_STEP));  
	            } else if (ca[i] == SBC_SPACE) { // 如果是全角空格  
	                buf.append(DBC_SPACE);  
	            } else { // 不处理全角空格，全角！到全角～区间外的字符  
	                buf.append(ca[i]);  
	            }  
	        }  
	        return buf.toString();  
	    } 
	 
	 public static int getQueryLength(String query,boolean isNorm){
		 if(isNorm){
			 return query.length();
		 }else{
			 return normalize(query).length();
		 }
		 
	 }
	 
	 public static int getQueryWordCount(String query,boolean isNorm){
		 if(isNorm){
			 return query.split(" ").length;
		 }else{
			 return normalize(query).split(" ").length;
		 }
	 }
	 
	 

}
