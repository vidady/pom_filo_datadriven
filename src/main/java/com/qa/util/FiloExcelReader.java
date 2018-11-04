package com.qa.util;

import java.util.ArrayList;
import java.util.List;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class FiloExcelReader {
	
	public static String readClassRunnerMode(String testClassName) {
		Fillo fillo=new Fillo();
		Recordset rs;
		Connection connection;
		String result = null;
		try {
			connection = fillo.getConnection(System.getProperty("user.dir")+"/Test_Suite_Data.xlsx");
			String query="Select runmode from runner where TCID='"+testClassName+"'";
		    rs=connection.executeQuery(query);
			
			while(rs.next()) {
				result=rs.getField("runmode");
			}
			System.out.println(result);
		} catch (FilloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static List<String> getAllTestClasses(){
		Fillo fillo=new Fillo();
		List<String> list=null;
		Recordset rs;
		Connection connection;
		try {
			connection = fillo.getConnection(System.getProperty("user.dir")+"/Test_Suite_Data.xlsx");
			String query="Select TCID from runner";
		    rs=connection.executeQuery(query);
		    list=new ArrayList<String>();
		    while(rs.next()) {
		    	list.add(rs.getField("TCID"));
		    }

		    	
		    	System.out.println(list);

		} catch (FilloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return list;
	}

}
