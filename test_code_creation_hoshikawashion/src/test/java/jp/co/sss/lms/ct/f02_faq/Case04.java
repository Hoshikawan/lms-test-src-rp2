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
		
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		// コース詳細画面に機能というプルダウン（ドロップダウンメニュー）があるか探す。
//		WebElement dropdown = webDriver.findElement(By.className("dropdown-toggle"));
//		
//		// クリックする
//		dropdown.click();
		
		// そのプルダウンに ヘルプ リンクがあるか探す
//		WebElement function = webDriver.findElement(By.className("dropdown-toggle"));
		
		WebElement function = wait.until(
				ExpectedConditions.elementToBeClickable(By.className("dropdown"))
		);
		// クリックする
		function.click();
		
//		// エビデンス取得
//		getEvidence(new Object() {
//		});
		
		// org.openqa.selenium.NoSuchElementException: no such element: Unable to locate element: {"method":"partial link text","selector":"ヘルプ"}
		// このように赤タグが出てきたので、どこで間違えているかのチェックを実施
//		System.out.println("URL: " + webDriver.getCurrentUrl());
//		System.out.println(webDriver.getTitle());
//		System.out.println("ヘルプ含まれている？: " +webDriver.getPageSource().contains("ヘルプ"));
//		WebElement helpLink = webDriver.findElement(By.cssSelector("a[href*='/lms/help']"));

		
		// そのプルダウンに ヘルプ リンクがあるか探す
//		WebElement helpLink = webDriver.findElement(By.partialLinkText("ヘルプ"));
//		WebElement helpLink = webDriver.findElement(By.cssSelector("a[href='http://localhost:8080/lms/help']"));
//		WebElement helpLink = webDriver.findElement(By.xpath("//a[contains(@href,'/lms/help')]"));
//		System.out.println("Displayed: " + helpLink.isDisplayed());  要素が画面上に見えているか
//		System.out.println("Enabled: " + helpLink.isEnabled());  要素が操作可能か

		WebElement helpLink = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/lms/help']"))
		);
		// クリックする
		helpLink.click();
		
		// 本当にヘルプ画面に遷移したか確認
//		assertEquals("http://localhost:8080/lms/help", webDriver.getCurrentUrl());
		
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// Case3で遷移した画面の中にある「よくある質問」を探す
		WebElement faq = webDriver.findElement(By.linkText("よくある質問"));
		
		// クリックする
		faq.click();
		
		// getWindowHandle()は「現在操作中のタブを識別するID」を返すメソッドで、
		// このIDはブラウザ内部でユニークな文字列として管理されてるため「型はString」となる
		String tab = webDriver.getWindowHandle();
		
		// 新しいタブを開く
		WebElement newTab = webDriver.findElement(By.id("wrap"));
		
		// クリックする
		newTab.click();
		
		// 全てのタブのIDを取得
		// allTabs の中に「元のタブ+新しいタブ」が含まれる
		// 元のタブのハンドルと違うものを探す → 新しいタブがどれか分かる
		Set<String> allTabs = webDriver.getWindowHandles();
		
		// 現在いくつのタブが開かれているか分からないから拡張for文で全て見て、
		// その中のtab（現在操作中のタブを識別するID）じゃないのがあったら
		// （新しいタブを）操作対象にする  ※ 全ハンドルを取得しないと、新しいタブを識別できない
		for(String nowTab : allTabs) {
			if(!nowTab.equals(tab)) {
				// 新しいタブを操作対象にする
				webDriver.switchTo().window(nowTab);
				break;
			}
		}
		
		// 本当によくある質問画面に遷移したか確認
		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());
		
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
