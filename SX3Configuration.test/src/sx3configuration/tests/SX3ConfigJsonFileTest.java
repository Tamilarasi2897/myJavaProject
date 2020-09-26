package sx3configuration.tests;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import junit.framework.Assert;
import sx3Configuration.mergertool.BytesStreamsAndHexFileUtil;
import sx3Configuration.model.ConfigTableGeneral;
import sx3Configuration.model.SX3Configuration;
//import utility.tool.merger.BytesStreamsAndHexFileUtil;
public class SX3ConfigJsonFileTest {
	/*public static final String JSON_FILE =  "C:\Users\test01.json"; 
	private SX3Configuration sx3Obj;
	private Properties hexMapProperties;
	//C:\Users\test01.json
	@Before
	public void setup(){
		// Gson parser object to parse json file
		Gson gson = new Gson();
		hexMapProperties = BytesStreamsAndHexFileUtil.gethexValueMappingProperties();
		try {
			JsonReader reader = new JsonReader(new FileReader(JSON_FILE));
			sx3Obj = gson.fromJson(reader, SX3Configuration.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings("deprecation")
	@Test
	public void testConfigTableeneral(){
		ConfigTableGeneral configTableGen = sx3Obj.getCONFIG_TABLE_GENERAL();
		
		/// check weather object present or not 
		Assert.assertNotNull("ConfigTableGeneral Oject not found in Json", configTableGen);
			// check hex value for config table general 
		Assert.assertEquals("CY", configTableGen.getSIGNATURE());
		Assert.assertEquals(2, configTableGen.getSIGNATURE().length());
		Assert.assertTrue( configTableGen.getSIGNATURE() instanceof String);
		
		Assert.assertEquals("0x3A61F", configTableGen.getCONFIG_TABLE_CHECKSUM());
		Assert.assertEquals(7, configTableGen.getCONFIG_TABLE_CHECKSUM().length());
		Assert.assertTrue( configTableGen.getCONFIG_TABLE_CHECKSUM().contains("0x3A61F"));
		
		Assert.assertEquals("0x000011E4", configTableGen.getCONFIG_TABLE_LENGTH());
		Assert.assertEquals(10, configTableGen.getCONFIG_TABLE_LENGTH().length());
		Assert.assertTrue( configTableGen.getCONFIG_TABLE_LENGTH().contains("0x000011E4"));*/
		
		/*Assert.assertEquals("1", configTableGen.getVERSION_MAJOR());
		//Assert.assertEquals(1, configTableGen.getVERSION_MAJOR().length());
		//Assert.assertTrue( configTableGen.getVERSION_MAJOR().contains("1"));
			
			Assert.assertEquals("1", configTableGen.getVERSION_MAJOR());
			Assert.assertEquals(6, configTableGen.getSIGNATURE().length());
			Assert.assertTrue( configTableGen.getSIGNATURE().contains("1"));
			
			Assert.assertEquals("0", configTableGen.getVERSION_MINOR());
			Assert.assertEquals(6, configTableGen.getSIGNATURE().length());
			Assert.assertTrue( configTableGen.getSIGNATURE().contains("0"));
			
			Assert.assertEquals("0", configTableGen.getVERSION_PATCH());
			Assert.assertEquals(6, configTableGen.getSIGNATURE().length());
			Assert.assertTrue( configTableGen.getSIGNATURE().contains("0"));
			
			Assert.assertEquals("4", configTableGen.getCONFIGURATION_TYPE());
			Assert.assertEquals(6, configTableGen.getSIGNATURE().length());
			Assert.assertTrue( configTableGen.getSIGNATURE().contains("4"));
			
			Assert.assertEquals("FF", configTableGen.getRESERVED());
			Assert.assertEquals(6, configTableGen.getSIGNATURE().length());
			Assert.assertTrue( configTableGen.getSIGNATURE().contains("FF"));*/
			
			
	}
