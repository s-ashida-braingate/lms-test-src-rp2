package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.TestConstants.*;
import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {

		// 期待値
		final String EXPECTED_H2 = "勤怠管理";
		final String EXPECTED_URL = "attendance/detail";

		// 上部メニューの「勤怠」リンクの表示待ち
		By attendance = By.linkText("勤怠");
		visibilityTimeout(attendance, WAIT_SECOND);

		// リンクを押下
		webDriver.findElement(attendance).click();

		By h2 = By.xpath("//*[@id=\"main\"]/h2");
		// 見出しを検証
		assertTrue(expectedConditionTimeout(ExpectedConditions.textToBe(h2, EXPECTED_H2), WAIT_SECOND));
		// URLを検証
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		});
	}

	/**
	 * 
	 */
	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {

		// 今日の日付の取得
		Date date = new Date();
		String today = new SimpleDateFormat("yyyy年M月d日(E)", Locale.JAPANESE).format(date);
		SimpleDateFormat hhmm = new SimpleDateFormat("HH:mm");
		String now = hhmm.format(date);
		// 1分後の時刻を用意（テスト中に1分経過する可能性があるため）
		date.setTime(date.getTime() + 60000);
		String now1min = hhmm.format(date);

		// ページ最下部へスクロール
		scrollElementToBottom(By.tagName("html"));
		// テーブル内の最下部へスクロール
		scrollElementToBottom(By.tagName("tbody"));
		// 今日のセクションが現れるまで待つ
		By timeIn = By.xpath("//td[text()='" + today + "']/following-sibling::td[2]");
		visibilityTimeout(timeIn, WAIT_SECOND);

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "01");

		// ページ最上部へスクロール
		scrollTo("0");
		// 「出勤」ボタンが現れるまで待つ
		By btn = By.xpath("//input[@value='出勤']");
		visibilityTimeout(btn, WAIT_SECOND);

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "02");

		// 「出勤」ボタンを押下
		clickOn(btn);
		// アラートでOKを選択
		alertAccept();

		// ページ最下部へスクロール
		scrollElementToBottom(By.tagName("html"));
		// テーブル内の最下部へスクロール
		scrollElementToBottom(By.tagName("tbody"));
		// 時刻の検証
		assertTrue(webDriver.findElement(timeIn).getText().equals(now) ||
				webDriver.findElement(timeIn).getText().equals(now1min));

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "03");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {

		// 今日の日付の取得
		Date date = new Date();
		String today = new SimpleDateFormat("yyyy年M月d日(E)", Locale.JAPANESE).format(date);
		SimpleDateFormat hhmm = new SimpleDateFormat("HH:mm");
		String now = hhmm.format(date);
		// 1分後の時刻を用意（テスト中に1分経過する可能性があるため）
		date.setTime(date.getTime() + 60000);
		String now1min = hhmm.format(date);

		// ページ最下部へスクロール
		scrollElementToBottom(By.tagName("html"));
		// テーブル内の最下部へスクロール
		scrollElementToBottom(By.tagName("tbody"));
		// 今日のセクションが現れるまで待つ
		By timeOut = By.xpath("//td[text()='" + today + "']/following-sibling::td[3]");
		visibilityTimeout(timeOut, WAIT_SECOND);

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "01");

		// ページ最上部へスクロール
		scrollTo("0");
		// 「退勤」ボタンが現れるまで待つ
		By btn = By.xpath("//input[@value='退勤']");
		visibilityTimeout(btn, WAIT_SECOND);

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "02");

		// 「退勤」ボタンを押下
		clickOn(btn);
		// アラートでOKを選択
		alertAccept();

		// ページ最下部へスクロール
		scrollElementToBottom(By.tagName("html"));
		// テーブル内の最下部へスクロール
		scrollElementToBottom(By.tagName("tbody"));
		// 時刻の検証
		assertTrue(webDriver.findElement(timeOut).getText().equals(now) ||
				webDriver.findElement(timeOut).getText().equals(now1min));

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, "03");
	}

}
