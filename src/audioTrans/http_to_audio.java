package audioTrans;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javazoom.jl.player.Player;

public class http_to_audio {
	/**
	 * �ӵ�����˼
	 * */
	public  void setAudio(String str) {
	//���뵥����˼�������뵥����˼��ʱ���ȡ�������ļ����������е�����api��һλ���е�jar�ļ�����httpתΪaudio
		str=str.replace(" ","%20");//�ո񻻳��е�����ʶ�������
	    String url = "http://dict.youdao.com/dictvoice?audio="+str;
	    getImgerFormNetByUrl(url);
	}
	/**
	 * ���ݵ�ַ������ݵ��ֽ���
	 * @param strUrl �������ӵ�ַ
	 * @return
	 */
	public static byte[] getImgerFormNetByUrl(String strUrl){
	    HttpURLConnection conn = null;
	    try {
	        URL url = new URL(strUrl);
	        conn = (HttpURLConnection) url.openConnection();
	        //conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"); //Ȩ������ʱ���ô˷���
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