package blackjack;
import javax.swing.JFrame;


public class black extends JFrame{ //eredito i metodi e gli attributi della classe JFrame
	
	private static final long serialVersionUID = 3781469417039574636L;

	public black() { //costruttore
		setTitle("BlackJack ??? ??? ??? ??? ");
		apple tuna = new apple();
		add(tuna);
		setBounds(100, 80, 500, 300);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	public static void main(String[] args) { //richiamo del costruttore 
		new black();
		
		
	}
	
	
}
