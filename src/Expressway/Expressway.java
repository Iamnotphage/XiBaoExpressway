package Expressway;

import java.util.*;
import java.awt.*;

public class Expressway {
	
	//专门查看汽车运动状态的面板 980x720  路长设定为870像素（174千米）
	public static DisplayPanel panel_cars=new DisplayPanel(0,0,980,100,new Color(255,250,250));
	
	//专门放置公路的面板980x100
	public static DisplayPanel panel_road=new DisplayPanel(0,100,980,100,new Color(255,250,250));

	//专门给自己设置的面板300x100
	private static DisplayPanel panelformyself=new DisplayPanel(980,100,300,100,new Color(35,35,35));
	private static Label personalinfo=null;
	
	//日志面板
	//西安和宝鸡站点的车辆和乘客数目信息版
	public static DisplayDialogPanel panel_xian_station=new DisplayDialogPanel(965,200,300,520,new Color(255,250,250));
	public static DisplayDialog XNStation=new DisplayDialog("Xi'An Station",50,50,0,0,300,520);
	public static DisplayDialogPanel panel_baoji_station=new DisplayDialogPanel(665,200,300,520,new Color(255,250,250));
	public static DisplayDialog BJStation=new DisplayDialog("BaoJi Station",50,50,0,0,300,520);
	
	//在高速路上的车辆的信息
	public static DisplayDialogPanel panel_on_way=new DisplayDialogPanel(315,200,350,520,new Color(255,250,250));
	public static DisplayDialog OntheWayInfo=new DisplayDialog("On The Expressway",50,50,0,0,350,520);
	
	//Console终端，记录发车，产生乘客等信息
	public static DisplayDialogPanel panel_console=new DisplayDialogPanel(0,200,315,520,new Color(255,250,250));
	public static DisplayDialog console=new DisplayDialog("Console",50,50,0,0,315,520);
	
	//创建Frame
	public static DisplayFrame display=new DisplayFrame("Expressway");
	
	//用于计时的面板 和 标签
	public static DisplayPanel panel_timer=new DisplayPanel(980,0,300,100,new Color(35,35,35));
	public static Label timer=null;
	
	
	//XN和BJ在仿真开始时拥有的客车数量XNW、XNY、BJW和BJY。可先按XNW=5，XNY=12，BJW=4，BJY=15进行
	public static int XN_Volvo = 5;
	public static int XN_Iveco = 12;
	public static int BJ_Volvo = 4;
	public static int BJ_Iveco = 15;
	
	//这里新建公共汽车
	public static Iveco ivecos[]=new Iveco[XN_Iveco+BJ_Iveco];//包含了在西安点的，在宝鸡点的
	public static Volvo volvos[]=new Volvo[XN_Volvo+BJ_Volvo];
	
	//这里是到达终点站后即将反转的车辆队列，队列FIFO，排队按照规则发车
	public static Queue<Volvo> BjVolvoQ=new LinkedList<Volvo>();
	public static Queue<Volvo> XnVolvoQ=new LinkedList<Volvo>();
	public static Queue<Iveco> BjIvecoQ=new LinkedList<Iveco>();
	public static Queue<Iveco> XnIvecoQ=new LinkedList<Iveco>();
	
	//在路上的车辆
	public static LinkedList<Iveco> ivecosOnRoad=new LinkedList<Iveco>();
	public static LinkedList<Volvo> volvosOnRoad=new LinkedList<Volvo>();
	//用于记录在路上车辆的数目
	public static int ivecosOnRoadNum=0;
	public static int volvosOnRoadNum=0;
	
	//乘客相关的数据
	//分别在西安站和宝鸡站等车的乘客队列
	public static Queue<Passenger> XnPassengerQ=new LinkedList<Passenger>();
	public static Queue<Passenger> BjPassengerQ=new LinkedList<Passenger>();
	//每分钟到达乘客的人数上限
	public static int Pn=2;
	
	
	
