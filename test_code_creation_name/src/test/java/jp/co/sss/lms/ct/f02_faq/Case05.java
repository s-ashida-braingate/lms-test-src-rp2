package jp.co.sss.lms.ct.f02_faq;

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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		gotoHelp(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		gotoFaq(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {

		// フォームが表示されるまで待つ
		By form = By.id("form");
		visibilityTimeout(form, WAIT_SECOND);
		// フォームに入力し実行
		webDriver.findElement(form).clear();
		webDriver.findElement(form).sendKeys("キャンセル");
		webDriver.findElement(form).sendKeys(Keys.ENTER);

		// GETリクエストの確認
		assertTrue(expectedUrlTimeout(CONTEXT_PATH
				+ "faq?keyword=%E3%82%AD%E3%83%A3%E3%83%B3%E3%82%BB%E3%83%AB", 5));

		// スクリーンショット取得
		getEvidence(new Object() {
		}, "01");

		// 検索結果が表示されるまで待つ
		visibilityTimeout(By.className("text-primary"), WAIT_SECOND);
		// 下記の検索結果が表示されているか
		List<WebElement> elems = webDriver.findElements(By.xpath(
				"//span[contains(text(), 'キャンセル料・途中退校について')]"));
		assertTrue(elems.size() == 1);

		// 少しだけスクロール
		scrollTo("200");
		// スクリーンショット取得
		getEvidence(new Object() {
		}, "02");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {

		// 画面上部へスクロール
		scrollTo("0");

		// スクリーンショット取得
		getEvidence(new Object() {
		}, "01");

		// クリアボタンをクリック
		WebElement clearBtn = webDriver.findElements(By.className("btn-primary")).get(1);
		clearBtn.click();

		// フォームが消去されているか
		By form = By.id("form");
		assertTrue(expectedConditionTimeout(webDriver -> {
			// フォームの値を取得
			String val = webDriver.findElement(form).getAttribute("value");
			// 空かどうか
			return val == null || val.isBlank();
		}, WAIT_SECOND));

		// スクリーンショット取得
		getEvidence(new Object() {
		}, "02");
	}

}
