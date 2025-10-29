package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.TestConstants.*;
import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * 結合テスト 試験実施機能
 * ケース13
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果0点")
public class Case13 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		gotoSectionDetail(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {
		gotoExamStart(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {
		gotoExamQuestion(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 未回答の状態で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() throws InterruptedException {

		// 期待値
		final String EXPECTED_H2 = "ITリテラシー①";
		final String EXPECTED_URL = CONTEXT_PATH + "exam/answerCheck";

		By btn = By.xpath("//input[@value='確認画面へ進む']");
		// 「試験を開始する」ボタンへスクロール
		scrollToLocate(btn);
		// ボタンをクリック
		Thread.sleep(2000L);
		clickOn(btn);

		visibilityTimeout(By.xpath("//h2[1]"), WAIT_SECOND);
		// 見出しを検証
		assertTrue(expectedConditionTimeout(
				ExpectedConditions.textToBePresentInElementLocated(
						By.xpath("//h2[1]"), EXPECTED_H2),
				WAIT_SECOND));
		// URLを検証
		assertTrue(expectedUrlTimeout(EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {

		// 期待値
		final String EXPECTED_H2 = "ITリテラシー① あなたのスコア：0.0点   正答数：0／12";
		final String EXPECTED_URL = CONTEXT_PATH + "exam/result";

		By btn = By.xpath("//button[text()='回答を送信する']");
		// 「回答を送信する」ボタンへスクロール
		scrollToLocate(btn);
		Thread.sleep(2000L);
		// ボタンをクリック
		clickOn(btn);
		// OKを選択
		alertAccept();

		visibilityTimeout(By.xpath("//h2[1]"), WAIT_SECOND);
		// 見出しを検証
		assertTrue(expectedH2Timeout(EXPECTED_H2, WAIT_SECOND));
		// URLを検証
		assertTrue(expectedUrlTimeout(EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() {

		// 期待値
		final String EXPECTED_URL = CONTEXT_PATH + "exam/start";

		By btn = By.xpath("//input[@value='戻る']");
		// 「戻る」ボタンへスクロール
		scrollToLocate(btn);
		// ボタンをクリック
		clickOn(btn);

		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒");
		By dateBy = By.xpath("//tbody/tr[last()]/td[4]");

		Date lastTime = new Date();
		try {
			// 最新の試験の日時を文字列から
			lastTime = sdf.parse(getTextTimeout(dateBy, WAIT_SECOND));
		} catch (ParseException e) {
			assertTrue(false, "日付フォーマットの不一致");
			e.printStackTrace();
		}

		// 最新の試験結果の検証
		assertTrue(30000 > now.getTime() - lastTime.getTime());
		// URLを検証
		assertTrue(expectedUrlTimeout(EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		});
	}

}
