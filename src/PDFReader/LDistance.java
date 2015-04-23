package PDFReader;

public class LDistance {
	//Dynamic Programming : To find the degree of difference b/w the two strings
	//Done using top-down DP implementation
	public static int levenDistance(String S1 , String S2 , int len1 , int len2 , int[][]array){
		if(len1 == 0){
			return len2 ;
		}
		else if (len2 == 0 ){
			return len1 ;
		}
		if(array[len1][len2] != -1){
			return array[len1][len2] ;
		}
		int cost ;
		if(S1.charAt(len1 - 1 ) == S2.charAt(len2 - 1)){
			cost = 0 ;
		}
		else{
			cost = 1 ;
		}
		int answer =  min ( levenDistance(S1, S2, len1 - 1 , len2, array) + 1,
					 levenDistance(S1, S2, len1, len2 - 1 , array) + 1 , 
					 levenDistance(S1, S2, len1 - 1 , len2 - 1 , array) + cost ) ;
		array[len1][len2] = answer ;
		return answer ;
	}
	
	public static int min(int a , int b , int c){
		return min (min(a,b) , c); 
	}
	public static int min (int a  , int b){
		if (a<b){
			return a ;
		}
		return b ;
	}
	
	public static int[][] makeArray(int len1 , int len2){
		int[][] array = new int[len1 + 1 ][len2 + 1] ;
		int i =0 , j = 0 ;
		for(i=0 ; i<len1+1 ; ++i){
			for(j=0 ; j<len2+1 ; ++j){
				array[i][j] = -1 ;
			}
		}
		return array ;
	}
}
