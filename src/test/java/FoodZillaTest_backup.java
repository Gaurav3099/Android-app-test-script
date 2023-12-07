import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.MobileElement;
import junit.framework.Assert;




@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FoodZillaTest_backup {
	AndroidDriver<AndroidElement> driver;

	@Before
	public void setUp() throws MalformedURLException {

		System.out.println("Running Automated Test cases on FOODZILLA AI Mobile Application");
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		dc.setCapability("platformName", "Android");
		dc.setCapability("udid", "8b42d3ae");
		dc.setCapability(MobileCapabilityType.DEVICE_NAME, "SM A536U1");
		dc.setCapability("appPackage", "io.foodzilla.app");
		dc.setCapability("appActivity", "io.foodzilla.app.MainActivity");
		dc.setCapability("noReset", "true");
		URL url = new URL("http://127.0.0.1:4723/wd/hub");
		driver = new AndroidDriver<AndroidElement>(url, dc);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

//	boolean askLogin = true;
	boolean askLogin = false;

	@Test
	public void Login() {

		try {
			driver.findElementByXPath("//android.widget.TextView[@text='Log in (for invited users)']").isDisplayed();
		} catch (Exception e) {
			askLogin = false;
		}

		if (askLogin) {
			System.out.println("Login Required");
			System.out.println("About to click on loggin button");
			String loginInvitedUser = "//android.widget.TextView[@text='Log in (for invited users)']";
			// driver.findElementByXPath("//android.widget.TextView[@text='Log in (for
			// invited users)']").click();
			clickElement(loginInvitedUser);
			System.out.println("clicked on loggin button");

			String enterEmailLocator = "//android.widget.EditText[@text='Enter email']";
			
			enterText(enterEmailLocator, "gaurav.sarkar830@gmail.com");

			String enterPasswordLocator = "//android.widget.EditText[@text='Enter password']";
			// driver.findElementByXPath("//android.widget.EditText[@text='Enter
			// password']").sendKeys("AP24m2349@");
			enterText(enterPasswordLocator, "Foodzilla@23");

			String continueButton = "//android.widget.TextView[@text='Continue']";
			// driver.findElementByXPath("//android.widget.TextView[@text='Continue']").click();
			clickElement(continueButton);

			System.out.println("clicked on continue button");

		} else {
			System.out.println("Didnt ask for login");
		}

	}
	
	
	public void clickElement(String xpathString) {
		driver.findElementByXPath(xpathString).click();
		 System.out.println("The element got clicked");

	}

	public void enterText(String xpathString, String value) {
		driver.findElementByXPath(xpathString).sendKeys(value);
		;
	}

	public String getText(String xpathString) {
		String textValue = driver.findElementByXPath(xpathString).getText();
		return textValue;

	}

	public List<String> getActualItems() {
		String items = "//android.widget.TextView[contains(@text,'(')]";
		List<AndroidElement> elements = driver.findElements(By.xpath(items));
		List<String> itemsNamesActual = new ArrayList<String>();
		for (AndroidElement androidElement : elements) {
			String text = androidElement.getText();
			itemsNamesActual.add(text);
		}

		 System.out.println("itemsNamesActual " + itemsNamesActual);
		return itemsNamesActual;

	}

	public List<String> getExpectedItems(String[] expectedItems) {
		List<String> itemsNamesExpected = new ArrayList<String>();
		for (String item : expectedItems) {
			itemsNamesExpected.add(item);
		}
		return itemsNamesExpected;

	}
	

	public String getGivenAlbum(String albumName) {
		String albumNameDynamic = "//android.widget.TextView[@text='" + albumName + "']";
		return albumNameDynamic;

	}
	

	public void openGalleryAndSelectAlbum(String albumName, String imageIndex) throws InterruptedException {

		Thread.sleep(2000);
		String openGalleryLocator = "//android.widget.TextView[@text='From Gallery']";
		clickElement(openGalleryLocator);		
		String openFoodAlbum = getGivenAlbum(albumName);
		clickElement(openFoodAlbum);
		
		
		//-----
//		String selectFoodPicture = "(//android.widget.FrameLayout)[" + imageIndex + "]";
		String selectFoodPicture = getGivenAlbum(imageIndex);
//		System.out.println(selectFoodPicture);
		clickElement(selectFoodPicture);
		//------

		Thread.sleep(2000);
	}

	public void verifyExpectedAndActual(String[] expectedItems) {
		boolean matches = false;
		List<String> itemsNamesActual = getActualItems();
		
		List<String> itemsNamesExpected = getExpectedItems(expectedItems);
		
		if(itemsNamesExpected.size() > itemsNamesActual.size()) {
			for(String value: itemsNamesExpected) {
				//System.out.println("Expected > actual");
				System.out.println(itemsNamesActual.size());
				if (itemsNamesActual.contains(value)) {
					matches = true;
					break;
				}
			}
		}else {
			//System.out.println("Expected < actual");
			for(String value: itemsNamesActual) {
				if (itemsNamesExpected.contains(value)) {
					matches = true;
					break;
				}
			}
		}
		
		if (itemsNamesExpected.size() == itemsNamesActual.size()) {
		
			matches = itemsNamesActual.equals(itemsNamesExpected);
		}

	
		if (itemsNamesActual.size() <= 0) {

			
			String noItemsSorry = "//android.view.ViewGroup//android.view.ViewGroup//android.widget.TextView[contains(@text,'Sorry')]";
			String message = getText(noItemsSorry);
			// String finalMessage = "The expected Items are " + itemsNamesExpected + "
			// however we got this **** " + message +" **** ";
			System.out.println("The expected Items are " + itemsNamesExpected + " however we got this **** " + message
					+ "  ****   ");
			assertTrue(false);
			// Assert.assertTrue(false, finalMessage);
		} else if (!matches) {
			System.out.println(
					"The items doesnt match, Expected " + itemsNamesExpected + " however got this " + itemsNamesActual);

			assertTrue(false);

		} else {
			System.out.println(
					"The items matched, Expected " + itemsNamesExpected + " and actual items " + itemsNamesActual);
			assertTrue(true);
		}

	}

	public void addToDairy() throws InterruptedException {
		Thread.sleep(7000);
		System.out.println("Add to Dairy");
		String addToDairy = "//android.widget.TextView[@text='Add to Diary']";
		clickElement(addToDairy);
		Thread.sleep(3000);

	}

	@Test
	public void TestCaseA1() throws InterruptedException {
		openGalleryAndSelectAlbum("Pics", "bread.jpeg");

		String[] expectedItems = { "Bread (50grams)" };
		verifyExpectedAndActual(expectedItems);

		addToDairy();
		driver.closeApp();

	}
	@Test
	public void TestCaseA2() throws InterruptedException {
		openGalleryAndSelectAlbum("Pics", "fruitsalad.jpg");

		String[] expectedItems = { "Fruit (50grams)", "Blackberries (50grams)", "Strawberries (50grams)", "Raspberries (50grams)", "Blueberries (50grams)"};
		verifyExpectedAndActual(expectedItems);

		addToDairy();
		driver.closeApp();

	}
	@Test
	public void TestCaseA3() throws InterruptedException {
		openGalleryAndSelectAlbum("Pics", "icecream.jpg");

		String[] expectedItems = { "Chocolate chip ice cream (50grams)"  };
		verifyExpectedAndActual(expectedItems);

		addToDairy();
		driver.closeApp();

	}
	@Test
	public void TestCaseA4() throws InterruptedException {
		openGalleryAndSelectAlbum("Pics", "chicken.jpeg");

		String[] expectedItems = { "Fried chicken (200grams)" };
		verifyExpectedAndActual(expectedItems);

		addToDairy();
		driver.closeApp();

	}
	@Test
	public void TestCaseA5() throws InterruptedException {
		openGalleryAndSelectAlbum("Pics", "rice2.jpg");

		String[] expectedItems = { "Rice (0.1cup)"};
		verifyExpectedAndActual(expectedItems);

		addToDairy();
		driver.closeApp();

	}
	@Test
	public void TestCaseA6() throws InterruptedException {
		openGalleryAndSelectAlbum("Pics", "chocolate.jpeg");

		String[] expectedItems = { "Chocolate (50grams)"};
		verifyExpectedAndActual(expectedItems);

		addToDairy();
		driver.closeApp();

	}

	@Test
	public void TestCaseA8() throws InterruptedException {
		openGalleryAndSelectAlbum("Pics", "coffeebeans.jpg");

		String[] expectedItems = { "Coffee beans (300ml)"};
		verifyExpectedAndActual(expectedItems);

		addToDairy();
		driver.closeApp();

	}
	
	@Test
	public void TestCase16() throws InterruptedException {
		openGalleryAndSelectAlbum("Pics", "camera.jpg");

		String[] expectedItems = { "Sorry, we could not detect foods in this picture, tap on \"Add Foods\" to add foods and ingredients."};
		verifyExpectedAndActual(expectedItems);

		addToDairy();
		driver.closeApp();

	}
	@Test
	public void TestCase17() throws InterruptedException {
		openGalleryAndSelectAlbum("Pics", "car.jpg");

		String[] expectedItems = { "Sorry, we could not detect foods in this picture, tap on \"Add Foods\" to add foods and ingredients."};
		verifyExpectedAndActual(expectedItems);

		addToDairy();
		driver.closeApp();

	}
	
	//-------------------------
	
//	@Test
//	public void TestCaseA7() throws InterruptedException {
//		openGalleryAndSelectAlbum("Pics", "14");
//
//		String[] expectedItems = { "Rice (0.1cup)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCaseA9() throws InterruptedException {
//		openGalleryAndSelectAlbum("Pics", "9");
//
//		String[] expectedItems = { "Sushi (200grams)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCaseB1() throws InterruptedException {
//		openGalleryAndSelectAlbum("Pics", "10");
//
//		String[] expectedItems = { "Kidney bean (50grams)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	
//	@Test
//	public void TestCaseB2() throws InterruptedException {
//		openGalleryAndSelectAlbum("Pics", "11");
//
//		String[] expectedItems = { "Ice cream (200grams)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//		
//
//	}
//	@Test
//	public void TestCaseB3() throws InterruptedException {
//		openGalleryAndSelectAlbum("Pics", "12");
//
//		String[] expectedItems = { "Chocolate (50grams)", "Chocolate (50grams)", "Wafer (50grams)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCaseB4() throws InterruptedException {
//		openGalleryAndSelectAlbum("Pics", "14");
//
//		String[] expectedItems = { "Cake (1000grams)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCaseB5() throws InterruptedException {
//		openGalleryAndSelectAlbum("Pics", "14");
//
//		String[] expectedItems = { "Rice (0.1cup)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCaseB6() throws InterruptedException {
//		openGalleryAndSelectAlbum("Pics", "15");
//
//		String[] expectedItems = { "Rice (0.1cup)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	
	
	
	//-------------------
//	@Test
//	public void TestCase18() throws InterruptedException {
//		openGalleryAndSelectAlbum("Camera", "3");
//
//		String[] expectedItems = { "Bread (50grams", "Lettuce (50grams)", "Tomato (50grams)", "Cheese (1 slice)", "Toast (50grams)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCase19() throws InterruptedException {
//		openGalleryAndSelectAlbum("Camera", "4");
//
//		String[] expectedItems = { "Burrito (50grams)", "Chicken (50grams)", "Cheese (1 slice)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCase20() throws InterruptedException {
//		openGalleryAndSelectAlbum("Camera", "5");
//
//		String[] expectedItems = { "Apple (50grams)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCase21() throws InterruptedException {
//		openGalleryAndSelectAlbum("Camera", "6");
//
//		String[] expectedItems = { "Apple (50grams)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCase22() throws InterruptedException {
//		openGalleryAndSelectAlbum("Camera", "7");
//
//		String[] expectedItems = { "Apple (50grams)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCase23() throws InterruptedException {
//		openGalleryAndSelectAlbum("Camera", "8");
//
//		String[] expectedItems = { "Chili (10ml)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCase24() throws InterruptedException {
//		openGalleryAndSelectAlbum("Camera", "9");
//
//		String[] expectedItems = { "Chili (10ml)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCase25() throws InterruptedException {
//		openGalleryAndSelectAlbum("Camera", "10");
//
//		String[] expectedItems = { "Milk (10ml)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCase26() throws InterruptedException {
//		openGalleryAndSelectAlbum("Camera", "11");
//
//		String[] expectedItems = { "Milk (10ml)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCase27() throws InterruptedException {
//		openGalleryAndSelectAlbum("Camera", "12");
//
//		String[] expectedItems = { "Strawberry (20grams)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCase28() throws InterruptedException {
//		openGalleryAndSelectAlbum("Camera", "13");
//
//		String[] expectedItems = { "Fish (200grams)" };
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCase29() throws InterruptedException {
//		openGalleryAndSelectAlbum("Camera", "14");
//
//		String[] expectedItems = { "Sorry, we could not detect foods in this picture, tap on \\\"Add Foods\\\" to add foods and ingredients."};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();
//
//	}
//	@Test
//	public void TestCase30() throws InterruptedException {
//		openGalleryAndSelectAlbum("Camera", "15");
//
//		String[] expectedItems = { "Egg (50grams)"};
//		verifyExpectedAndActual(expectedItems);
//
//		addToDairy();
//		driver.closeApp();

//	}

}
