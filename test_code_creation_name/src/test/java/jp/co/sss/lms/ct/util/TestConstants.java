package jp.co.sss.lms.ct.util;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * テスト用定数
 * @author 芦田
 */
public class TestConstants {

	// コンテキストパス
	public static final String CONTEXT_PATH = "http://localhost:8080/lms/";
	public static final int WAIT_SECOND = 5;

	public static void gotoTop(Object instance) {

		// トップページへアクセス
		goTo(CONTEXT_PATH);

		// ログインフォームが現れるまで待つ
		visibilityTimeout(By.id("loginId"), WAIT_SECOND);
		// URLの確認
		assertTrue(expectedUrlTimeout(CONTEXT_PATH, WAIT_SECOND));

		// スクリーンショット取得
		getEvidence(instance);
	}

	public static void loginTest(Object instance) {

		// ID:test PASS:test を入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("Test1234");
		// ログインをクリックする
		webDriver.findElement(By.className("btn-primary")).click();

		// <small>タグが表示されるまで待つ
		visibilityTimeout(By.tagName("small"), WAIT_SECOND);
		// ログインユーザの権限とユーザ名の確認
		assertEquals("ようこそ受講生ＡＡ１さん", webDriver.findElement(By.tagName("small")).getText());
		// URLの確認
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + "course/detail", WAIT_SECOND));

		// スクリーンショット取得
		getEvidence(instance);
	}

	public static void gotoHelp(Object instance) {

		By menuBy = By.className("dropdown-toggle");
		// 「機能」リンクが表示されるまで待つ
		visibilityTimeout(menuBy, WAIT_SECOND);
		// 「機能」をクリック
		webDriver.findElement(menuBy).click();
		// 「ヘルプ」リンクが表示されるまで待つ
		visibilityTimeout(By.linkText("ヘルプ"), WAIT_SECOND);
		// 「ヘルプ」をクリック
		webDriver.findElement(By.linkText("ヘルプ")).click();

		// 見出しタグの確認
		assertEquals("ヘルプ", webDriver.findElement(By.tagName("h2")).getText());
		// URLの確認
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + "help", WAIT_SECOND));

		// スクリーンショット取得
		getEvidence(instance);
	}

	public static void gotoFaq(Object instance) {

		By linkBy = By.linkText("よくある質問");
		// 「よくある質問」リンクが表示されるまで待つ
		visibilityTimeout(linkBy, WAIT_SECOND);
		// 「よくある質問」をクリック
		webDriver.findElement(linkBy).click();

		// フォーカスを新規ウィンドウに切り替え
		switchToNewWindowTimeout(WAIT_SECOND);
		// 見出しタグが表示されるまで待つ
		visibilityTimeout(By.tagName("h2"), WAIT_SECOND);
		// 見出しタグの確認
		assertEquals("よくある質問", webDriver.findElement(By.tagName("h2")).getText());
		// URLの確認
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + "faq", WAIT_SECOND));

		// スクリーンショット取得
		getEvidence(instance);
	}

	public static void clickUser(Object instance) {

		// 入力値
		final String WELCOME_USERNAME = "ようこそ受講生ＡＡ１さん";
		// 期待値
		final String EXPECTED_URL = "user/detail";
		final String HEADER_TEXT = "ユーザー詳細";

		// 上部メニューの「ようこそ○○さん」リンクを押下
		webDriver.findElement(By.xpath("//small[text()='" + WELCOME_USERNAME + "']")).click();

		// URLが期待値になっているか
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));
		// 見出しが期待値になっているか
		assertTrue(expectedConditionTimeout(ExpectedConditions.textToBe(
				By.tagName("h2"), HEADER_TEXT), WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(instance);
	}
}
