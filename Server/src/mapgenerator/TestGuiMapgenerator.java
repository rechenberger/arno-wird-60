package mapgenerator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

//import mapgenerator.TestFrame.MyPanel;
//CHECKSTYLE:OFF
/**
 * Klasse zum teten.
 * @author Marius
 *
 */
public class TestGuiMapgenerator extends JFrame implements MouseListener, MouseWheelListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 308224964371822068L;
	/**
	 * 
	 */
	private MyPanel p;
	/**
	 * 
	 */
	private static int sizeOfField = 3;
	
	/**
	 * 
	 * @author Marius
	 *
	 */
	class MyPanel extends JPanel {
		
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -3590259798984649668L;

		/**
		 * 
		 */
		MyPanel() {
			
		}

		/**
		 * @param g Das Graphics object
		 */
		public void paintComponent(final Graphics g) {
			//super.paintComponent(g);

			//cache = new BufferedImage(getWidth(), getHeight(), this);
			//ArrayList<Point> route = this.area.getContent();
			int x = 0;
			int y = 0;
			for (int j = 0; j < area[0].length; j++) {
				for (int i = 0; i < area.length; i++) {
					if (area[i][j] == -1) {
						Color tmp = g.getColor();
						g.setColor(Color.BLUE);
						//g.drawLine(x, y, x, y);
						g.fillRect(x, y, sizeOfField, sizeOfField);
						g.setColor(tmp);
					} else if (area[i][j] == 4) {
						Color tmp = g.getColor();
						g.setColor(Color.cyan);
						g.fillRect(x, y, sizeOfField, sizeOfField);
						//g.drawLine(x, y, x, y);
						g.setColor(tmp);					
					} else if (area[i][j] == 1) {
						Color tmp = g.getColor();
						g.setColor(Color.LIGHT_GRAY);
						//g.drawLine(x, y, x, y);
						g.fillRect(x, y, sizeOfField, sizeOfField);
						g.setColor(tmp);					
					} else if (area[i][j] == 0) {
						Color tmp = g.getColor();
						g.setColor(Color.GREEN);
						//g.drawLine(x, y, x, y);
						g.fillRect(x, y, sizeOfField, sizeOfField);
						g.setColor(tmp);					
					}
					else if (area[i][j] == 5) {
						Color tmp = g.getColor();
						g.setColor(Color.BLACK);
						//g.drawLine(x, y, x, y);
						g.fillRect(x, y, sizeOfField, sizeOfField);
						g.setColor(tmp);					
					}
					else if (area[i][j] == 6) {
						Color tmp = g.getColor();
						g.setColor(Color.RED);
						//g.drawLine(x, y, x, y);
						g.fillRect(x, y, sizeOfField, sizeOfField);
						g.setColor(tmp);					
					}
					else if (area[i][j] == 10) {
						Color tmp = g.getColor();
						g.setColor(Color.YELLOW);
						//g.drawLine(x, y, x, y);
						g.fillRect(x, y, sizeOfField, sizeOfField);
						g.setColor(tmp);					
					}
					else if (area[i][j] == 11) {
						Color tmp = g.getColor();
						g.setColor(Color.RED);
						//g.drawLine(x, y, x, y);
						g.fillRect(x, y, sizeOfField, sizeOfField);
						g.setColor(tmp);					
					}
					else if (area[i][j] == 12) {
						Color tmp = g.getColor();
						g.setColor(Color.BLACK);
						//g.drawLine(x, y, x, y);
						g.fillRect(x, y, sizeOfField, sizeOfField);
						g.setColor(tmp);					
					}
					else if (area[i][j] == 13) {
						Color tmp = g.getColor();
						g.setColor(Color.BLUE);
						//g.drawLine(x, y, x, y);
						g.fillRect(x, y, sizeOfField, sizeOfField);
						g.setColor(tmp);					
					}
					else if (area[i][j] == 50) {
						Color tmp = g.getColor();
						g.setColor(Color.ORANGE);
						//g.drawLine(x, y, x, y);
						g.fillRect(x, y, sizeOfField, sizeOfField);
						g.setColor(tmp);					
					}
					else if (area[i][j] == 51) {
						Color tmp = g.getColor();
						g.setColor(Color.WHITE);
						//g.drawLine(x, y, x, y);
						g.fillRect(x, y, sizeOfField, sizeOfField);
						g.setColor(tmp);					
					}
					x += sizeOfField;
					
				}
				x = 0;
				y += sizeOfField;
			}
			
		}
		
		
	}
	
	/**
	 * 
	 */
	public int[][] area;
	
	/**
	 * 
	 * @param name
	 * @param area
	 */
	public TestGuiMapgenerator(final String name, final int[][] area) {
		super(name);
		
		MyPanel panel=new MyPanel(); 
		p = panel;
		panel.setPreferredSize(new Dimension(area.length,area[0].length));
		panel.setIgnoreRepaint(true);
		JScrollPane scrollBar = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
		panel.setAutoscrolls(true);
		scrollBar.setPreferredSize(new Dimension( 800,300));
		add(scrollBar);
		setSize(new Dimension(1200,900));
		setVisible(true);
		
		panel.addMouseListener(this);
		//panel.addMouseWheelListener(this);
		
		
		//super.setResizable(false);
		super.addWindowListener(new WindowAdapter() {
			public void windowClosing(final WindowEvent we) {
				System.exit(0);
			}
		});
		this.area = area;
	}

	@Override
	public void mouseClicked(final MouseEvent arg0) {
		System.out.println(" X " + arg0.getPoint().x + " / Y " + arg0.getPoint().y);
		System.out.println(p.getComponentAt(arg0.getPoint()).getGraphics().getColor().toString());		
	}

	@Override
	public void mouseEntered(final MouseEvent arg0) {
		//System.out.println(" X " + arg0.getPoint().x + " / Y " + arg0.getPoint().y);
		
	}

	@Override
	public void mouseExited(final MouseEvent arg0) {
		//System.out.println(" X " + arg0.getPoint().x + " / Y " + arg0.getPoint().y);
		
	}

	@Override
	public void mousePressed(final MouseEvent arg0) {
		//System.out.println(" X " + arg0.getPoint().x + " / Y " + arg0.getPoint().y);
		
	}

	@Override
	public void mouseReleased(final MouseEvent arg0) {
		//System.out.println(" X " + arg0.getPoint().x + " / Y " + arg0.getPoint().y);
		
	}

	@Override
	public void mouseWheelMoved(final MouseWheelEvent arg0) {
	if (arg0.getWheelRotation() < 0) {
		if (sizeOfField <= 10) {
			sizeOfField++;
		}
	} else {

		if (sizeOfField >= 2) {
			sizeOfField--;
		}
	}
		
		
	}

}