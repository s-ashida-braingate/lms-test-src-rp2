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
import org.openqa.selenium.WebElement;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
		visibilityTimeout(By.id("loginId"), WAIT_SECOND);
		// URLの確認
		assertTrue(expectedUrlTimeout(CONTEXT_PATH, WAIT_SECOND));

		// スクリーンショット取得
		getEvidence(new Object() {
		});
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
		visibilityTimeout(By.tagName("small"), WAIT_SECOND);
		// ログインユーザの権限とユーザ名の確認
		assertEquals("ようこそ受講生ＡＡ１さん", webDriver.findElement(By.tagName("small")).getText());
		// URLの確認
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + "course/detail", WAIT_SECOND));

		// スクリーンショット取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		// 「機能」リンクが表示されるまで待つ
		visibilityTimeout(By.className("dropdown-toggle"), WAIT_SECOND);
		// 「機能」をクリック
		webDriver.findElement(By.className("dropdown-toggle")).click();
		// 「ヘルプ」リンクが表示されるまで待つ
		visibilityTimeout(By.linkText("ヘルプ"), WAIT_SECOND);
		// 「ヘルプ」をクリック
		webDriver.findElement(By.linkText("ヘルプ")).click();

		// 見出しタグの確認
		assertEquals("ヘルプ", webDriver.findElement(By.tagName("h2")).getText());
		// URLの確認
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + "help", WAIT_SECOND));

		// スクリーンショット取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		// 「よくある質問」リンクが表示されるまで待つ
		visibilityTimeout(By.linkText("よくある質問"), WAIT_SECOND);
		// 「よくある質問」をクリック
		webDriver.findElement(By.linkText("よくある質問")).click();

		// フォーカスを新規ウィンドウに切り替え
		switchToNewWindowTimeout(WAIT_SECOND);
		// 見出しタグが表示されるまで待つ
		visibilityTimeout(By.tagName("h2"), WAIT_SECOND);
		// 見出しタグの確認
		assertEquals("よくある質問", webDriver.findElement(By.tagName("h2")).getText());
		// URLの確認
		assertTrue(expectedUrlTimeout(CONTEXT_PATH + "faq", WAIT_SECOND));

		// スクリーンショット取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {

		// 入力値
		final String SEARCH_CATEGORY = "【研修関係】";
		// 期待値
		final String EXPECTED_URL = "faq?frequentlyAskedQuestionCategoryId=1";
		final String EXPECTED_MSG = "2 件中 1 件から 2 件までを表示";
		final String EXPECTED_RESULT1 = "キャンセル料・途中退校について";
		final String EXPECTED_RESULT2 = "研修の申し込みはどのようにすれば良いですか？";

		// 少しだけスクロール
		scrollTo("400");
		// スクリーンショット取得
		getEvidence(new Object() {
		}, "01");

		// スクロールを戻す
		scrollTo("0");
		By category = By.linkText(SEARCH_CATEGORY);
		// 「【研修関係】」リンクが表示されるまで待つ
		visibilityTimeout(category, WAIT_SECOND);
		// 「【研修関係】」をクリック
		webDriver.findElement(category).click();

		// URLを確認
		assertTrue(expectedUrlTimeout(CONTEXT_PATH +
				EXPECTED_URL, WAIT_SECOND));

		// 検索後の検索件数が表示されるまで待つ
		expectedConditionTimeout(driver -> {
			return driver.findElement(By.id("DataTables_Table_0_info")).getText().equals(EXPECTED_MSG);
		}, WAIT_SECOND);
		// 期待する検索結果が表示されているか
		List<WebElement> elems = webDriver.findElements(By.xpath(
				"//span[text()='Q.']/following-sibling::span[1]"));
		assertTrue(elems.get(0).getText().equals(EXPECTED_RESULT1));
		assertTrue(elems.get(1).getText().equals(EXPECTED_RESULT2));

		// 少しだけスクロール
		scrollTo("400");
		// スクリーンショット取得
		getEvidence(new Object() {
		}, "02");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		// TODO ここに追加
	}

}
