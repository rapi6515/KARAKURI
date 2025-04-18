//戸高　②PlanDetailServletプラン詳細を表示次のフォームjspへ
package ecSite;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Commom.K_Bean;


@WebServlet("/PlanDetailServlet")
public class PlanDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");
        String planId = req.getParameter("plan_id");

        // セッションから検索条件を取得
        HttpSession session = req.getSession();
        String date = (String) session.getAttribute("date");
        String time = (String) session.getAttribute("time");
        String people = (String) session.getAttribute("people");

        // 予約デザインクラスを利用してプランを取得
        ReservDesign rde = new ReservDesign();
        Map<String, Object> planDetail = rde.getPlanDetail(planId);

        // 取得したデータをリクエストスコープに保存
        req.setAttribute("planDetail", planDetail);
        req.setAttribute("date", date);
        req.setAttribute("time", time);
        req.setAttribute("people", people);

        // JSPへ転送前に、セッションにプランIDを保存
        K_Bean bean = new K_Bean();
        bean.setPlan_id(planId);
        session.setAttribute("reservation", bean);

        // ここでプラン選択画面に戻るための処理を追加
        // 検索条件をそのまま保持して、プラン詳細ページに遷移
        RequestDispatcher rd = req.getRequestDispatcher("jsp/planDetail.jsp");
        rd.forward(req, res);
    }

    // doGetメソッド
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }
}


