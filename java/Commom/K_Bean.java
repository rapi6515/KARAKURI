package Commom;

public class K_Bean {
	
	private String staff_id = "";       //スタッフID
	private String podition = "";       //役職
	private String name = "";           //氏名
	private String job_name = "";      	//職種名
	private String tel = "";            //電話番号
	private String sex = "";            //性別
	private String rank_id = "";           //権限
	private String address = "";        //住所
	private String residence = "";      //建物名
	private String avatar = "";         //アバター
	private String reserve_num = "";    //予約番号
	private String reserve_day = "";    //予約日付
	private String reserve_time = "";   //予約時間
	private String reserve_comment = "";//予約コメント
	private String kana = "";           //氏名カナ
	private String rank = "";           //権限
	private String plan_id = "";        //プランID
	private String birth_day = "";      //生年月日
	private String email = "";           //メアド
	private String nationality = "";    //国籍
	private String reason = "";         //予約のきっかけ
	private String start_time = "";		//予約検索時にフォームに入力した予約スタート時間（戸高）
	private String end_time = ""; 		//予約検索時にフォームに入力した予約終了時間（戸高）
	private String date = ""; 			//予約検索時にフォームに入力した日にち（戸高）
	private String t_plan_id;			//テーブル用プランID
	private String plan_name;			//プラン名
	private String stock;				//在庫
	private String cost;				//単価
	private String subplan;				//サブプラン
	private String contents;			//プラン内容
	private String time_required;		//正味所要
	private String plan_required;		//プラン所要
	private String periodstart;			//開始日
	private String periodend;			//終了日
	private String btn = "";            //ボタン
	private String staff_memo;			//スタッフメモ
	private String errStf = "";			//staffエラーメッセージ
	private String errName = "";		//名前エラーメッセージ
	private String errJob = "";			//職種エラーメッセージ
	private String errSex = "";			//性別エラーメッセージ
	private String errRank = "";		//権限エラーメッセージ
	private String errPod = "";			//役職エラーメッセージ
	private String errTel = "";			//TELエラーメッセージ
	private String errBirth = "";		//誕生日エラーメッセージ
	private String errAddress = "";		//住所エラーメッセージ
	private String errResidence = "";	//建物名エラーメッセージ
	private String errAvatar = "";		//アバターエラーメッセージ
	private String job_id1;				//職種ID1
	private String job_id2;				//職種ID2
	private String job_id3;				//職種ID3
	private String image;				//画像
	
	
	
	
	
	public String getErrAddress() {
		return errAddress;
	}
	public void setErrAddress(String errAddress) {
		this.errAddress = errAddress;
	}
	
	public String getErrStf() {
		return errStf;
	}
	public void setErrStf(String errStf) {
		this.errStf = errStf;
	}
	public String getErrName() {
		return errName;
	}
	public void setErrName(String errName) {
		this.errName = errName;
	}
	public String getErrJob() {
		return errJob;
	}
	public void setErrJob(String errJob) {
		this.errJob = errJob;
	}
	public String getErrSex() {
		return errSex;
	}
	public void setErrSex(String errSex) {
		this.errSex = errSex;
	}
	public String getErrRank() {
		return errRank;
	}
	public void setErrRank(String errRank) {
		this.errRank = errRank;
	}
	public String getErrPod() {
		return errPod;
	}
	public void setErrPod(String errPod) {
		this.errPod = errPod;
	}
	public String getErrTel() {
		return errTel;
	}
	public void setErrTel(String errTel) {
		this.errTel = errTel;
	}
	public String getErrBirth() {
		return errBirth;
	}
	public void setErrBirth(String errBirth) {
		this.errBirth = errBirth;
	}
	
	public String getErrResidence() {
		return errResidence;
	}
	public void setErrResidence(String errResidence) {
		this.errResidence = errResidence;
	}
	public String getErrAvatar() {
		return errAvatar;
	}
	public void setErrAvatar(String errAvatar) {
		this.errAvatar = errAvatar;
	}
	public String getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}
	public String getPodition() {
		return podition;
	}
	public void setPodition(String podition) {
		this.podition = podition;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob_name() {
		return job_name;
	}
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getRank() {
		return rank_id;
	}
	public void setRank(String rank) {
		this.rank_id = rank;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getReserve_num() {
		return reserve_num;
	}
	public void setReserve_num(String reserve_num) {
		this.reserve_num = reserve_num;
	}
	public String getReserve_day() {
		return reserve_day;
	}
	public void setReserve_day(String reserve_day) {
		this.reserve_day = reserve_day;
	}
	public String getReserve_time() {
		return reserve_time;
	}
	public void setReserve_time(String reserve_time) {
		this.reserve_time = reserve_time;
	}
	public String getReserve_comment() {
		return reserve_comment;
	}
	public void setReserve_comment(String reserve_comment) {
		this.reserve_comment = reserve_comment;
	}
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}
	public String getBirth_day() {
		return birth_day;
	}
	public void setBirth_day(String birth_day) {
		this.birth_day = birth_day;
	}
	public String getBtn() {
		return btn;
	}
	public void setBtn(String btn) {
		this.btn = btn;
	}

	public String getPlan_name() {
		return plan_name;
	}
	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getSubplan() {
		return subplan;
	}
	public void setSubplan(String subplan) {
		this.subplan = subplan;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getTime_required() {
		return time_required;
	}
	public void setTime_required(String time_required) {
		this.time_required = time_required;
	}
	public String getPlan_required() {
		return plan_required;
	}
	public void setPlan_required(String plan_required) {
		this.plan_required = plan_required;
	}
	
	public String getStaff_memo() {
		return staff_memo;
	}
	public void setStaff_memo(String staff_memo) {
		this.staff_memo = staff_memo;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getT_plan_id() {
		return t_plan_id;
	}
	public void setT_plan_id(String t_plan_id) {
		this.t_plan_id = t_plan_id;
	}
	public String getPeriodstart() {
		return periodstart;
	}
	public void setPeriodstart(String periodstart) {
		this.periodstart = periodstart;
	}
	public String getPeriodend() {
		return periodend;
	}
	public void setPeriodend(String periodend) {
		this.periodend = periodend;
	}
	public String getJob_id1() {
		return job_id1;
	}
	public void setJob_id1(String job_id1) {
		this.job_id1 = job_id1;
	}
	public String getJob_id2() {
		return job_id2;
	}
	public void setJob_id2(String job_id2) {
		this.job_id2 = job_id2;
	}
	public String getJob_id3() {
		return job_id3;
	}
	public void setJob_id3(String job_id3) {
		this.job_id3 = job_id3;
	}
	
	/**
	 * @return kana
	 */
	public String getKana() {
		return kana;
	}
	/**
	 * @param kana セットする kana
	 */
	public void setKana(String kana) {
		this.kana = kana;
	}
	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email セットする email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return nationality
	 */
	public String getNationality() {
		return nationality;
	}
	/**
	 * @param nationality セットする nationality
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	/**
	 * @return reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason セットする reason
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * @return start_time
	 */
	public String getStart_time() {
		return start_time;
	}
	/**
	 * @param start_time セットする start_time
	 */
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	/**
	 * @return end_time
	 */
	public String getEnd_time() {
		return end_time;
	}
	/**
	 * @param end_time セットする end_time
	 */
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	/**
	 * @return date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date セットする date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	
	
	
}
	
