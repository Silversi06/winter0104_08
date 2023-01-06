package jlabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RaceHorseFrame extends JFrame {

	JLabel[] horses = new JLabel[5];
	HorseThread[] hts = new HorseThread[horses.length];
	int[] winnerIndex = new int[horses.length];
	int index;
	String[] comboStr = {"1��: Shining White", "2��: Black and White", "3��: Scream Tiger", "4��: Blue Hipo", "5��: Kind Elephant"};
	JComboBox<String> combo = new JComboBox<String>(comboStr);
	BetingPerson bet1, bet2;
	
	public RaceHorseFrame() {		
		JPanel pan = new JPanel(null);
		ImageIcon icon = null;
		JLabel lineLbl = new JLabel(new ImageIcon("images/line.png"));
		lineLbl.setBounds(540, 27, 5, 420);
		JLabel flagLbl = new JLabel(new ImageIcon("images/flag.jpg"));
		flagLbl.setBounds(530, 5, 20, 27);
		pan.add(lineLbl);
		pan.add(flagLbl);
		
		JPanel panN = new JPanel();
		
		JButton btnBeting1 = new JButton("���ӹ���1");
		JButton btnBeting2 = new JButton("���ӹ���2");
		JButton btnStart = new JButton("���ӽ���");
		btnBeting1.addActionListener(btnL);
		btnBeting2.addActionListener(btnL);
		btnStart.addActionListener(btnL);
		panN.add(combo);
		panN.add(btnBeting1);
		panN.add(btnBeting2);
		panN.add(btnStart);
		
		for (int i = 0; i < horses.length; i++) {
			icon = new ImageIcon("images/horse"+(i+1)+".gif");
			horses[i] = new JLabel(icon);
			horses[i].setLocation(0, 50 + i*85);
			horses[i].setSize(60, 40);
			pan.add(horses[i]);
		}
		
		
		add(pan, "Center");
		add(panN, "North");
		setTitle("���ָ�����");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(50, 30, 620, 520);
		setVisible(true);
		setResizable(false);		
	}

	public static void main(String[] args) {
		new RaceHorseFrame();
	}
	
	ActionListener btnL = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "���ӹ���1":
				bet1 = new BetingPerson();
				bet1.setOrderNum(1);
				bet1.setName(JOptionPane.showInputDialog("�����ϴ� ����� �̸��� �Է��ϼ���"));
				bet1.setBetingIndex(combo.getSelectedIndex());
				break;
			case "���ӹ���2":
				bet2 = new BetingPerson();
				bet2.setOrderNum(2);
				bet2.setName(JOptionPane.showInputDialog("�����ϴ� ����� �̸��� �Է��ϼ���"));
				bet2.setBetingIndex(combo.getSelectedIndex());
				break;
			case "���ӽ���":
				for (int i = 0; i < horses.length; i++) {
					HorseThread t = new HorseThread(horses[i],"stop_horse"+(i+1), i);
					t.start();
				}
				break;
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
						JOptionPane.showMessageDialog(RaceHorseFrame.this, (winnerIndex[0]+1)+"���� ���!!!");
						if(winnerIndex[0]==bet1.getBetingIndex())
							JOptionPane.showMessageDialog(RaceHorseFrame.this, "�����մϴ�. "+bet1.getName()+"�� ���ÿ� �����Ͽ����ϴ�.");
						else if(winnerIndex[0]==bet2.getBetingIndex())
							JOptionPane.showMessageDialog(RaceHorseFrame.this, "�����մϴ�. "+bet2.getName()+"�� ���ÿ� �����Ͽ����ϴ�.");
						else
							JOptionPane.showMessageDialog(RaceHorseFrame.this, "������ �ٽ� ���� ��Ź�����~. ��� ���ÿ� �����Ͽ����ϴ�.");
						index = 0;
						for (int i = 0; i < horses.length; i++) {
//							System.out.println(winnerIndex[i]);
							horses[i].setLocation(0, horses[i].getY());
							horses[i].setIcon(new ImageIcon("images/horse"+(i+1)+".gif"));
						}
					}
					break;
				}
				try {
					Random random = new Random();
					randomValue = random.nextInt(10);
					sleep(20 * randomValue);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}			
		}
	}
}