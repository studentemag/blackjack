package blackjack;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import test.util.BjOracle;

public class Apple extends JPanel { // la classe apple eredita i metodi e gli
									// attributi della classe JPanel

	public class Ascoltatore implements ActionListener { // eredito il metodo
															// ActionPerformed

		public void actionPerformed(ActionEvent e) {
			String tmp = e.getActionCommand();

			if (tmp == "start") {
				field.setText("");
				area.setText("Player\n");
				player = 1 + (int) (Math.random() * 12) + 1;
				player = control(player);
				x = (int) (Math.random() * 12) + 1;
				x = control(x);
				player += x;
				if (player == 21)
					field.setText("21 Vittoria Grande Baldoria!");
				out = false;
			}

			if (tmp == "hit") {
				if (out != true) {
					x = (int) (Math.random() * 12) + 1;
					x = control(x);
					player += x;
					if (player > 20) {
						field.setText("Busted!");
						out = true;
						state_cpu += 1;
						area_score.setText("Player: " + state_player
								+ "\n\nCpu: " + state_cpu);
					} else if (player == 21)
						field.setText("BlackJack!");
				} else
					JOptionPane.showMessageDialog(null, "push start!", "",
							JOptionPane.ERROR_MESSAGE);
			}
			if (tmp == "stand") {
				if (out != true) {
					out = true;
					area.setText(area.getText() + "\n\nCpu\n");
					cpu();
				} else
					JOptionPane.showMessageDialog(null, "push start!", "",
							JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private static final long serialVersionUID = -4708058535472595126L;
	private JButton b[] = { new JButton("hit"), new JButton("stand"),
			new JButton("start") };
	private JTextArea area, area_score;

	private JTextField field;

	private int player;

	private int x;
	private int cpu;
	private int state_player;
	private int state_cpu;
	private boolean out = true;
	public Apple() { // costruttore campo da gioco
		area = new JTextArea();
		field = new JTextField();
		area_score = new JTextArea();

		area.setName("area");
		field.setName("field");
		area_score.setName("area_score");

		add(field);
		add(area_score);
		add(area);
		setLayout(null);
		setBackground(Color.BLACK);
		setBounds(100, 80, 520, 300);

		for (int i = 0, y = 0; i < 3; i++, y += 20) {
			b[i].addActionListener(new Ascoltatore());
			b[i].setBounds(20, 20 + y, 100, 20);
			add(b[i]);
		}

		area.setBounds(140, 20, 325, 200);
		area.setFont(new Font("Sanserif", Font.ITALIC, 25));
		area.setBackground(Color.LIGHT_GRAY);
		area.setEditable(false);
		field.setEditable(false);
		field.setBounds(140, 228, 325, 20);
		field.setFont(new Font("Sanserif", Font.BOLD, 15));
		field.setBackground(Color.LIGHT_GRAY);
		area_score.setBackground(Color.GREEN);
		area_score.setForeground(Color.BLACK);
		area_score.setFont(new Font("Sanserif", Font.BOLD, 23));
		area_score.setBounds(20, 120, 95, 90);
		area_score.setEditable(false);
	}

	public int control(int p) { // metodo di controllo per stabilire se il
								// valore generato ? una figura, in tal caso
								// ritono il relativo valore

		if (p >= 2 && p <= 10) {
			area.setText(area.getText() + " " + p);
			return p;
		}
		if (p == 1) {
			area.setText(area.getText() + " A");
			return 11;
		} else {
			if (p == 13)
				area.setText(area.getText() + " K");
			else if (p == 12)
				area.setText(area.getText() + " Q");
			else if (p == 11)
				area.setText(area.getText() + " Q");
			return 10;
		}

	}

	public void cpu() { // metodo cpu simula la giocata da parte della cpu
						// tramite un semplice while
		cpu = (int) (Math.random() * 12) + 1;
		cpu = control(cpu);
		x = (int) (Math.random() * 12) + 1;
		x = control(x);
		cpu += x;
		while (cpu < player) { // tramite questa istruzione il programma non fa
								// altro ke generare un numero rand finche la
								// somma dei valori usciti non supera quella
								// della giocatore
			x = (int) (Math.random() * 12) + 1;
			cpu += control(x);
		}
		if (cpu > 21) {
			field.setText("Win!");
			state_player += 1;
			area_score.setText("Player: " + state_player + "\n\nCpu: "
					+ state_cpu);
		} else {
			field.setText("Lose!");
			state_cpu += 1;
			area_score.setText("Player: " + state_player + "\n\nCpu: "
					+ state_cpu);
		}
		cpu = 0;

	}

	public void cpu_TESTABLE(int player_setted, int state_player_setted, int state_cpu_setted) { // metodo cpu simula la giocata da parte della cpu
		// tramite un semplice while
		cpu = (int) (BjOracle.randomDouble() * 12) + 1;
		cpu = BjOracle.fromCardToScore(cpu);
		x = (int) (BjOracle.randomDouble() * 12) + 1;
		x = BjOracle.fromCardToScore(x);
		cpu += x;
		while (cpu < player_setted) { // tramite questa istruzione il programma non fa
								// altro ke generare un numero rand finche la
								// somma dei valori usciti non supera quella
								// della giocatore
			x = (int) (BjOracle.randomDouble() * 12) + 1;
			cpu += BjOracle.fromCardToScore(x);
		}
		if (cpu > 21) {
			field.setText("Win!");
			state_player_setted += 1;
			area_score.setText("Player: " + state_player_setted + "\n\nCpu: "
					+ state_cpu_setted);
		} else {
			field.setText("Lose!");
			state_cpu_setted += 1;
			area_score.setText("Player: " + state_player_setted + "\n\nCpu: "
					+ state_cpu_setted);
		}
		cpu = 0;
	
	}

	public JTextArea getArea() {
		return area;
	}

	public void setArea(JTextArea area) {
		this.area = area;
	}
}