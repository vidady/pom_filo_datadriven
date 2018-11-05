package com.qa.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map.Entry;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class jsonReader {


	@Test(dataProvider="searchJsonElement")
	public void testing(Hashtable<String,String>data) {

		System.out.println(data.get("Amount"));
	}

	@DataProvider
	public static Object[][] searchJsonElement(Method m) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonParser jsonParser= new JsonParser();
		JsonObject jsonObj = jsonParser.parse(new FileReader(System.getProperty("user.dir")+"/src/test/data/TestData.json")).getAsJsonObject();
		JsonArray array= (JsonArray) jsonObj.get("DataSet");
		int j=0;
		Hashtable<String,String> table=null;
		Object[][] matrix= new Object[array.size()][1];
		for(JsonElement jsonElement :array)
		{
			if(jsonElement.toString().contains(m.getName().toString()))
			{
				table=new Hashtable<String,String>();
				for(Entry<String, JsonElement> entry:jsonElement.getAsJsonObject().entrySet())
				{
					String key = entry.getKey().toString();
					String value= entry.getValue().toString().replace("\"", "").toString();		
					table.put(key, value);			
				}
				matrix[j][0]=table;
				j++;
			}
		}
		return matrix;
	}
}
