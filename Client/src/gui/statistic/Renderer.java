package gui.statistic;

import gui.Colors;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import module.ModuleHandler;

/**
 * Renderer legt das Layout der Tabellen fest.
 * @author Alex
 *
 */
public class Renderer extends DefaultTableCellRenderer {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -9201746226077008401L;
	
	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object obj,
			final boolean isSelected, final boolean hasFocus, final int row, final int column) {
		Component c = super.getTableCellRendererComponent(table, obj,
				isSelected, hasFocus, row, column);
		
		table.getValueAt(row, column);
		
		if (table.getValueAt(row, 0).equals(ModuleHandler.MATCH.getMe().getHero().getFraction()
				.toString()) || table.getValueAt(row, 0).equals(ModuleHandler.MATCH.getMe().getHero()
						.getFraction().getEnemyTeam().toString())) {
			c.setBackground(Colors.ORANGE);
			c.setForeground(Colors.WHITE);
		} else if (table.getValueAt(row, 0).equals(ModuleHandler.MATCH.getMe().getName())) {
			c.setBackground(Colors.GREY8.darker());
			c.setForeground(Colors.BLACK);
		} else {
			c.setBackground(Colors.GREY8);
			c.setForeground(Colors.BLACK);
		}

		return c;
	}
}
