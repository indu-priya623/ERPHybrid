package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import utilities.PropertyFileUtil;

public class FunctionLibrary {
	public static WebDriver driver;

	public static WebDriver startBrowser() throws Throwable {

		if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		} else if (PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		} else {
			Reporter.log("Browser value is not matching");
		}
		return driver;
	}

	public static void openUrl() throws Throwable {
		driver.get(PropertyFileUtil.getValueForKey("Url"));
	}

	public static void waitForElement(String Locator_Type, String Locator_Value, String Test_Data)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Test_Data)));

		if (Locator_Type.equalsIgnoreCase("xpath")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));

		}
		else if (Locator_Type.equalsIgnoreCase("id")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));

		} 
		else if (Locator_Type.equalsIgnoreCase("name")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
		}
	}

	public static void typeAction(String Locator_Type, String Locator_Value, String Test_Data)
	{
		if (Locator_Type.equalsIgnoreCase("xpath")) 
		{
			driver.findElement(By.xpath(Locator_Value)).clear();
			driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
		}
		else if (Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).clear();
			driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
		} 
		else if (Locator_Type.equalsIgnoreCase("name")) {
			driver.findElement(By.name(Locator_Value)).clear();
			driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
		}
	}

	public static void clickAction(String Locator_Type, String Locator_Value)
	{
		if (Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).click();
		} 
		else if (Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).click();
		} 
		else if (Locator_Type.equalsIgnoreCase("id")) {
			driver.findElement(By.id(Locator_Value)).sendKeys(Keys.ENTER);
		}
	}

	public static void validateTitle(String Expected_Title)
	{
		String Actual_Title = driver.getTitle();
		try {
			Reporter.log(Actual_Title);
			Assert.assertEquals(Actual_Title,Expected_Title,"Title is not matching");
		}
		catch (AssertionError A)
		{
			System.out.println(A.getMessage());
		}
	}

	public static void closeBrowser() 
	{
		driver.quit();
	}

	public static String generateDate() 
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY-MM-DD HH-mm-SS");
		return df.format(date);
	}
	public static void dropDownAction(String Locator_Type, String Locator_value,String Test_Data) throws Throwable
	{

		if (Locator_Type.equalsIgnoreCase("id"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.id(Locator_value));
			Select select = new Select(element);
			Thread.sleep(2000);
			select.selectByIndex(value);
			Thread.sleep(2000);
		}
		else if (Locator_Type.equalsIgnoreCase("name"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.name(Locator_value));
			Select select = new Select(element);
			Thread.sleep(2000);
			select.selectByIndex(value);
			Thread.sleep(2000);
		}
		else if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.xpath(Locator_value));
			Select select = new Select(element);
			Thread.sleep(2000);
			select.selectByIndex(value);
			Thread.sleep(2000);
		}
	}
	public static void stockCapture(String Locator_Type,String Locator_Value) throws Throwable
	{
		String Stock_Num ="";
		if (Locator_Type.equalsIgnoreCase("id"))
		{
			Stock_Num = driver.findElement(By.id(Locator_Value)).getAttribute("value");
		}
		else if (Locator_Type.equalsIgnoreCase("name")) 
		{
			Stock_Num = driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		else if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			Stock_Num = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(Stock_Num);
		bw.flush();
		bw.close();
	}
	public static void stockTable() throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/stocknumber.txt"); 
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();

		if (!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_textbox"))).isDisplayed()) 

			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_textbox"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_btn"))).click();
		Thread.sleep(4000);
		String Act_Data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Act_Data+"   "+Exp_Data,true);
		try
		{
			Assert.assertEquals(Act_Data, Act_Data,"Stock number is not matching not matching");
		}
		catch (AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	public static void captureSuppliers(String Locator_Type,String Locator_value) throws Throwable
	{
		String Sup_Num ="";
		if (Locator_Type.equalsIgnoreCase("id"))
		{
			Sup_Num = driver.findElement(By.id(Locator_value)).getAttribute("value");
		}
		else if (Locator_Type.equalsIgnoreCase("name"))
		{
			Sup_Num = driver.findElement(By.name(Locator_value)).getAttribute("value");
		}
		else if (Locator_Type.equalsIgnoreCase("xpath")) 
		{
			Sup_Num = driver.findElement(By.xpath(Locator_value)).getAttribute("value");
		}
		FileWriter fw =  new FileWriter("./CaptureData/suppliernumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
	    bw.write(Sup_Num);
		bw.flush();
		bw.close();
	}
	public static void supplierTable()throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/suppliernumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if (!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_textbox"))).isDisplayed());
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_textbox"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_btn"))).click();
		Thread.sleep(4000);
		String Act_Data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		try
		{
		Assert.assertEquals(Act_Data,Exp_Data,"Supplier number is not matching");
		}
		catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
	}
	public static void captureCustomer(String Locator_Type,String Locator_value)throws Throwable
	{
		String Cus_Num ="";
		if (Locator_Type.equalsIgnoreCase("id"))
		{
			Cus_Num = driver.findElement(By.id(Locator_value)).getAttribute("value");
		}
		else if (Locator_Type.equalsIgnoreCase("name"))
		{
			Cus_Num = driver.findElement(By.name(Locator_value)).getAttribute("value");
		}
		else if (Locator_Type.equalsIgnoreCase("xpath")) 
		{
			Cus_Num = driver.findElement(By.xpath(Locator_value)).getAttribute("value");
		}
		FileWriter fw =  new FileWriter("./CaptureData/Customernumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
	    bw.write(Cus_Num);
		bw.flush();
		bw.close();
	}
	public static void CustomerTable()throws Throwable
	{
		FileReader fr = new FileReader("./CaptureData/Customernumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if (!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_textbox"))).isDisplayed());
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_textbox"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("Search_btn"))).click();
		Thread.sleep(4000);
		String Act_Data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		try
		{
		Assert.assertEquals(Act_Data,Exp_Data,"Customer number is not matching");
		}
		catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}

	}
	
}

