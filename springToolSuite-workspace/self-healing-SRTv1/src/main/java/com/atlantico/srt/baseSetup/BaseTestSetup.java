package com.atlantico.srt.baseSetup;

import com.epam.healenium.SelfHealingDriver;

import java.util.concurrent.TimeUnit;

import org.junit.After;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.junit.After;
import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Configuration;


/*
 * Class with necessary configurations for before/after the test to be run.
 */
@Configuration
public class BaseTestSetup {
	
	public SelfHealingDriver driver;
	protected JavascriptExecutor js;
	
    @Before
    public void setUp() {

        System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chromedriver94.exe");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(false);
        //declare delegate
        WebDriver delegate = new ChromeDriver(options);
        this.driver = SelfHealingDriver.create(delegate);
        this.driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        this.driver.manage().window().setSize(new Dimension(1200, 800));
        js = (JavascriptExecutor) this.driver;
    }

    @After
    public void after() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

}
