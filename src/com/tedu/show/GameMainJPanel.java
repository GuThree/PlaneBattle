package com.tedu.show;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;


/**
 * @说明：游戏的主要面板
 * @author 10129
 * @功能说明 主要进行元素的显示，同时进行界面刷新（多线程）
 * 
 * @题外话 java开发首先思考的应该是：做继承或者是接口实现
 * 
 * @多线程刷新 1.本类实现线程接口 2.本类中定义一个内部类来实现
 */
public class GameMainJPanel extends JPanel implements Runnable, ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	/* 获取面板 */
	private static GameJFrame gj = GameJFrame.getJFrame();
	/* 联动管理器 */
	private ElementManager em;

	public static int count = 1;

	public JButton startButton = new JButton("开始游戏");
	public JButton outButton = new JButton("退出游戏");
//	public JButton restartButton = new JButton("重新开始");
//	public JButton returnStartButton = new JButton("返回主页");
	public JLabel jLabel = new JLabel(" "+com.tedu.element.Map.point+" ");
	
	public GameMainJPanel() {
		init();
	}

	public void init() {
		em = ElementManager.getManager();// 得到元素管理器对象
	}

	/**
	 * paint是进行绘画元素。 绘画时时有固定顺序，先绘画的图片会在底层，后绘画的图片会覆盖先绘画的
	 * 
	 * @约定 本方法只执行一次，想实时刷新需要使用 多线程
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		if (count == 1) {
			paintwelcome(g);
		} else if (count == 2) {
			paintplay(g);
		} else {
			paintend(g);
			outButton.setVisible(true);
//			this.restartButton.setVisible(true);
//			this.returnStartButton.setVisible(true);
		}
	}

	/**
	 * 绘制游戏界面
	 * 
	 * @param g
	 */
	public void paintplay(Graphics g) {
		Image ImageCursor = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(""));
		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageCursor, new Point(0, 0), "cursor"));
		Map<GameElement, List<ElementObj>> all = em.getGameElements();
		// GameElement.values();//隐藏方法，返回值是一个数组，数组的顺序就是定义的枚举类型的顺序
		for (GameElement ge : GameElement.values()) {
			List<ElementObj> list = all.get(ge);
			for (int i = 0; i < list.size(); i++) {
				ElementObj obj = list.get(i);// 读取为基类
				obj.showElement(g);// 调用每个类自己的show方法王朝自己的显示
			}
		}
	}

	/**
	 * 绘制开始界面
	 * 
	 * @param g
	 */
	public void paintwelcome(Graphics g) {
		this.setOpaque(false);// 设置边界布局
		this.setLayout(null);// 设置布局
		/* 设置位置 */
		startButton.setBounds((450 - 160) / 2, 425, 160, 45);
		outButton.setBounds((450 - 160) / 2, 500, 160, 45);
		/* 添加按钮事件 */
		startButton.addActionListener(this);
		outButton.addActionListener(this);
		/* 添加按钮 */
		this.add(startButton);
		this.add(outButton);

		// System.out.println("欢迎面板显示");//测试

		g.drawImage(new ImageIcon("img/background/star.png").getImage(), 0, 0, 450, 700, null);
		g.drawImage(new ImageIcon("img/background/logo.png").getImage(), (450 - 240) / 2, 130, 240, 129, null);
		g.setFont(new Font("微软雅黑", Font.BOLD, 58));
		g.setColor(Color.white);
		g.drawString("飞", 85, 335);
		g.drawString("机", 155, 335);
		g.drawString("大", 225, 335);
		g.drawString("战", 295, 335);
		// System.out.println("欢迎面板绘制2");//测试
	}

	/**
	 * 绘制结束界面
	 * 
	 * @param g
	 */
	public void paintend(Graphics g) {
		Cursor cur=new Cursor(Cursor.DEFAULT_CURSOR);//这一句就是设置了一个十字形的鼠标样式
		this.setCursor(cur);
		this.setOpaque(false);// 设置边界布局
		this.setLayout(null);// 设置布局
		/* 设置位置 */
		outButton.setBounds((450 - 160) / 2, 450, 160, 45);
		/* 添加按钮事件 */
		outButton.addActionListener(this);
		/* 添加按钮 */
		this.add(outButton);
		/* 添加分数 */
		jLabel.setText(" "+com.tedu.element.Map.point+" ");
		jLabel.setBounds((450 - 200) / 2, 340, 200, 50);
		jLabel.setFont(new Font("微软雅黑", Font.BOLD, 48));
		jLabel.setForeground(Color.red);
		jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(jLabel);
		
		g.drawImage(new ImageIcon("img/background/star.png").getImage(), 0, 0, 450, 700, null);
		g.drawImage(new ImageIcon("img/background/end.png").getImage(), (450 - 240) / 2, 130, 240, 129, null);
		g.setFont(new Font("微软雅黑", Font.BOLD, 58));
		g.setColor(Color.white);		
		g.drawString("游", 85, 310);
		g.drawString("戏", 155, 310);
		g.drawString("结", 225, 310);
		g.drawString("束", 295, 310);

	}

	@Override
	public void run() { // 接口实现
		while (true) {
			this.repaint();
			// System.out.println("重绘"+ispause);//测试
			/* 一般情况下多线程都会使用一个休眠控制速度 */
			try {
				Thread.sleep(10);// 休眠 刷新
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 按钮监听事件
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String text = e.getActionCommand();
		if (text == "开始游戏") {
			count = 2;
			startButton.setVisible(false);
			outButton.setVisible(false);
//			restartButton.setVisible(false);
//			returnStartButton.setVisible(false);

		}
		if (text == "退出游戏") {
			System.exit(0);
		}
//		if (text == "重新开始") {
//			count = 2;
//			startButton.setVisible(false);
//			outButton.setVisible(false);
//			restartButton.setVisible(false);
//			returnStartButton.setVisible(false);
//		}
//		if (text == "返回主页") {
//			count = 1;
//			startButton.setVisible(true);
//			outButton.setVisible(true);
//			restartButton.setVisible(false);
//			returnStartButton.setVisible(false);
//		}
		gj.requestFocus();
	}

	public void keyPressed(KeyEvent e) {
		/* 拿到玩家集合 */
		// System.out.println("keyPressed"+e.getKeyCode()); //测试
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
