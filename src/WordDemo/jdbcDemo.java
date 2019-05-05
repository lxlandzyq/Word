package WordDemo;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class jdbcDemo {
	 static int day;
	 static Map<String,String> mark = new HashMap();
	 static Map<String,String> map = new HashMap<String, String>();//���ڴ�ŵ��ʺ͵�����˼
	 static ArrayList<String> array = new ArrayList();//���ڴ�Ŵ��
	 static ArrayList<String> al = new ArrayList();
	 static String ant;
	 static int[] removeDay = {1,1,2,3,7};//���ڴ��ÿһ��ʱ����
	public static void getDay() throws Exception {//��ȡ�Ѿ����������Ϊ����
		Class.forName("com.mysql.cj.jdbc.Driver");
		try(
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/word","root","123456");
				//3��ʹ��Connection������һ��Statement����
				Statement stmt = conn.createStatement();
				//4��ִ��sql���
				ResultSet rs = stmt.executeQuery("select MAX(day) "+" from cet4; ");
				){
						rs.next();
						day=rs.getInt(1);
					}
	}
	public static void cleanMap() {
		while(!map.isEmpty()) {
			map.clear();
			al.clear();
			array.clear();
			System.out.println("����ɾ������"+map.size());
		}
	}
	public static String getMean(String word) {//������ʶ�Ľ�����ֵ���ظ����ڣ�������
		
		return map.get(word);
	}
	public static void addArray(String word){
		array.add(word);
	}
	public static String getWord() {
		return ant;//����ʱ�ĵ��ʸ�window
	}
	
	public static String nextWord() {
		ArrayList<String> temp = new ArrayList();
		if(!(al.size()==0)) {
			temp=al;
		}
		else {
			if(!(array.size()==0)) {
				temp=array;
			}
			else {
				return "";//ȫ�����������Է���
			}
		}
		Random r = new Random();
		System.out.println(temp.size());
		System.out.println(array);
		int a=r.nextInt(temp.size());
		
		ant=temp.remove(a);//��ǰ�ĵĵ���Ϊant
		return ant;
	}
	/**
	 * @param ���뵽����
	 * @param ������ǵڼ��������
	 * @throws Exception 
	 * */
	//���ڴ�����
	static String[][] Misdata = new String[200][3] ;
	static String[] misTitle = {"����","��˼","�������"};
	static DefaultTableModel model = new DefaultTableModel(); // �½�һ��Ĭ������ģ��
	static JTable misJta = new JTable(Misdata,misTitle);
	public static void misRank() throws Exception {
		//��������  һ����ѯ��䣬�Ӵ�С����,���������ֵ
		misJta.setRowHeight(50);//���ø߶�
		misJta.setFont(new Font("����",Font.PLAIN,15));
		Class.forName("com.mysql.cj.jdbc.Driver");
		model.setColumnIdentifiers(new Object[]{misTitle[0],misTitle[1],misTitle[2]});
		try(
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/word","root","123456");
				//3��ʹ��Connection������һ��Statement����
				Statement stmt = conn.createStatement();
				//4��ִ��sql���
				ResultSet rs = stmt.executeQuery("select *"+"from cet4 ORDER BY mistake DESC LIMIT 199;");//ֻ������ǰ199��
				){
					for(int i=0;i<200;i++) {
						
						if(!rs.next())break;
						Misdata[i][0]=rs.getString(2);
						Misdata[i][1]=rs.getString(3);
						Misdata[i][2]=rs.getString(5);	
					}
					
				}
	}
	/**
	 * @param�����Ϊ�Ӵ�ĵ���
	 *�Բ���ʶ������+1������
	 * */
	public static void addMistak(String word) throws Exception {
		//�����ݵļӴ�
		Class.forName("com.mysql.cj.jdbc.Driver");
		try(
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/word?useUnicode=true&characterEncoding=UTF-8","root","123456");
				//3��ʹ��Connection������һ��Statement����
				//Statement stmt = conn.createStatement();
				//4��ִ��sql���
				PreparedStatement ps = conn.prepareStatement("UPDATE cet4 SET mistake=mistake+1 WHERE word='"+word+"';");//�����޸�
				
				){
			ps.executeUpdate();
		}
	}
	/**
	 * @param �����ݿ��д�����
	 * @throws ClassNotFoundException 
	 * */
	public static int getCount() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		int count=0;
		try(
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/word?useUnicode=true&characterEncoding=UTF-8","root","123456");
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select MAX(count) "+" from cet4; ");
				){
						rs.next();
						 count=rs.getInt(1);
					}
		return count;
		
	}
	public static void WriteData(ArrayList<String> al1,ArrayList<String> al2,int day) throws Exception {
		//���뵽���ݿ���,����д��
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println(al1);
		int count = getCount()+1;
		try(
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/word?useUnicode=true&characterEncoding=UTF-8","root","123456");
				//3��ʹ��Connection������һ��Statement����
				//Statement stmt = conn.createStatement();
				//4��ִ��sql���
				PreparedStatement ps = conn.prepareStatement("insert into cet4 values(?,?,?,?,0)")
				
				){
				int t=0;
				while(t<al1.size()&&!al1.get(t).equals("")) {
					count++;
					ps.setInt(1, count);
					ps.setString(2, al1.get(t));
					ps.setString(3, al2.get(t));
					ps.setInt(4, day);
					ps.executeUpdate();
					t++;
				}
					System.out.println("����ɹ�");
				}
	}
	//д���ʴ��ĳ�ʼֵ
	public static void init(int i) throws Exception {//�����ݿ��ʼֵ
		// TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver");
		try(
				//2��ʹ��DriverManager��ȡ���ݿ�����
				//���з��ص�Connection�ʹ���java��������ݿ������
				//��ͬ���ݿ��URLд����Ҫ�������ĵ����û�����������DBA����
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/word","root","123456");
				//3��ʹ��Connection������һ��Statement����
				
				Statement stmt = conn.createStatement();
				//4��ִ��sql���
				ResultSet rs = stmt.executeQuery("select *"+"from cet4"+" where day="+i);
				){
					while(rs.next()) {
						al.add(rs.getString(2));//��������
						map.put(rs.getString(2), rs.getString(3));
					}
				}
		
	}

}