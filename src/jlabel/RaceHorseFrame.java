package jlabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RaceHorseFrame extends JFrame {

	JLabel[] horses = new JLabel[5];
	HorseThread[] hts = new HorseThread[horses.length];
	int[] winnerIndex = new int[horses.length];
	int index;
	
	
	public RaceHorseFrame() {		
		JPanel pan = new JPanel(null);
		ImageIcon icon = null;
		JLabel lineLbl = new JLabel(new ImageIcon("images/line.png"));
		lineLbl.setBounds(540, 27, 5, 500);
		JLabel flagLbl = new JLabel(new ImageIcon("images/flag.jpg"));
		flagLbl.setBounds(530, 5, 20, 27);
		pan.add(lineLbl);
		pan.add(flagLbl);
		
		JButton btnStart = new JButton("���ӽ���");
		btnStart.addActionListener(btnL);
		
		for (int i = 0; i < horses.length; i++) {
			icon = new ImageIcon("images/horse"+(i+1)+".gif");
			horses[i] = new JLabel(icon);
			horses[i].setLocation(0, 50 + i*85);
			horses[i].setSize(60, 40);
			pan.add(horses[i]);
		}
		
		
		add(pan, "Center");
		add(btnStart, "North");
		setTitle("���ָ�����");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(50, 30, 620, 500);
		setVisible(true);
		setResizable(false);		
	}

	public static void main(String[] args) {
		new RaceHorseFrame();
	}
	
	ActionListener btnL = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < horses.length; i++) {
				HorseThread t = new HorseThread(horses[i],"stop_horse"+(i+1), i);
				t.start();
			}
		}
	};

	public class HorseThread extends Thread{
		JLabel lblHorse;
		String stopImageName;
		int randomValue;
		int horseIndex;
		
		public HorseThread(JLabel lblHorse, String stopImageName, int horseIndex) {
			this.lblHorse = lblHorse;
			this.stopImageName = stopImageName;
			this.horseIndex = horseIndex;
		}

		@Override
		public void run() {
			while (true) {
				lblHorse.setLocation(lblHorse.getX()+5, lblHorse.getY());
				if(lblHorse.getX()==540) {
					lblHorse.setIcon(new ImageIcon("images/"+stopImageName+".gif"));
					winnerIndex[index++] = horseIndex;
					if(index == horses.length-1) {
						JOptionPane.showMessageDialog(RaceHorseFrame.this, "�����մϴ�. "+(winnerIndex[0]+1)+"���� ���!!!");
						index = 0;
						for (int i = 0; i < horses.length; i++) {
							horses[i].setLocation(0, horses[i].getY());
							horses[i].setIcon(new ImageIcon("images/small_horse"+(i+1)+".gif"));
						}
						
					}
//					lblHorse.setLocation(0, lblHorse.getY());
//					lblHorse.setIcon(new ImageIcon("images/"+moveImageName+".gif"));
					break;
				}
				try {
					Random random = new Random();
					randomValue = random.nextInt(10);
					sleep(10 * randomValue);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}			
		}
	}
}