package Expressway;

import java.awt.*;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

abstract class PubVehicles{
	public static final int LEFT_BOUND=20;//�����˶��߽磬x������ 20~890  ���ٹ�·��870����
	public static final int RIGHT_BOUND=890;
	protected boolean isRight;//�Ƿ�������ʻ
	protected boolean isLaunched=false;//�Ƿ񷢳���
	protected int currentP;//��ǰ��λ��
	protected int number;//�������ı��
	protected int speed;//����
	protected int capacity;//����
	protected LinkedList<Passenger> ps=new LinkedList<Passenger>();//�˿�������¼���ϵĸ����˿���Ϣ  ���յ�վ�ǵ����
	protected int duration;//�����ļ��
	protected JLabel lb;//���ڴ�ͼ��JLabel
	protected ImageIcon ii;//ͼƬ
	
	PubVehicles(boolean isRight,int number,int speed,int capacity,int duration){
		this.isRight=isRight;
		this.number=number;
		this.speed=speed;
		this.capacity=capacity;
		this.duration=duration;
	}
	
	abstract protected void headReverse();//�����յ�վ��ͷ
	abstract protected void display(int x,int y);//��ʾ��panel_car�����
	abstract protected void launch();//ÿ��һ��ʱ�䷢��
	abstract protected void CarMoving();//�����ƶ�
	abstract protected int CheckStop();//��鳵���Ƿ��г˿ͼ�����վ ���û�У�����-1������У����ص�ǰվ��ı��0��1��2��3��4��5��6
	abstract protected void Stop(int index);//��indexվͣ�����ȴ��˿��³�
}

//��ά�� �ٶ���7����ÿ�룬Ҳ����1.4����ÿ����  ����21
//����8:00��ʼ��ÿ20����һ�࣬���һ��Ϊ����6:00
class Iveco extends PubVehicles{
	
	private int temp=0;//����ͣ2���ӵ���ʱ����
	
	public Image right=Toolkit.getDefaultToolkit().createImage("images/Iveco_Right.png");
	public Image left=Toolkit.getDefaultToolkit().createImage("images/Iveco_Left.png");
	
