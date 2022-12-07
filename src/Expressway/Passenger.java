package Expressway;

public class Passenger {
	public boolean isBaoJi;//����Ҫ��ֻ��������վ���߱���վ�ȳ�
	public int destination;//Ŀ�ĵأ�����ȡһЩ��ɢ��ֵ վ����Frame�����꣬�ֱ���
	/*20 140 245 555 660 780 890
	 *���ڳ����ٶȲ�һ������ֻ��ȡ����
	 *����Volvo��10����ÿ���ӣ�����վ������������
	 *20 140 (240,250) (550,560) 660 780 890
	 *����Iveco: 7����ÿ���ӣ�����վ������������
	 *20 (139,146) (244,251) (552,559) (657,664) (776,783) (889,896) 
	 *890 (785,778) (666,659) (561,554) (246,239) (141,134) (22,15)
	 *
	 *����ǰ����������ĸ�վ���������ȡ
	 *20 139 244 552 657 783 889
	 *ǰ����������ĸ�վ���������ȡ
	 *22 141 246 554 659 778 890
	 *
	 * 
	 * destinationȡֵ�涨ΪRandom�����[0,6]���������ֱ������...����
	 * 
	 */
	
	
	public Passenger(boolean isBaoji,int destination){
		this.isBaoJi=isBaoji;
		this.destination=destination;
		System.out.println("["+Timer.hour+":"+Timer.min+"]"+"�˿ʹ����ɹ� �Ƿ��ڱ���վ��"+this.isBaoJi+" Ŀ�ĵأ� "+this.destination);
	}
	public boolean AboardIveco() {//�ɹ��ϳ�����true
		//������ڱ���վ
		if(this.isBaoJi) {
			//�ϳ��Ĳ��裬�ڷ���ǰ��˲���ϳ�
			//�ڸ�վ�����ĳ������� �м����׵ĳ��� ps�����Ƿ��������û�о��ϳ�������о��ݲ�ִ��
			if((Timer.hour>=8 && Timer.hour<=17 && Timer.min%20==0) || (Timer.hour==18&&Timer.min==0)) {//Iveco����ʱ�䣬��ִ��launch֮ǰִ�и÷���
				if(Expressway.BjIvecoQ.peek()!=null) {//�ǿգ������쳣
					if(Expressway.BjIvecoQ.peek().ps.size() < Expressway.BjIvecoQ.peek().capacity) {//���ϳ˿���С������
						Expressway.BjIvecoQ.peek().ps.add(this);
						//System.out.println("size()"+Expressway.BjIvecoQ.peek().ps.size());
						//Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]Passenger Aboard the Iveco"+Expressway.BjIvecoQ.peek().number+"in BaoJi");
						return true;
					}
				}
			}
			
		}else {
			if((Timer.hour>=8 && Timer.hour<=17 && Timer.min%20==0) || (Timer.hour==18&&Timer.min==0)) {//Iveco����ʱ�䣬��ִ��launch֮ǰִ�и÷���
				if(Expressway.XnIvecoQ.peek()!=null) {
					if(Expressway.XnIvecoQ.peek().ps.size() < Expressway.XnIvecoQ.peek().capacity) {//���ϳ˿���С������
						Expressway.XnIvecoQ.peek().ps.add(this);
						//Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]Passenger Aboard the Iveco"+Expressway.XnIvecoQ.peek().number+"in Xi'An");
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean AboardVolvo() {//�ɹ��ϳ�����true
		//������ڱ���վ
		if(this.isBaoJi) {
			//�ϳ��Ĳ��裬�ڷ���ǰ��˲���ϳ�
			//�ڸ�վ�����ĳ������� �м����׵ĳ��� ps�����Ƿ��������û�о��ϳ�������о��ݲ�ִ��
			if(Timer.min==30 && Timer.hour>=8 && Timer.hour<=17) {//Volvo����ʱ�䣬��ִ��launch֮ǰִ�и÷���
				if(Expressway.BjVolvoQ.peek()!=null) {
					if(Expressway.BjVolvoQ.peek().ps.size() < Expressway.BjVolvoQ.peek().capacity) {//���ϳ˿���С������
						Expressway.BjVolvoQ.peek().ps.add(this);
						//Expressway.console.append("\n["+Timer.hour+":"+Timer.min+"]Passenger Aboard the Volvo"+Expressway.BjVolvoQ.peek().number+"in BaoJi");
						return true;
					}
				}
				
			}
			
		}else {
			if(Timer.min==30 && Timer.hour>=8 && Timer.hour<=17) {//Iveco����ʱ�䣬��ִ��launch֮ǰִ�и÷���
				if(Expressway.XnVolvoQ.peek()!=null) {
					if(Expressway.XnVolvoQ.peek().ps.size() < Expressway.XnVolvoQ.peek().capacity) {//���ϳ˿���С������
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
