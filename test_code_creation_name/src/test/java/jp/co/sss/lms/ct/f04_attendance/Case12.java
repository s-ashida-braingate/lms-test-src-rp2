package jp.co.sss.lms.ct.f04_attendance;

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
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

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
		gotoAttendance(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		gotoAttendanceUpdate(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {

		// 入力値
		final String INPUT_VALUE = "";
		// 期待値
		final String EXPECTED_MESSAGE1 = "* 出勤時間が正しく入力されていません。";
		final String EXPECTED_MESSAGE2 = "* 退勤時間が正しく入力されていません。";
		final String EXPECTED_URL = "attendance/update";

		// プルダウンリストから入力値を選択
		selectValue(By.xpath("//tbody[@class='db']/tr/td[4]/select"), INPUT_VALUE);
		selectValue(By.xpath("//tbody[@class='db']/tr/td[7]/select"), INPUT_VALUE);

		By button = By.xpath("//input[@value='更新']");
		// 要素へスクロール
		scrollToLocate(button);
		visibilityTimeout(button, WAIT_SECOND);
		// 「更新」ボタンを押下
		clickOn(button);
		// OKを押下
		alertAccept();

		// エラーメッセージの検証
		By msg1 = By.xpath("//*[@id=\"main\"]/div/div/ul/li[1]/span");
		By msg2 = By.xpath("//*[@id=\"main\"]/div/div/ul/li[2]/span");
		assertEquals(EXPECTED_MESSAGE1, getTextTimeout(msg1, WAIT_SECOND));
		assertEquals(EXPECTED_MESSAGE2, getTextTimeout(msg2, WAIT_SECOND));
		// URLの検証
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {

		// 入力値
		final String INPUT_VALUE = "";
		// 期待値
		final String EXPECTED_MESSAGE1 = "* 出勤情報がないため退勤情報を入力出来ません。";
		final String EXPECTED_URL = "attendance/update";

		gotoAttendance(null);
		gotoAttendanceUpdate(null);

		selectValue(By.xpath("//tbody[@class='db']/tr/td[4]/select"), INPUT_VALUE);
		selectValue(By.xpath("//tbody[@class='db']/tr/td[6]/select"), INPUT_VALUE);

		By button = By.xpath("//input[@value='更新']");
		// 要素へスクロール
		scrollToLocate(button);
		visibilityTimeout(button, WAIT_SECOND);
		// 「更新」ボタンを押下
		clickOn(button);
		// OKを押下
		alertAccept();

		// エラーメッセージの検証
		By msg1 = By.xpath("//*[@id=\"main\"]/div/div/ul/li[1]/span");
		assertEquals(EXPECTED_MESSAGE1, getTextTimeout(msg1, WAIT_SECOND));
		// URLの検証
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {

		// 入力値
		final String INPUT_VALUE1 = "8";
		final String INPUT_VALUE2 = "0";
		// 期待値
		final String EXPECTED_MESSAGE1 = "* 退勤時刻[0]は出勤時刻[0]より後でなければいけません。";
		final String EXPECTED_URL = "attendance/update";

		gotoAttendance(null);
		gotoAttendanceUpdate(null);

		selectValue(By.xpath("//tbody[@class='db']/tr/td[7]/select"), INPUT_VALUE1);
		selectValue(By.xpath("//tbody[@class='db']/tr/td[9]/select"), INPUT_VALUE2);

		By button = By.xpath("//input[@value='更新']");
		// 要素へスクロール
		scrollToLocate(button);
		visibilityTimeout(button, WAIT_SECOND);
		// 「更新」ボタンを押下
		clickOn(button);
		// OKを押下
		alertAccept();

		// エラーメッセージの検証
		By msg1 = By.xpath("//*[@id=\"main\"]/div/div/ul/li[1]/span");
		assertEquals(EXPECTED_MESSAGE1, getTextTimeout(msg1, WAIT_SECOND));
		// URLの検証
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {

		// 入力値
		final String INPUT_VALUE1 = "10";
		final String INPUT_VALUE2 = "0";
		final String INPUT_VALUE3 = "60";
		// 期待値
		final String EXPECTED_MESSAGE1 = "* 中抜け時間が勤務時間を超えています。";
		final String EXPECTED_URL = "attendance/update";

		gotoAttendance(null);
		gotoAttendanceUpdate(null);

		selectValue(By.xpath("//tbody[@class='db']/tr/td[7]/select"), INPUT_VALUE1);
		selectValue(By.xpath("//tbody[@class='db']/tr/td[9]/select"), INPUT_VALUE2);
		selectValue(By.xpath("//tbody[@class='db']/tr/td[10]/select"), INPUT_VALUE3);

		By button = By.xpath("//input[@value='更新']");
		// 要素へスクロール
		scrollToLocate(button);
		visibilityTimeout(button, WAIT_SECOND);
		// 「更新」ボタンを押下
		clickOn(button);
		// OKを押下
		alertAccept();

		// エラーメッセージの検証
		By msg1 = By.xpath("//*[@id=\"main\"]/div/div/ul/li[1]/span");
		assertEquals(EXPECTED_MESSAGE1, getTextTimeout(msg1, WAIT_SECOND));
		// URLの検証
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {

		// 入力値
		final String INPUT_VALUE = "a".repeat(101);
		// 期待値
		final String EXPECTED_MESSAGE1 = "* 備考の長さが最大値(100)を超えています。";
		final String EXPECTED_URL = "attendance/update";

		gotoAttendance(null);
		gotoAttendanceUpdate(null);

		By note = By.xpath("//*[@id=\"main\"]/div/div/form/table/tbody/tr[1]/td[12]/input");
		inputForm(note, INPUT_VALUE);

		By button = By.xpath("//input[@value='更新']");
		// 要素へスクロール
		scrollToLocate(button);
		visibilityTimeout(button, WAIT_SECOND);
		// 「更新」ボタンを押下
		clickOn(button);
		// OKを押下
		alertAccept();

		// エラーメッセージの検証
		By msg1 = By.xpath("//*[@id=\"main\"]/div/div/ul/li[1]/span");
		assertEquals(EXPECTED_MESSAGE1, getTextTimeout(msg1, WAIT_SECOND));
		// URLの検証
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		});
	}

}