	public static void initialization() {
		//初始化
		for(int i=0;i<ivecos.length;i++) {//编号就按照宝鸡优先编号了
			if(i < BJ_Iveco) {
				//宝鸡站出发的一共有BJ_Iveco个,也就是向右行驶
				ivecos[i]=new Iveco(true,i,7,21,20);
				BjIvecoQ.add(ivecos[i]);
			}else {
				//西安站出发的一共有XN_Iveco个,也就是向左行驶
				ivecos[i]=new Iveco(false,i,7,21,20);
				XnIvecoQ.add(ivecos[i]);
			}
		}
		for(int i=0;i<volvos.length;i++) {
			if(i<BJ_Volvo) {
				volvos[i]=new Volvo(true,i,10,40,60);
				BjVolvoQ.add(volvos[i]);
			}else {
				volvos[i]=new Volvo(false,i,10,40,60);
				XnVolvoQ.add(volvos[i]);
			}
		}
		
		//面板放置到display frame
		display.add(panel_cars);
		display.add(panel_timer);
		display.add(panelformyself);
		display.add(panel_road);
		display.add(panel_xian_station);
		display.add(panel_baoji_station);
		display.add(panel_on_way);
		display.add(panel_console);
		
		//创建时间显示揭界面
		timer=new Label();
		timer.setText(Timer.getTime());
		timer.setFont(new Font(null,Font.PLAIN,25));
		timer.setBounds(0,0,300,100);
		timer.setForeground(new Color(255,255,255));
		panel_timer.add(timer);
		timer.setVisible(true);
		
		//创建作者信息
		personalinfo=new Label();
		personalinfo.setText("杨晨 21009201418");
		personalinfo.setFont(new Font(null,Font.PLAIN,25));
		personalinfo.setBounds(0,0,300,100);
		personalinfo.setForeground(new Color(255,255,255));
		panelformyself.add(personalinfo);
		personalinfo.setVisible(true);
		
		//日志部分
		panel_xian_station.setViewportView(XNStation);
		panel_baoji_station.setViewportView(BJStation);

		panel_on_way.setViewportView(OntheWayInfo);
		console.setFont(new Font("黑体",Font.BOLD,15));
		panel_console.setViewportView(console);
		
		
		//铺路
		//这里取60 原因：汽车最左边的左上角x坐标为20 汽车图片长80，取汽车几何中心为质点，也就是40的长度，这样起点就是20+40=60
		RoadButton BJ_GZ=new RoadButton("BaoJi to GaoZhen",60,0,120);//长120像素
		RoadButton GZ_CP=new RoadButton("GaoZhen to CaijiaPo",180,0,105);//长105像素
		RoadButton CP_WG=new RoadButton("CaijiaPo to WuGong",285,0,310);//长310像素
		RoadButton WG_XP=new RoadButton("WuGong to XingPin",595,0,105);;
		RoadButton XP_XY=new RoadButton("XingPin to Xianyang",700,0,120);
		RoadButton XY_XN=new RoadButton("XianYang to XiAn",820,0,110);
		
		panel_road.add(BJ_GZ);
		panel_road.add(GZ_CP);
		panel_road.add(CP_WG);
		panel_road.add(WG_XP);
		panel_road.add(XP_XY);
		panel_road.add(XY_XN);
		
		//面板刷新一下 以解决初次运行程序需要拖动Frame才能正常显示面板的问题
		panel_cars.setVisible(false);
		panel_cars.setVisible(true);
		panel_road.setVisible(false);
		panel_road.setVisible(true);
		panel_xian_station.setVisible(false);
		panel_xian_station.setVisible(true);
		panel_baoji_station.setVisible(false);
		panel_baoji_station.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		
		initialization();
		
		//主循环
		while(true) {
			
			
			Timer.FreshTime(timer);//调用Timer类里面的FreshTime进行时间刷新
			
		}
	}

}
