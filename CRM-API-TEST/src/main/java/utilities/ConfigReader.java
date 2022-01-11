package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private static Properties properties;
	
	public static Properties getGlobalDataProperties() {
		properties = new Properties();
		try {
			FileInputStream ip = new FileInputStream("./src/test/resources/data.properties");
			properties.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return properties;
	}
}
