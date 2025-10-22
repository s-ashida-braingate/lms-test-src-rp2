package jp.co.sss.lms.ct.f01_login1;

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
 * 結合テスト ログイン機能①
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

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

}
