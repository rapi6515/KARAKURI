package Commom;
//サインイン情報専用
public class T_Bean {
	
	private String tStaff_id = "";       //スタッフID
	private String tPodition = "";       //役職
	private String tName = "";           //氏名
	private String tRank_id = "";        //権限
	private String tAvatar="";           //アバター
	public String gettStaff_id() {
		return tStaff_id;
	}
	public void settStaff_id(String tStaff_id) {
		this.tStaff_id = tStaff_id;
	}
	public String gettPodition() {
		return tPodition;
	}
	public void settPodition(String tPodition) {
		this.tPodition = tPodition;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	public String gettRank_id() {
		return tRank_id;
	}
	public void settRank_id(String tRank_id) {
		this.tRank_id = tRank_id;
	}
	public String gettAvatar() {
		return tAvatar;
	}
	public void settAvatar(String tAvatar) {
		this.tAvatar = tAvatar;
	}
	
	
}
