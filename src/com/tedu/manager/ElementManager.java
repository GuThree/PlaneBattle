package com.tedu.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tedu.element.ElementObj;

/**
 * @说明 本类是元素管理器，专门存储所有的元素，同时提供方法
 *      给予视图和控制获取元素
 * @author 10129
 * @问题一：存储所有元素数据，怎么存放？list map set 3大集合
 * @问题二：管理器是视图和控制要访问，管理器就必须只有一个，单例模式
 */
public class ElementManager {
	private List<Object> listMap;
	private List<Object> listPlay;
	
	/*
	 * String 作为key 匹配所有的元素 play -> List<Object> listPlay;
	 * 							enemy -> List<Object> listEnemy;
	 * 枚举类型，当作map的key用来区分不一样的资源，用于获取资源
	 * List中元素的泛型 应该是 元素 基类
	 * 所有的元素都可以存放到map集合中，显示模块只需要获取到这个 map 就可以
	 * 显示是有的界面需要显示的元素（调用元素基类的showElement（））
	 */
	private Map<GameElement, List<ElementObj>> gameElements;
	
	public Map<GameElement, List<ElementObj>> getGameElements() {//本方法一定不够用
		return gameElements;
	}
	
	/**
	 * 添加元素（多半由加载器调用）
	 */
	public void addElement(ElementObj obj, GameElement ge) {
		gameElements.get(ge).add(obj);//添加对象到集合中，按key值进行存储
	}
	
	/**
	 * 依据key返回list列表，取出某一类元素
	 */
	public List<ElementObj> getElementsByKey(GameElement ge) {
		return gameElements.get(ge);
	}
	
	/*
	 * 单例模式：内存中有且仅有一个实例
	 * 饿汉模式-启动就加载实例
	 * 饱汉模式-需要使用的时候才加载实例
	 * 
	 * 编写方法：
	 * 1.需要一个静态的属性（定义一个常量）单例的引用
	 * 2.提供一个静态的方法（返回这个实例）return单例的引用
	 * 3.一般为防止其他人自己使用（类是可以实例化），所以会私有化构造方法
	 * 		ElementManager em=new ElementManager();
	 */
	/** 
	 * 静态的属性（定义一个常量）单例的引用 
	 */
	private static ElementManager EM=null; //引用
	
	/**
	 * 静态的方法（返回这个实例）return单例的引用 （饱汉模式）
	 */
	//synchronized线程锁->保证本方法执行中只有一个线程（饱汉模式）
		//不加的话可能出现 显示模块 和 控制模块 同时调用时 实例化两次对象
	public static synchronized ElementManager getManager() {
		if (EM == null ) {//空值判定
			EM = new ElementManager();//EM是静态的，
		}//只会实例化一次，单例的最简写法
		return EM;
	}

	/*
	 * 饿汉模式 实例化对象 
	 * static {//静态语句块时在类被加载的时候直接执行
	 * 		EM=new
	 * 		ElementManager();//只会执行一次 
	 * }
	 */	
	
	/* 一般为防止其他人自己使用（类是可以实例化），所以会私有化构造方法 */
	private ElementManager() {//私有化构造方法，只能在本类中实例化，其他地方无法实例化本类
		//构造方法无法被继承，也就不能重写
		//如果需要扩展功能，重新实现构造方式，重写init方法即可
		init();//实例化方法
	}
	/**
	 * 本方法是为将来可能出现的功能扩展，重写init方法准备的。
	 */
	public void init() {//实例化在这里完成
		/* hasMap hash散列 */
		gameElements = new HashMap<GameElement, List<ElementObj>>();
		for(GameElement ge:GameElement.values()) {
			gameElements.put(ge, new ArrayList<ElementObj>());
		}//道具，子弹，爆炸效果，死亡效果。。。等都会存放于该集合中
	}
}
















