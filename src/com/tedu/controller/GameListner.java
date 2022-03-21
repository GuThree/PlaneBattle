package com.tedu.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.show.GameMainJPanel;

/**
 * @说明 监听类，用于监听用户操作
 * @author 10129
 */
public class GameListner implements KeyListener,MouseListener,MouseMotionListener{
	private ElementManager em = ElementManager.getManager();
	public static boolean press = false;
	/*
	 * 能否通过一个集合来记录所有按下的键，如果重复触发，就直接结束
	 * 同时，第一次按下 记录到集合中，第二次判定集合中是否有
	 * 	   松开 就直接删除集合中的记录
	 * set集合
	 */
	private Set<Integer> set = new HashSet<Integer>();
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO 自动生成的方法存根
			int x = e.getX();
			int y = e.getY();

			List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
			for(ElementObj obj:play) {
				obj.move(x,y);
			}
	}
	public void keyTyped(KeyEvent e) {	
	}

	/**
	 * 键盘监听
	 * 按下
	 */
	@Override
	public void keyPressed(KeyEvent e) {
	}

	/**
	 * 键盘松开时
	 * 游戏暂停监听（空格控制）
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		int a=1;
		if(key == 32 && GameThread.ispause == true) {
			GameThread.ispause = false;
//			System.out.println("stop" + GameThread.ispause); //测试
			press = true;
			return;
		}
		if(key == 32 && GameThread.ispause == false) {
			GameThread.ispause = true;
//			System.out.println("start" + GameThread.ispause); //测试
			press = false;
			return;
		}
//		System.out.println("keyPressed"+e.getKeyCode());//测试
		if(!set.contains(e.getKeyCode())) {//如果这个不存在，就停止
			return;
		}
		set.remove(e.getKeyCode());//移除数据
		
		List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
		for(ElementObj obj:play) {
			obj.keyClick(false,e.getKeyCode());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO 自动生成的方法存根
	
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}
	

 
}












