package audioTrans;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javazoom.jl.player.Player;

public class http_to_audio {
	/**
	 * 加单词意思
	 * */
	public  void setAudio(String str) {
	//输入单词意思，当输入单词意思的时候读取其语音文件，调用了有道语音api和一位大佬的jar文件进行http转为audio
		str=str.replace(" ","%20");//空格换成有道可以识别的语音
	    String url = "http://dict.youdao.com/dictvoice?audio="+str;
	    getImgerFormNetByUrl(url);
	}
	/**
	 * 根据地址获得数据的字节流
	 * @param strUrl 网络连接地址
	 * @return
	 */
	public static byte[] getImgerFormNetByUrl(String strUrl){
	    HttpURLConnection conn = null;
	    try {
	        URL url = new URL(strUrl);
	        conn = (HttpURLConnection) url.openConnection();
	        //conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"); //权限限制时可用此方法
	        conn.setRequestMethod("GET");
	        conn.setConnectTimeout(5*1000);
	        InputStream inputStream = conn.getInputStream();
	        Player p = new Player(inputStream);
	        p.play();
	    }catch (Exception e){
	        e.printStackTrace();
	    }finally {
	        conn.disconnect();
	    }
	    return null;
	}
}