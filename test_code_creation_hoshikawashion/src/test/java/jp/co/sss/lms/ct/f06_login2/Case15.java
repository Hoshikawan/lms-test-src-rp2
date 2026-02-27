package jp.co.sss.lms.ct.f06_login2;

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
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.f01_login1.Case01;

/**
 * 結合テスト ログイン機能②
 * ケース15
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース15 受講生 初回ログイン 利用規約に不同意")
public class Case15 {

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
		getEvidence(new Case01() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		// ログイン画面を開く
		WebElement loginId = webDriver.findElement(By.id("loginId"));
		WebElement pass = webDriver.findElement(By.id("password"));
		
		// テストを毎回同じ条件で実行するためにclearメソッドを実行
		loginId.clear();
		pass.clear();
		
		// DBに初期登録された未ログインの受講生ユーザーID・パスワードを入力
		loginId.sendKeys("StudentAA02");
		pass.sendKeys("StudentAA02");
		
		// ログインボタンを押す
		WebElement loginButton = webDriver.findElement(By.className("btn-primary"));
		loginButton.click();
		
		// 利用規約画面に遷移することを検証する
		assertEquals("http://localhost:8080/lms/user/agreeSecurity", webDriver.getCurrentUrl());
		
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックをせず「次へ」ボタンを押下")
	void test03() {
		// 次へボタンをクリック
		WebElement next = webDriver.findElement(By.xpath("//button[text()='次へ']"));
		next.click();
		
		// 利用規約エラー画面に遷移することを検証する
		assertEquals("http://localhost:8080/lms/user/agreeSecurity/changePassword", webDriver.getCurrentUrl());
		
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
