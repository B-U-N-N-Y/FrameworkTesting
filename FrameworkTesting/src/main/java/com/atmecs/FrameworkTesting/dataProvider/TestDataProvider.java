package com.atmecs.FrameworkTesting.dataProvider;

import org.testng.annotations.DataProvider;

import com.atmecs.FrameworkTesting.constant.FileConstant;
import com.atmecs.FrameworkTesting.utils.ProvideData;


/**
 * In this class, data is given by the dataprovider
 * @author arjun.santra
 */
public class TestDataProvider {
	/**
	 * In this method, getting the More info button data object array and returning
	 * to the calling method
	 */

	@DataProvider(name = "dummy")
	public Object[][] getData() {
		ProvideData provideData = new ProvideData(FileConstant.TESTDATA_FILE, 0);
		Object[][] getData = provideData.provideData();
		return getData;
	}
	
	
	

	

//	public static void main(String[] args) {
//
//		Object[][] data = new TestDataProvider().getCityData();
//		for (Object[] objects : data) {
//			String userName = (String) objects[0];
//			String password = (String) objects[1];
//			System.out.println(" " + userName + "   " + password);
//		}
//	}
}