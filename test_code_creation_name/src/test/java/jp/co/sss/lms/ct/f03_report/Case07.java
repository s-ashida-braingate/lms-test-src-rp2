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

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		// 期待値
		final String EXPECTED_URL = "section/detail";

		// 未提出の詳細ボタンが表示されるのを待つ
		By btn = By.xpath("//span[text()='未提出']/following::input[@class='btn btn-default'][1]");
		visibilityTimeout(btn, WAIT_SECOND);
		// 詳細ボタンを押下　
		webDriver.findElement(btn).click();

		// セクション詳細画面が表示されるのを待つ
		visibilityTimeout(By.id("sectionDetail"), WAIT_SECOND);
		// URLが期待値になっているか
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		// 期待値
		final String EXPECTED_URL = "report/regist";

		By btn = By.xpath("//input[@value='日報【デモ】を提出する']");
		visibilityTimeout(btn, WAIT_SECOND);
		// 詳細ボタンを押下　
		webDriver.findElement(btn).click();

		// セクション詳細画面が表示されるのを待つ
		visibilityTimeout(By.id("content_0"), WAIT_SECOND);
		// URLが期待値になっているか
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {

		// 入力値
		final String INPUT_REPORT = "テスト";
		// 期待値
		final String BUTTON_NAME = "提出済み日報【デモ】を確認する";
		final String EXPECTED_URL = "section/detail?sectionId=3";

		// 報告内容を入力
		webDriver.findElement(By.id("content_0")).clear();
		webDriver.findElement(By.id("content_0")).sendKeys(INPUT_REPORT);

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "01");

		// 「提出する」ボタンを押下
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();

		// 確認ボタン名が更新されているか
		By btn = By.xpath("//input[@value='" + BUTTON_NAME + "']");
		visibilityTimeout(btn, WAIT_SECOND);

		// URLが期待値になっているか
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "02");
	}

}
