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
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import junit.framework.Assert;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FoodZillaTest_backup2 {
	AndroidDriver<AndroidElement> driver;

	@Before
	public void setUp() throws MalformedURLException {

		System.out.println("Running Automated Test cases on FOODZILLA AI Mobile Application");
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		dc.setCapability("platformName", "Android");
		dc.setCapability("udid", "6e774b0");
		dc.setCapability(MobileCapabilityType.DEVICE_NAME, "vivo 1921");
		dc.setCapability("appPackage", "io.foodzilla.app");
		dc.setCapability("appActivity", "io.foodzilla.app.MainActivity");
		dc.setCapability("noReset", "true");
		URL url = new URL("http://127.0.0.1:4723/wd/hub");
		driver = new AndroidDriver<AndroidElement>(url, dc);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		
	}

	boolean askLogin = true;

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
			// driver.findElementByXPath("//android.widget.EditText[@text='Enter
			// email']").sendKeys("naga26.nagubandi@gmail.com");
			enterText(enterEmailLocator, "shreya.kommera@sjsu.edu");

			String enterPasswordLocator = "//android.widget.EditText[@text='Enter password']";
			// driver.findElementByXPath("//android.widget.EditText[@text='Enter
			// password']").sendKeys("AP24m2349@");
			enterText(enterPasswordLocator, "AP24m2349@");

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
		//System.out.println("The element got clicked");

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

		//System.out.println("itemsNamesActual  " + itemsNamesActual);
		return itemsNamesActual;

	}

	public List<String> getExpectedItems(String[] expectedItems) {
		List<String> itemsNamesExpected = new ArrayList<String>();
		for (String item : expectedItems) {
			itemsNamesExpected.add(item);
		}
		//System.out.println("itemsNamesExpected " + itemsNamesExpected);

		// itemsNamesExpected.add("Rice (0.1cup)");
		// itemsNamesExpected.add("Chicken (200grams)");
		return itemsNamesExpected;

	}

	public String getGivenAlbum(String albumName) {
		String albumNameDynamic = "//android.widget.LinearLayout//android.widget.RelativeLayout//android.widget.TextView[@text='"
				+ albumName + "']";
		//System.out.println("printing album with passed name" + albumNameDynamic);
		// "Hello All this is" +firstName + " and Surna me is "+ lastName
		return albumNameDynamic;

	}

	public void openGalleryAndSelectAlbum(String albumName, String imageIndex) throws InterruptedException {
		//System.out.println("Importing from Gallery");

		String openGalleryLocator = "//android.widget.TextView[@text='From Gallery']";
		clickElement(openGalleryLocator);

	//	String selectAlbumLocator = "//android.widget.LinearLayout[@content-desc='Albums']/android.widget.RelativeLayout";
		//clickElement(selectAlbumLocator);

		//System.out.println("opening food album");
		String openFoodAlbum = getGivenAlbum(albumName);
		clickElement(openFoodAlbum);

		// (//android.widget.FrameLayout[@content-desc='Button']/android.widget.FrameLayout[2])[2]

		String selectFoodPicture = "(//android.widget.FrameLayout[@content-desc='Button']/android.widget.FrameLayout[2])["
				+ imageIndex + "]";
		clickElement(selectFoodPicture);
		Thread.sleep(150);
	}

	public void verifyExpectedAndActual(String[] expectedItems) {
		List<String> itemsNamesActual = getActualItems();
		//System.out.println("itemsNamesActual  " + itemsNamesActual);

		List<String> itemsNamesExpected = getExpectedItems(expectedItems);
		boolean matches = itemsNamesActual.equals(itemsNamesExpected);
		//System.out.println("The two ist comparision " + matches);
		// String message = "";

		if (itemsNamesActual.size() <= 0) {

			// Sorry, we could not detect foods in this picture, click on "Add Foods" to
			// analyse nutrition automatically.

			String noItemsSorry = "//android.view.ViewGroup//android.view.ViewGroup//android.widget.TextView[contains(@text,'Sorry')]";
			String message = getText(noItemsSorry);
			// String finalMessage = "The expected Items are " + itemsNamesExpected + "
			// however we got this **** " + message +" **** ";
			System.out.println("The expected Items are " + itemsNamesExpected + " however we got this **** " + message
					+ "  ****   ");
			assertTrue(false);
			// Assert.assertTrue(false, finalMessage);
		} else if (!matches) {
			System.out.println("The items doesnt match, Expected "  + itemsNamesExpected + " however got this " + itemsNamesActual );

			assertTrue(false);

		} else {
			System.out.println("The items matched, Expected "  + itemsNamesExpected + " and actual items " + itemsNamesActual );
			assertTrue(true);
		}

	}

	public void addToDairy() throws InterruptedException {
		String addToDairy = "//android.widget.TextView[@text='Add to Diary']";
		clickElement(addToDairy);
		Thread.sleep(150);

	}

	@Test
	public void TestCase1() throws InterruptedException {
		openGalleryAndSelectAlbum("food", "1");

		String[] expectedItems = { "Bread (50grams)" };
		verifyExpectedAndActual(expectedItems);

		addToDairy();
		driver.closeApp();

	}
	
	@Test
	public void TestCase2() throws InterruptedException {
		openGalleryAndSelectAlbum("food", "3");

		String[] expectedItems = { "Cabbage (50grams)" };
		verifyExpectedAndActual(expectedItems);

		addToDairy();
		driver.closeApp();

	}

	
}
