package gui.statistic;

import game.objects.Player;

import java.awt.BorderLayout;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import module.ModuleHandler;

/**
 * Panel, das eine Tabelle mit der AllTimeStatistik anzeigt.
 * @author Alex
 *
 */
public class AllTimeStatisticTable extends JPanel {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7780365701546807669L;

	/**
	 * Konstruktor.
	 */
	public AllTimeStatisticTable() {
		Player.addStatisticsCurrentGameToStatisticsAllTimeForAllPlayer();
		// Titel der Tabelle
		Vector<String> statNames = new Vector<String>();
		
		ConcurrentHashMap<String, Integer> allPlayerStatistics = ModuleHandler.MATCH.getMe().getStatisticAllTime();
		
		for (Player player : Player.getAllPlayers()) {
			allPlayerStatistics.putAll(player.getStatisticAllTime());
		}
		
		for (String statName : allPlayerStatistics.keySet()) {
			statNames.add(statName);
		}

		// Die Reihen der Spieler
		Vector<Vector<String>> myTeam = new Vector<Vector<String>>();
		Vector<Vector<String>> otherTeam = new Vector<Vector<String>>();

		Vector<String> myTeamHeader = new Vector<String>();
		myTeamHeader.add(ModuleHandler.MATCH.getMe().getHero().getFraction()
				.toString());
		myTeam.add(myTeamHeader);

		Vector<String> otherTeamHeader = new Vector<String>();
		otherTeamHeader.add(ModuleHandler.MATCH.getMe().getHero().getFraction()
				.getEnemyTeam().toString());
		otherTeam.add(otherTeamHeader);

		for (Player player : Player.getAllPlayers()) {
			Vector<String> row = new Vector<String>();
			row.add(player.getName());

			for (String name : statNames) {
				row.add(player.getStatisticAllTime(name) + "");
			}

			if (player.getHero().getFraction() == ModuleHandler.MATCH
					.getMyHero().getFraction()) {
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
		
		statNames.add(0, "");

		// Die Tabelle
		JTable table = new JTable(data, statNames);
		table.setDefaultRenderer(Object.class, new Renderer());

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(120);

		this.setLayout(new BorderLayout());

		this.add(table.getTableHeader(), BorderLayout.PAGE_START);
		this.add(table, BorderLayout.CENTER);
	}
}
