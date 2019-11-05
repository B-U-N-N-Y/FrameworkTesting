package com.atmecs.FrameworkTesting.testscripts;

import java.util.Properties;

import org.testng.annotations.Test;

import com.atmecs.FrameworkTesting.base.TestBase;
import com.atmecs.FrameworkTesting.constant.FileConstant;
import com.atmecs.FrameworkTesting.helper.Utility;
import com.atmecs.FrameworkTesting.helper.WebTable;
import com.atmecs.FrameworkTesting.reports.LogReport;
import com.atmecs.FrameworkTesting.utils.ReadPropertiesFile;

public class TableTest extends TestBase {
	
	Utility utility;
	WebTable webTable;
	Properties loc;
	LogReport log;
	
		@Test
		public void tableTesting() {
			utility=new Utility(driver);
			webTable=new WebTable(driver);
			log=new LogReport();
			loc=new ReadPropertiesFile().loadProperty(FileConstant.LOCATOR_FILE);
			log.info("Table reading start");
			webTable.getTableData(loc.getProperty("loc.toolqa.table"));
			log.info("Table data print successfully");
		}
}
