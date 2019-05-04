package com.baidu.translate.demo;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	/**
	 * @param args
	 */
	private static final String APP_ID="20190322000280155";
	private static final String Se_key="rwUVprA2TcnYF5GdbU64";
	static String temp=null;
	String InPut;
	public String putOut(){
		return temp;
	}
	public void inPut(String input) throws UnsupportedEncodingException{
		InPut = input;
		System.out.println(input);
		char first = input.charAt(0);
		if((first>='a'&&first<='z')||(first>='A'&&first<='Z')){
			Trans();
		}
		else{
			Trans2();
		}
		putOut();
	}
	//英文转中文
	private void Trans() throws UnsupportedEncodingException{
		TransApi api = new TransApi(APP_ID,Se_key);
		String query=InPut;
		//对百度翻译获得文档拆分
		api.getTransResult(query, "auto", "zh");
		//System.out.println(api.getTransResult(query, "auto", "zh"));
		//对此获取的网页的信息进行获取
		StringTokenizer token = new StringTokenizer(api.getTransResult(query, "auto", "zh"),"\"}]}");
		while(token.hasMoreTokens()){
			temp=token.nextToken();
		}
		temp=unicodeToString(temp);
		//putOut();
	}
	//中文转英文
	private void Trans2() throws UnsupportedEncodingException{
		TransApi api = new TransApi(APP_ID,Se_key);
		String query=InPut;
		//对百度翻译获得文档拆分
		api.getTransResult(query, "auto", "en");
		String str =api.getTransResult(query, "auto", "en");
		//System.out.println(api.getTransResult(query, "auto", "en"));
		//对此获取的网页的信息进行获取
		StringTokenizer token = new StringTokenizer(api.getTransResult(query, "auto", "en"),"\"}]}");
		while(token.hasMoreTokens()){
			temp=token.nextToken();
		}
		int fin = str.lastIndexOf("\"");
		int fir = str.lastIndexOf("\"", fin-1);
		temp=str.substring(fir+1, fin);
		//temp=unicodeToString(temp);
		//putOut();
	}
	public static String unicodeToString(String unicode) {
		StringBuffer sb = new StringBuffer();
		String[] hex = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) {
			int index = Integer.parseInt(hex[i], 16);
			sb.append((char) index);
		}
		return sb.toString();
	}/*
	public  String gbEncoding(String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }*/
	/* public  String decodeUnicode( String dataStr) {
	        int start = 0;
	        int end = 0;
	        final StringBuffer buffer = new StringBuffer();
	        while (start > -1) {
	            end = dataStr.indexOf("\\u", start + 2);
	            String charStr = "";
	            if (end == -1) {
	                charStr = dataStr.substring(start + 2, dataStr.length());
	            } else {
	                charStr = dataStr.substring(start + 2, end);
	            }
	            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
	            buffer.append(new Character(letter).toString());
	            start = end;
	        }
	        return buffer.toString();
	    }*/
	public void trant(String str){
		try {
			inPut(str);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String str = sc.next();
		Main m = new Main();
		m.inPut(str);
	}*/

}
