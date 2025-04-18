package Menu;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entity.K_DTO;

public class MenuRequest {
	private List<Map<String, Object>> menuList;
	//DTOの初期化
	K_DTO dto = new K_DTO();

	//メニュー情報取得
	public MenuRequest() {
		menuList = dto.getmenu();
		if (menuList == null) {
	        menuList = new ArrayList<>();  // nullの場合は空リストを設定
	    }
	}
	
    
    //メニューリストを返す
    public List<Map<String, Object>> getMenuList() {
        return menuList;
    }

}
