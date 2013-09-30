package net.bommy.kalaha.spieler;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class SpielerDialog extends JDialog {
	private Spieler[] sp = new Spieler[2];
	
	private JTextField sp1name = new JTextField();
	private JTextField sp2name = new JTextField();
	private JToggleButton sp1tgl[] = new JToggleButton[2];
	private JToggleButton sp2tgl[] = new JToggleButton[2];
	private JButton btnOk = new JButton();
	
	private boolean loop = true;
	
	public SpielerDialog(Frame parent) {
		super(parent, "Spielerauswahl", true);
		sp[0] = new Spieler("Spieler1", false);
		sp[1] = new Spieler("Spieler2", true);
	}
	
	private void baueDialog() {
		//JToggleButton Spieler 1
		sp1tgl[0] = new JToggleButton("Mensch");
			sp1tgl[0].setSelected( true );
		sp1tgl[1] = new JToggleButton("Computer");
			sp1tgl[1].setSelected( false );
		//JToggleButton Spieler 2
		sp2tgl[0] = new JToggleButton("Mensch");
			sp2tgl[0].setSelected( false );
		sp2tgl[1] = new JToggleButton("Computer");
			sp2tgl[1].setSelected( true );
		//Textfelder
		sp1name.setText( sp[0].getName() );
		sp2name.setText( sp[1].getName() );
		
		//Buttongroups
		ButtonGroup btng1 = new ButtonGroup();
			btng1.add(sp1tgl[0]);
			btng1.add(sp1tgl[1]);
		ButtonGroup btng2 = new ButtonGroup();
			btng2.add(sp2tgl[0]);
			btng2.add(sp2tgl[1]);
		
		//Spieler 1 Panel
		JPanel span1 = new JPanel();
			span1.setBorder( BorderFactory.createTitledBorder("Spieler 1") );
			span1.add(sp1name);
			span1.add(sp1tgl[0]);
			span1.add(sp1tgl[1]);
		//Spieler 2 Panel
		JPanel span2 = new JPanel();
			span2.setBorder( BorderFactory.createTitledBorder("Spieler 2") );
			span2.add(sp2name);
			span2.add(sp2tgl[0]);
			span2.add(sp2tgl[1]);
		
		JPanel main = new JPanel();
			main.setLayout(new GridLayout(2,1));
			main.add(span1);
			main.add(span2);
		
		//OK-Button Panel
		JPanel panok = new JPanel();
			btnOk.setText("OK");
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible( false );
				}
			});
			panok.add(btnOk);
		
			
		this.setLayout(new BorderLayout());
		this.add(main, BorderLayout.CENTER);
		this.add(panok, BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);
	}
	
	public Spieler[] createSpieler() {
		baueDialog();
		while( this.isVisible() ) {}
		sp[0].setName( sp1name.getText() );
		sp[0].setKI( sp1tgl[1].isSelected() );
		sp[1].setName( sp2name.getText() );
		sp[1].setKI( sp2tgl[1].isSelected() );
		return sp;
	}
}
