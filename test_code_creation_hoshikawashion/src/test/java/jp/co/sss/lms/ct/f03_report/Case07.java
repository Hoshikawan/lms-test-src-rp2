package jp.co.sss.lms.ct.f03_report;

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
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		/*
		 * webDriver.findElement(By.xpath("(//tr[td[text() = '未提出']]//input[@value='詳細'])[1]").click();
		 * ↑最初は上記の方法で試してみたが、エラーが出てきてしまった。理由として考えられるのは、
		 * 		→「DOM構造の想定違い」つまり今回だと、「「divの中にある」という前提が間違っている可能性が高い」
		 * 
		 * テキスト（表示文字）での条件指定をするために、XPathを使用する。
		 *   →XPathはタグ名に合わせて書き換えるだけ。今回は下記のようになる。
		 * //tr[td[text() = '未提出']] = tdが"未提出"のtr（行）を探す
		 * //input[@value='詳細'] = inputタグで詳細を書かれているものを探す
		 * (...)[1] = 一番上の1件だけ
		 */
		
//		// [7]以降はこれが必要
//		// テストできるのはその時画面に映っている部分だけなので、数字が大きくなるにつれてスクロールしないといけない。
//		pageLoadTimeout(10);
//		scrollTo("700");
		
		/* 
		 * 理論上テストコードはこれで問題ないはずだが、Seleniumが実際にブラウザを操作しているためDBを初期化しないとエラーになる。
		 * 
		 * //tr = ページ全体からtrを全部探す（今回だと日付、学習内容、日報の有無、試験の有無、詳細で1つのtr）
		 * tr[各条件] = 条件付きtrである
		 * .//span = .がその中の、//spanがspanを探す（= そのtrの中にあるspanを探す）
		 * [normalize-space()='未提出'] = そのspanの文字が「未提出」
		 * 	 → //tr[.//span[normalize-space()='未提出']] = 「未提出を含むtrだけ残す」
		 * ( さっきの結果 )[1] = 一番上の1件だけ取得
		 * 		※今回はDBの更新を毎回やっていないため[]の中の数字が大きくなっていく
		 */
		webDriver.findElement(
			    By.xpath("(//tr[.//span[normalize-space()='未提出']]//input[@value='詳細'])[1]")
			).click();
		
//		// [7]以降はこれが必要
//		pageLoadTimeout(10);
//		scrollTo("100");
		
		// セクション詳細画面に遷移したか確認
		WebElement section = webDriver.findElement(By.className("active"));
		assertEquals("セクション詳細", section.getText());
		
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 日報を提出するボタンをクリック
		WebElement submission = webDriver.findElement(By.cssSelector("input[value='日報【デモ】を提出する']"));
		submission.click();
		
		// レポート登録画面に遷移したか確認
		WebElement dailyReport = webDriver.findElement(By.className("bs-component"));
		assertEquals("報告レポート\n本日の報告内容をお書きください。", dailyReport.getText());
		
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// 値を渡したい要素を見つけて、中身をきれいにし、値を代入
		WebElement dailyReport = webDriver.findElement(By.id("content_0"));
		dailyReport.clear();
		dailyReport.sendKeys("日報テスト");
		
		// 提出するボタンをクリック
		WebElement submitButton = webDriver.findElement(By.className("btn-primary"));
		submitButton.click();
		
		// ボタン名が提出済み日報【デモ】を確認するになったか確認する。
		/*
		 * .getText() = タグの中に表示されているテキストが取得される
		 * 		例：<legend>報告レポート</legend> → 報告レポート
		 * .getAttribute("value") = HTML属性valueの値が取得される
		 * 		例：<input type="submit" value="提出済み日報【デモ】を確認する"> → 提出済み日報【デモ】を確認する
		 */
		WebElement changeConfirmation = webDriver.findElement(By.xpath("//input[@value='提出済み日報【デモ】を確認する']"));
		assertEquals("提出済み日報【デモ】を確認する", changeConfirmation.getAttribute("value"));
		
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
