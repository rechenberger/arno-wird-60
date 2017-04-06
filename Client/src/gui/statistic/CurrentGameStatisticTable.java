package gui.statistic;

import game.objects.Player;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import module.ModuleHandler;

/**
 * Panel, dass die Statistik des Spiels in einer JTable in einem JScrollPane darstellt.
 * @author Alex
 *
 */
public class CurrentGameStatisticTable extends JPanel {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4031219179827103620L;
	
	/**
	 * Konstruktor.
	 */
	public CurrentGameStatisticTable() {
		//Titel der Tabelle
		Vector<String> statNames = new Vector<String>();
		statNames.add("");
		statNames.add("Held");
		statNames.add("Level");
		for (String name : Player.getAllStatisticNames()) {
			statNames.add(name);
		}
		
		//Die Reihen der Spieler
		Vector<Vector<String>> myTeam = new Vector<Vector<String>>();
		Vector<Vector<String>> otherTeam = new Vector<Vector<String>>();
		
		Vector<String> myTeamHeader = new Vector<String>();
		myTeamHeader.add(ModuleHandler.MATCH.getMe().getHero().getFraction().toString());
		myTeam.add(myTeamHeader);
		
		Vector<String> otherTeamHeader = new Vector<String>();
		otherTeamHeader.add(ModuleHandler.MATCH.getMe().getHero().getFraction().getEnemyTeam().toString());
		otherTeam.add(otherTeamHeader);
		
		
		for (Player player : Player.getAllPlayers()) {
			Vector<String> row = new Vector<String>();
			row.add(player.getName());
			row.add(player.getHero().getName());
			row.add(player.getHero().getLevel() + "");
			
			for (String name : Player.getAllStatisticNames()) {
				row.add(player.getStatistic(name) + "");
			}
			
			if (player.getHero().getFraction() == ModuleHandler.MATCH.getMyHero()
					.getFraction()) {
				myTeam.add(row);
			} else {
				otherTeam.add(row);
			}
			
		}

		Vector<Vector<String>> data = new Vector<Vector<String>>();
		if (myTeam.size() > 1) {
			data.addAll(myTeam);
		}
		if (otherTeam.size() > 1) {
			data.addAll(otherTeam);
		}
		
		
		//Die Tabelle
		JTable table = new JTable(data, statNames) {

			/**
			 * serialVersionUID.
			 */
			private static final long serialVersionUID = 1324541702448194696L;

			public boolean isCellEditable(final int x, final int y) {
				return false;
			}
		};
        table.setDefaultRenderer(Object.class, new Renderer());
		
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(120);
		columnModel.getColumn(1).setPreferredWidth(150);
				
		this.setLayout(new BorderLayout());
		
		this.add(table.getTableHeader(), BorderLayout.PAGE_START);
		this.add(table, BorderLayout.CENTER);
	}
}
