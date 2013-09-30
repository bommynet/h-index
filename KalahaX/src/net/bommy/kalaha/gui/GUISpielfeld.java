package net.bommy.kalaha.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.bommy.kalaha.ctrl.SpielController;
import net.bommy.kalaha.feld.Spielfeld;

public class GUISpielfeld extends JFrame implements Observer {
	private final Color FARBESPIELER1 = new Color(255,145,145);
	private final Color FARBESPIELER2 = new Color(145,145,255);
	private final Color FARBEINAKTIV  = new Color(145,145,145);
	
	private SpielController ctrl;
	private JButton btn[] = new JButton[Spielfeld.FELDER];
	private JLabel lbl;
	
//### KONSTRUKTOR #########################################
	public GUISpielfeld(SpielController ctrl) {
		//zustaendigen SpielController festhalten
		this.ctrl = ctrl;
		//jeden Spieler initialisieren
		for(int i=0; i<Spielfeld.FELDER; i++) {
			int id = i % Spielfeld.PROSPIELER;
			btn[i] = (id == Spielfeld.PROSPIELER-1) ? createButtonKalaha(i) : createButtonKuhle(i);
			btn[i].setBackground( getSpielerFarbe((int)(i/Spielfeld.PROSPIELER)) );
		}
		
		//alle Buttons einfuegen
		this.positioniereElemente();
		//Fenster einrichten und anzeigen
		this.erstelleFenster();
	}
	
//### FUNKTIONEN: GUI aufbauen ############################
	/**
	 * 
	 */
	private void erstelleFenster() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //Programm beenden wenn Fenster zu
		this.pack(); //alles auf minimale Groesse setzen
		this.setVisible(true); //Fenster anzeigen
	}
	
	/**
	 * 
	 */
	private void positioniereElemente() {
		JPanel pan1 = new JPanel(); //Spielfeld: gesamt
		JPanel panMitte = new JPanel(); //Spielfeld: nur die Kuhlen
		
		//Kuhlen positionieren
		panMitte.setLayout(new GridLayout(2,6));
		for(int i=0; i<Spielfeld.FELDER; i++) {
			//Kalahas ueberspringen
			if(i==(Spielfeld.FELDER-1) || i==(Spielfeld.PROSPIELER-1)) continue;
			
			if(i < Spielfeld.PROSPIELER) { //Spieler 1: Felder umgekehrt positionieren
				panMitte.add( btn[(Spielfeld.PROSPIELER-2)-i] );
			} else {
				panMitte.add( btn[i] );
			}
		}
		
		//Spielfeld als BorderLayout
		pan1.setLayout(new BorderLayout());
		//Kalaha Spieler 1 ganz lanch links
		pan1.add(btn[6], BorderLayout.WEST);
		//Kalaha Spieler 2 ganz lanch rechts
		pan1.add(btn[13], BorderLayout.EAST);
		//Kuhlen einfuegen
		pan1.add( panMitte, BorderLayout.CENTER );
		//Spielinfos einfuegen
		pan1.add( erstelleInfoPanel(), BorderLayout.SOUTH );
		
		//Panel in Fenster einfuegen
		this.add( pan1 );
	}
	
//### FUNKTIONEN: GUI Aktionen ############################
	public void aktiverSpieler(int spieler) {
		//Infotext anzeigen
		lbl.setText("Spieler ["+ (spieler+1) +"] am Zug!");
		
		for(int i=(Spielfeld.PROSPIELER*spieler); i<(Spielfeld.PROSPIELER*spieler)+(Spielfeld.PROSPIELER-1); i++) {
			btn[i].setBackground( getSpielerFarbe(spieler) );
		}
		
		int nspieler = 1 - spieler;
		for(int i=(Spielfeld.PROSPIELER*nspieler); i<(Spielfeld.PROSPIELER*nspieler)+(Spielfeld.PROSPIELER-1); i++) {
			btn[i].setBackground( getSpielerFarbe(-1) );
		}
	}
	
	/**
	 * @param gewinner
	 * @return 0=Noch einmal | 1=Neues Spiel | sonst=Beenden
	 */
	public int showSpielVorbei(int gewinner) {
		String txt = "Das Spiel ging unentschieden aus!";
		if( gewinner==0 ) txt = "Spieler 1 hat gewonnen!";
		else if( gewinner==1 ) txt = "Spieler 2 hat gewonnen!";
		txt += " (" + btn[Spielfeld.PROSPIELER-1].getText() + ":" + btn[Spielfeld.FELDER-1].getText() + ")";
		txt += "\n\nNoch ein Spiel?";
		
		Object[] options = {"Noch einmal", "Neues Spiel", "Beenden"};
		int n = JOptionPane.showOptionDialog(this,
				txt,
				"Spiel vorbei",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
		return n;
	}
	
	private Color getSpielerFarbe(int spieler) {
		if(spieler == 0) return FARBESPIELER1;
		if(spieler == 1) return FARBESPIELER2;
		return FARBEINAKTIV;
	}
	
//### FUNKTIONEN: createButton():JButton ##################
	/**
	 * @param id
	 * @return
	 */
	private JButton createButtonKalaha(int id) {
		JButton tmp = new JButton("0"); //eine Kalaha enthält erstmal keine Bohnen
		//Anfangsgroesse der Buttons einstellen
		tmp.setPreferredSize(new Dimension(100,200));
		tmp.setMinimumSize(tmp.getPreferredSize());
		//tmp.setEnabled(false); //und braucht auch nicht angeklickt werden können
		return tmp;
	}

	/**
	 * @param id
	 * @return
	 */
	private JButton createButtonKuhle(int id) {
		//Standardwert für alle Kuhlen
		JButton tmp = new JButton("3");
		//Anfangsgroesse der Buttons einstellen
		tmp.setPreferredSize(new Dimension(100,100));
		tmp.setMinimumSize(tmp.getPreferredSize());
		//Command des Buttons ist ButtonID
		tmp.setActionCommand(Integer.toString(id));
		//Listener gibt ButtonID zurück
		tmp.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.clickedFeld( Integer.parseInt(e.getActionCommand()) );
			}
		});
		return tmp;
	}
	
	/**
	 * @return
	 */
	private JPanel erstelleInfoPanel() {
		JPanel pan = new JPanel();
		lbl = new JLabel("Spieler [1] am Zug");
		pan.add( lbl );
		return pan;
	}

//### INTERFACE: Observer #################################
	@Override
	public void update(Observable obs, Object o) {
		if( !(obs instanceof Spielfeld) ) return;
		Spielfeld copy = (Spielfeld)obs;
		//Daten der Spielfelder anpassen
		for(int i=0; i<Spielfeld.FELDER; i++) {
			btn[i].setText(
				Integer.toString( copy.getBohnenAufFeld(i) )
			);
		}
		//aktiven Spieler anzeigen
		aktiverSpieler(copy.getAktiverSpieler());
	}
}
