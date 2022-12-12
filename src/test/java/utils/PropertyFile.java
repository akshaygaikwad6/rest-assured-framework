package utils;

import java.io.FileInputStream;
import java.io.InputStream;

import java.util.*;

public class PropertyFile {
	public static InputStream input;
	public static Properties prop;
	public static String inputValue;

	public static String getValue(String value) {

		try {
			prop = new Properties();
			FileInputStream fis = new FileInputStream("src/test/java/utils/config.properties");
			prop.load(fis);
			inputValue = prop.getProperty(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputValue;
	}
	
	// Second way to call config file properties 
public PropertyFile() {
		
		try {
			prop = new Properties();
			FileInputStream fis = new FileInputStream("D:\\RestApiDemoProject\\src\\utils\\config.properties");
			prop.load(fis);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
