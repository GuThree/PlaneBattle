package com.tedu.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.tedu.controller.GameListner;
import com.tedu.controller.GameThread;
import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;


public class GameStart {
	/**
	 * 程序的唯一入口
	 * @param args
	 */
	public static void main(String[] args) {
		GameJFrame gj = GameJFrame.getJFrame();
		
		/* 实例化面板 注入到jframe中 */
		GameMainJPanel gameMainJPanel = new GameMainJPanel();

		/* 实例化监听 */
		GameListner listner = new GameListner();
		
		/* 实例化主线程 */
		GameThread th = new GameThread();

		/* 注入 */
		gj.setjPanel(gameMainJPanel);
		gj.setKeyListener(listner);
		gj.setMouseMotionListener(listner);
		gj.setMouseListener(listner);
		gj.setThead(th);
		
		gj.start();

	}

}
