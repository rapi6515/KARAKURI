package common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationDesain {
    private K_DAO dao = new K_DAO();

    public int createReservation(String date, String time, String plan, String comment) {
        String query = "INSERT INTO reservations (date, time, plan, comment) VALUES (?, ?, ?, ?)";
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, date);
        params.put(2, time);
        params.put(3, plan);
        params.put(4, comment);
        return dao.executeBundle(query, List.of(params));
    }

    public int updateReservation(int id, String date, String time, String plan, String comment) {
        String query = "UPDATE reservations SET date=?, time=?, plan=?, comment=? WHERE id=?";
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, date);
        params.put(2, time);
        params.put(3, plan);
        params.put(4, comment);
        params.put(5, id);
        return dao.executeBundle(query, List.of(params));
    }

    public int deleteReservation(int id) {
        String query = "DELETE FROM reservations WHERE id=?";
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        return dao.executeBundle(query, List.of(params));
    }

    public List<Map<String, Object>> getReservations() {
        String query = "SELECT * FROM reservations";
        return dao.executeQuery(query);
    }
}
