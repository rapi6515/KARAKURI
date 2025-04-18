// SessionCheckUtil.java
package Commom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionManager{
	
	//セッションタイムアウトを確認するメソッド
    public static boolean timeout(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
       
    	//既存のセッションを取得、もしセッションが無ければ、nullを返す
    	HttpSession session = req.getSession(false);
    	
    	//セッションが無効またはタイムアウトしている場合
        if (session == null || session.getAttribute("tBean") == null) {
        	//新たにセッションを作成
            session = req.getSession(true);
            //セッションにtimeout属性をtrueとして設定(タイムアウトメッセージ表示のため)
            session.setAttribute("timeout", true);
            //ログイン画面にリダイレクト
            res.sendRedirect(req.getContextPath() +"/jsp/Login.jsp");
            return true;
        }
        return false;
    }
    
    
    //セッションに値を追加（または上書き）するメソッド
    public static void setAttribute(HttpServletRequest req, String key, Object value) {
        HttpSession session = req.getSession(true); // セッションがなければ新しく作る
        session.setAttribute(key, value);
    }
	    /*
	     * セットしたい時
	     	例） SessionManager.setAttribute(req, "userName", "田中さん");
	     	HttpServletRequest req： セッションを取得するために必要
	    	String key： 保存するデータの名前（例："userName"）
	    	Object value： 保存するデータそのもの（何でもOK）
	    */
	    
    

    // セッションから値を取得するメソッド（nullになる可能性あり）
    public static Object getAttribute(HttpServletRequest req, String key) {  
        HttpSession session = req.getSession(false);
        if (session != null) {
            return session.getAttribute(key);
        }
        return null;
    }

    	/*
    	 * ゲットしたい時
		 	例） String name = (String) SessionManager.getAttribute(req, "userName");
		 	HttpServletRequest req： セッションを取得するために必要
			String key： 欲しいデータの名前（例："userName"）
			※ 戻り値がObject 型の為、使う時はキャストが必要
    	 */
    
    
    
    // セッションから指定したデータを削除するメソッド
    public static void removeAttribute(HttpServletRequest req, String key) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute(key);
        }
    }
	    /*
	     * リムーブしたい時
		 	例） SessionManager.removeAttribute(req, "userName");
		 	HttpServletRequest req： セッションを取得するために必要
			String key： 削除したいデータの名前（例："userName"）
	     */


    

    // セッション全体を無効化する（ログアウト時など）
    public static void invalidate(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
	    /*
	     * セッション全体を削除したい時
		 	例） SessionManager.invalidate(req);
		 	HttpServletRequest req： セッションを取得して無効にするため
	     */
    
    
    
}
