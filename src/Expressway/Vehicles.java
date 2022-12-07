package Expressway;

import java.awt.*;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

abstract class PubVehicles{
	public static final int LEFT_BOUND=20;//车的运动边界，x方向上 20~890  高速公路长870像素
	public static final int RIGHT_BOUND=890;
	protected boolean isRight;//是否向右行驶
	protected boolean isLaunched=false;//是否发车了
	protected int currentP;//当前的位置
	protected int number;//公交车的编号
	protected int speed;//车速
	protected int capacity;//容量
	protected LinkedList<Passenger> ps=new LinkedList<Passenger>();//乘客链表，记录车上的各个乘客信息  到终点站记得清空
	protected int duration;//发车的间隔
	protected JLabel lb;//用于存图的JLabel
	protected ImageIcon ii;//图片
	
	PubVehicles(boolean isRight,int number,int speed,int capacity,int duration){
		this.isRight=isRight;
		this.number=number;
		this.speed=speed;
		this.capacity=capacity;
		this.duration=duration;
	}
	
	abstract protected void headReverse();//到达终点站调头
	abstract protected void display(int x,int y);//显示在panel_car面板上
	abstract protected void launch();//每隔一段时间发车
	abstract protected void CarMoving();//车辆移动
	abstract protected int CheckStop();//检查车上是否有乘客即将到站 如果没有，返回-1，如果有，返回当前站点的编号0，1，2，3，4，5，6
	abstract protected void Stop(int index);//靠index站停车，等待乘客下车
}

//依维柯 速度是7像素每秒，也就是1.4公里每分钟  容量21
//上午8:00开始，每20分钟一班，最后一班为下午6:00
class Iveco extends PubVehicles{
	
	private int temp=0;//用于停2分钟的临时变量
	
	public Image right=Toolkit.getDefaultToolkit().createImage("images/Iveco_Right.png");
	public Image left=Toolkit.getDefaultToolkit().createImage("images/Iveco_Left.png");
	
