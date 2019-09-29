package com.qa.util;

import java.util.Hashtable;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.qa.base.TestBase;

public class test extends TestBase{
	public static Workbook book;
	public static Sheet sheet;
	
	
	@Test(dataProvider="dataSet")
	public void testing(Hashtable<String,String>data)  {
	System.out.println(data.get("TCID"));
	    }

	@DataProvider
	public Object[][] dataSet() throws FilloException {
		Hashtable<String,String>table=null;
		Fillo filo=new Fillo();
		Connection con=filo.getConnection(System.getProperty("user.dir")+"/Test_Suite_Data.xlsx");
		String query="Select * from runner";
		Recordset rs=con.executeQuery(query);
		Object[][] data=new Object[rs.getCount()][1];
		int count=0;
		List<String> l=rs.getFieldNames();
		while(rs.next()) {
			table=new Hashtable<String,String>();
			for(int i=0;i<l.size();i++) {
				table.put(l.get(i), rs.getField(l.get(i)));
				
			}
			data[count][0]=table;
			count++;
			
		}rs.close();
		con.close();
		
		
return data;

	}




}
