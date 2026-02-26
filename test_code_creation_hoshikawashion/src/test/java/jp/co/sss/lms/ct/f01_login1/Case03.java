package jp.co.sss.lms.ct.f01_login1;

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

/**
 * 結合テスト ログイン機能①
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

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

}
