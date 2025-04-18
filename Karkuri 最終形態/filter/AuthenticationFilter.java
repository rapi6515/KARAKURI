package filter; // パッケージ名は適宜変更してください

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter; // javax.servlet.Filter を実装
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// import Commom.SessionManager; // もし SessionManager でセッションチェックするなら必要

/**
 * ログイン認証を確認するフィルタ
 */
// ★どのURLにこのフィルタを適用するか指定します
// "/*" は全てのURLを意味しますが、ログインページなどは除外する必要があります
// ここでは例として "/reservation", "/MenuServlet", "/K_PlanServlet" など保護したいURLを指定します
// もっと簡単なのは "/*" にして、doFilter内で除外パスを判定する方法です
@WebFilter(urlPatterns = {"/reservation", "/MenuServlet", "/K_PlanServlet", "/KarakuriTop.jsp"}) // ←★保護したいページのURLパターンを指定
// 注意: "/karakuri/*" のようにする場合、ログインページ等を除外する処理がdoFilter内で必要
public class AuthenticationFilter extends HttpFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {

        // HttpServletRequest/Response にキャスト
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 現在のリクエストURLを取得
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String targetPath = requestURI.substring(contextPath.length()); // プロジェクト名以降のパス

        System.out.println("[AuthFilter] Accessing: " + targetPath); // デバッグ用

        // --- チェック除外対象のパス ---
        // ログインページ、ログイン処理Servlet、CSS/JS/画像などはチェックしない
        if (targetPath.equals("/jsp/Login.jsp") || 
            targetPath.equals("/LogServlet") ||
            targetPath.startsWith("/css/") ||   // /css/ 以下のファイル
            targetPath.startsWith("/js/") ||    // /js/ 以下のファイル
            targetPath.startsWith("/image/")) { // /image/ 以下のファイル
            
            System.out.println("[AuthFilter] Auth check skipped."); // デバッグ用
            // チェックせず、そのまま次の処理へ進む
            chain.doFilter(request, response);
            return;
        }

        // --- ログイン状態のチェック ---
        // セッションを取得 (存在しなければ null が返る)
        HttpSession session = httpRequest.getSession(false); 
        boolean loggedIn = false;
        if (session != null) {
            // セッションからログイン情報(tBean)を取得
            Object tBean = session.getAttribute("tBean"); 
            // tBean が null でなければログイン済みとみなす
            if (tBean != null) {
                loggedIn = true;
            }
        }

        // --- ログイン状態に応じて処理を分岐 ---
        if (loggedIn) {
            // ログイン済みの場合：そのまま目的の処理へ進む
            System.out.println("[AuthFilter] Logged in. Proceeding..."); // デバッグ用
            chain.doFilter(request, response);
        } else {
            // 未ログインまたはセッション切れの場合：ログインページへリダイレクト
            System.out.println("[AuthFilter] Not logged in or session expired. Redirecting to login page."); // デバッグ用
            // セッション切れなどのメッセージをリクエストにセット (任意)
            HttpSession sessionForMsg = httpRequest.getSession(true); // セッションを取得(なければ新規作成)
            sessionForMsg.setAttribute("timeout", Boolean.TRUE); // "timeout" という名前で true をセット
            // request.setAttribute("timeoutMsg", "セッションがタイムアウトしました。再度ログインしてください。");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/jsp/Login.jsp");
            // リダイレクトしたら、以降の処理は不要なので return する場合もある
        }
    }

    // init, destroy メソッドは通常は空でOK
    public void init(FilterConfig fConfig) throws ServletException {}
    public void destroy() {}

}