package test.util;

public class BlackJackOracleUtil {

	public static boolean playerBusted(String area){
		int player_score = 0;
		String[] player_scores = area.split("\n")[1].substring(1).split(" ");
		for (int i=0; i<player_scores.length; i++) {
			player_score += BlackJackOracleUtil.fromCardToScore(player_scores[i]); 
		}
		return (player_score > 21);
	}
	
	public static boolean cpuWon(String area){
		int player_score = 0, cpu_score=0;
		
		String[] player_scores = area.split("\n")[1].substring(1).split(" ");
		String[] cpu_scores = area.split("\n")[4].substring(1).split(" ");
		for (int i=0; i<player_scores.length; i++) {
			player_score += BlackJackOracleUtil.fromCardToScore(player_scores[i]); 
		}
		for (int i=0; i<cpu_scores.length; i++) {
			cpu_score += BlackJackOracleUtil.fromCardToScore(cpu_scores[i]);
		}
		
		return (cpu_score<=21 && cpu_score>player_score);
	}
	
	public static boolean blackJack(String area){
		int player_score = 0;
		String[] player_scores = area.split("\n")[1].substring(1).split(" ");
		for (int i=0; i<2; i++) {
			player_score += BlackJackOracleUtil.fromCardToScore(player_scores[i]); 
		}
		return (player_score == 21);
	}
	
	public static int fromCardToScore(int card){
		int[] scores = {11,2,3,4,5,6,7,8,9,10,10,10,10};
		if (card>=1 && card<=13)
			return scores[card-1];
		else
			return 0;
	}
	
	public static int fromCardToScore(String card){
		int value = 0;
		
		switch (card.toLowerCase().charAt(0)) {
	       case 'a':
	    	   value = 11;
	    	   break;
	       case 'k': case 'q': case 'j':
	    	   value = 10;
	    	   break;

	       case '1': case '2': case '3':
	       case '4': case '5': case '6':
	       case '7': case '8': case '9':
	    	   value = Integer.parseInt(card);
	    	   break;
	       default:
	    	   break;
		}
		return value;
	}
}
