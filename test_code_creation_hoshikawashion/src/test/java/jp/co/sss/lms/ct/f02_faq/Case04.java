package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
		// 画面遷移
		goTo("http://localhost:8080/lms/");
		// ログイン画面の中に「"loginId"というIDを持つ要素が画面に表示されているか」
		assertTrue(webDriver.findElement(By.id("loginId")).isDisplayed());
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// ログイン画面を開く
		WebElement loginId = webDriver.findElement(By.id("loginId"));
		WebElement pass = webDriver.findElement(By.id("password"));
		
		// テストを毎回同じ条件で実行するためにclearメソッドを実行
		loginId.clear();
		pass.clear();
		
		// 初回ログイン済みユーザーID・パスワードを入力
		loginId.sendKeys("StudentAA01");
		pass.sendKeys("StudentAA010101");
		
		// ログインボタンを押す
		WebElement loginButton = webDriver.findElement(By.className("btn-primary"));
		loginButton.click();
		
		// コース詳細画面に遷移することを検証する
		WebElement courseDetail = webDriver.findElement(By.className("active"));
		assertEquals("コース詳細", courseDetail.getText());
		
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 要素の描画遅延を考慮し、最大10秒間待機する
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		
		// クラス名"dropdown"の要素を探し、その要素がクリック可能になるまで待つ
		WebElement function = wait.until(
				ExpectedConditions.elementToBeClickable(By.className("dropdown"))
		);
		// クリックする
		function.click();

		// ヘルプリンク押下
		WebElement helpLink = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/lms/help']"))
		);
		helpLink.click();
		
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 「よくある質問」リンクを取得
		WebElement faq = webDriver.findElement(By.linkText("よくある質問"));
		faq.click();
		
		// 現在のタブハンドルを保持
		String tab = webDriver.getWindowHandle();
		
		// 新しいタブを開く
		WebElement newTab = webDriver.findElement(By.id("wrap"));
		newTab.click();
		
		// 全タブを取得し、新規タブへ切り替え
		Set<String> allTabs = webDriver.getWindowHandles();
		for(String nowTab : allTabs) {
			if(!nowTab.equals(tab)) {
				webDriver.switchTo().window(nowTab);
				break;
			}
		}
		
		// FAQ画面へ正しく遷移したことを確認
		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());
		
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
