package Expressway;

import java.awt.Label;
import java.util.Random;

//Timer�࣬���ڼ�¼�����ʱ�䣬����ʱ��1���������ʱ��1����
//һ�ж�����ʱ��仯����ı仯
public class Timer {
	public static int day=0;
	public static int hour=7;
	public static int min=25;
	public static final int period=1000;
	
	public static String getTime() {
		if(min==60) {
			hour++;
			min=0;
		}
		if(hour==20 && min==15) {//���ڹ���ʱ����7��30��18��00���������ÿɼ�ʱ��Ϊ7��15��18��15
			hour=7;
			min=15;
			day++;
			Expressway.XnPassengerQ.clear();
			Expressway.BjPassengerQ.clear();
		}
		String str=new String("Now: Day "+day+" Time: "+hour+" : "+min+" ");
		return str;
	}

	public static void FreshTime(Label timer) {//ˢ��ʱ���ͬʱ��ˢ�������˶�״̬
		min++;
		timer.setText(getTime());//����ʱ����ʾ
		//�ӳ�1���ˢ��״̬
		//��Ҫˢ�µ���: ����������λ�� �����Ϣ �˿���Ϊ
		try {
			//�����˿�
			ProducePassengerstoBaoJi();
			ProducePassengerstoXiAn();
			
			//����ǰ
			LetPassengersAboardVolvo();
			LetPassengersAboardIveco();
			
			
			for(int i=0;i<Expressway.ivecosOnRoad.size();i++) {
				Expressway.ivecosOnRoad.get(i).Stop(Expressway.ivecosOnRoad.get(i).CheckStop());
			}
			for(int i=0;i<Expressway.volvosOnRoad.size();i++) {
				Expressway.volvosOnRoad.get(i).Stop(Expressway.volvosOnRoad.get(i).CheckStop());
			}
			
			//����&�����ƶ�
			CarMovingWithTime();
			
			//ˢ�������Ϣ
			FreshStationInfo();
			FreshOnTheWayInfo();
			
			
			
			Thread.sleep(period);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void CarMovingWithTime() {
		
		//��ά�·���
		if(Expressway.BjIvecoQ.peek()!=null) {//��������ά�·���
			Expressway.BjIvecoQ.peek().launch();
			if(Expressway.BjIvecoQ.peek().isLaunched) {
				Expressway.ivecosOnRoad.add(Expressway.BjIvecoQ.poll());
				Expressway.console.append("\n["+hour+":"+min+"]Iveco "+Expressway.ivecosOnRoad.get(Expressway.ivecosOnRoadNum).number+" Launched from BaoJi");
				Expressway.ivecosOnRoad.get(Expressway.ivecosOnRoadNum).lb.setVisible(true);
				Expressway.ivecosOnRoadNum++;
			}
		}
		if(Expressway.XnIvecoQ.peek()!=null) {//��������ά�·���
			Expressway.XnIvecoQ.peek().launch();
			if(Expressway.XnIvecoQ.peek().isLaunched) {
				Expressway.ivecosOnRoad.add(Expressway.XnIvecoQ.poll());
				Expressway.console.append("\n["+hour+":"+min+"]Iveco "+Expressway.ivecosOnRoad.get(Expressway.ivecosOnRoadNum).number+" Launched from Xi'An");
				Expressway.ivecosOnRoad.get(Expressway.ivecosOnRoadNum).lb.setVisible(true);
				Expressway.ivecosOnRoadNum++;
			}
		}
		for(int i=0;i<Expressway.ivecosOnRoadNum;i++) {//����·�ϳ������ƶ�
			if(Expressway.ivecosOnRoad.get(i).isLaunched) {
				Expressway.ivecosOnRoad.get(i).CarMoving();
			}
		}
		
		//�ֶ��ַ���
		if(Expressway.BjVolvoQ.peek()!=null) {//�������ֶ��ַ���
			Expressway.BjVolvoQ.peek().launch();
			if(Expressway.BjVolvoQ.peek().isLaunched) {
				Expressway.volvosOnRoad.add(Expressway.BjVolvoQ.poll());
				Expressway.console.append("\n["+hour+":"+min+"]Volvo "+Expressway.volvosOnRoad.get(Expressway.volvosOnRoadNum).number+" Launched from BaoJi");
				Expressway.volvosOnRoad.get(Expressway.volvosOnRoadNum).lb.setVisible(true);
				Expressway.volvosOnRoadNum++;
			}
		}
		if(Expressway.XnVolvoQ.peek()!=null) {//�������ֶ��ַ���
			Expressway.XnVolvoQ.peek().launch();
			if(Expressway.XnVolvoQ.peek().isLaunched) {
				Expressway.volvosOnRoad.add(Expressway.XnVolvoQ.poll());
				Expressway.console.append("\n["+hour+":"+min+"]Volvo "+Expressway.volvosOnRoad.get(Expressway.volvosOnRoadNum).number+" Launched from Xi'An");
				Expressway.volvosOnRoad.get(Expressway.volvosOnRoadNum).lb.setVisible(true);
				Expressway.volvosOnRoadNum++;
			}
		}
		for(int i=0;i<Expressway.volvosOnRoadNum;i++) {//����·�ϳ������ƶ�
			if(Expressway.volvosOnRoad.get(i).isLaunched) {
				Expressway.volvosOnRoad.get(i).CarMoving();
			}
		}
	}
	
	public static void FreshStationInfo() {
		//����վ������Ϣ��ʾ
		Expressway.XNStation.setText("Xi'An Station");
		for(Volvo i:Expressway.XnVolvoQ) {
			Expressway.XNStation.append("\n Volvo: "+i.number);
		}
		for(Iveco i:Expressway.XnIvecoQ) {
			Expressway.XNStation.append("\n Iveco: "+i.number);
		}
		Expressway.XNStation.append("\n�򳵳˿���: "+Expressway.XnPassengerQ.size());
		
		//����վ������Ϣ��ʾ
		Expressway.BJStation.setText("BaoJi Station");
		for(Volvo i:Expressway.BjVolvoQ) {
			Expressway.BJStation.append("\n Volvo: "+i.number);
		}
		for(Iveco i:Expressway.BjIvecoQ) {
			Expressway.BJStation.append("\n Iveco: "+i.number);
		}
		Expressway.BJStation.append("\n�򳵳˿���: "+Expressway.BjPassengerQ.size());
		
	}
	public static void FreshOnTheWayInfo() {
		Expressway.OntheWayInfo.setText("On The Expressway");
		for(Volvo i:Expressway.volvosOnRoad) {
			Expressway.OntheWayInfo.append("\n Volvo: "+i.number+"�˿�����: "+i.ps.size());
		}
		for(Iveco i:Expressway.ivecosOnRoad) {
			Expressway.OntheWayInfo.append("\n Iveco: "+i.number+"�˿�����: "+i.ps.size());
		}
	}
	
	//���� ������վ ǰ������վ�ĳ˿�
	public static void ProducePassengerstoBaoJi() {
		//����7��30������17��59 �Ų����˿�
		if(hour*60+min<450 || hour*60+min >1079) {
			return;
		}
		Random r=new Random();
		//ÿ�����������0~Pn���˿� [0,Pn]
		int PassengerNum=r.nextInt(Expressway.Pn+1);
		//�þ��ȷֲ�������ͬ�յ�[0,5]
		//Ҳ����[0,6) 012345
		for(int i=0;i<PassengerNum;i++) {
			Expressway.XnPassengerQ.add(new Passenger(false,(int)(Math.random()*6)));
		}
		if(PassengerNum!=0) {
			//Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+PassengerNum+"Passengers "+"Wait at Xi'An Station");
		}
	}
	
	//���� �ڱ���վ ǰ������վ�ĳ˿�
	public static void ProducePassengerstoXiAn() {
		//����7��30������17��59 �Ų����˿�
		if(hour*60+min<450 || hour*60+min >1079) {
			return;
		}
		Random r=new Random();
		//ÿ�����������0~Pn���˿� [0,Pn]
		int PassengerNum=r.nextInt(Expressway.Pn+1);
		//�þ��ȷֲ�������ͬ�յ�[1,6]
		//[1,7) Ҳ����123456

		for(int i=0;i<PassengerNum;i++) {
			Expressway.BjPassengerQ.add(new Passenger(true,(int)(Math.random()*6+1)));	
		}
		if(PassengerNum!=0) {
			//Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+PassengerNum+"Passengers "+"Wait at BaoJi Station");
		}
	}

	//���ų˿���Iveco�����������˿Ͷ���
	public static void LetPassengersAboardIveco() {
		
		//����վ
		for(int i=0;i<Expressway.BjPassengerQ.size();i++) {
			if(Expressway.BjPassengerQ.peek()!=null) {
				if(Expressway.BjPassengerQ.peek().AboardIveco()) {
					Expressway.BjPassengerQ.poll();
				}else {
					//�����ϳ�ʧ��ֱ��break
					break;
				}
			}
		}
		//����վ
		for(int i=0;i<Expressway.XnPassengerQ.size();i++) {
			if(Expressway.XnPassengerQ.peek()!=null) {
				if(Expressway.XnPassengerQ.peek().AboardIveco()) {
					Expressway.XnPassengerQ.poll();
				}else {
					break;
				}
			}
		}
	}
	
	public static void LetPassengersAboardVolvo() {
		//����վ
		for(int i=0;i<Expressway.BjPassengerQ.size();i++) {
			if(Expressway.BjPassengerQ.peek()!=null) {
				if(Expressway.BjPassengerQ.peek().AboardVolvo()) {
					Expressway.BjPassengerQ.poll();
				}else {
					break;
				}
			}
		}
		//����վ
		for(int i=0;i<Expressway.XnPassengerQ.size();i++) {
			if(Expressway.XnPassengerQ.peek()!=null) {
				if(Expressway.XnPassengerQ.peek().AboardVolvo()) {
					Expressway.XnPassengerQ.poll();
				}else {
					break;
				}
			}
		}		
	}

}
