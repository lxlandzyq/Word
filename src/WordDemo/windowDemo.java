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
	JFrame f = new JFrame("�������");
	static public boolean flag=false;
	static int[] removeDay = {1,1,2,3,7};//���ڴ��ÿһ��ʱ���� 
	static int currentDay=0;//��ǰʱ������������
	static int mainDay=0;//��Ҫʱ������
	static JLabel days;
	static JPanel sum = new JPanel();//����CardLayout
	static CardLayout c = new CardLayout();
	static JLabel rjbFirst = new JLabel("�����Ǽǵ�"+jdbcDemo.day+"��ĵ���");//������ʾ�������ʱ������
	static JLabel jl = new JLabel();
	static boolean rrjbFlag=true;
	static JTextArea meanFile;//������˼��ʾλ��
	static JTextField cxj ;//���뵥����ʾ
	static Main m ;//�ٶȷ���ӿ�
	static http_to_audio hta ;//ʵ����������
	class Listner implements ActionListener{//�����������������ť���¼�����
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			switch( e.getActionCommand()) {
			case "����" : c.show(sum,"0");//������ʾ�ڼ�������
			jdbcDemo.cleanMap();try {
					jdbcDemo.getDay();
					rrjbFlag=true;
					days.setText("�Ѿ���"+jdbcDemo.day+"��");
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}break;
			case "�ǵ���": c.show(sum, "1");try {
					//jdbcDemo.init(jdbcDemo.day);
					jdbcDemo.init(jdbcDemo.day);
					jdbcDemo.init(jdbcDemo.day);//������������
					rjbFirst.setText("�����Ǽǵ�"+jdbcDemo.day+"��ĵ���");;
					jl.setText(jdbcDemo.nextWord());
					jdbcDemo.getDay();//��ȡ��ǰ���ֵ
					currentDay=0;
					mainDay=jdbcDemo.day;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}break;
			case "д����": c.show(sum, "2");break;
			case "����": m.trant(cxj.getText());meanFile.setText(m.putOut());break;
			case "��������": c.show(sum,"3");try {
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
				//˵����ʱ�ǲ���ʶ�İ�ť
				if(rrjbFlag){//����Ѿ�����ʶ�Ľ����˵������ʶ�Ļ�����Ҳ���뵽ϵͳ����ǲ���ʾ��˼�������һ��ʼ�͵������ʶ�Ļ����ֱ�Ӵ浽ϵͳ���棬Ҳ��ʾ��˼
					//��ʾ��һ�����ʲ���ʾ��˼��
					jl.setText("<html><body>"+jdbcDemo.getWord()+"<br>"+jdbcDemo.getMean(jdbcDemo.getWord())+"<body></html>");//��������ʱ���뵥��ͬʱ��ʾ��������˼��ʾ����
					rrjbFlag=false;
				}
				else{
					rrjbFlag=true;
					jdbcDemo.addArray(jdbcDemo.getWord());//������һ��ֵ
					try {
						jdbcDemo.addMistak((String)jdbcDemo.getWord());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//�Բ����ĵ��ʴ����1
					String temp=jdbcDemo.nextWord();	
					jl.setText(temp);//��ʾ��һ�����ʣ�ͬʱ������һ������Ϊ��
				}
			}
			else{
				if(e.getKeyCode()==KeyEvent.VK_V){
					//Ϊ��ʶ�Ľ���
					if(!jdbcDemo.al.isEmpty()||!jdbcDemo.array.isEmpty()||rrjbFlag) {//˵��������һ��ֵ,�������һ�����������ж���һ������һ������������ʾ��˼
						//��ʾ֮������ʾ����˼����ʾ��˼֮�����ж��Ƿ������ʶ�������
						if(rrjbFlag){//��Ϊtrue��ʱ��˵����������Ѿ�������ʶ�ˣ��ٽ����ж��Ƿ������ʶ
							jl.setText("<html><body>"+jdbcDemo.getWord()+"<br>"+jdbcDemo.getMean(jdbcDemo.getWord())+"<body></html>");
							rrjbFlag=false;
						}
						else{
							rrjbFlag=true;
							String temp=jdbcDemo.nextWord();
							jl.setText(temp);
						}
						
					}
					else {//�жϽ������������Ƿ��е���
						
						if(currentDay==5||currentDay<0||mainDay-removeDay[currentDay]<=0)
							jl.setText("ȫ�������Ѿ�����");
						else {
							mainDay=mainDay-removeDay[currentDay];
							jl.setText("��ʼ��"+mainDay+"��");
							try {
								Thread.sleep(2000);
								rjbFirst.setText("�����Ǽǵ�"+mainDay+"��ĵ���");
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
						//��ʱΪ������Ƶ�İ�ť
						//��ȡ�䵥�ʣ�Ȼ����з���
						String temp=jdbcDemo.getWord();
						hta.setAudio(temp);//���ж�ȡ
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
		jdbcDemo.getDay();//��ȡ������
		 days= new JLabel("�Ѿ���"+jdbcDemo.day+"��");
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
		days.setFont(new Font("����", Font.PLAIN, 30));
		jp.add(days);
		Box transtBox = new Box(BoxLayout.Y_AXIS);
		Box orient = new Box(BoxLayout.X_AXIS);
		cxj = new JTextField(5);//׫д���ʵ�λ��
		orient.add(cxj);
		meanFile = new JTextArea(3,3);
		Listner listner = new Listner();
		JButton transjb = new JButton("����");
		transjb.addActionListener(listner);
		orient.add(transjb);
		transtBox.add(orient);
		meanFile.setBorder(BorderFactory.createLineBorder(Color.gray,1));
		meanFile.setFont(new Font("����", Font.PLAIN, 15));
		transtBox.add(meanFile);
		transtBox.setBounds(500, 10, 300, 100);
		for(int i=2;i<=12;i++) {//����������
			if(i==5) {
				rjb = new JButton("�ǵ���");
				rjb.setBounds(270, 160, 200, 100);
				jp.add(rjb);
				continue;
			}
			if(i==8) {
				wjb = new JButton("д����");
				wjb.setBounds(270, 290, 200, 100);
				jp.add(wjb);
				continue;
			}
			if(i==3){//��ѯ���ʽ���
				jp.add(transtBox);
				continue;
			}
			if(i==11) {
				mjb = new JButton("��������");
				mjb.setBounds(270, 420, 200, 100);
				jp.add(mjb);
				continue;
			}
		}
		
		//����������ı���
		ImageIcon mainBack = new ImageIcon("lib/wallhaven-751360.jpg");
		JLabel jlback = new JLabel(mainBack);
		jlback.setBounds(0, 0, 800, 650);
		jp.add(jlback);
		jp.setBounds(0, 0, 1000, 600);
		JPanel transJpanel = new JPanel();
		
		//final JLabel jl = new JLabel();
		//final JLabel rjbFirst = new JLabel("�����Ǽǵ�"+jdbcDemo.day+"��ĵ���");
		rjbFirst.setFont(new Font("����",Font.PLAIN,20));
		
		//Ϊ�����������
		sum.add("0", jp);//������
		sum.add("1", rjp);//�ǵ��ʴ���
		sum.add("2", wjp);//д���ʴ���
		sum.add("3", mjp);//���󵥴ʴ���
		JButton b = new JButton("����");
		JButton b2 = new JButton("����");
		JButton b3 = new JButton("����");
		wjp.add(b2);
		
		//���ü���
		b.addActionListener(listner);
		b2.addActionListener(listner);
		b3.addActionListener(listner);
		rjb.addActionListener(listner);
		wjb.addActionListener(listner);
		mjb.addActionListener(listner);
		JPanel jpS = new JPanel();
		jpS.setPreferredSize(new Dimension(20,50));
		
		
		//�ǵ��ʴ���
		rjp.setLayout(null/*new GridLayout(3,3,50,50)*/);
		JButton rrjb=null;
		JButton rrjb2=null;
		JButton audioJB=null;
		JPanel rjpFirst = new JPanel() ;//������ʾ��ǰ����ǵڼ���ĵ���
		rjp.add(rjbFirst);
		b.setBounds(130, 10, 130, 60);//�ǵ��ʽ���ķ��ص�λ��
		rjp.add(b);
		jl.setBounds(300, 200, 400, 150);//��˼��ʾ��λ��
		rjp.add(jl);
		rrjb = new JButton("��ʶ");//������ʾ���ʴ���
		rrjb.setBounds(130, 400, 130, 80);//��ʶ��λ��
		jl.setFont(new Font("����", Font.PLAIN, 30));
		rjp.add(rrjb);
		rrjb2 = new JButton("����ʶ");
		rrjb2.setBounds(550, 400, 130, 80);//����ʶ�ؼ���λ��
		rjp.add(rrjb2);
		rjbFirst.setBounds(550, 10, 300, 100);
		ImageIcon rBack = new ImageIcon("lib/tourise1.jpg");
		JLabel rback = new JLabel(rBack);
		rback.setBounds(0, 0, 800, 650);
		rjp.add(rback);
		rjp.setBackground(Color.lightGray);
		
		//����¹���ʵ���������а�ť
		audioJB= new JButton("����");
		audioJB.setBounds(130, 100, 130, 80);
		audioJB.addActionListener(new ActionListener(){

			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_R){
					//����ʶ��
				}
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					//��ʱΪ������Ƶ�İ�ť
					//��ȡ�䵥�ʣ�Ȼ����з���
					String temp=jdbcDemo.getWord();
					hta.setAudio(temp);//���ж�ȡ
			}
			
		});
		//audioJB.addKeyListener(new KeyBordListener());
		rjp.add(audioJB);
		
		//rrjb2.addKeyListener(new KeyBordListener());//ʵ�ּ��̵ļ���
		rrjb2.addActionListener(new ActionListener(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_Q){
					//����ʶ��
				}
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(rrjbFlag){//����Ѿ�����ʶ�Ľ����˵������ʶ�Ļ�����Ҳ���뵽ϵͳ����ǲ���ʾ��˼�������һ��ʼ�͵������ʶ�Ļ����ֱ�Ӵ浽ϵͳ���棬Ҳ��ʾ��˼
					//��ʾ��һ�����ʲ���ʾ��˼��
					jl.setText("<html><body>"+jdbcDemo.getWord()+"<br>"+jdbcDemo.getMean(jdbcDemo.getWord())+"<body></html>");//��������ʱ���뵥��ͬʱ��ʾ��������˼��ʾ����
					rrjbFlag=false;
				}
				else{
					rrjbFlag=true;
					jdbcDemo.addArray(jdbcDemo.getWord());//������һ��ֵ
					try {
						jdbcDemo.addMistak((String)jdbcDemo.getWord());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//�Բ����ĵ��ʴ����1
					String temp=jdbcDemo.nextWord();	
					jl.setText(temp);//��ʾ��һ�����ʣ�ͬʱ������һ������Ϊ��
				}
			}
			
		});
		rrjb2.addKeyListener(new KeyBordListener());//ʵ�ּ��̵ļ���
		rrjb.addKeyListener(new KeyBordListener());//ʵ�ּ��̵ļ���
		rrjb.addActionListener(new ActionListener(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_V){
				}
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//��һ������
				if(!jdbcDemo.al.isEmpty()||!jdbcDemo.array.isEmpty()||rrjbFlag) {//˵��������һ��ֵ,�������һ�����������ж���һ������һ������������ʾ��˼
					//��ʾ֮������ʾ����˼����ʾ��˼֮�����ж��Ƿ������ʶ�������
					if(rrjbFlag){//��Ϊtrue��ʱ��˵����������Ѿ�������ʶ�ˣ��ٽ����ж��Ƿ������ʶ
						jl.setText("<html><body>"+jdbcDemo.getWord()+"<br>"+jdbcDemo.getMean(jdbcDemo.getWord())+"<body></html>");
						rrjbFlag=false;
					}
					else{
						rrjbFlag=true;
						String temp=jdbcDemo.nextWord();
						jl.setText(temp);
					}
					
				}
				else {//�жϽ������������Ƿ��е���
					
					if(currentDay==5||currentDay<0||mainDay-removeDay[currentDay]<=0)
						jl.setText("ȫ�������Ѿ�����");
					else {
						mainDay=mainDay-removeDay[currentDay];
						jl.setText("��ʼ��"+mainDay+"��");
						try {
							Thread.sleep(2000);
							rjbFirst.setText("�����Ǽǵ�"+mainDay+"��ĵ���");
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
		
		//д���ʴ���
		String[][] dataWord = new String[31][2];
		String[] title = {"����","��˼"};
		final JTable jta = new JTable(dataWord,title);
		jta.setBounds(250, 20, 100, 100);
		JScrollPane jsl = new JScrollPane(jta);//�������ӹ���
		JButton jbRight = new JButton("����");
		final JTextField jtf = new JTextField(5);
		jta.setRowHeight(50);//���ñ��ĸ߶�
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
			if(!al1.isEmpty()) {//���ݷǿ� ����
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
		
		JLabel jlabel = new JLabel("����Ҫ�����������");
		wjp.add(jlabel);
		jlabel.setBounds(350, 510, 150, 30);//��ǩ��λ��
		wjp.add(jsl);
		jsl.setBounds(150, 10, 500, 500);//����д���ʽ���
		wjp.add(jtf);
		jtf.setBounds(477, 510, 90, 30);//��������������λ��
		wjp.add(jbRight);
		jbRight.setBounds(570, 510, 70, 30);//���ô����λ��
		wjp.setLayout(null);
		ImageIcon wBack = new ImageIcon("lib/wave1.jpg");
		JLabel wback = new JLabel(wBack);
		wback.setBounds(0, 0, 800, 650);
		wjp.add(wback);
		
		//�������д���,���һ������������Ŵ�
		JScrollPane misJsl = new JScrollPane(jdbcDemo.misJta);//�������ӹ���
		mjp.setLayout(null);
		b3.setBounds(50, 30, 80, 50);//���÷���λ��
		misJsl.setBounds(150, 10, 500, 500);//���ô������а����
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