	//��ʼ��
	Iveco(boolean isRight,int number, int speed, int capacity,int duration) {
		super(isRight,number, speed,capacity,duration);
		lb=new JLabel();
		ii=null;
		//ѡ��ͷ����
		if(isRight) {//�����ͷ����
			ii=new ImageIcon(this.right);	
			this.currentP=LEFT_BOUND;
		}else {
			ii=new ImageIcon(this.left);
			this.currentP=RIGHT_BOUND;
		}
		ii.setImage(ii.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));//���ŵ�80x80
		lb.setToolTipText("Iveco "+this.number);//����Ƶ���������Ϣ��ʾ
		lb.setVisible(true);
		lb.setIcon(ii);
		Expressway.panel_cars.add(lb);
	}

	@Override
	protected void display(int x, int y) {
		//��ʾ��panel_cars
		if( (this.isRight && x>=RIGHT_BOUND) || (!this.isRight && x<=LEFT_BOUND)) {
			if(this.isRight) {//�����յ���յ���е�β������Ӹó���·�ϵĳ�����ɾ���ó�����������·�ϳ����ļ�����
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
		//��ͷ֮ǰ��ճ˿�����ps
		this.ps.clear();
		
		this.isRight=!this.isRight;
		if(this.isRight){
			ii=new ImageIcon(this.right);
			ii.setImage(ii.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));//���ŵ�80x80
			lb.setIcon(ii);
			Expressway.panel_cars.add(lb);
		}else{
			ii=new ImageIcon(this.left);
			ii.setImage(ii.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));//���ŵ�80x80
			lb.setIcon(ii);
			Expressway.panel_cars.add(lb);
			
		}
		
	}

	@Override
	protected void launch() {
		if( (Timer.hour>=8 && Timer.hour<=17 && Timer.min%20==0) || (Timer.hour==18&&Timer.min==0)) {//8������һ�� ÿ20���ӷ�һ�� 18�����һ�� 
			this.isLaunched=true;
			return;
		}else {
			return;
		}
		
	}

	@Override
	protected void CarMoving() {//Ҫ������Timerʹ�ã�ÿmin�ƶ�һ��
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
	protected int CheckStop() {//���˿��Ƿ񼴽���վ
		// TODO Auto-generated method stub
		//ֻ�п쵽վ�ż�飬�����˷�ʱ��
		int index=-1;
		if(this.isRight) {
			//ǰ������������������ȡ20 139 244 552 657 783 889
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
			//ǰ����������ĸ����������ȡ22 141 246 554 659 778 890
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
		return index;//�����-1��ʾ����Ҫͣ��
		
	}

	@Override
	protected void Stop(int index) {//CheckStop����index����-1����ִ��Stop����indexվ��ȴ������ӣ������ó˿��³�
		// TODO Auto-generated method stub
		//ͣ2���ӵĲ���
		if(index==-1&&this.temp==0) {//���û��ִ�й�stop����index��-1��˵������Ҫͣ��
			return;//��һ��ִ��stop��ps�������Ѿ�û����Ҫ�³��ĳ˿��ˣ���һ������ִ��checkstop�󷵻�-1������ʱtemp�Ѿ���1�����Ժ������᷵��
		}
		this.isLaunched=false;
		Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+"Iveco"+this.number+" Stops");
		this.temp++;
		if(this.temp==2) {
			this.temp=0;
			this.isLaunched=true;//isLaunched�����������Ƿ��ƶ�
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+"Iveco"+this.number+" ReLauched!");
			return;
		}
		//����index���ж�ps��������Щ�˿���Ҫ�³�
		int count=0;//���������³��ĳ˿ͣ����������console�б��ⷱ��
		//����ֱ�ӱ���ps����ע��size����ٵ����
		for(int i=0;i<this.ps.size();i++) {
			if(this.ps.get(i).destination==index) {
				this.ps.remove(i--);//�������������size()��С��������ȫ
				count++;
			}
		}
		//����index���
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

//�ֶ��� �ٶ���10����ÿ���ӣ�Ҳ����2����ÿ����  ����40
//����8:30��ʼ��ÿСʱһ�࣬���һ��Ϊ����5:30
class Volvo extends PubVehicles{
	private int temp=0;
	public Image right=Toolkit.getDefaultToolkit().createImage("images/Volvo_Right.png");
	public Image left=Toolkit.getDefaultToolkit().createImage("images/Volvo_Left.png");
	
	Volvo(boolean isRight,int number, int speed, int capacity,int duration) {
		super(isRight,number, speed,capacity,duration);
		lb=new JLabel();
		ImageIcon ii=null;
		//ѡ��ͷ����
		if(isRight) {//�����ͷ����
			ii=new ImageIcon(this.right);	
			this.currentP=LEFT_BOUND;
		}else {
			ii=new ImageIcon(this.left);
			this.currentP=RIGHT_BOUND;
		}
		ii.setImage(ii.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));//���ŵ�80x80
		lb.setToolTipText("Volvo "+this.number);//����Ƶ���������Ϣ��ʾ
		lb.setVisible(true);
		lb.setIcon(ii);
		Expressway.panel_cars.add(lb);
	}

	@Override
	protected void display(int x, int y) {
		//��ʾ��panel_cars
		if( (this.isRight && x>=RIGHT_BOUND) || (!this.isRight && x<=LEFT_BOUND)) {
			if(this.isRight) {//�����յ���յ���е�β������Ӹó���·�ϵĳ�����ɾ���ó�����������·�ϳ����ļ�����
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
		//��ͷ֮ǰ���ps����
		this.ps.clear();
		
		this.isRight=!this.isRight;
		if(this.isRight){
			ii=new ImageIcon(this.right);
			ii.setImage(ii.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));//���ŵ�80x80
			lb.setIcon(ii);
			Expressway.panel_cars.add(lb);
		}else{
			ii=new ImageIcon(this.left);
			ii.setImage(ii.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));//���ŵ�80x80
			lb.setIcon(ii);
			Expressway.panel_cars.add(lb);
			
		}
	}

	@Override
	protected void launch() {
		// TODO Auto-generated method stub
		if(Timer.min==30 && Timer.hour>=8 && Timer.hour<=17) {//8��30��һ�࣬ÿСʱһ�࣬���һ��17��30
			this.isLaunched=true;
			return;
		}else {
			return;
		}
	}

	@Override
	protected void CarMoving() {//Ҫ������Timerʹ�ã�ÿmin�ƶ�һ��
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
		//ֻ�п쵽վ�ż�飬�����˷�ʱ��
		int index=-1;
		if(this.isRight) {
			//ǰ������������������ȡ20 140 (240,250) (550,560) 660 780 890
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
			//ǰ����������ĸ����������ȡ20 140 (240,250) (550,560) 660 780 890
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
		return index;//�����-1��ʾ����Ҫͣ��		
	}

	@Override
	protected void Stop(int index) {
		// TODO Auto-generated method stub
		//ͣ2���ӵĲ���
		if(index==-1&&this.temp==0) {//���û��ִ�й�stop����index��-1��˵������Ҫͣ��
			return;//��һ��ִ��stop��ps�������Ѿ�û����Ҫ�³��ĳ˿��ˣ���һ������ִ��checkstop�󷵻�-1������ʱtemp�Ѿ���1�����Ժ������᷵��
		}
		this.isLaunched=false;
		Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+"Volvo"+this.number+" Stops");
		this.temp++;
		if(this.temp==2) {
			this.temp=0;
			this.isLaunched=true;//isLaunched�����������Ƿ��ƶ�
			Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+"Volvo"+this.number+" ReLauched!");
			return;
		}
		//����index���ж�ps��������Щ�˿���Ҫ�³�
		int count=0;//���������³��ĳ˿ͣ����������console�б��ⷱ��
		//����ֱ�ӱ���ps����ע��size����ٵ����
		for(int i=0;i<this.ps.size();i++) {
			if(this.ps.get(i).destination==index) {
				this.ps.remove(i--);//�������������size()��С��������ȫ
				count++;
			}
		}
		//����index���
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
	 * Ĭ�����ɵ����к�
	 */
	private static final long serialVersionUID = 1L;
	
	
	
}