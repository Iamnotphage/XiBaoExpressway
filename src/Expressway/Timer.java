package Expressway;

import java.awt.Label;
import java.util.Random;

//Timer类，用于记录虚拟的时间，机器时间1秒等于虚拟时间1分钟
//一切都是由时间变化引起的变化
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
		if(hour==20 && min==15) {//由于工作时间是7：30到18：00，这里设置可见时间为7：15至18：15
			hour=7;
			min=15;
			day++;
			Expressway.XnPassengerQ.clear();
			Expressway.BjPassengerQ.clear();
		}
		String str=new String("Now: Day "+day+" Time: "+hour+" : "+min+" ");
		return str;
	}

	public static void FreshTime(Label timer) {//刷新时间的同时，刷新汽车运动状态
		min++;
		timer.setText(getTime());//设置时间显示
		//延迟1秒后刷新状态
		//需要刷新的有: 各个车辆的位置 面板信息 乘客行为
		try {
			//产生乘客
			ProducePassengerstoBaoJi();
			ProducePassengerstoXiAn();
			
			//发车前
			LetPassengersAboardVolvo();
			LetPassengersAboardIveco();
			
			
			for(int i=0;i<Expressway.ivecosOnRoad.size();i++) {
				Expressway.ivecosOnRoad.get(i).Stop(Expressway.ivecosOnRoad.get(i).CheckStop());
			}
			for(int i=0;i<Expressway.volvosOnRoad.size();i++) {
				Expressway.volvosOnRoad.get(i).Stop(Expressway.volvosOnRoad.get(i).CheckStop());
			}
			
			//发车&车辆移动
			CarMovingWithTime();
			
			//刷新面板信息
			FreshStationInfo();
			FreshOnTheWayInfo();
			
			
			
			Thread.sleep(period);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void CarMovingWithTime() {
		
		//依维柯发车
		if(Expressway.BjIvecoQ.peek()!=null) {//宝鸡的依维柯发车
			Expressway.BjIvecoQ.peek().launch();
			if(Expressway.BjIvecoQ.peek().isLaunched) {
				Expressway.ivecosOnRoad.add(Expressway.BjIvecoQ.poll());
				Expressway.console.append("\n["+hour+":"+min+"]Iveco "+Expressway.ivecosOnRoad.get(Expressway.ivecosOnRoadNum).number+" Launched from BaoJi");
				Expressway.ivecosOnRoad.get(Expressway.ivecosOnRoadNum).lb.setVisible(true);
				Expressway.ivecosOnRoadNum++;
			}
		}
		if(Expressway.XnIvecoQ.peek()!=null) {//西安的依维柯发车
			Expressway.XnIvecoQ.peek().launch();
			if(Expressway.XnIvecoQ.peek().isLaunched) {
				Expressway.ivecosOnRoad.add(Expressway.XnIvecoQ.poll());
				Expressway.console.append("\n["+hour+":"+min+"]Iveco "+Expressway.ivecosOnRoad.get(Expressway.ivecosOnRoadNum).number+" Launched from Xi'An");
				Expressway.ivecosOnRoad.get(Expressway.ivecosOnRoadNum).lb.setVisible(true);
				Expressway.ivecosOnRoadNum++;
			}
		}
		for(int i=0;i<Expressway.ivecosOnRoadNum;i++) {//遍历路上车辆，移动
			if(Expressway.ivecosOnRoad.get(i).isLaunched) {
				Expressway.ivecosOnRoad.get(i).CarMoving();
			}
		}
		
		//沃尔沃发车
		if(Expressway.BjVolvoQ.peek()!=null) {//宝鸡的沃尔沃发车
			Expressway.BjVolvoQ.peek().launch();
			if(Expressway.BjVolvoQ.peek().isLaunched) {
				Expressway.volvosOnRoad.add(Expressway.BjVolvoQ.poll());
				Expressway.console.append("\n["+hour+":"+min+"]Volvo "+Expressway.volvosOnRoad.get(Expressway.volvosOnRoadNum).number+" Launched from BaoJi");
				Expressway.volvosOnRoad.get(Expressway.volvosOnRoadNum).lb.setVisible(true);
				Expressway.volvosOnRoadNum++;
			}
		}
		if(Expressway.XnVolvoQ.peek()!=null) {//西安的沃尔沃发车
			Expressway.XnVolvoQ.peek().launch();
			if(Expressway.XnVolvoQ.peek().isLaunched) {
				Expressway.volvosOnRoad.add(Expressway.XnVolvoQ.poll());
				Expressway.console.append("\n["+hour+":"+min+"]Volvo "+Expressway.volvosOnRoad.get(Expressway.volvosOnRoadNum).number+" Launched from Xi'An");
				Expressway.volvosOnRoad.get(Expressway.volvosOnRoadNum).lb.setVisible(true);
				Expressway.volvosOnRoadNum++;
			}
		}
		for(int i=0;i<Expressway.volvosOnRoadNum;i++) {//遍历路上车辆，移动
			if(Expressway.volvosOnRoad.get(i).isLaunched) {
				Expressway.volvosOnRoad.get(i).CarMoving();
			}
		}
	}
	
	public static void FreshStationInfo() {
		//西安站面板的信息显示
		Expressway.XNStation.setText("Xi'An Station");
		for(Volvo i:Expressway.XnVolvoQ) {
			Expressway.XNStation.append("\n Volvo: "+i.number);
		}
		for(Iveco i:Expressway.XnIvecoQ) {
			Expressway.XNStation.append("\n Iveco: "+i.number);
		}
		Expressway.XNStation.append("\n候车乘客数: "+Expressway.XnPassengerQ.size());
		
		//宝鸡站面板的信息显示
		Expressway.BJStation.setText("BaoJi Station");
		for(Volvo i:Expressway.BjVolvoQ) {
			Expressway.BJStation.append("\n Volvo: "+i.number);
		}
		for(Iveco i:Expressway.BjIvecoQ) {
			Expressway.BJStation.append("\n Iveco: "+i.number);
		}
		Expressway.BJStation.append("\n候车乘客数: "+Expressway.BjPassengerQ.size());
		
	}
	public static void FreshOnTheWayInfo() {
		Expressway.OntheWayInfo.setText("On The Expressway");
		for(Volvo i:Expressway.volvosOnRoad) {
			Expressway.OntheWayInfo.append("\n Volvo: "+i.number+"乘客人数: "+i.ps.size());
		}
		for(Iveco i:Expressway.ivecosOnRoad) {
			Expressway.OntheWayInfo.append("\n Iveco: "+i.number+"乘客人数: "+i.ps.size());
		}
	}
	
	//生成 在西安站 前往宝鸡站的乘客
	public static void ProducePassengerstoBaoJi() {
		//上午7点30到下午17点59 才产生乘客
		if(hour*60+min<450 || hour*60+min >1079) {
			return;
		}
		Random r=new Random();
		//每分钟随机生成0~Pn个乘客 [0,Pn]
		int PassengerNum=r.nextInt(Expressway.Pn+1);
		//用均匀分布产生不同终点[0,5]
		//也就是[0,6) 012345
		for(int i=0;i<PassengerNum;i++) {
			Expressway.XnPassengerQ.add(new Passenger(false,(int)(Math.random()*6)));
		}
		if(PassengerNum!=0) {
			//Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+PassengerNum+"Passengers "+"Wait at Xi'An Station");
		}
	}
	
	//生成 在宝鸡站 前往西安站的乘客
	public static void ProducePassengerstoXiAn() {
		//上午7点30到下午17点59 才产生乘客
		if(hour*60+min<450 || hour*60+min >1079) {
			return;
		}
		Random r=new Random();
		//每分钟随机生成0~Pn个乘客 [0,Pn]
		int PassengerNum=r.nextInt(Expressway.Pn+1);
		//用均匀分布产生不同终点[1,6]
		//[1,7) 也就是123456

		for(int i=0;i<PassengerNum;i++) {
			Expressway.BjPassengerQ.add(new Passenger(true,(int)(Math.random()*6+1)));	
		}
		if(PassengerNum!=0) {
			//Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]"+PassengerNum+"Passengers "+"Wait at BaoJi Station");
		}
	}

	//安排乘客上Iveco车，即遍历乘客队列
	public static void LetPassengersAboardIveco() {
		
		//宝鸡站
		for(int i=0;i<Expressway.BjPassengerQ.size();i++) {
			if(Expressway.BjPassengerQ.peek()!=null) {
				if(Expressway.BjPassengerQ.peek().AboardIveco()) {
					Expressway.BjPassengerQ.poll();
				}else {
					//队首上车失败直接break
					break;
				}
			}
		}
		//西安站
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
		//宝鸡站
		for(int i=0;i<Expressway.BjPassengerQ.size();i++) {
			if(Expressway.BjPassengerQ.peek()!=null) {
				if(Expressway.BjPassengerQ.peek().AboardVolvo()) {
					Expressway.BjPassengerQ.poll();
				}else {
					break;
				}
			}
		}
		//西安站
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
