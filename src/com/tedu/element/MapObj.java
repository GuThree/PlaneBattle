package com.tedu.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

/**
 * 已弃用
 * @author 10129
 *
 */
public class MapObj extends ElementObj{
	/*墙需要血量*/
	private int hp;
	private String name;//墙的type 也可以用枚举
	
	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), this.getX(), getY(), getW(), getH(), null);
	}
	
	@Override //如果可以传入 墙类型，x，y
	public ElementObj createElement(String str) {
//		System.out.println(str);
		String []arr = str.split(",");
		//先写一个假图片
		ImageIcon icon = null;
		switch(arr[0]) {//设置图片信息 图片未加载到内存中
		case "GRASS": icon = new ImageIcon("image/wall/grass.png"); break;
		case "BRICK": icon = new ImageIcon("image/wall/brick.png");break;
		case "IRON": icon = new ImageIcon("image/wall/iron.png");
					this.hp = 4;
					name = "IRON";
					break;
		case "RIVER": icon = new ImageIcon("image/wall/ricer.png");break;
		}
//		int x = Integer.parseInt(arr[1]);
//		int y = Integer.parseInt(arr[2]);
//		int w = icon.getIconWidth();
//		int h = icon.getIconHeight(); 
		this.setH(icon.getIconHeight());
		this.setW(icon.getIconWidth());
		this.setX(Integer.parseInt(arr[1]));
		this.setY(Integer.parseInt(arr[2]));
		this.setIcon(icon);
		
		return this;
	}
	
	@Override
	public void setLive(boolean live) {
		//被调用一次 被减少一次血
		if("IRON".equals(name)) {//水泥墙需要4下
			this.hp--;
			if(this.hp > 0) {
				return;
			}
		}
		super.setLive(live);
	}
}




