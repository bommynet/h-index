package view.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;

import javax.imageio.ImageIO;

import model.Point;
import model.Rectangle;

/**
 * Dies stellt ein QixView-Objekt dar, welches ein Bild aus den Resourcen
 * (Package view.img) laden kann und auf der Graphics2D-Schnittstelle
 * ausgibt.
 * @author Thomas Laarmann
 * @version 130624
 */
public class ImageDraw extends QixView {

//### VARIABLEN #####################################################
	/** Speicher f&uuml;r das zu zeichnende Bild */
	private BufferedImage img;
	/** Position x des Bildes */
	private int x=0;
	/** Position y des Bildes */
	private int y=0;
	private int width;
	private int height;


//### KONSTRUKTOR ###################################################
	/**
	 * Erstellt ein neues ImageDraw-Objekt und l&auml;dt eine Grafik aus
	 * dem Package view.img zur Anzeige. Das Bild wird auf die Gr&ouml;&szlig;e
	 * width * height eingepasst.
	 * @param imgRes Name der Bilddatei
	 * @param width Breite des Bildes
	 * @param height H&ouml;he des Bildes
	 */
	public ImageDraw(String imgRes, int width, int height) {
		//l�dt das angegebene Bild
		loadImage(imgRes, width, height);
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Erstellt ein neues ImageDraw-Objekt und l&auml;dt eine Grafik aus
	 * dem Package view.img zur Anzeige. Das Bild wird auf die Gr&ouml;&szlig;e
	 * width * height eingepasst.
	 * @param imgRes Name der Bilddatei
	 * @param width Breite des Bildes
	 * @param height H&ouml;he des Bildes
	 */
	public ImageDraw(String imgRes, Point startPos, int width, int height) {
		this(imgRes, width, height);
		x = (int) startPos.getX();
		y = (int) startPos.getY();
	}
	

//### FUNKTIONEN ####################################################
	/**
	 * L&auml;dt eine Grafik f&uuml;r das ImageDraw-Objekt aus den Resourcen. Ein
	 * Resourcen-Bild muss im Package view.img liegen und als voller Datei
	 * name angegeben werden (ohne Pfad). Z.B. "kreuz.png". Das geladene
	 * Bild wird auf die gegebene Gr&ouml;&szlig;e skaliert und dann f&uuml;r das
	 * ImageDraw-Objekt gespeichert.
	 * Sollte beim Laden des Bildes ein Fehler auftreten, wird ein wei&szlig;es
	 * Rechteck als Bild gezeichnet, mit der Gr&ouml;&szlig;e width * height.
	 * @param imgRes der Name des neuen Bilds als Resource
	 * @param width die Breite des Bildes
	 * @param height die H&ouml;he des Bildes
	 */
	public void loadImage(String imgRes, int width, int height) {
		//Grafik in Speicher schreiben
		try {
			BufferedImage tmp;
			//lade die Grafik aus dem Package png
			tmp = ImageIO.read( getClass().getResource("/view/img/" + imgRes) );
			//muss das Bild skaliert werden?
			if(tmp.getWidth() == width && tmp.getHeight() == height)
				//nein, so uebernehmen
				img = tmp;
			else
				//ja, skaliere die Grafik auf gegebene Werte
				img = resizeImage(tmp, width, height);
		} catch (IOException e) {
			//die Grafik konnte nicht geladen werden, also wei�es Rechteck zeichnen
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			img.getGraphics().setColor(Color.white);
			img.getGraphics().fillRect(0, 0, width, height);
		}
	}
	
	/**
	 * Skaliert das &uuml;bergebene Bild auf eine neue Gr&ouml;&szlig;e und liefert das neue
	 * Bild als BufferedImage zur&uuml;ck.
	 * @param original das originale Bild
	 * @param newWidth die neue Breite des Bildes
	 * @param newHeight die neue H&ouml;he des Bildes
	 * @return das skalierte Bild
	 */
	public BufferedImage resizeImage(BufferedImage original, int newWidth, int newHeight) {
		//legt ein neues Bild als temporaeren Speicher an
		BufferedImage tmp = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		//Zeichenschnittstelle f&uuml;r das neue Bild oeffnen
		Graphics2D g = tmp.createGraphics();
		//das Original skaliert auf tmp zeichnen
		g.drawImage( original.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH),
		             0, 0, newWidth, newHeight, null);
		//Zeichenschnittstelle schlie�en...
		g.dispose();
		//...und das skalierte Bild zurueck geben
		return tmp;
	}

//### OVERRIDES: Interfaces ##################################################
	/* (non-Javadoc)
	 * @see view.draw.QixView#drawObject(java.awt.Graphics2D)
	 */
	@Override
	public void drawObject(Graphics2D g) {
		//Bild an Position (x,y) zeichnen
		g.drawImage(img, null, x-width/2, y-height/2);
	}

	/* (non-Javadoc)
	 * @see view.draw.QixView#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable obj, Object arg1) {
		//bei leerem Objekt gar nicht erst die Funktionen durchlaufen
		if( obj == null ) return;
		//Datenquelle ist ein model.Point
		if(obj instanceof Point) {
			this.x = (int) ((Point)obj).getX();
			this.y = (int) ((Point)obj).getY();
		} 
		//Datenquelle ist ein model.Rectangle
		else if(obj instanceof Rectangle) {
			this.x = (int) ((Rectangle)obj).getTopLeft().getX();
			this.y = (int) ((Rectangle)obj).getTopLeft().getY();
		}
	}
}
