package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.TestConstants.*;
import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {

		// トップページへアクセス
		goTo(CONTEXT_PATH);

		// ログインフォームが現れるまで待つ
		visibilityTimeout(By.id("loginId"), 5);
		// URLの確認
		assertTrue(expectedUrlTimeout(CONTEXT_PATH, 5));

		// スクリーンショット取得
		getEvidence(new Object() {
		}, "01");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		// ID:test PASS:test を入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("Test1234");
		// ログインをクリックする
		webDriver.findElement(By.className("btn-primary")).click();

		// <small>タグが表示されるまで待つ
		visibilityTimeout(By.tagName("small"), 5);
		// ログインユーザの権限とユーザ名の確認
		assertEquals("ようこそ受講生ＡＡ１さん", webDriver.findElement(By.tagName("small")).getText());
		// URLの確認
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + "course/detail", 5));

		// スクリーンショット取得
		getEvidence(new Object() {
		}, "02");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		// 「機能」リンクが表示されるまで待つ
		visibilityTimeout(By.className("dropdown-toggle"), 5);
		// 「機能」をクリック
		webDriver.findElement(By.className("dropdown-toggle")).click();
		// 「ヘルプ」リンクが表示されるまで待つ
		visibilityTimeout(By.linkText("ヘルプ"), 5);
		// 「ヘルプ」をクリック
		webDriver.findElement(By.linkText("ヘルプ")).click();

		// 見出しタグの確認
		assertEquals("ヘルプ", webDriver.findElement(By.tagName("h2")).getText());
		// URLの確認
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + "help", 5));

		// スクリーンショット取得
		getEvidence(new Object() {
		}, "03");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		// 「よくある質問」リンクが表示されるまで待つ
		visibilityTimeout(By.linkText("よくある質問"), 5);
		// 「よくある質問」をクリック
		webDriver.findElement(By.linkText("よくある質問")).click();

		// フォーカスを新規ウィンドウに切り替え
		switchToNewWindowTimeout(5);
		// 見出しタグが表示されるまで待つ
		visibilityTimeout(By.tagName("h2"), 5);
		// 見出しタグの確認
		assertEquals("よくある質問", webDriver.findElement(By.tagName("h2")).getText());
		// URLの確認
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + "faq", 5));

		// スクリーンショット取得
		getEvidence(new Object() {
		}, "03");
	}

}
