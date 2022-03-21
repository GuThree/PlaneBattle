package com.tedu.show;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.tedu.manager.ElementManager;


/**
 * @说明：游戏窗体 主要功能实现：关闭，显示，最大化、最小化
 * @author 10129
 * @功能说明：
 *		需要嵌入面板，启动主线程等等
 * @窗体说明：swing awt 窗体大小（记录用户上次使用软件的窗体样式）
 * 
 * @分析 1.面板绑定到窗体
 * 		2.监听绑定
 * 		3.游戏主线程启动
 * 		4.显示窗体
 */
public class GameJFrame extends JFrame{
	public static int GameX = 450;//GAMEX（规范写法）
	public static int GameY = 700;//GAMEY（规范写法）
	
	private JPanel jPanel = null;//正在显示的面板
	private KeyListener keyListener = null;//键盘监听
	private MouseMotionListener mouseMotionListener = null;//鼠标监听
	private MouseListener mouseListener = null;//鼠标监听
	private Thread thead = null;//游戏主线程
	
	/**创建唯一的面板*/
	private static GameJFrame GJ = null;
	public static synchronized GameJFrame getJFrame() {
		if (GJ == null ) {//空值判定
			GJ = new GameJFrame();//EM是静态的，
		}//只会实例化一次，单例的最简写法
		return GJ;
	}
	
	public GameJFrame() {
		init();
	}	
	public void init() {
		this.setSize(GameX, GameY);//窗体大小
		this.setTitle("飞机大战");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置退出并且关闭
		this.setLocationRelativeTo(null);//窗体居中
		this.setResizable(false);//设置不可拉伸	

	}
	
	/**
	 * 启动方法
	 */
	public void start() {
		if(jPanel != null) {
			this.add(jPanel);
		}
		if(keyListener != null) {
			this.addKeyListener(keyListener);
		}
		if(thead != null ) {
			thead.start();//启动线程
		}
		this.setVisible(true);//显示窗体 
		/* 如果jp 是runnable的 子类实体对象 */
		if(this.jPanel instanceof Runnable) {		
			new Thread((Runnable)this.jPanel).start();//已经做类型判定，强制类型转换不会出错
		}
	}
	
	/*
	 * set注入：通过set方法注入配置文件中读取的数据；
	 * 将配置文件中的数据赋值为类的属性
	 * 构造注入：需要配合构造方法
	 * spring中ioc进行对象的自动生成，管理
	 */
	public void setjPanel(JPanel jPanel) {
		this.jPanel = jPanel;
	}
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}
	public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
		this.addMouseMotionListener(mouseMotionListener);
	}
	public void setMouseListener(MouseListener mouseListener) {
		this.addMouseListener(mouseListener); 
	}
	public void setThead(Thread thead) {
		this.thead = thead;
	}	
}

