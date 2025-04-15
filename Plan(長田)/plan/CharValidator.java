package plan;


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
		//Pattern p = Pattern.compile("/p{HalfWidth}"); //空文字をNGとしたい場合
		Pattern p = Pattern.compile("^[\u30a0-\u30ff]*$"); 	//空文字をOKとしたい場合
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
		//Pattern p = Pattern.compile("^[+-]?[0-9]+$") 		  	//空文字をNGとしたい場合
		Pattern p = Pattern.compile("^[+-]?[0-9]*$"); //空文字をOKとしたい場合
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
