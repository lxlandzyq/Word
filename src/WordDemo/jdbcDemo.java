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
	 static Map<String,String> map = new HashMap<String, String>();//用于存放单词和单词意思
	 static ArrayList<String> array = new ArrayList();//用于存放错词
	 static ArrayList<String> al = new ArrayList();
	 static String ant;
	 static int[] removeDay = {1,1,2,3,7};//用于存放每一个时间点的
	public static void getDay() throws Exception {//获取已经输入的天数为多少
		Class.forName("com.mysql.cj.jdbc.Driver");
		try(
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/word","root","123456");
				//3，使用Connection来创建一个Statement对象
				Statement stmt = conn.createStatement();
				//4，执行sql语句
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
			System.out.println("正在删除数据"+map.size());
		}
	}
	public static String getMean(String word) {//当不认识的将此数值返回给窗口，并存入
		
		return map.get(word);
	}
	public static void addArray(String word){
		array.add(word);
	}
	public static String getWord() {
		return ant;//将此时的单词给window
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
				return "";//全部结束，可以返回
			}
		}
		Random r = new Random();
		System.out.println(temp.size());
		System.out.println(array);
		int a=r.nextInt(temp.size());
		
		ant=temp.remove(a);//当前的的单词为ant
		return ant;
	}
	/**
	 * @param 存入到数据
	 * @param 存入的是第几天的数据
	 * @throws Exception 
	 * */
	//用于存错误的
	static String[][] Misdata = new String[200][3] ;
	static String[] misTitle = {"单词","意思","错误次数"};
	static DefaultTableModel model = new DefaultTableModel(); // 新建一个默认数据模型
	static JTable misJta = new JTable(Misdata,misTitle);
	public static void misRank() throws Exception {
		//错误排行  一个查询语句，从大到小排行,将排序的数值
		misJta.setRowHeight(50);//设置高度
		misJta.setFont(new Font("宋体",Font.PLAIN,15));
		Class.forName("com.mysql.cj.jdbc.Driver");
		model.setColumnIdentifiers(new Object[]{misTitle[0],misTitle[1],misTitle[2]});
		try(
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/word","root","123456");
				//3，使用Connection来创建一个Statement对象
				Statement stmt = conn.createStatement();
				//4，执行sql语句
				ResultSet rs = stmt.executeQuery("select *"+"from cet4 ORDER BY mistake DESC LIMIT 199;");//只输出结果前199条
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
	 * @param放入的为加错的单词
	 *对不认识的数据+1个错误
	 * */
	public static void addMistak(String word) throws Exception {
		//对数据的加错
		Class.forName("com.mysql.cj.jdbc.Driver");
		try(
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/word?useUnicode=true&characterEncoding=UTF-8","root","123456");
				//3，使用Connection来创建一个Statement对象
				//Statement stmt = conn.createStatement();
				//4，执行sql语句
				PreparedStatement ps = conn.prepareStatement("UPDATE cet4 SET mistake=mistake+1 WHERE word='"+word+"';");//数据修改
				
				){
			ps.executeUpdate();
		}
	}
	/**
	 * @param 对数据库的写入操作
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
		//存入到数据库中,多条写入
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println(al1);
		int count = getCount()+1;
		try(
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/word?useUnicode=true&characterEncoding=UTF-8","root","123456");
				//3，使用Connection来创建一个Statement对象
				//Statement stmt = conn.createStatement();
				//4，执行sql语句
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
					System.out.println("导入成功");
				}
	}
	//写单词处的初始值
	public static void init(int i) throws Exception {//将数据库初始值
		// TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver");
		try(
				//2，使用DriverManager获取数据库连接
				//其中返回的Connection就代表java程序和数据库的连接
				//不同数据库的URL写法需要查驱动文档，用户名，密码有DBA分配
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/word","root","123456");
				//3，使用Connection来创建一个Statement对象
				
				Statement stmt = conn.createStatement();
				//4，执行sql语句
				ResultSet rs = stmt.executeQuery("select *"+"from cet4"+" where day="+i);
				){
					while(rs.next()) {
						al.add(rs.getString(2));//用于乱序
						map.put(rs.getString(2), rs.getString(3));
					}
				}
		
	}

}