package test.util;

public class BjOracle {

	public static final String winString = "Win!";
	public static final String bjWinString = "21 Vittoria Grande Baldoria!";
	public static final String bustedString = "Busted!";
	public static final String bjString = "BlackJack!";
	public static final String loseString = "Lose!";
	
	public static boolean blackJack(String area){
		return (BjOracle.playerScore(area) == 21);
	}
	
	public static int cpuGlobalScore(String area) {
		int cpu_score = 0;
		if (!area.isEmpty()){
			String cpu_score_s = (area.split("\n")[2]).split(" ")[1];
			cpu_score=Integer.parseInt(cpu_score_s);
		}
		return cpu_score;
	}
	
	public static int cpuScore(String area){
		int cpu_score=0;
		String[] area_splitted = area.split("\n");
		if (!(area_splitted.length<4)) {
			String[] cpu_scores = area.split("\n")[4].substring(1).split(" ");
			for (int i=0; i<cpu_scores.length; i++) {
				cpu_score += BjOracle.fromCardToScore(cpu_scores[i]);
			}
		}
		return cpu_score;
	}
	
	public static boolean cpuWins(String area){
		int player_score = BjOracle.playerScore(area);
		int cpu_score=BjOracle.cpuScore(area);
		
		return (cpu_score<=21 && cpu_score>=player_score);
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
	
	public static String fromCartToSymbol(int card){
		String[] symbols = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
		if (card>=1 && card<=13)
			return symbols[card-1];
		else
			return null;
	}
	
	public static boolean playerBusted(String area){
		int player_score = BjOracle.playerScore(area);
		return (player_score > 21);
	}
	
	public static int playerGlobalScore(String area) {
		int player_score = 0;
		if (!area.isEmpty()){
			String player_score_s = (area.split("\n")[0]).split(" ")[1];
			player_score=Integer.parseInt(player_score_s);
		}
		return player_score;
	}

	public static int playerScore(String area){
		int player_score = 0;
		String[] player_scores = area.split("\n")[1].substring(1).split(" ");
		for (int i=0; i<player_scores.length; i++) {
			player_score += BjOracle.fromCardToScore(player_scores[i]); 
		}
		return player_score;
	}

	/**
	 * @param cards cards da impostare
	 */
	public static double fromCardToDouble(int card) {
		return (double) (card - 1) / 12;
	}

}
