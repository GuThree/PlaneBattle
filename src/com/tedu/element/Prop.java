package com.tedu.element;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

public class Prop extends ElementObj{
	private Random ra = new Random();
	private String item;
	
	boolean fx;//true向左移，false向右移
	
	@Override
	public ElementObj createElement(String str) {
		String[] split = str.split(",");
		for(String str1 : split) {
			String[] split2 = str1.split(":");
			switch (split2[0]) {
			case "x": this.setX(Integer.parseInt(split2[1]));break;
			case "y": this.setY(Integer.parseInt(split2[1]));break;
			case "item":this.item = split2[1];break;
			}
		}
		this.setType(Integer.parseInt(this.item.substring(10,11)));
		this.setW(30);
		this.setH(30);
		this.setIcon(new ImageIcon(item));
		return this;
	}
	public Prop() {
	}
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}
	@Override
	protected void move() {
		if(this.getY() > 700) {
			this.setLive(false);
			return;
		}
		if (this.getX()<0) {
			fx=false;
			this.setX(this.getX()+1);
		}
		else if(this.getX()>380) {
			fx=true;
			this.setX(this.getX()-1);
		}
		else {
			if (fx) {
				this.setX(this.getX()-1);
			}else {
				this.setX(this.getX()+1);
			}
		}
		this.setY(this.getY()+1);//道具向下移动速度
	}
	
}
