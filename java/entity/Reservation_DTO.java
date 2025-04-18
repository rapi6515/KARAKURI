package entity;

public class Reservation_DTO {
    private int planId; 
    private String planName;

    // コンストラクタ
    public Reservation_DTO(int planId, String planName) {
        this.planId = planId;
        this.planName = planName;
    }
    public Reservation_DTO() {}

    // GetterとSetter
    public int getPlanId() { return planId; }
    public void setPlanId(int planId) { this.planId = planId; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
}
