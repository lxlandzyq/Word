package WordDemo;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import audioTrans.http_to_audio;

import com.baidu.translate.demo.Main;

//import com.baidu.translate.demo.Main;

public class windowDemo {
	JFrame f = new JFrame("单词软件");
	static public boolean flag=false;
	static int[] removeDay = {1,1,2,3,7};//用于存放每一个时间点的 
	static int currentDay=0;//当前时间天数的索引
	static int mainDay=0;//主要时间天数
	static JLabel days;
	static JPanel sum = new JPanel();//用于CardLayout
	static CardLayout c = new CardLayout();
	static JLabel rjbFirst = new JLabel("现在是记第"+jdbcDemo.day+"天的单词");//用于显示主界面的时间天数
	static JLabel jl = new JLabel();
	static boolean rrjbFlag=true;
	static JTextArea meanFile;//翻译意思显示位置
	static JTextField cxj ;//输入单词显示
	static Main m ;//百度翻译接口
	static http_to_audio hta ;//实现语音朗诵
	class Listner implements ActionListener{//用于主界面的三个按钮的事件监听
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch( e.getActionCommand()) {
			case "返回" : c.show(sum,"0");//用于显示第几个窗口
			jdbcDemo.cleanMap();try {
					jdbcDemo.getDay();
					rrjbFlag=true;
					days.setText("已经存"+jdbcDemo.day+"天");
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}break;
			case "记单词": c.show(sum, "1");try {
					//jdbcDemo.init(jdbcDemo.day);
					jdbcDemo.init(jdbcDemo.day);
					jdbcDemo.init(jdbcDemo.day);//两遍今天的内容
					rjbFirst.setText("现在是记第"+jdbcDemo.day+"天的单词");;
					jl.setText(jdbcDemo.nextWord());
					jdbcDemo.getDay();//获取当前最大值
					currentDay=0;
					mainDay=jdbcDemo.day;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}break;
			case "写单词": c.show(sum, "2");break;
			case "翻译": m.trant(cxj.getText());meanFile.setText(m.putOut());break;
			case "错误排行": c.show(sum,"3");try {
					jdbcDemo.misRank();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}break;
			}
		}
	}
	class KeyBordListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_Q){
				//说明此时是不认识的按钮
				if(rrjbFlag){//如果已经是认识的界面了点击不认识的话，则也存入到系统里，但是不显示意思，如果是一开始就点击不认识的话则会直接存到系统里面，也显示意思
					//显示下一个单词不显示意思咯
					jl.setText("<html><body>"+jdbcDemo.getWord()+"<br>"+jdbcDemo.getMean(jdbcDemo.getWord())+"<body></html>");//当不懂的时候与单词同时显示出来将意思显示出来
					rrjbFlag=false;
				}
				else{
					rrjbFlag=true;
					jdbcDemo.addArray(jdbcDemo.getWord());//存入下一个值
					try {
						jdbcDemo.addMistak((String)jdbcDemo.getWord());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//对不懂的单词错误加1
					String temp=jdbcDemo.nextWord();	
					jl.setText(temp);//显示下一个单词，同时存入这一个单词为错
				}
			}
			else{
				if(e.getKeyCode()==KeyEvent.VK_V){
					//为认识的界面
					if(!jdbcDemo.al.isEmpty()||!jdbcDemo.array.isEmpty()||rrjbFlag) {//说明还有下一个值,其中最后一个参数用来判断这一天的最后一个单词用来显示意思
						//显示之后，再显示其意思，显示意思之后再判断是否真的认识这个单词
						if(rrjbFlag){//当为true的时候说明这个单词已经点了认识了，再进行判断是否真的认识
							jl.setText("<html><body>"+jdbcDemo.getWord()+"<br>"+jdbcDemo.getMean(jdbcDemo.getWord())+"<body></html>");
							rrjbFlag=false;
						}
						else{
							rrjbFlag=true;
							String temp=jdbcDemo.nextWord();
							jl.setText(temp);
						}
						
					}
					else {//判断接下来的天数是否还有单词
						
						if(currentDay==5||currentDay<0||mainDay-removeDay[currentDay]<=0)
							jl.setText("全部单词已经结束");
						else {
							mainDay=mainDay-removeDay[currentDay];
							jl.setText("开始的"+mainDay+"天");
							try {
								Thread.sleep(2000);
								rjbFirst.setText("现在是记第"+mainDay+"天的单词");
								jdbcDemo.init(mainDay);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							currentDay++;
						}
					}
				}
				else{
					if(e.getKeyCode()==KeyEvent.VK_R){
						//此时为播放音频的按钮
						//获取其单词，然后进行发音
						String temp=jdbcDemo.getWord();
						hta.setAudio(temp);//进行读取
					}
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	windowDemo() throws Exception{
		//f.setLayout(null);
		//f.setSize(1000, 1000);
		f.setBounds(300, 0, 800, 650);
		jdbcDemo.getDay();//获取其天数
		 days= new JLabel("已经存"+jdbcDemo.day+"天");
		 days.setBounds(10, 10, 300, 100);
		
		JButton rjb = null;
		JButton wjb=null;
		JButton mjb=null;
		//JPanel sum = new JPanel();
		sum.setLayout(c);
		JPanel jp = new JPanel();
		JPanel wjp = new JPanel();
		JPanel rjp = new JPanel();
		JPanel mjp = new JPanel();
		jp.setLayout(null/*new GridLayout(4,3,50,50)*/);
		//jp.setLayout(new )
		days.setFont(new Font("宋体", Font.PLAIN, 30));
		jp.add(days);
		Box transtBox = new Box(BoxLayout.Y_AXIS);
		Box orient = new Box(BoxLayout.X_AXIS);
		cxj = new JTextField(5);//撰写单词的位置
		orient.add(cxj);
		meanFile = new JTextArea(3,3);
		Listner listner = new Listner();
		JButton transjb = new JButton("翻译");
		transjb.addActionListener(listner);
		orient.add(transjb);
		transtBox.add(orient);
		meanFile.setBorder(BorderFactory.createLineBorder(Color.gray,1));
		meanFile.setFont(new Font("宋体", Font.PLAIN, 15));
		transtBox.add(meanFile);
		transtBox.setBounds(500, 10, 300, 100);
		for(int i=2;i<=12;i++) {//设置主窗口
			if(i==5) {
				rjb = new JButton("记单词");
				rjb.setBounds(270, 160, 200, 100);
				jp.add(rjb);
				continue;
			}
			if(i==8) {
				wjb = new JButton("写单词");
				wjb.setBounds(270, 290, 200, 100);
				jp.add(wjb);
				continue;
			}
			if(i==3){//查询单词界面
				jp.add(transtBox);
				continue;
			}
			if(i==11) {
				mjb = new JButton("错误排行");
				mjb.setBounds(270, 420, 200, 100);
				jp.add(mjb);
				continue;
			}
		}
		
		//设置主界面的背景
		ImageIcon mainBack = new ImageIcon("lib/wallhaven-751360.jpg");
		JLabel jlback = new JLabel(mainBack);
		jlback.setBounds(0, 0, 800, 650);
		jp.add(jlback);
		jp.setBounds(0, 0, 1000, 600);
		JPanel transJpanel = new JPanel();
		
		//final JLabel jl = new JLabel();
		//final JLabel rjbFirst = new JLabel("现在是记第"+jdbcDemo.day+"天的单词");
		rjbFirst.setFont(new Font("宋体",Font.PLAIN,20));
		
		//为其组件起名字
		sum.add("0", jp);//主窗口
		sum.add("1", rjp);//记单词窗口
		sum.add("2", wjp);//写单词窗口
		sum.add("3", mjp);//错误单词窗口
		JButton b = new JButton("返回");
		JButton b2 = new JButton("返回");
		JButton b3 = new JButton("返回");
		wjp.add(b2);
		
		//设置监听
		b.addActionListener(listner);
		b2.addActionListener(listner);
		b3.addActionListener(listner);
		rjb.addActionListener(listner);
		wjb.addActionListener(listner);
		mjb.addActionListener(listner);
		JPanel jpS = new JPanel();
		jpS.setPreferredSize(new Dimension(20,50));
		
		
		//记单词窗口
		rjp.setLayout(null/*new GridLayout(3,3,50,50)*/);
		JButton rrjb=null;
		JButton rrjb2=null;
		JButton audioJB=null;
		JPanel rjpFirst = new JPanel() ;//用于显示当前存的是第几天的单词
		rjp.add(rjbFirst);
		b.setBounds(130, 10, 130, 60);//记单词界面的返回的位置
		rjp.add(b);
		jl.setBounds(300, 200, 400, 150);//意思显示的位置
		rjp.add(jl);
		rrjb = new JButton("认识");//设置显示单词窗口
		rrjb.setBounds(130, 400, 130, 80);//认识的位置
		jl.setFont(new Font("宋体", Font.PLAIN, 30));
		rjp.add(rrjb);
		rrjb2 = new JButton("不认识");
		rrjb2.setBounds(550, 400, 130, 80);//不认识控件的位置
		rjp.add(rrjb2);
		rjbFirst.setBounds(550, 10, 300, 100);
		ImageIcon rBack = new ImageIcon("lib/tourise1.jpg");
		JLabel rback = new JLabel(rBack);
		rback.setBounds(0, 0, 800, 650);
		rjp.add(rback);
		rjp.setBackground(Color.lightGray);
		
		//添加新功能实现语音朗诵按钮
		audioJB= new JButton("发音");
		audioJB.setBounds(130, 100, 130, 80);
		audioJB.addActionListener(new ActionListener(){

			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_R){
					//不认识的
				}
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					//此时为播放音频的按钮
					//获取其单词，然后进行发音
					String temp=jdbcDemo.getWord();
					hta.setAudio(temp);//进行读取
			}
			
		});
		//audioJB.addKeyListener(new KeyBordListener());
		rjp.add(audioJB);
		
		//rrjb2.addKeyListener(new KeyBordListener());//实现键盘的监听
		rrjb2.addActionListener(new ActionListener(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_Q){
					//不认识的
				}
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(rrjbFlag){//如果已经是认识的界面了点击不认识的话，则也存入到系统里，但是不显示意思，如果是一开始就点击不认识的话则会直接存到系统里面，也显示意思
					//显示下一个单词不显示意思咯
					jl.setText("<html><body>"+jdbcDemo.getWord()+"<br>"+jdbcDemo.getMean(jdbcDemo.getWord())+"<body></html>");//当不懂的时候与单词同时显示出来将意思显示出来
					rrjbFlag=false;
				}
				else{
					rrjbFlag=true;
					jdbcDemo.addArray(jdbcDemo.getWord());//存入下一个值
					try {
						jdbcDemo.addMistak((String)jdbcDemo.getWord());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//对不懂的单词错误加1
					String temp=jdbcDemo.nextWord();	
					jl.setText(temp);//显示下一个单词，同时存入这一个单词为错
				}
			}
			
		});
		rrjb2.addKeyListener(new KeyBordListener());//实现键盘的监听
		rrjb.addKeyListener(new KeyBordListener());//实现键盘的监听
		rrjb.addActionListener(new ActionListener(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_V){
				}
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//下一个数据
				if(!jdbcDemo.al.isEmpty()||!jdbcDemo.array.isEmpty()||rrjbFlag) {//说明还有下一个值,其中最后一个参数用来判断这一天的最后一个单词用来显示意思
					//显示之后，再显示其意思，显示意思之后再判断是否真的认识这个单词
					if(rrjbFlag){//当为true的时候说明这个单词已经点了认识了，再进行判断是否真的认识
						jl.setText("<html><body>"+jdbcDemo.getWord()+"<br>"+jdbcDemo.getMean(jdbcDemo.getWord())+"<body></html>");
						rrjbFlag=false;
					}
					else{
						rrjbFlag=true;
						String temp=jdbcDemo.nextWord();
						jl.setText(temp);
					}
					
				}
				else {//判断接下来的天数是否还有单词
					
					if(currentDay==5||currentDay<0||mainDay-removeDay[currentDay]<=0)
						jl.setText("全部单词已经结束");
					else {
						mainDay=mainDay-removeDay[currentDay];
						jl.setText("开始的"+mainDay+"天");
						try {
							Thread.sleep(2000);
							rjbFirst.setText("现在是记第"+mainDay+"天的单词");
							jdbcDemo.init(mainDay);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						currentDay++;
					}
				}
			}
			
		});
		
		//写单词窗口
		String[][] dataWord = new String[31][2];
		String[] title = {"单词","意思"};
		final JTable jta = new JTable(dataWord,title);
		jta.setBounds(250, 20, 100, 100);
		JScrollPane jsl = new JScrollPane(jta);//给表格添加滚轮
		JButton jbRight = new JButton("存入");
		final JTextField jtf = new JTextField(5);
		jta.setRowHeight(50);//设置表格的高度
		jbRight.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int t=0;
			ArrayList<String> al1 = new ArrayList();
			ArrayList<String> al2 = new ArrayList();
			while(!jta.getValueAt(t, 0).equals("0")) {
				al1.add((String)jta.getValueAt(t, 0));
				al2.add((String)jta.getValueAt(t, 1));
				t++;
			}
			if(!al1.isEmpty()) {//数据非空 存入
				try {
					jdbcDemo.WriteData(al1,al2,Integer.valueOf(jtf.getText()));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	
			}
			
		});
		b2.setBounds(50, 30, 80, 50);
		
		JLabel jlabel = new JLabel("输入要存入的天数：");
		wjp.add(jlabel);
		jlabel.setBounds(350, 510, 150, 30);//标签的位置
		wjp.add(jsl);
		jsl.setBounds(150, 10, 500, 500);//设置写单词界面
		wjp.add(jtf);
		jtf.setBounds(477, 510, 90, 30);//设置输入天数的位置
		wjp.add(jbRight);
		jbRight.setBounds(570, 510, 70, 30);//设置存入的位置
		wjp.setLayout(null);
		ImageIcon wBack = new ImageIcon("lib/wave1.jpg");
		JLabel wback = new JLabel(wBack);
		wback.setBounds(0, 0, 800, 650);
		wjp.add(wback);
		
		//错误排行窗口,设计一个表格来进行排错
		JScrollPane misJsl = new JScrollPane(jdbcDemo.misJta);//给表格添加滚轮
		mjp.setLayout(null);
		b3.setBounds(50, 30, 80, 50);//设置返回位置
		misJsl.setBounds(150, 10, 500, 500);//设置错误排行榜界面
		mjp.add(b3);
		mjp.add(misJsl);
		ImageIcon mBack = new ImageIcon("lib/wu.jpg");
		JLabel mback = new JLabel(mBack);
		mback.setBounds(0, 0, 800, 650);
		mjp.add(mback);	
		
		f.add(sum);
		f.add(jpS,BorderLayout.SOUTH);
		f.setVisible(true);
		//f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) throws Exception {
		m = new Main();
		new windowDemo();
		hta = new http_to_audio();
		
	}

} 
