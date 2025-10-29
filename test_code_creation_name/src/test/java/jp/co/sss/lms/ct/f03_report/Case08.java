package jp.co.sss.lms.ct.f03_report;

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
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		loginTest(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		gotoSectionDetail(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		// 入力値
		final String BUTTON_NAME = "提出済み週報【デモ】を確認する";
		// 期待値
		final String EXPECTED_H2 = "週報【デモ】 2022年10月2日";
		final String EXPECTED_URL = "report/regist";

		// 「確認」ボタンへスクロール
		By btn = By.xpath("//input[@value='" + BUTTON_NAME + "']");
		scrollToLocate(btn);
		visibilityTimeout(btn, WAIT_SECOND);
		// ボタンを押下　
		clickOn(btn);

		// 見出しを検証
		assertTrue(expectedH2Timeout(EXPECTED_H2, WAIT_SECOND));
		// URLを検証
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {

		// 入力値
		final String INPUT_TEXT = "4";
		// 期待値
		final String EXPECTED_H2 = "アルゴリズム、フローチャート 2022年10月2日";
		final String EXPECTED_URL = "section/detail?sectionId=2";

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "01");

		// 目標の達成度を入力
		WebElement form = webDriver.findElement(By.id("content_0"));
		form.clear();
		form.sendKeys(INPUT_TEXT);

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "02");

		// 「提出する」ボタンを押下
		By btn = By.xpath("//button[text()='提出する']");
		scrollToLocate(btn);
		clickOn(btn);

		// 見出しの検証
		assertTrue(expectedH2Timeout(EXPECTED_H2, WAIT_SECOND));
		// URLの検証
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "03");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {

		gotoUserDetail(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {

		// 期待値
		final String EXPECTED_URL = "report/detail";
		final String EXPECTED_VALUE = "4";

		// 要素へスクロール
		By btn = By.xpath(
				"//h3[text()='レポート']/following-sibling::table/tbody/tr[3]/td[5]/form[1]/input[1][@value='詳細']");
		scrollToLocate(btn);
		// 「詳細」ボタンを押下
		clickOn(btn);

		// 期待値の検証
		By target = By.xpath("//th[text()='目標の達成度']/../td");
		scrollToLocate(target);
		assertEquals(EXPECTED_VALUE, webDriver.findElement(target).getText());
		// URLの検証
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		});
	}

}
