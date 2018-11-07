
/**
 * 根据服务端口判断amoeba服务是否运行
 * 
 * @author Xiaojie Chen
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class AmoebaHandler {
	private static AmoebaHandler instance = new AmoebaHandler();
	
	public static AmoebaHandler getInstance()
	{
		return instance;
	}
	
	public  boolean getRunning() {
		Process process;
		String line;
		
		try {
			process = Runtime.getRuntime().exec("netstat -an");
			InputStreamReader reader = new InputStreamReader(process.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(reader);		
			
			while((line = bufferedReader.readLine()) != null)
			{
				if(line.contains(":8066"))
					return true;
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(AmoebaHandler.getInstance().getRunning());
	}
}
