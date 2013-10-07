package keyboard;
import java.awt.event.*;
   
   public abstract class MovementListener implements KeyListener {
      private boolean left = false;
      private boolean right = false;
      private boolean up = false;
      private boolean down = false;
   
      public void keyTyped(java.awt.event.KeyEvent e) {
        // do nothing
      }
   
      public void keyPressed(java.awt.event.KeyEvent e)
      {
         boolean hasChanged = false;
         switch (e.getKeyCode())
         {
			case KeyEvent.VK_LEFT:
				if(!left)
					hasChanged = true;
				left = true;
				break;
			case KeyEvent.VK_RIGHT:
				if(!right)
					hasChanged = true;
				right = true;
				break;
			case KeyEvent.VK_UP:
				if(!up)
					hasChanged = true;
				up = true;
				break;
			case KeyEvent.VK_DOWN:
				if(!down)
					hasChanged = true;
				down = true;
				break;
			case KeyEvent.VK_A:
				if(!left)
					hasChanged = true;
				left = true;
				break;
			case KeyEvent.VK_D:
				if(!right)
					hasChanged = true;
				right = true;
				break;
			case KeyEvent.VK_W:
				if(!up)
					hasChanged = true;
				up = true;
				break;
			case KeyEvent.VK_S:
				if(!down)
					hasChanged = true;
				down = true;
				break;
				
         }
         if(hasChanged)
        	 this.doMovement(left,right,up,down);
      }
   
      public void keyReleased(java.awt.event.KeyEvent e)
      {
    	  boolean hasChanged = false;
          switch (e.getKeyCode())
          {
 			case KeyEvent.VK_LEFT:
 				if(left)
 					hasChanged = true;
 				left = false;
 				break;
 			case KeyEvent.VK_RIGHT:
 				if(right)
 					hasChanged = true;
 				right = false;
 				break;
 			case KeyEvent.VK_UP:
 				if(up)
 					hasChanged = true;
 				up = false;
 				break;
 			case KeyEvent.VK_DOWN:
 				if(down)
 					hasChanged = true;
 				down = false;
 				break;
 			case KeyEvent.VK_A:
 				if(left)
 					hasChanged = true;
 				left = false;
 				break;
 			case KeyEvent.VK_D:
 				if(right)
 					hasChanged = true;
 				right = false;
 				break;
 			case KeyEvent.VK_W:
 				if(up)
 					hasChanged = true;
 				up = false;
 				break;
 			case KeyEvent.VK_S:
 				if(down)
 					hasChanged = true;
 				down = false;
 				break;
          }
          if(hasChanged)
         	 this.doMovement(left,right,up,down);
      }
   
      public abstract void doMovement(boolean left, boolean right, boolean up, boolean down);
   }