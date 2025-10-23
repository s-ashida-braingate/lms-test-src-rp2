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
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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

		gotoTop(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {

		// トップページへアクセス
		goTo(CONTEXT_PATH);

		// ログインフォームが現れるまで待つ
		visibilityTimeout(By.id("loginId"), WAIT_SECOND);

		// ID:test PASS:test を入力
		webDriver.findElement(By.id("loginId")).clear();
		webDriver.findElement(By.id("loginId")).sendKeys("test");
		webDriver.findElement(By.id("password")).clear();
		webDriver.findElement(By.id("password")).sendKeys("test");
		// ログインをクリックする
		webDriver.findElement(By.className("btn-primary")).click();

		// エラーメッセージが表示されるまで待つ
		visibilityTimeout(By.className("help-inline"), WAIT_SECOND);
		// エラーメッセージの確認
		assertEquals("* ログインに失敗しました。", webDriver.findElement(By.className("help-inline")).getText());
		// URLの確認
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + "login", WAIT_SECOND));

		// スクリーンショット取得
		getEvidence(new Object() {
		});
	}

}
