package blackjack;

import javax.swing.JFrame;

public class Black extends JFrame { // eredito i metodi e gli attributi della
									// classe JFrame

	private static final long serialVersionUID = 3781469417039574636L;
// CIAO belli!
	public Black() { // costruttore
		setTitle("BlackJack ??? ??? ??? ??? ");
		Apple tuna = new Apple();
		add(tuna);
		setBounds(100, 80, 500, 300);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
//HHHG6
	public static void main(String[] args) { // richiamo del costruttore
		new Black();

	}

}
