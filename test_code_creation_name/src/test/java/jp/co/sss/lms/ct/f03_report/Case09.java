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
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		gotoUserDetail(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		// 期待値
		final String EXPECTED_URL = "report/regist";

		// 引数のところまでスクロール
		scrollTo("400");

		By btn = By.xpath("//td[text()='週報【デモ】']/following::input[@value='修正する']");
		visibilityTimeout(btn, WAIT_SECOND);
		webDriver.findElement(btn).click();

		ExpectedCondition<Boolean> condition = ExpectedConditions.textToBe(
				By.tagName("h2"), "週報【デモ】 2022年10月2日");
		// 見出しが期待値になっているか検査
		assertTrue(expectedConditionTimeout(condition, WAIT_SECOND));
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {

		// 期待値
		final String EXPECTED_URL = "report/complete";

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "01");

		// 学習項目が表示されるまで待つ
		visibilityTimeout(By.id("intFieldName_0"), WAIT_SECOND);
		// 学習項目を削除
		webDriver.findElement(By.id("intFieldName_0")).clear();

		// 引数のところまでスクロール
		scrollTo("400");

		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();

		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));
		assertTrue(expectedConditionTimeout(ExpectedConditions.textToBe(
				By.id("intFieldName_0"), ""), WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "02");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {

		// 期待値
		final String EXPECTED_URL = "report/complete";

		// このページに入りなおす
		gotoReportFromUser();

		// 理解度を押下
		webDriver.findElement(By.id("intFieldValue_0")).click();
		By option = By.xpath("//*[@id='intFieldValue_0']/option[1]");
		// プルダウンメニューの表示待ち
		visibilityTimeout(option, WAIT_SECOND);
		// 理解度未入力を選択
		webDriver.findElement(option).click();

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "01");

		// 引数のところまでスクロール
		scrollTo("400");

		// 「提出する」ボタンを押下
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();

		// 理解度が未入力になっているか検査
		assertTrue(expectedConditionTimeout(ExpectedConditions.elementToBeSelected(option), WAIT_SECOND));
		// URLが期待値か検査
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "02");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {

		// 入力値
		final String INPUT_VALUE = "test";
		// 期待値
		final String EXPECTED_URL = "report/complete";

		// このページに入りなおす
		gotoReportFromUser();

		By degree = By.id("content_0");
		// 目標の達成度に数値以外を入力
		webDriver.findElement(degree).clear();
		webDriver.findElement(degree).sendKeys(INPUT_VALUE);

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "01");

		// 引数のところまでスクロール
		scrollTo("400");

		// 「提出する」ボタンを押下
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();

		// 目標の達成度が未入力になっているか検査
		assertTrue(expectedConditionTimeout(ExpectedConditions.textToBe(degree, INPUT_VALUE), WAIT_SECOND));
		// URLが期待値か検査
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "02");
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {

		// 入力値
		final String INPUT_VALUE1 = "-1";
		final String INPUT_VALUE2 = "11";
		// 期待値
		final String EXPECTED_URL = "report/complete";

		// このページに入りなおす
		gotoReportFromUser();

		By degree = By.id("content_0");
		// 目標の達成度に数値以外を入力
		webDriver.findElement(degree).clear();
		webDriver.findElement(degree).sendKeys(INPUT_VALUE1);

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "01");

		// 引数のところまでスクロール
		scrollTo("400");

		// 「提出する」ボタンを押下
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();

		// 目標の達成度が未入力になっているか検査
		assertTrue(expectedConditionTimeout(ExpectedConditions.textToBe(degree, INPUT_VALUE1), WAIT_SECOND));
		// URLが期待値か検査
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "02");

		// このページに入りなおす
		gotoReportFromUser();

		// 目標の達成度に数値以外を入力
		webDriver.findElement(degree).clear();
		webDriver.findElement(degree).sendKeys(INPUT_VALUE2);

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "03");

		// 引数のところまでスクロール
		scrollTo("400");

		// 「提出する」ボタンを押下
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();

		// 目標の達成度が未入力になっているか検査
		assertTrue(expectedConditionTimeout(ExpectedConditions.textToBe(degree, INPUT_VALUE2), WAIT_SECOND));
		// URLが期待値か検査
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "04");
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {

		// 期待値
		final String EXPECTED_URL = "report/complete";

		// このページに入りなおす
		gotoReportFromUser();

		By degree = By.id("content_0");
		// 目標の達成度に数値以外を入力
		webDriver.findElement(degree).clear();

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "01");

		// 引数のところまでスクロール
		scrollTo("400");

		// 「提出する」ボタンを押下
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();

		// 目標の達成度が未入力になっているか検査
		assertTrue(expectedConditionTimeout(ExpectedConditions.textToBe(degree, ""), WAIT_SECOND));
		// URLが期待値か検査
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "02");

		// このページに入りなおす
		gotoReportFromUser();

		By imp = By.id("content_1");
		// 所感に数値以外を入力
		webDriver.findElement(imp).clear();

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "03");

		// 引数のところまでスクロール
		scrollTo("400");

		// 「提出する」ボタンを押下
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();

		// 所感が未入力になっているか検査
		assertTrue(expectedConditionTimeout(ExpectedConditions.textToBe(imp, ""), WAIT_SECOND));
		// URLが期待値か検査
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "04");
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {

		// 期待値
		final String EXPECTED_URL = "report/complete";

		// このページに入りなおす
		gotoReportFromUser();

		By imp = By.id("content_1");
		webDriver.findElement(imp).clear();
		// 所感に2001文字入力
		// 2001文字を一気に入力するとレンダリングエラーとなるため500文字ずつ入力
		String str = "a".repeat(500);
		webDriver.findElement(imp).sendKeys(str);
		webDriver.findElement(imp).sendKeys(str);
		webDriver.findElement(imp).sendKeys(str);
		str += "a";
		webDriver.findElement(imp).sendKeys(str);

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "01");

		// 引数のところまでスクロール
		scrollTo("400");

		// 「提出する」ボタンを押下
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();

		// 所感が2001文字になっているか検査
		str = "a".repeat(2001);
		assertTrue(expectedConditionTimeout(ExpectedConditions.textToBe(imp, str), WAIT_SECOND));
		// URLが期待値か検査
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "02");

		// このページに入りなおす
		gotoReportFromUser();

		By weekly = By.id("content_2");
		// 一週間の振り返りに2001文字入力
		webDriver.findElement(weekly).clear();
		// 2001文字を一気に入力するとレンダリングエラーとなるため500文字ずつ入力
		str = "a".repeat(500);
		webDriver.findElement(weekly).sendKeys(str);
		webDriver.findElement(weekly).sendKeys(str);
		webDriver.findElement(weekly).sendKeys(str);
		str += "a";
		webDriver.findElement(weekly).sendKeys(str);

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "03");

		// 引数のところまでスクロール
		scrollTo("400");

		// 「提出する」ボタンを押下
		webDriver.findElement(By.xpath("//button[text()='提出する']")).click();

		// 一週間の振り返りが2001文字になっているか検査
		str = "a".repeat(2001);
		assertTrue(expectedConditionTimeout(ExpectedConditions.textToBe(weekly, str), WAIT_SECOND));
		// URLが期待値か検査
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "04");
	}

	/**
	 * ユーザー名を押下してリポート修正画面に入りなおす
	 */
	void gotoReportFromUser() {

		// 入力値
		final String WELCOME_USERNAME = "ようこそ受講生ＡＡ１さん";

		// 上部メニューの「ようこそ○○さん」リンクを押下
		webDriver.findElement(By.xpath("//small[text()='" + WELCOME_USERNAME + "']")).click();

		// 引数のところまでスクロール
		scrollTo("400");

		By btn = By.xpath("//td[text()='週報【デモ】']/following::input[@value='修正する']");
		visibilityTimeout(btn, WAIT_SECOND);
		webDriver.findElement(btn).click();
	}
}
