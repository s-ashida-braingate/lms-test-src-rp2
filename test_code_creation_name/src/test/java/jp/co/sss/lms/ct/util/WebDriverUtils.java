package jp.co.sss.lms.ct.util;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

/**
 * Webドライバーユーティリティ
 * @author holy
 */
public class WebDriverUtils {

	/** Webドライバ */
	public static WebDriver webDriver;

	/**
	 * インスタンス取得
	 * @return Webドライバ
	 */
	public static void createDriver() {
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
		webDriver = new ChromeDriver();
	}

	/**
	 * インスタンス終了
	 */
	public static void closeDriver() {
		webDriver.quit();
	}

	/**
	 * 画面遷移
	 * @param url
	 */
	public static void goTo(String url) {
		webDriver.get(url);
		pageLoadTimeout(5);
	}

	/**
	 * ページロードタイムアウト設定
	 * @param second
	 */
	public static void pageLoadTimeout(int second) {
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(second));
	}

	/**
	 * 要素の可視性タイムアウト設定
	 * @param locater
	 * @param second
	 */
	public static void visibilityTimeout(By locater, int second) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(second));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locater));
	}

	/**
	 * 要素の期待文字列タイムアウト設定
	 * @param locater
	 * @param second
	 * @return
	 * @author 芦田 add:10/28
	 */
	public static String getTextTimeout(By locater, int second) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(second));
		String text = webDriver.findElement(locater).getText();
		if (wait.until(ExpectedConditions.textToBe(locater, text))) {
			return text;
		}
		return null;
	}

	/**
	 * 期待値タイムアウト設定
	 * @param condition
	 * @param second
	 * @return
	 * @author 芦田 add:10/23
	 */
	public static boolean expectedConditionTimeout(ExpectedCondition<Boolean> condition, int second) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(second));
		return wait.until(condition);
	}

	/**
	 * 期待URLタイムアウト設定
	 * @param url
	 * @param second
	 * @return boolean
	 * @author 芦田 add:10/23
	 */
	public static boolean expectedUrlTimeout(String url, int second) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(second));
		return wait.until(ExpectedConditions.urlToBe(url));
	}

	/**
	 * 見出しのタイムアウト設定
	 * @param h2
	 * @param second
	 * @return boolean
	 * @author 芦田 add:10/28
	 */
	public static boolean expectedH2Timeout(String h2, int second) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(second));
		return wait.until(ExpectedConditions.textToBe(By.xpath("//h2[1]"), h2));
	}

	/**
	 * 新規ウィンドウに切り替え
	 * @param second
	 * @author 芦田 add:10/23
	 */
	public static void switchToNewWindowTimeout(int second) {
		// 新しいウィンドウが開かれるのを待つ
		new WebDriverWait(webDriver, Duration.ofSeconds(second))
				.until(d -> d.getWindowHandles().size() > 1);
		// 新しいウィンドウに切り替える
		List<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
		webDriver.switchTo().window(tabs.get(tabs.size() - 1));
	}

	/**
	 * プルダウンリストを選択する
	 * @param locater
	 * @param value
	 * @author 芦田 add:10/28
	 */
	public static void selectValue(By locater, String value) {
		new Select(webDriver.findElement(locater)).selectByValue(value);
	}

	/**
	 * 要素を押下する
	 * @param locater
	 * @author 芦田 add:10/27
	 */
	public static void clickOn(By locater) {
		webDriver.findElement(locater).click();
	}

	/**
	 * アラートでOKを選択する
	 * @author 芦田 add:10/27
	 */
	public static void alertAccept() {
		Alert alert = webDriver.switchTo().alert();
		alert.accept();
	}

	/**
	 * フォームに文字列を入力する
	 * @param locater
	 * @param str
	 * @author 芦田 add:10/28
	 */
	public static void inputForm(By locater, String str) {
		WebElement elem = webDriver.findElement(locater);
		((JavascriptExecutor) webDriver).executeScript("arguments[0].value = arguments[1];", elem, str);
	}

	/**
	 * フォームの内容を削除する
	 * @param locater
	 * @author 芦田 add:10/28
	 */
	public static void clearForm(By locater) {
		webDriver.findElement(locater).clear();
	}

	/**
	 * 指定ピクセル分だけスクロール
	 * @param pixel
	 */
	public static void scrollBy(String pixel) {
		((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0," + pixel + ");");
	}

	/**
	 * 指定位置までスクロール
	 * @param pixel
	 */
	public static void scrollTo(String pixel) {
		((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0," + pixel + ");");
	}

	/**
	 * 要素(By)が画面中央にくるまでスクロール
	 * @param locater
	 * @author 芦田 add:10/27
	 */
	public static void scrollToLocate(By locater) {
		WebElement elem = webDriver.findElement(locater);
		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollIntoView({block: 'center'});", elem);
	}

	/**
	 * 要素(WebElement)が画面中央にくるまでスクロール
	 * @param element
	 * @author 芦田 add:10/27
	 */
	public static void scrollToElement(WebElement elem) {
		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollIntoView({block: 'center'});", elem);
	}

	/**
	 * タグ内の最下部までスクロール
	 * @param element
	 * @author 芦田 add:10/27
	 */
	public static void scrollElementToBottom(By locater) {
		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollTop = arguments[0].scrollHeight;", webDriver.findElement(locater));
	}

	/**
	 * エビデンス取得
	 * @param instance
	 */
	public static void getEvidence(Object instance) {
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			String className = instance.getClass().getEnclosingClass().getSimpleName();
			String methodName = instance.getClass().getEnclosingMethod().getName();
			Files.move(tempFile, new File("evidence\\" + className + "_" + methodName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * エビデンス取得（サフィックスあり）
	 * @param instance
	 * @param suffix
	 */
	public static void getEvidence(Object instance, String suffix) {
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			String className = instance.getClass().getEnclosingClass().getSimpleName();
			String methodName = instance.getClass().getEnclosingMethod().getName();
			Files.move(tempFile, new File("evidence\\" + className + "_" + methodName + "_" + suffix + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
