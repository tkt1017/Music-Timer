package MusicTimer;

import java.awt.Dimension;
import java.awt.Toolkit;

public class MainThread{
  public static void main(String[] args){
	  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  int w = screenSize.width;
	  int h = screenSize.height;
	  createMainThread(w,h);
  }

  private static void createMainThread(int w, int h){
	  int l = w/8;
	  MainPane2 frame = new MainPane2(l);
    frame.setDefaultCloseOperation(3);
    frame.setBounds(w/4, h/4, l, l);
    frame.setTitle("Music_Timer");
    frame.setResizable(false);
    frame.setVisible(true);
  }
}