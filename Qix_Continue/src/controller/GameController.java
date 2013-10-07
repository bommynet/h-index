package controller;

import java.awt.Color;
import javax.swing.JOptionPane;

import config.Config;

import keyboard.MovementListener;
import model.Line;
import model.MusicStyle;
import model.Player;
import view.GameWindow;
import view.draw.ImageDraw;
import view.draw.LineDraw;

/**
 * Der GameController enth&auml;lt alle f&uuml;r das Spiel wichtigen Elemente, wie
 * die Controller f&uuml;r Spieler, Gegner und Bl&�uml;cke, aber auch den Grafik-
 * Controller. Hier wird das Spiel initialisiert, angezeit und gestartet, so wie
 * s&auml;mtliche Ereignisse rund um den Spieler abgefragt; z.B. eine Kollision
 * mit einem Gegner und ob der Spieler das Spiel gewonnen hat.
 * @author Thomas Laarmann
 * @version 130629
 */
public class GameController implements Runnable {
//### VARIABLEN #####################################################
	/** das Spielfenster */
	private GameWindow view;
	/** das Spielermodell */
	private Player player;
	/** die Spielergrafik */
	private ImageDraw playerImg;

	/** das Modell einer neuen Linie */
	private Line curLine;
	/** die Grafik einer neuen Linie */
	private LineDraw drawCurLine;

	/** Verwalter aller Bl&ouml;cke auf dem Spielfeld */
	private BlockController bc;
	/** Verwalter aller Gegner auf dem Spielfeld */
	private EnemyController ec;
	
	/** Spielt Musik ab und nimmt Wuensche entgegen */
	private JukeBox jb;
	

//### KONSTRUKTOR ###################################################
	/**
	 * Initialisiert einen neuen GameController, welcher alle Unter-Controller
	 * erstellt, einen Spieler erstellt und in das Modell einf&uuml;gt, die
	 * Lebensanzeige einf&uuml;gt und letztendlich alle Threads startet.
	 */
	public GameController() {
		//Während der Rest lädt, soll schon mal Musik lauen
		jb = new JukeBox();
		jb.getMusicThread().start();
		Config.MUSIC_STYLE = MusicStyle.Loud;
		
		//Anzeige erstellen
		view = new GameWindow();
		//Controller erstellen
		ec = new EnemyController( view, Config.NEW_ENEMY_COUNT, Config.NEW_ENEMY_TYPE );
		bc = new BlockController( view );
		//EnemyController ben�tigt Blocks
		ec.setBlockPointer( bc.getBlockList() );
		//MovementListener von Player an Window übergeben
		view.addMovementListener( new MovementListener() {
			@Override
			public void doMovement(boolean left, boolean right, boolean up, boolean down)
			{
				player.doMovement(left, right, up, down);
			}
		} );
		
		player = new Player(10,10);
		playerImg = new ImageDraw("kreuz.png", 20,20);
		player.addObserver(playerImg);
		view.setPlayer(playerImg);
		new HeartController(view, player); //wird ja nur einmal erstellt und dann uninteressant

		//Threads holen/erstellen und starten
		ec.getEnemyThread().start();
		new Thread( this ).start();
	}

	
//### OVERRIDES: Interface ##########################################
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(Config.running) {
			//Spieler aktualisieren
			player.Update(10);

			//Pr�fen: Wird neue Linie gezeichnet?
			boolean isInAnyBlock = false;
			for(int i = 0; i < bc.getBlockList().size(); i++)
			{
				if(bc.getBlockList().get(i).Contains(player))
					isInAnyBlock = true;
			}
			if(isInAnyBlock == player.isInField()) {
				if(!player.isInField()) {
					curLine = new Line( player.getLastPos(), player );
					drawCurLine = new LineDraw( curLine, Color.RED );
					curLine.addObserver(drawCurLine);
					view.addEnemy(drawCurLine);
					System.out.println("Start");
					Config.MUSIC_STYLE = MusicStyle.Gentle;
				} else {
					bc.conquerArea( curLine );
					curLine.deleteObservers();
					curLine = null;
					view.removeEnemy(drawCurLine);
					System.out.println("End");
					Config.MUSIC_STYLE = MusicStyle.Loud;
				}
				player.toggleInField();
			}
			
			//Pr�fen: Kollidiert Linie mit Gegner?
			if(curLine != null && player.isInField()) {
				for(int i=0; i < ec.getEnemyList().size(); i++) {
					if( curLine.CollidesWith( new Line(ec.getEnemyList().get(i).getLastPos(), ec.getEnemyList().get(i)) ) ) {
						player.setPos( curLine.getStart() );
						player.removeOneLive();
						//Line l�schen
						curLine.deleteObservers();
						curLine = null;
						view.removeEnemy(drawCurLine);
						player.setInField(false);
						System.out.println("Kill");
						Config.MUSIC_STYLE = MusicStyle.Loud;
						break;
					}
				}
			}
			
			//Pr�fen: Spiel gewonnen? Bei 75% erobert oder alle Gegner tot
			if((bc.getConqueredAreaValue() >= 0.75)) {
				Config.running = false;
				JOptionPane.showMessageDialog(view.getComponent(),
						"Mehr als 75% der Fl�che eingenommen!",
						"Gewonnen!",
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			} else if(ec.getEnemyList().isEmpty()) {
				Config.running = false;
				JOptionPane.showMessageDialog(view.getComponent(),
						"Alle Gegner wurden vernichtet!",
						"Gewonnen!",
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
			
			//Pr�fen: Spiel verloren?
			if(player.getLivesLeft() <= 0) {
				Config.running = false;
				JOptionPane.showMessageDialog(view.getComponent(),
						"Leider kein Leben mehr �brig.",
						"Verloren.",
						JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			
			//Pause nicht vergessen :D
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
