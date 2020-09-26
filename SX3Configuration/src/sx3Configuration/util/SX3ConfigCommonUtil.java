package sx3Configuration.util;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import sx3Configuration.model.SX3Configuration;

public class SX3ConfigCommonUtil {

	private SX3ConfigCommonUtil() {
		// don't instantiate this util class
	}
	

	public static void createSx3JsonFile(SX3Configuration sx3Configuration, String sx3ConfigurationFilePath) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Double.class,  new JsonSerializer<Double>() {

		    public JsonElement serialize(Double src, Type typeOfSrc,
		                JsonSerializationContext context) {
		            Long value = (long)Math.round(src);
		            return new JsonPrimitive(value);
		        }
		    });
		Gson gson = gsonBuilder.setPrettyPrinting().disableHtmlEscaping().create();
		try (FileWriter writer = new FileWriter(sx3ConfigurationFilePath)) {
			gson.toJson(sx3Configuration, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
