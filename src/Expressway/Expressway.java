package Expressway;

import java.util.*;
import java.awt.*;

public class Expressway {
	
	//ר�Ų鿴�����˶�״̬����� 980x720  ·���趨Ϊ870���أ�174ǧ�ף�
	public static DisplayPanel panel_cars=new DisplayPanel(0,0,980,100,new Color(255,250,250));
	
	//ר�ŷ��ù�·�����980x100
	public static DisplayPanel panel_road=new DisplayPanel(0,100,980,100,new Color(255,250,250));

	//ר�Ÿ��Լ����õ����300x100
	private static DisplayPanel panelformyself=new DisplayPanel(980,100,300,100,new Color(35,35,35));
	private static Label personalinfo=null;
	
	//��־���
	//�����ͱ���վ��ĳ����ͳ˿���Ŀ��Ϣ��
	public static DisplayDialogPanel panel_xian_station=new DisplayDialogPanel(965,200,300,520,new Color(255,250,250));
	public static DisplayDialog XNStation=new DisplayDialog("Xi'An Station",50,50,0,0,300,520);
	public static DisplayDialogPanel panel_baoji_station=new DisplayDialogPanel(665,200,300,520,new Color(255,250,250));
	public static DisplayDialog BJStation=new DisplayDialog("BaoJi Station",50,50,0,0,300,520);
	
	//�ڸ���·�ϵĳ�������Ϣ
	public static DisplayDialogPanel panel_on_way=new DisplayDialogPanel(315,200,350,520,new Color(255,250,250));
	public static DisplayDialog OntheWayInfo=new DisplayDialog("On The Expressway",50,50,0,0,350,520);
	
	//Console�նˣ���¼�����������˿͵���Ϣ
	public static DisplayDialogPanel panel_console=new DisplayDialogPanel(0,200,315,520,new Color(255,250,250));
	public static DisplayDialog console=new DisplayDialog("Console",50,50,0,0,315,520);
	
	//����Frame
	public static DisplayFrame display=new DisplayFrame("Expressway");
	
	//���ڼ�ʱ����� �� ��ǩ
	public static DisplayPanel panel_timer=new DisplayPanel(980,0,300,100,new Color(35,35,35));
	public static Label timer=null;
	
	
	//XN��BJ�ڷ��濪ʼʱӵ�еĿͳ�����XNW��XNY��BJW��BJY�����Ȱ�XNW=5��XNY=12��BJW=4��BJY=15����
	public static int XN_Volvo = 5;
	public static int XN_Iveco = 12;
	public static int BJ_Volvo = 4;
	public static int BJ_Iveco = 15;
	
	//�����½���������
	public static Iveco ivecos[]=new Iveco[XN_Iveco+BJ_Iveco];//��������������ģ��ڱ������
	public static Volvo volvos[]=new Volvo[XN_Volvo+BJ_Volvo];
	
	//�����ǵ����յ�վ�󼴽���ת�ĳ������У�����FIFO���ŶӰ��չ��򷢳�
	public static Queue<Volvo> BjVolvoQ=new LinkedList<Volvo>();
	public static Queue<Volvo> XnVolvoQ=new LinkedList<Volvo>();
	public static Queue<Iveco> BjIvecoQ=new LinkedList<Iveco>();
	public static Queue<Iveco> XnIvecoQ=new LinkedList<Iveco>();
	
	//��·�ϵĳ���
	public static LinkedList<Iveco> ivecosOnRoad=new LinkedList<Iveco>();
	public static LinkedList<Volvo> volvosOnRoad=new LinkedList<Volvo>();
	//���ڼ�¼��·�ϳ�������Ŀ
	public static int ivecosOnRoadNum=0;
	public static int volvosOnRoadNum=0;
	
	//�˿���ص�����
	//�ֱ�������վ�ͱ���վ�ȳ��ĳ˿Ͷ���
	public static Queue<Passenger> XnPassengerQ=new LinkedList<Passenger>();
	public static Queue<Passenger> BjPassengerQ=new LinkedList<Passenger>();
	//ÿ���ӵ���˿͵���������
	public static int Pn=2;
	
	
	
	public static void initialization() {
		//��ʼ��
		for(int i=0;i<ivecos.length;i++) {//��žͰ��ձ������ȱ����
			if(i < BJ_Iveco) {
				//����վ������һ����BJ_Iveco��,Ҳ����������ʻ
				ivecos[i]=new Iveco(true,i,7,21,20);
				BjIvecoQ.add(ivecos[i]);
			}else {
				//����վ������һ����XN_Iveco��,Ҳ����������ʻ
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
		
		//�����õ�display frame
		display.add(panel_cars);
		display.add(panel_timer);
		display.add(panelformyself);
		display.add(panel_road);
		display.add(panel_xian_station);
		display.add(panel_baoji_station);
		display.add(panel_on_way);
		display.add(panel_console);
		
		//����ʱ����ʾ�ҽ���
		timer=new Label();
		timer.setText(Timer.getTime());
		timer.setFont(new Font(null,Font.PLAIN,25));
		timer.setBounds(0,0,300,100);
		timer.setForeground(new Color(255,255,255));
		panel_timer.add(timer);
		timer.setVisible(true);
		
		//����������Ϣ
		personalinfo=new Label();
		personalinfo.setText("� 21009201418");
		personalinfo.setFont(new Font(null,Font.PLAIN,25));
		personalinfo.setBounds(0,0,300,100);
		personalinfo.setForeground(new Color(255,255,255));
		panelformyself.add(personalinfo);
		personalinfo.setVisible(true);
		
		//��־����
		panel_xian_station.setViewportView(XNStation);
		panel_baoji_station.setViewportView(BJStation);

		panel_on_way.setViewportView(OntheWayInfo);
		console.setFont(new Font("����",Font.BOLD,15));
		panel_console.setViewportView(console);
		
		
		//��·
		//����ȡ60 ԭ����������ߵ����Ͻ�x����Ϊ20 ����ͼƬ��80��ȡ������������Ϊ�ʵ㣬Ҳ����40�ĳ��ȣ�����������20+40=60
		RoadButton BJ_GZ=new RoadButton("BaoJi to GaoZhen",60,0,120);//��120����
		RoadButton GZ_CP=new RoadButton("GaoZhen to CaijiaPo",180,0,105);//��105����
		RoadButton CP_WG=new RoadButton("CaijiaPo to WuGong",285,0,310);//��310����
		RoadButton WG_XP=new RoadButton("WuGong to XingPin",595,0,105);;
		RoadButton XP_XY=new RoadButton("XingPin to Xianyang",700,0,120);
		RoadButton XY_XN=new RoadButton("XianYang to XiAn",820,0,110);
		
		panel_road.add(BJ_GZ);
		panel_road.add(GZ_CP);
		panel_road.add(CP_WG);
		panel_road.add(WG_XP);
		panel_road.add(XP_XY);
		panel_road.add(XY_XN);
		
		//���ˢ��һ�� �Խ���������г�����Ҫ�϶�Frame����������ʾ��������
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
		
		//��ѭ��
		while(true) {
			
			
			Timer.FreshTime(timer);//����Timer�������FreshTime����ʱ��ˢ��
			
		}
	}

}
