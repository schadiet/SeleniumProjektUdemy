package de.codingsolo.seleniumkurs.test;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import de.codingsolo.seleniumkurs.pages.SeleniumKursHomePage;
import de.codingsolo.seleniumkurs.pages.SeleniumKursLoginPage;
import de.codingsolo.seleniumkurs.pages.SeleniumKursTestApplikationenPage;
import de.codingsolo.seleniumkurs.pages.SeleniumKursTestForm1Page;

public class TestForm1SeleniumKursRemote {

	WebDriver driver;

	@Before
	public void setUp() throws Exception {
		System.out.println("Initialisiere Webdriver");

		URL linkHub = new URL("http://localhost:4444/wd/hub");
		
		driver = new RemoteWebDriver(linkHub, DesiredCapabilities.firefox());
		
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
		loginPage.zugangsdatenEingeben("selenium101", "codingsolo");
		loginPage.loginButtonAnklicken();

		// Navigation zum Formular
		SeleniumKursHomePage homePage = new SeleniumKursHomePage(driver);
		homePage.menuAusklappen();
		homePage.seleniumTestAppLinkAnklicken();
		SeleniumKursTestApplikationenPage testAppPage = new SeleniumKursTestApplikationenPage(driver);
		testAppPage.testForm1anklicken();

		// Starte Formular
		SeleniumKursTestForm1Page testForm1Page = new SeleniumKursTestForm1Page(driver);
		testForm1Page.betreffEingeben("Automatischer Test");
		testForm1Page.nameEingeben("Dieter");

		testForm1Page.kursAuswaehlen("Java Grundlagen Kurs mit Dieter");

		testForm1Page.firmaInBox1Auswaehlen(new int[] { 2, 4, 6 });
		testForm1Page.firmenUerbernehmen();
		testForm1Page.firmaInBox2Auswaehlen(new int[] { 2 });
		testForm1Page.ausgew�hlteFirmenNachObenVerschieben();

		// Act

		testForm1Page.formularSpeichern();

		// Assert

		String erfolgsMeldung = testForm1Page.statusMeldungAuslesen();
		assertTrue(erfolgsMeldung.contains("Java Grundlagen Kurs"));

		String erstesElement = testForm1Page.erstesListenElementAuslesen();
		assertEquals(erstesElement, "Magazzini Alimentari Riuniti");
	}

}
