package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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
		// クラス名"dropdown"の要素を探し、その要素をクリックする。
		WebElement dropdown = webDriver.findElement(By.className("dropdown"));
		dropdown.click();
		
		// プルダウンに ヘルプ リンクテクストがあるか探す
		WebElement helpLink = webDriver.findElement(By.partialLinkText("ヘルプ"));
		helpLink.click();
		
		// ヘルプ画面に遷移したか確認
		assertEquals("http://localhost:8080/lms/help", webDriver.getCurrentUrl());
		
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
	
	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// テストを毎回同じ条件で実行するための処理
		WebElement keyword = webDriver.findElement(By.id("form"));
		keyword.clear();
		
		// フォーム要素に キャンセル と打ちクリックする
		keyword.sendKeys("キャンセル");
		
		// 検索ボタンをクリック
		WebElement search = webDriver.findElement(By.cssSelector("input[value = '検索']"));
		search.click();
		
		// 待ち処理（ページロードタイムアウト設定）
		pageLoadTimeout(10);
		
		// 検索結果欄を確認
		WebElement result = webDriver.findElement(By.className("mb10"));
		
		// 検索結果に期待している文言（キャンセル・・・）と同一か確認
		assertEquals("Q.キャンセル料・途中退校について", result.getText());
		
		// エビデンス取得
		getEvidence(new Object() {
		});
	}
	
	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// クリアボタンをクリック
		WebElement clear = webDriver.findElement(By.cssSelector("input[value = 'クリア']"));
		clear.click();
		
		// キーワード欄を確認
		WebElement keyword = webDriver.findElement(By.className("form-control"));
		
		// キーワード欄が空白か確認
		assertEquals("", keyword.getText());
		
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
