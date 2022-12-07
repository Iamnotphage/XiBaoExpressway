package Expressway;

public class Passenger {
	public boolean isBaoJi;//根据要求，只能在西安站或者宝鸡站等车
	public int destination;//目的地，可以取一些离散的值 站点在Frame上坐标，分别是
	/*20 140 245 555 660 780 890
	 *由于车辆速度不一，而且只能取整数
	 *对于Volvo：10像素每分钟，各个站点的坐标近似是
	 *20 140 (240,250) (550,560) 660 780 890
	 *对于Iveco: 7像素每分钟，各个站点的坐标近似是
	 *20 (139,146) (244,251) (552,559) (657,664) (776,783) (889,896) 
	 *890 (785,778) (666,659) (561,554) (246,239) (141,134) (22,15)
	 *
	 *于是前往西安方向的各站点坐标近似取
	 *20 139 244 552 657 783 889
	 *前往宝鸡方向的个站点坐标近似取
	 *22 141 246 554 659 778 890
	 *
	 * 
	 * destination取值规定为Random类产生[0,6]的整数，分别代表宝鸡...西安
	 * 
	 */
	
	
	public Passenger(boolean isBaoji,int destination){
		this.isBaoJi=isBaoji;
		this.destination=destination;
		System.out.println("["+Timer.hour+":"+Timer.min+"]"+"乘客创建成功 是否在宝鸡站："+this.isBaoJi+" 目的地： "+this.destination);
	}
	public boolean AboardIveco() {//成功上车返回true
		//如果是在宝鸡站
		if(this.isBaoJi) {
			//上车的步骤，在发车前的瞬间上车
			//在该站发车的车辆队列 中检查队首的车的 ps链表是否满，如果没有就上车，如果有就暂不执行
			if((Timer.hour>=8 && Timer.hour<=17 && Timer.min%20==0) || (Timer.hour==18&&Timer.min==0)) {//Iveco发车时间，在执行launch之前执行该方法
				if(Expressway.BjIvecoQ.peek()!=null) {//非空，避免异常
					if(Expressway.BjIvecoQ.peek().ps.size() < Expressway.BjIvecoQ.peek().capacity) {//车上乘客数小于容量
						Expressway.BjIvecoQ.peek().ps.add(this);
						//System.out.println("size()"+Expressway.BjIvecoQ.peek().ps.size());
						//Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]Passenger Aboard the Iveco"+Expressway.BjIvecoQ.peek().number+"in BaoJi");
						return true;
					}
				}
			}
			
		}else {
			if((Timer.hour>=8 && Timer.hour<=17 && Timer.min%20==0) || (Timer.hour==18&&Timer.min==0)) {//Iveco发车时间，在执行launch之前执行该方法
				if(Expressway.XnIvecoQ.peek()!=null) {
					if(Expressway.XnIvecoQ.peek().ps.size() < Expressway.XnIvecoQ.peek().capacity) {//车上乘客数小于容量
						Expressway.XnIvecoQ.peek().ps.add(this);
						//Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]Passenger Aboard the Iveco"+Expressway.XnIvecoQ.peek().number+"in Xi'An");
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean AboardVolvo() {//成功上车返回true
		//如果是在宝鸡站
		if(this.isBaoJi) {
			//上车的步骤，在发车前的瞬间上车
			//在该站发车的车辆队列 中检查队首的车的 ps链表是否满，如果没有就上车，如果有就暂不执行
			if(Timer.min==30 && Timer.hour>=8 && Timer.hour<=17) {//Volvo发车时间，在执行launch之前执行该方法
				if(Expressway.BjVolvoQ.peek()!=null) {
					if(Expressway.BjVolvoQ.peek().ps.size() < Expressway.BjVolvoQ.peek().capacity) {//车上乘客数小于容量
						Expressway.BjVolvoQ.peek().ps.add(this);
						//Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]Passenger Aboard the Volvo"+Expressway.BjVolvoQ.peek().number+"in BaoJi");
						return true;
					}
				}
				
			}
			
		}else {
			if(Timer.min==30 && Timer.hour>=8 && Timer.hour<=17) {//Iveco发车时间，在执行launch之前执行该方法
				if(Expressway.XnVolvoQ.peek()!=null) {
					if(Expressway.XnVolvoQ.peek().ps.size() < Expressway.XnVolvoQ.peek().capacity) {//车上乘客数小于容量
						Expressway.XnVolvoQ.peek().ps.add(this);
						//Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]Passenger Aboard the Volvo"+Expressway.XnVolvoQ.peek().number+"in Xi'An");
						return true;
					}
				}
			}
		}
		return false;
	}
}
