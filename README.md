# XiBaoExpressway
XiBaoExpressway 西宝高速公路模拟


# 写在前面 before reading
It's just my Java lab. For reference only. Java features used are: encapsulation, inheritance, polymorphism, packages, GUI, Swing, and collection classes. 
It takes a week, about a thousand lines.
只是我的Java课程的一个大作业，仅供参考。使用到的Java特性有：封装、继承、多态、包、GUI、Swing、集合类。耗时一周，一千行左右。
![GUI演示](https://img-blog.csdnimg.cn/0b6540608c32411280ccc1333bc23076.png#pic_center)


# 一、仿真模拟的具体要求 requirements for simulation

![在这里插入图片描述](https://img-blog.csdnimg.cn/3e2367d244954fb3822f1b781bbd2d32.png#pic_center)


# 二、类的设计 class diagrams

Considering that program simulation is complex, priority is given to thinking about the association between classes, which is described by class diagrams below.
考虑到程序仿真比较复杂，优先思考类与类之间的关联，下面用类图来描述。

## 2.1 抽象父类PubVehicles
![在这里插入图片描述](https://img-blog.csdnimg.cn/31a124465663445585fa57e67d82298e.png#pic_center)

PubVehicles作为抽象父类，有俩个子类分别是Iveco和Volvo，基本的成员变量在右侧，抽象方法在左侧。

## 2.2 Expressway类
![在这里插入图片描述](https://img-blog.csdnimg.cn/ad781787dd8d4caeb624456b97b6e6ac.png#pic_center)
Expressway类，放置在Expressway.java文件中，包含需要使用的变量，以及main方法和initialization方法。



## 2.3 Passenger类
![在这里插入图片描述](https://img-blog.csdnimg.cn/4cf6664457bc4ab4b8179ee9305e902c.png#pic_center)
 Passenger类，有构造方法、上车等方法。右侧是成员变量，分别是是否在宝鸡站和目的地。

## 2.4 Timer类
![在这里插入图片描述](https://img-blog.csdnimg.cn/3c103eb8486f4b71bb33f29d94703cdc.png#pic_center)

Timer类，用于记录时间，以及刷新GUI界面，从而实现运动的画面。左侧是相关的方法，右侧是一些基本成员变量。

## 2.5 DisplayToolkit类
![在这里插入图片描述](https://img-blog.csdnimg.cn/928816d02e094dc3986e9ac3a809a2f3.png#pic_center)
DisplayToolkit类主要运用GUI和Swing的组件，创建可视化用户界面。

# 三、图像显示 images
这里主要是让小车运动起来的实现，小车的PNG图片如下（背景透明）
![在这里插入图片描述](https://img-blog.csdnimg.cn/323ce83ddd3744f7938d3ab9ed0fc7ef.png#pic_center)
images文件夹与src文件夹放置在同一目录下即可。
具体放置到GUI界面中基本上是八股文代码，如下所示(仅供演示）

```java
//right变量  图片路径
public Image right=Toolkit.getDefaultToolkit().createImage("images/Iveco_Right.png");
//lb和ii，将图片放置到JLabel上
JLabel lb=new JLabel();
ImageIcon ii=new ImageIcon(right);
//设置
ii.setImage(ii.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));//缩放到80x80
lb.setToolTipText("Iveco "+this.number);//鼠标移到上面有信息显示
lb.setVisible(true);
lb.setIcon(ii);
Expressway.panel_cars.add(lb);
```

# 四、代码实现 codes(part)
完整代码、PNG素材、类图设计，都包含在该项目文件中，请自行下载。

下面是预览效果
## 4.3 DisplayToolkit.java

```java
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

//显示界面
class DisplayFrame extends JFrame{
	//自动生成的序列版本号
	private static final long serialVersionUID = 1L;
	//窗口属性区域
	private static final int WIDTH=1280;
	private static final int HEIGHT=720;
	
	DisplayFrame(String title){
		super(title);
		this.setSize(WIDTH,HEIGHT);
		this.setBackground(new Color(51,51,51));
		
		this.setResizable(false);
		
		this.setLayout(null);//不使用布局管理器
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		//关闭窗口的事件
		this.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
              System.exit(0);
          }
      });
		
	}
}

//显示面板
class DisplayPanel extends JPanel{
	//自动生成的序列版本号
	private static final long serialVersionUID = 1L;
	DisplayPanel(int x,int y,int width,int height,Color c){
		super(null);
		this.setBounds(x,y,width,height);
		this.setBackground(c);
	}
}

//显示公路
class RoadButton extends Button{
	//默认序列号
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
	//公路长 870像素，即174千米
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
	//默认序列号
	private static final long serialVersionUID = 1L;

	
	DisplayDialog(String str,int row,int col,int x,int y,int w,int h){
		super(str,row,col);
		this.setBounds(x,y,w,h);
		this.setBackground(new Color(15,15,15));
		this.setForeground(new Color(0,255,0));
		this.setFont(new Font("黑体",Font.BOLD,20));
	}
}
public class DisplayToolkit {
	
}

```

