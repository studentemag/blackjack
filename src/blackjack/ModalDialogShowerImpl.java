package blackjack;

import javax.swing.JOptionPane;

public class ModalDialogShowerImpl implements ModalDialogShower{

	@Override
	public void showErrorMessage() {
		JOptionPane.showMessageDialog(null, "push start!", "",
				JOptionPane.ERROR_MESSAGE);
		
	}
	

}
