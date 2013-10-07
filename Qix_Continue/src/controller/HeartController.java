package controller;

import java.util.Observable;
import java.util.Observer;

import config.Config;

import model.Player;
import model.Point;

import view.GameWindow;
import view.draw.ImageDraw;

/**
 * 
 * @author Jan Wittler
 *
 */
public class HeartController implements Observer
{
	private ImageDraw hearts[];
	private GameWindow view;
	private Player player;
	
	
	public HeartController(GameWindow view, Player player)
	{
		this.player = player;
		this.view = view;
		
		hearts = new ImageDraw[3];
		player.addObserver(this);
		
		for(int i = 0; i < 3; i++)
		{
			
			Point p = new Point(Config.HEARTS_POSX + i * Config.HEARTS_SIZE, Config.HEARTS_POSY);
			hearts[i] = new ImageDraw("heart.png", p, Config.HEARTS_SIZE, Config.HEARTS_SIZE);
			p.addObserver(hearts[i]);
			view.addUiElement(hearts[i]);
		}
	}

	int heartsCount = 0;
	
	@Override
	public void update(Observable arg0, Object arg1)
	{
		if (heartsCount == player.getLivesLeft())
		  return;
        heartsCount = player.getLivesLeft();
/*		if (heartsCount == 0)
		{
			System.exit(1);
		}
*/
        for(int i = 0; i < 3; i++)
		{
			view.removeUiElement(hearts[i]);
			if (i < heartsCount)
				view.addUiElement(hearts[i]);				
		}
	}
	
}
