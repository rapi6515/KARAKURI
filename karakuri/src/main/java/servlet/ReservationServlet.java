package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ReservationDesain;

@WebServlet("/reservation")
public class ReservationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReservationDesain reservationService = new ReservationDesain();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestData = getRequestBody(request);

        String action = extractValue(requestData, "action");
        String date = extractValue(requestData, "date");
        String time = extractValue(requestData, "time");
        String plan = extractValue(requestData, "plan");
        String comment = extractValue(requestData, "comment");
        int result = 0;

        if ("create".equals(action)) {
            result = reservationService.createReservation(date, time, plan, comment);
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(extractValue(requestData, "id"));
            result = reservationService.updateReservation(id, date, time, plan, comment);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(extractValue(requestData, "id"));
            result = reservationService.deleteReservation(id);
        }

        response.getWriter().write(result > 0 ? "成功" : "失敗");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, Object>> reservations = reservationService.getReservations();

        StringBuilder jsonOutput = new StringBuilder("[");
        for (int i = 0; i < reservations.size(); i++) {
            Map<String, Object> record = reservations.get(i);
            jsonOutput.append("{")
                      .append("\"id\":").append(record.get("id")).append(",")
                      .append("\"date\":\"").append(record.get("date")).append("\",")
                      .append("\"time\":\"").append(record.get("time")).append("\",")
                      .append("\"plan\":\"").append(record.get("plan")).append("\",")
                      .append("\"comment\":\"").append(record.get("comment")).append("\"")
                      .append("}");
            if (i < reservations.size() - 1) jsonOutput.append(",");
        }
        jsonOutput.append("]");

        response.setContentType("application/json");
        response.getWriter().write(jsonOutput.toString());
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }

    private String extractValue(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? matcher.group(1) : "";
    }
}
