# XiBaoExpressway
XiBaoExpressway 西宝高速公路模拟


# 写在前面

只是我的Java课程的一个大作业，仅供参考。使用到的Java特性有：封装、继承、多态、包、GUI、Swing、集合类。耗时一周，一千行左右。
![GUI演示](https://img-blog.csdnimg.cn/0b6540608c32411280ccc1333bc23076.png#pic_center)


# 一、仿真模拟的具体要求

![在这里插入图片描述](https://img-blog.csdnimg.cn/3e2367d244954fb3822f1b781bbd2d32.png#pic_center)


# 二、类的设计

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

# 三、图像显示
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

# 四、代码实现
完整代码、PNG素材、类图设计，都包含在该项目文件中，请自行下载。