	//初始化
	Iveco(boolean isRight,int number, int speed, int capacity,int duration) {
		super(isRight,number, speed,capacity,duration);
		lb=new JLabel();
		ii=null;
		//选择车头朝向
		if(isRight) {//如果车头向右
			ii=new ImageIcon(this.right);	
			this.currentP=LEFT_BOUND;
		}else {
			ii=new ImageIcon(this.left);
			this.currentP=RIGHT_BOUND;
		}
		ii.setImage(ii.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));//缩放到80x80
		lb.setToolTipText("Iveco "+this.number);//鼠标移到上面有信息显示
		lb.setVisible(true);
		lb.setIcon(ii);
		Expressway.panel_cars.add(lb);
	}

	@Override
	protected void display(int x, int y) {
		//显示在panel_cars
		if( (this.isRight && x>=RIGHT_BOUND) || (!this.isRight && x<=LEFT_BOUND)) {
			if(this.isRight) {//到达终点后，终点队列的尾部中添加该车，路上的车链表删除该车，并减少在路上车辆的计数器
				Expressway.XnIvecoQ.add(this);
				Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]Iveco "+this.number+" Arrived at Xi'An");
			}else{
				Expressway.BjIvecoQ.add(this);
				Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]Iveco "+this.number+" Arrived at BaoJi");
			}
			for(int i=0;i<Expressway.ivecosOnRoadNum;i++) {
				if(this.number==Expressway.ivecosOnRoad.get(i).number) {
					Expressway.ivecosOnRoad.remove(i);
					Expressway.ivecosOnRoadNum--;
					this.isLaunched=false;
					this.lb.setVisible(isLaunched);
					break;
				}
			}
			
			this.headReverse();
		}
		lb.setBounds(x,y,80,80);
		
	}

	@Override
	protected void headReverse() {
		// TODO Auto-generated method stub
		//掉头之前清空乘客链表ps
		this.ps.clear();
		
		this.isRight=!this.isRight;
		if(this.isRight){
			ii=new ImageIcon(this.right);
			ii.setImage(ii.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));//缩放到80x80
			lb.setIcon(ii);
			Expressway.panel_cars.add(lb);
		}else{
			ii=new ImageIcon(this.left);
			ii.setImage(ii.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));//缩放到80x80
			lb.setIcon(ii);
			Expressway.panel_cars.add(lb);
			
		}
		
	}

	@Override
	protected void launch() {
		if( (Timer.hour>=8 && Timer.hour<=17 && Timer.min%20==0) || (Timer.hour==18&&Timer.min==0)) {//8点整第一班 每20分钟发一次 18点最后一班 
			this.isLaunched=true;
			return;
		}else {
			return;
		}
		
	}

	@Override
	protected void CarMoving() {//要伴随着Timer使用，每min移动一次
		// TODO Auto-generated method stub
		this.display(this.currentP,0);
		if(this.isRight) {
			this.currentP += this.speed;
		}else {
			this.currentP -= this.speed;
		}
	}

	@SuppressWarnings("unused")
	@Override
	protected int CheckStop() {//检查乘客是否即将到站
		// TODO Auto-generated method stub
		//只有快到站才检查，否则浪费时间
		int index=-1;
		if(this.isRight) {
			//前往西安方向各坐标近似取20 139 244 552 657 783 889
			if(this.currentP==139||this.currentP==244||this.currentP==552||this.currentP==657||this.currentP==783||this.currentP==889) {
				for(int i=0;i<this.ps.size();i++) {
					if(this.ps.get(i).destination==1&&this.currentP==139) {
						index=1;
						return index;
					}else if(this.ps.get(i).destination==2&&this.currentP==244) {
						index=2;
						return index;
					}else if(this.ps.get(i).destination==3&&this.currentP==552) {
						index=3;
						return index;
					}else if(this.ps.get(i).destination==4&&this.currentP==657) {
						index=4;
						return index;
					}else if(this.ps.get(i).destination==5&&this.currentP==783) {
						index=5;
						return index;
					}else if(this.ps.get(i).destination==6&&this.currentP==889) {
						index=6;
						return index;
					}else {
						return -1;
					}
				}
			}else {
				return -1;
			}
		}else {
			//前往宝鸡方向的各个坐标近似取22 141 246 554 659 778 890
			if(this.currentP==22||this.currentP==141||this.currentP==246||this.currentP==554||this.currentP==659||this.currentP==778) {
				for(int i=0;i<this.ps.size();i++) {
					if(this.ps.get(i).destination==0&&this.currentP==22) {
						index=0;
						return index;
					}else if(this.ps.get(i).destination==1&&this.currentP==141) {
						index=1;
						return index;
					}else if(this.ps.get(i).destination==2&&this.currentP==246) {
						index=2;
						return index;
					}else if(this.ps.get(i).destination==3&&this.currentP==554) {
						index=3;
						return index;
					}else if(this.ps.get(i).destination==4&&this.currentP==659) {
						index=4;
						return index;
					}else if(this.ps.get(i).destination==5&&this.currentP==778) {
						index=5;
						return index;
					}else {
						return -1;
					}
				}
			}else {
				return -1;
			}
		}
		return index;//如果是-1表示不需要停车
		
	}

	@Override
	protected void Stop(int index) {//CheckStop返回index不是-1，就执行Stop，在index站点等待两分钟，并且让乘客下车
		// TODO Auto-generated method stub
		//停2分钟的部分
		if(index==-1&&this.temp==0) {//如果没有执行过stop并且index是-1，说明不需要停车
			return;//第一次执行stop后，ps链表中已经没有需要下车的乘客了，下一分钟再执行checkstop后返回-1，但此时temp已经是1，所以函数不会返回
		}
		this.isLaunched=false;
		Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+"Iveco"+this.number+" Stops");
		this.temp++;
		if(this.temp==2) {
			this.temp=0;
			this.isLaunched=true;//isLaunched来控制汽车是否移动
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+"Iveco"+this.number+" ReLauched!");
			return;
		}
		//利用index来判断ps链表中哪些乘客需要下车
		int count=0;//用来计数下车的乘客，最终输出到console中避免繁琐
		//这里直接遍历ps链表，注意size会减少的情况
		for(int i=0;i<this.ps.size();i++) {
			if(this.ps.get(i).destination==index) {
				this.ps.remove(i--);//必须减减，否则size()变小，遍历不全
				count++;
			}
		}
		//根据index输出
		switch(index) {
		case 0:
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+count+"Passengers"+" Arrived at BaoJi Station");
			break;
		case 1:
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+count+"Passengers"+" Arrived at GaoZhen Station");
			break;
		case 2:
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+count+"Passengers"+" Arrived at CaijiaPo Station");
			break;
		case 3:
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+count+"Passengers"+" Arrived at WuGong Station");
			break;
		case 4:
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+count+"Passengers"+" Arrived at XingPin Station");
			break;
		case 5:
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+count+"Passengers"+" Arrived at XianYang Station");
			break;
		case 6:
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+count+"Passengers"+" Arrived at Xi'An Station");
			break;
		}
	}
	
}

