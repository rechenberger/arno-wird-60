package gui.statistic;

import gui.Colors;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel, das nach dem Spiel die Statistik anzeigt.
 * @author Alex
 *
 */
public class AfterGameStatistic extends JPanel {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2609575746912481422L;
	
	/**
	 * Konstruktor.
	 */
	public AfterGameStatistic() {
		JLabel label3 = new JLabel("Die Statistiken des Spiels");
		label3.setAlignmentX(CENTER_ALIGNMENT);
		label3.setFont(getFont().deriveFont(32f));
		label3.setForeground(Colors.BLACK);	
		label3.setBackground(Colors.WHITE);

		JLabel label2 = new JLabel("Die Statistiken aller Spiele");
		label2.setAlignmentX(CENTER_ALIGNMENT);
		label2.setFont(getFont().deriveFont(32f));
		label2.setForeground(Colors.BLACK);
		label2.setBackground(Colors.WHITE);
		
		this.setOpaque(true);	
		
		this.add(label3);
		this.add(new CurrentGameStatisticTable());
		this.add(label2);
		this.add(new AllTimeStatisticTable());
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
}
