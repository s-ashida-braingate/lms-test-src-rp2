package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.TestConstants.*;
import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
 * 結合テスト 勤怠管理機能
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {

		// 期待値
		final String EXPECTED_MESSAGE = "勤怠情報の登録が完了しました。";
		final String EXPECTED_URL = "attendance/update";

		// 出勤か退勤が空のリストを取得
		By blankTime = By.xpath(
				"//tbody[@class='db']/tr/td[position()=4 or position()=7]" +
						"/select/option[position()=1 and @selected='selected']");
		List<WebElement> blankList = webDriver.findElements(blankTime);

		int count = 1;
		for (WebElement blank : blankList) {
			// 違うセクションなら処理をする
			if (blank.findElement(By.xpath(
					"../option[1]")).isSelected()) {
				WebElement button = blank.findElement(By.xpath("../../../td/button[text()='定時']"));
				scrollToElement(button);

				// スクリーンショットを取得
				getEvidence(new Object() {
				}, String.format("%02d_01", count));

				button.click();

				// スクリーンショットを取得
				getEvidence(new Object() {
				}, String.format("%02d_02", count));
				count++;
			}
		}
		if (count > 1) {
			By button = By.xpath("//input[@value='更新']");
			// 要素へスクロール
			scrollToLocate(button);
			visibilityTimeout(button, WAIT_SECOND);
			// 「更新」ボタンを押下
			clickOn(button);
			// OKを押下
			alertAccept();
		}

		By update = By.xpath("//span[text()='" + EXPECTED_MESSAGE + "']");
		// 表示待ち
		visibilityTimeout(update, WAIT_SECOND);
		// 登録完了メッセージの検証
		assertEquals(EXPECTED_MESSAGE, webDriver.findElement(update).getText());
		// 打刻漏れの検証
		By blank = By.xpath("//tbody/tr/td[position()=3 or position()=4][normalize-space()='']");
		blankList = webDriver.findElements(blank);
		assertTrue(blankList.size() == 0);
		// URLの検証
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + EXPECTED_URL, WAIT_SECOND));

		// スクリーンショットを取得
		getEvidence(new Object() {
		}, String.format("%02d", count));
	}
}
