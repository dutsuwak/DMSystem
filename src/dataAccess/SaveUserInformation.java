package dataAccess;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

//Clase que permite leer el archivo properties.
public class SaveUserInformation {
	
	public static SaveUserInformation file = null;
	public static Properties props = new Properties();
	OutputStream input =null;
	static FileInputStream in;
	static FileOutputStream out;
	
	public static void SaveInfo(String pUser, String pPassword){
		
		try{
			
			
			in = new FileInputStream("src/Resources/user.properties");
			props = new Properties();
			props.load(in);
			in.close();

			out = new FileOutputStream("src/Resources/user.properties");
			props.setProperty(pUser, pPassword);
			props.store(out, null);
			out.close();
			
			
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	

}
