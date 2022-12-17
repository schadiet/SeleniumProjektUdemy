package de.codingsolo.seleniumkurs.test2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import de.codingsolo.seleniumkurs.pages.SeleniumKursHomePage;
import de.codingsolo.seleniumkurs.pages.SeleniumKursLoginPage;
import de.codingsolo.seleniumkurs.pages.SeleniumKursTestApplikationenPage;
import de.codingsolo.seleniumkurs.pages.SeleniumKursTestForm1Page;

@RunWith(Parameterized.class)
public class TestForm1ParameterizedSeleniumKursFireFox {

	WebDriver driver;

	String browsername;
	String browserdriver;
	String username;
	String password;
	String betreff;
	String name;
	String kursTitel;
	int[] firmenBox1;
	int[] firmenBox2;
	String assert1;
	String assert2;

	public TestForm1ParameterizedSeleniumKursFireFox(String testName, String browsername, String browserdriver, String username, String password, String betreff,
			String name, String kursTitel, int[] firmenBox1, int[] firmenBox2, String assert1, String assert2) {
		this.browsername = browsername;
		this.browserdriver = browserdriver;
		this.username = username;
		this.password = password;
		this.betreff = betreff;
		this.name = name;
		this.kursTitel = kursTitel;
		this.firmenBox1 = firmenBox1;
		this.firmenBox2 = firmenBox2;
		this.assert1 = assert1;
		this.assert2 = assert2;
	}

//	@Before
//	public void setUp() throws Exception {
//		System.out.println("Initialisiere Webdriver");
//		System.setProperty(Config.getBrowserName(browsername), Config.getBrowserDriver(browserdriver));
//		driver = new FirefoxDriver();
//		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//		driver.manage().window().maximize();
//		driver.get(Config.getBasURL());
//	}

	@Before
	public void setUp() throws Exception {
		System.out.println("Initialisiere Webdriver");
		System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://seleniumkurs.codingsolo.de");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Test abgeschlossen- ich r�ume");
		driver.close();
	}

	@Test
	public void testForm1() {
		System.out.println("Starte TestForm1 Testseite");

		// Arrange

		// Login
		SeleniumKursLoginPage loginPage = new SeleniumKursLoginPage(driver);
		loginPage.zugangsdatenEingeben(username, password);
		loginPage.loginButtonAnklicken();

		// Navigation zum Formular
		SeleniumKursHomePage homePage = new SeleniumKursHomePage(driver);
		homePage.menuAusklappen();
		homePage.seleniumTestAppLinkAnklicken();
		SeleniumKursTestApplikationenPage testAppPage = new SeleniumKursTestApplikationenPage(driver);
		testAppPage.testForm1anklicken();

		// Starte Formular
		SeleniumKursTestForm1Page testForm1Page = new SeleniumKursTestForm1Page(driver);
		testForm1Page.betreffEingeben(betreff);
		testForm1Page.nameEingeben(name);

		testForm1Page.kursAuswaehlen(kursTitel);

		testForm1Page.firmaInBox1Auswaehlen(firmenBox1);
		testForm1Page.firmenUerbernehmen();
		testForm1Page.firmaInBox2Auswaehlen(firmenBox2);
		testForm1Page.ausgew�hlteFirmenNachObenVerschieben();

		// Act

		testForm1Page.formularSpeichern();

		// Assert

		String erfolgsMeldung = testForm1Page.statusMeldungAuslesen();
		assertTrue(erfolgsMeldung.contains(assert1));

		String erstesElement = testForm1Page.erstesListenElementAuslesen();
		assertEquals(erstesElement, assert2);
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> provideTestData() throws Exception {

		Collection<Object[]> collection;

		Object[][] daten = {
				{ "Test Form 1 Test 1 FireFox", "firefoxbrowser", "firefoxdriver", "selenium102", "codingsolo", "Parametrisierter Test 1", "Dieter",
						"Java Grundlagen Kurs mit Dieter", new int[] { 2, 4, 6 }, new int[] { 2 },
						"Java Grundlagen Kurs", "Magazzini Alimentari Riuniti" }};

		List<Object[]> listObjects = Arrays.asList(daten);
		collection = new ArrayList<Object[]>(listObjects);

		return collection;
	}

}
