package MusicTimer;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class MainPane2 extends JFrame implements ActionListener {
	JPanel cardPanel;
	CardLayout layout;
	JButton c_btn1;
	JButton c_btn2;
	JButton btn1;
	JButton btn2;
	JTextArea area;
	JTextField length_timer;
	TimerThread2 timethread;

	static final int m_size = 15;

	private boolean threadjudge = true;
	private boolean firstconst = false;

	MainPane2(int l) {
		JPanel p1 = new JPanel();
		SpringLayout layout1 = new SpringLayout();
		p1.setLayout(layout1);

		this.c_btn1 = new JButton("To Console");
		this.c_btn1.addActionListener(this);
		this.c_btn1.setActionCommand("btn_s");
		c_btn1.setFont(new Font("Arial", Font.PLAIN, m_size));
		layout1.putConstraint(SpringLayout.NORTH, c_btn1, (int) (3 * l / 4), SpringLayout.NORTH, p1);
		layout1.putConstraint(SpringLayout.SOUTH, c_btn1, 0, SpringLayout.SOUTH, p1);
		layout1.putConstraint(SpringLayout.WEST, c_btn1, 0, SpringLayout.WEST, p1);
		layout1.putConstraint(SpringLayout.EAST, c_btn1, 0, SpringLayout.EAST, p1);
		p1.add(this.c_btn1);

		this.btn1 = new JButton("Start");
		this.btn1.addActionListener(this);
		this.btn1.setActionCommand("set_up");
		btn1.setFont(new Font("Arial", Font.PLAIN, m_size));
		layout1.putConstraint(SpringLayout.NORTH, btn1, (int) (l / 2), SpringLayout.NORTH, p1);
		layout1.putConstraint(SpringLayout.SOUTH, btn1, -(int) (l / 8), SpringLayout.NORTH, c_btn1);
		layout1.putConstraint(SpringLayout.WEST, btn1, (int) (5 * l / 8), SpringLayout.WEST, p1);
		layout1.putConstraint(SpringLayout.EAST, btn1, 0, SpringLayout.EAST, p1);
		p1.add(this.btn1);

		this.btn2 = new JButton("Stop");
		this.btn2.addActionListener(this);
		this.btn2.setActionCommand("stop");
		this.btn2.setEnabled(false);
		btn2.setFont(new Font("Arial", Font.PLAIN, m_size));
		layout1.putConstraint(SpringLayout.NORTH, btn2, (int) (l / 2), SpringLayout.NORTH, p1);
		layout1.putConstraint(SpringLayout.SOUTH, btn2, -(int) (l / 8), SpringLayout.NORTH, c_btn1);
		layout1.putConstraint(SpringLayout.WEST, btn2, 0, SpringLayout.WEST, p1);
		layout1.putConstraint(SpringLayout.EAST, btn2, -(int) (5 * l / 8), SpringLayout.EAST, p1);
		p1.add(this.btn2);

		this.length_timer = new JTextField(String.valueOf(30), 3);
		this.length_timer.setHorizontalAlignment(4);
		length_timer.setFont(new Font("Arial", Font.PLAIN, m_size));
		layout1.putConstraint(SpringLayout.NORTH, length_timer, (int) (l / 8), SpringLayout.NORTH, p1);
		layout1.putConstraint(SpringLayout.WEST, length_timer, (int) (l / 4), SpringLayout.WEST, p1);
		p1.add(this.length_timer);

		JLabel length_timer_lab = new JLabel("minutes");
		length_timer_lab.setFont(new Font("Arial", Font.PLAIN, m_size));
		layout1.putConstraint(SpringLayout.NORTH, length_timer_lab, (int) (l / 8), SpringLayout.NORTH, p1);
		layout1.putConstraint(SpringLayout.WEST, length_timer_lab, 0, SpringLayout.EAST, length_timer);
		p1.add(length_timer_lab);

		JPanel p2 = new JPanel();
		SpringLayout layout2 = new SpringLayout();
		p2.setLayout(layout2);

		this.c_btn2 = new JButton("To Set up");
		this.c_btn2.addActionListener(this);
		this.c_btn2.setActionCommand("btn_c");
		c_btn2.setFont(new Font("Arial", Font.PLAIN, m_size));
		layout2.putConstraint(SpringLayout.NORTH, c_btn2, (int) (3 * l / 4), SpringLayout.NORTH, p2);
		layout2.putConstraint(SpringLayout.SOUTH, c_btn2, 0, SpringLayout.SOUTH, p2);
		layout2.putConstraint(SpringLayout.WEST, c_btn2, 0, SpringLayout.WEST, p2);
		layout2.putConstraint(SpringLayout.EAST, c_btn2, 0, SpringLayout.EAST, p2);
		p2.add(this.c_btn2);

		this.area = new JTextArea();
		this.area.setForeground(Color.WHITE);
		this.area.setBackground(Color.BLACK);
		this.area.setEnabled(false);
		area.setFont(new Font("Utf-8", Font.PLAIN, m_size));
		JScrollPane scrollpane = new JScrollPane(this.area);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		layout2.putConstraint(SpringLayout.NORTH, scrollpane, 0, SpringLayout.NORTH, p2);
		layout2.putConstraint(SpringLayout.SOUTH, scrollpane, 0, SpringLayout.NORTH, c_btn2);
		layout2.putConstraint(SpringLayout.WEST, scrollpane, 0, SpringLayout.WEST, p2);
		layout2.putConstraint(SpringLayout.EAST, scrollpane, 0, SpringLayout.EAST, p2);
		p2.add(scrollpane);

		this.cardPanel = new JPanel();
		this.layout = new CardLayout();
		this.cardPanel.setLayout(this.layout);
		this.cardPanel.add(p1, "Set_up");
		this.cardPanel.add(p2, "Console");

		getContentPane().add(this.cardPanel, "Center");
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals("btn_s")) {
			this.layout.last(this.cardPanel);
		} else if (cmd.equals("btn_c")) {
			this.layout.first(this.cardPanel);
		} else if (cmd.equals("set_up")) {
			try {
				int length_timer = Integer.valueOf(this.length_timer.getText()).intValue();

				if (firstconst) {
					if (this.timethread.getEndflag()) {
						threadjudge = true;
					}
				}
				if (length_timer > 0) {
					this.firstconst = true;
					this.btn1.setEnabled(false);
					this.btn2.setEnabled(true);
					if (threadjudge) {
						this.timethread = new TimerThread2(length_timer, this.area, this.btn1, this.btn2);
						threadjudge = false;
						timethread.start();
					} else {
						timethread.Restart();
					}
				} else {
					this.area.append("Error : Timer must be over 0. \n");
				}
				this.layout.last(this.cardPanel);
			} catch (Exception ex) {
				this.area.append("Error : " + ex + " \n");
				this.layout.last(this.cardPanel);
			}
		} else if (cmd.equals("stop")) {
			timethread.StopMusic();
			this.btn2.setEnabled(false);
			this.btn1.setEnabled(true);
		}
	}
}