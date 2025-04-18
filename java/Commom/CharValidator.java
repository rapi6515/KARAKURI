package Commom;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//文字検証クラス
public class CharValidator {

	public static boolean isKanaHalf(String value) {
		//nullの場合はfalseを返す
		if (value == null) {
			return false;
		}
		//半角カナ文字+半角スペース判定
		Pattern p = Pattern.compile("^[｡-ﾟ]+$ && /s+"); //空文字をNGとする場合
		//Pattern p = Pattern.compile("^[｡-ﾟ]*$");       				//空文字をOKとする場合

		Matcher m = p.matcher(value);

		return m.find();
	}

	public static boolean isKanaFull(String value) {
		//nullの場合はfalseを返す
		if (value == null) {
			return false;
		}
		// 全角カナ文字＋全角スペース判定
		Pattern p = Pattern.compile("^[\u30a0-\u30ff]+$ || /u3000+$"); //空文字をNGとしたい場合
		//Pattern p = Pattern.compile("^[\u30a0-\u30ff]*$ || /u3000+");	//空文字をOKとしたい場合
		Matcher m = p.matcher(value);

		return m.find();
	}

	public static boolean isHiragana(String value) {
		//nullの場合はfalseを返す
		if (value == null) {
			return false;
		}
		// ひらがな判定
		Pattern p = Pattern.compile("^[\u3040-\u309F]+$ "); //空文字をNGとしたい場合
		//Pattern p = Pattern.compile("^[\u3040-\u309F]*$"); 	//空文字をNGとしたい場合
		Matcher m = p.matcher(value);

		return m.find();
	}

	public static boolean isHalf(String value) {
		//nullの場合はfalseを返す
		if (value == null) {
			return false;
		}
		// 半角文字判定
		Pattern p = Pattern.compile("^[\u30a0-\u30ff]$"); //空文字をNGとしたい場合　　
		//Pattern p = Pattern.compile("^[\u30a0-\u30ff]*$"); 	//空文字をOKとしたい場合
		Matcher m = p.matcher(value);

		return m.find();
	}


	public static boolean isFull(String value) {
		if (value == null) {
			return false;
		}
		// 全角文字判定
		//Pattern p = Pattern.compile("/p{FullWidth}+"); 			//空文字をNGとしたい場合
		Pattern p = Pattern.compile("^[^!-~｡-ﾟ]*$"); //空文字をOKとしたい場合
		Matcher m = p.matcher(value);

		return m.find();
	}

	public static boolean isPlusInt(String value) {
		if (value == null) {
			return false;
		}
		// +整数判定
		//Pattern p = Pattern.compile("^[+]?[0-9]+$"); 		  	//空文字をNGとしたい場合
		Pattern p = Pattern.compile("^[+]?[0-9]*$"); //空文字をOKとしたい場合
		Matcher m = p.matcher(value);

		return m.find();
	}

	public static boolean isInteger(String value) {
		if (value == null) {
			return false;
		}
		// 整数判定
		Pattern p = Pattern.compile("^[+-]?[0-9]+$"); //空文字をNGとしたい場合
		//Pattern p = Pattern.compile("^[+-]?[0-9]*$"); //空文字をOKとしたい場合
		Matcher m = p.matcher(value);

		return m.find();
	}

	public static boolean isNumber(String value) {
		if (value == null) {
			return false;
		}
		// 数値判定
		Pattern p = Pattern.compile("^[+-]?[0-9]+$"); //空文字をNGとしたい場合
		//Pattern p = Pattern.compile("^[+-]?[0-9]*$");        	//空文字をOKとしたい場合
		Matcher m = p.matcher(value);

		return m.find();
	}

	public static boolean isTel(String value) {
		if (value == null) {
			return false;
		}
		// 数字とハイフンOK
		Pattern phonePattern = Pattern.compile("^[0-9\\-]+$");
		Matcher m = phonePattern.matcher(value);
		return m.find();

	}

	public static boolean isBirthday(String value) {
		if (value == null || value.trim().isEmpty()) {
			return false; // 必須チェックは呼び出し元で行う想定
		}
		// 形式チェック（yyyy-MM-dd）
		Pattern datePattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
		Matcher m = datePattern.matcher(value);
		return m.find();
	}

	public static boolean isAddressValid(String value) {
		if (value == null || value.trim().isEmpty()) {
			return false; // 空文字NG
		}
		// 日本語住所で使用される文字（全角文字、半角数字、ハイフン、スペースなど）を許容
		Pattern p = Pattern.compile("^[\\p{InHiragana}\\p{InKatakana}\\p{InCJKUnifiedIdeographs}ー－0-9０-９－\\-\\s　]+$");
		Matcher m = p.matcher(value);
		return m.matches();
	}

	public static boolean isBuildingNameValid(String value) {
		if (value == null) {
			return true; // 任意入力のためnullでもOK
		}
		// 任意の文字列（文字数制限などは呼び出し側で）
		// 制御文字などを除き、大抵の文字を許容
		Pattern p = Pattern.compile("^[^\\p{Cntrl}]+$"); // 制御文字以外ならOK
		Matcher m = p.matcher(value);
		return m.matches();
	}
	
	public static boolean isValidTime(String time) {
        if (time == null || time.isEmpty()) {
            return false; // 未入力
        }
 
        // HH:mm:ss の形式をチェックする正規表現
        Pattern pattern = Pattern.compile("^([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$");
        Matcher matcher = pattern.matcher(time);
 
        if (!matcher.matches()) {
            return false; // 形式不正
        }
 
        return true; // 形式OK
    }

}