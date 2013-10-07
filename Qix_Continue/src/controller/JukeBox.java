package controller;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import model.MusicStyle;

import config.Config;

//KEKSE!

public class JukeBox implements Runnable {

	Clip clip1;
	Clip clip2;
	Clip activeClip;
	MusicStyle style = MusicStyle.Off;
	
	@Override
	public void run()
	{
		clip1 = getClip("src/view/sound/qix_v4_solo.wav");
		clip2 = getClip("src/view/sound/qix_v4.wav");
			
		while (Config.running)
		{
			try
			{
				Thread.sleep(50);
				
				if(Config.MUSIC_STYLE == style)
					continue;
				
				switch(Config.MUSIC_STYLE)
				{
					case Off:
						switchOff();
					case Gentle:
						switchGentle();
					case Loud:
						switchLoud();
				}
				
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public Thread getMusicThread()
	{
		return new Thread( this );
	}
	
	public void switchLoud()
	{
		switchClip(clip2);
		style = MusicStyle.Loud;
	}
	
	public void switchGentle()
	{
		switchClip(clip1);
		style = MusicStyle.Loud;
	}
	
	public void switchOff()
	{
		if (activeClip != null)
		{
			activeClip.stop();
		    activeClip = null;
		}
	}
	
	private void switchClip(Clip clip)
	{
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		long micros = ( System.currentTimeMillis() * 1000 ) % clip.getMicrosecondLength();
		clip.setMicrosecondPosition(micros);
		if (activeClip != null)
		{
		  activeClip.stop();
		}
		activeClip = clip;
	}
	
	public static Clip getClip(String filename)
	  {
		  try
		  {
			  /* könnte zum Problem werden, wenn wir das Spiel als JAR
			   * packen. Dann sind die Pfade nicht mehr so absolut an-
			   * zusprechen, da der Interpreter dann im Verzeichnis
			   * sucht und nicht in der JAR-Datei... Schau dir z.B. mal
			   * an, wie ich die Funktion loadImage(xyz) in ImageDraw
			   * aufgebaut habe => getClass().getResource()
			   */
		  File file = new File(filename);
			
			AudioInputStream iStream = AudioSystem.getAudioInputStream(file);
			AudioFormat format = iStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip)AudioSystem.getLine(info);
			clip.open(iStream);
			return clip;
		  }
		  catch (IOException e) {
				e.printStackTrace();
		  } catch (UnsupportedAudioFileException e) {
			    e.printStackTrace();	    
		  } catch (LineUnavailableException e) {
				e.printStackTrace();
		  }
		  return null;
	  }

}
