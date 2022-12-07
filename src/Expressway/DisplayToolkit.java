package Expressway;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

//��ʾ����
class DisplayFrame extends JFrame{
	//�Զ����ɵ����а汾��
	private static final long serialVersionUID = 1L;
	//������������
	private static final int WIDTH=1280;
	private static final int HEIGHT=720;
	
	DisplayFrame(String title){
		super(title);
		this.setSize(WIDTH,HEIGHT);
		this.setBackground(new Color(51,51,51));
		
		this.setResizable(false);
		
		this.setLayout(null);//��ʹ�ò��ֹ�����
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		//�رմ��ڵ��¼�
		this.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
              System.exit(0);
          }
      });
		
	}
}

//��ʾ���
class DisplayPanel extends JPanel{
	//�Զ����ɵ����а汾��
	private static final long serialVersionUID = 1L;
	DisplayPanel(int x,int y,int width,int height,Color c){
		super(null);
		this.setBounds(x,y,width,height);
		this.setBackground(c);
	}
}

//��ʾ��·
class RoadButton extends Button{
	//Ĭ�����к�
	private static final long serialVersionUID = 1L;

	public RoadButton(String str,int x,int y,int w) {
		super(str);
		this.setBackground(Color.black);
		this.setForeground(new Color(255,255,255));
		this.setBounds(x,y,w,50);
	}
}
class DisplayRoad extends JPanel{


	private static final long serialVersionUID = 1L;
	//��·�� 870���أ���174ǧ��
	DisplayRoad(int x,int y,int width,int height,Color c){
		super(null);
		this.setBounds(x,y,width,height);
		this.setBackground(c);
	}
		
}
class DisplayDialogPanel extends JScrollPane{
	private static final long serialVersionUID = 1L;
	
	DisplayDialogPanel(int x,int y,int width,int height,Color c){
		super(null);
		this.setBounds(x,y,width,height);
		this.setBackground(c);
	}
}
class DisplayDialog extends JTextArea{
	//Ĭ�����к�
	private static final long serialVersionUID = 1L;

	
	DisplayDialog(String str,int row,int col,int x,int y,int w,int h){
		super(str,row,col);
		this.setBounds(x,y,w,h);
		this.setBackground(new Color(15,15,15));
		this.setForeground(new Color(0,255,0));
		this.setFont(new Font("����",Font.BOLD,20));
	}
}
public class DisplayToolkit {
	
}