//沃尔沃 速度是10像素每分钟，也就是2公里每分钟  容量40
//上午8:30开始，每小时一班，最后一班为下午5:30
class Volvo extends PubVehicles{
	private int temp=0;
	public Image right=Toolkit.getDefaultToolkit().createImage("images/Volvo_Right.png");
	public Image left=Toolkit.getDefaultToolkit().createImage("images/Volvo_Left.png");
	
	Volvo(boolean isRight,int number, int speed, int capacity,int duration) {
		super(isRight,number, speed,capacity,duration);
		lb=new JLabel();
		ImageIcon ii=null;
		//选择车头朝向
		if(isRight) {//如果车头向右
			ii=new ImageIcon(this.right);	
			this.currentP=LEFT_BOUND;
		}else {
			ii=new ImageIcon(this.left);
			this.currentP=RIGHT_BOUND;
		}
		ii.setImage(ii.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));//缩放到80x80
		lb.setToolTipText("Volvo "+this.number);//鼠标移到上面有信息显示
		lb.setVisible(true);
		lb.setIcon(ii);
		Expressway.panel_cars.add(lb);
	}

	@Override
	protected void display(int x, int y) {
		//显示在panel_cars
		if( (this.isRight && x>=RIGHT_BOUND) || (!this.isRight && x<=LEFT_BOUND)) {
			if(this.isRight) {//到达终点后，终点队列的尾部中添加该车，路上的车链表删除该车，并减少在路上车辆的计数器
				Expressway.XnVolvoQ.add(this);
				Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]Volvo "+this.number+" Arrived at Xi'An");
			}else{
				Expressway.BjVolvoQ.add(this);
				Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]Volvo "+this.number+" Arrived at BaoJi");
			}
			for(int i=0;i<Expressway.volvosOnRoadNum;i++) {
				if(this.number==Expressway.volvosOnRoad.get(i).number) {
					Expressway.volvosOnRoad.remove(i);
					Expressway.volvosOnRoadNum--;
					this.isLaunched=false;
					this.lb.setVisible(false);
					break;
				}
			}
			this.headReverse();
		}
		lb.setBounds(x,y,80,80);
				
	}

	@Override
	protected void headReverse() {
		// TODO Auto-generated method stub
		//掉头之前清空ps链表
		this.ps.clear();
		
		this.isRight=!this.isRight;
		if(this.isRight){
			ii=new ImageIcon(this.right);
			ii.setImage(ii.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));//缩放到80x80
			lb.setIcon(ii);
			Expressway.panel_cars.add(lb);
		}else{
			ii=new ImageIcon(this.left);
			ii.setImage(ii.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));//缩放到80x80
			lb.setIcon(ii);
			Expressway.panel_cars.add(lb);
			
		}
	}

	@Override
	protected void launch() {
		// TODO Auto-generated method stub
		if(Timer.min==30 && Timer.hour>=8 && Timer.hour<=17) {//8点30第一班，每小时一班，最后一班17点30
			this.isLaunched=true;
			return;
		}else {
			return;
		}
	}

	@Override
	protected void CarMoving() {//要伴随着Timer使用，每min移动一次
		// TODO Auto-generated method stub
		this.display(this.currentP,0);
		if(this.isRight) {
			this.currentP += this.speed;
		}else {
			this.currentP -= this.speed;
		}
	}

	@SuppressWarnings("unused")
	@Override
	protected int CheckStop() {
		// TODO Auto-generated method stub
		//只有快到站才检查，否则浪费时间
		int index=-1;
		if(this.isRight) {
			//前往西安方向各坐标近似取20 140 (240,250) (550,560) 660 780 890
			if(this.currentP==140||this.currentP==240||this.currentP==550||this.currentP==660||this.currentP==780||this.currentP==890) {
				for(int i=0;i<this.ps.size();i++) {
					if(this.ps.get(i).destination==1&&this.currentP==140) {
						index=1;
						return index;
					}else if(this.ps.get(i).destination==2&&this.currentP==240) {
						index=2;
						return index;
					}else if(this.ps.get(i).destination==3&&this.currentP==550) {
						index=3;
						return index;
					}else if(this.ps.get(i).destination==4&&this.currentP==660) {
						index=4;
						return index;
					}else if(this.ps.get(i).destination==5&&this.currentP==780) {
						index=5;
						return index;
					}else if(this.ps.get(i).destination==6&&this.currentP==890) {
						index=6;
						return index;
					}else {
						return -1;
					}
				}
			}else {
				return -1;
			}
		}else {
			//前往宝鸡方向的各个坐标近似取20 140 (240,250) (550,560) 660 780 890
			if(this.currentP==20||this.currentP==140||this.currentP==250||this.currentP==560||this.currentP==660||this.currentP==780) {
				for(int i=0;i<this.ps.size();i++) {
					if(this.ps.get(i).destination==0&&this.currentP==20) {
						index=0;
						return index;
					}else if(this.ps.get(i).destination==1&&this.currentP==140) {
						index=1;
						return index;
					}else if(this.ps.get(i).destination==2&&this.currentP==250) {
						index=2;
						return index;
					}else if(this.ps.get(i).destination==3&&this.currentP==560) {
						index=3;
						return index;
					}else if(this.ps.get(i).destination==4&&this.currentP==660) {
						index=4;
						return index;
					}else if(this.ps.get(i).destination==5&&this.currentP==780) {
						index=5;
						return index;
					}else {
						return -1;
					}
				}
			}else {
				return -1;
			}
		}
		return index;//如果是-1表示不需要停车		
	}

	@Override
	protected void Stop(int index) {
		// TODO Auto-generated method stub
		//停2分钟的部分
		if(index==-1&&this.temp==0) {//如果没有执行过stop并且index是-1，说明不需要停车
			return;//第一次执行stop后，ps链表中已经没有需要下车的乘客了，下一分钟再执行checkstop后返回-1，但此时temp已经是1，所以函数不会返回
		}
		this.isLaunched=false;
		Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+"Volvo"+this.number+" Stops");
		this.temp++;
		if(this.temp==2) {
			this.temp=0;
			this.isLaunched=true;//isLaunched来控制汽车是否移动
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+"Volvo"+this.number+" ReLauched!");
			return;
		}
		//利用index来判断ps链表中哪些乘客需要下车
		int count=0;//用来计数下车的乘客，最终输出到console中避免繁琐
		//这里直接遍历ps链表，注意size会减少的情况
		for(int i=0;i<this.ps.size();i++) {
			if(this.ps.get(i).destination==index) {
				this.ps.remove(i--);//必须减减，否则size()变小，遍历不全
				count++;
			}
		}
		//根据index输出
		switch(index) {
		case 0:
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+count+"Passengers"+" Arrived at BaoJi Station");
			break;
		case 1:
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+count+"Passengers"+" Arrived at GaoZhen Station");
			break;
		case 2:
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+count+"Passengers"+" Arrived at CaijiaPo Station");
			break;
		case 3:
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+count+"Passengers"+" Arrived at WuGong Station");
			break;
		case 4:
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+count+"Passengers"+" Arrived at XingPin Station");
			break;
		case 5:
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+count+"Passengers"+" Arrived at XianYang Station");
			break;
		case 6:
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+count+"Passengers"+" Arrived at Xi'An Station");
			break;
		}
	}
}

public class Vehicles extends JPanel{
	/**
	 * 默认生成的序列号
	 */
	private static final long serialVersionUID = 1L;
	
	
	
}