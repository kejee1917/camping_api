package kr.co.gocamping.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class exam {

	public static void main(String[] args) {
		
		String input = "covid2019";
		
		int num = 0;
		int str = 0;
		
		List<String> numlist = new ArrayList<String>();
		List<String> strlist = new ArrayList<String>();
		List<String> output = new ArrayList<String>();
		
		for(int i=0; i<input.length(); i++) {
			//0~9 라면
			if( 48<=input.charAt(i) && input.charAt(i)<=57 ) {
				num++;
				numlist.add(input.charAt(i) + "");
			}else {
				str++;
				strlist.add(input.charAt(i) + "");
			}
		}
		
		
		int temp = input.length()/2;
		//System.out.println(numlist.toString() + "   " + strlist.toString() );
		
		if(-1<=num-str && num-str <= 1) {
			for(int i=0; i<temp; i++) {
				if(num >= str) {
					output.add(numlist.get(i));
					output.add(strlist.get(i));	
				}else{
					output.add(strlist.get(i));	
					output.add(numlist.get(i));
				}
			}
			
			if(num > str) {
				output.add(numlist.get(temp));
			}else if(num < str){
				output.add(strlist.get(temp));	
			}
		}
 		
		System.out.println(output.toString());
		
	}
}
