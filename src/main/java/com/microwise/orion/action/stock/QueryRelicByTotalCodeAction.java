package com.microwise.orion.action.stock;

import com.microwise.blackhole.sys.Sessions;
import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.service.RelicService;
import com.microwise.orion.sys.Orion;
import com.opensymphony.xwork2.Action;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 文物查询
 *
 * @author gaohui
 * @date 13-5-29 11:28
 *
 * @check xiedeng 2013-6-6 15:38 svn:3874
 */
@Beans.Action
@Orion
public class QueryRelicByTotalCodeAction {

    /**
     * 档案
     */
    @Autowired
    private RelicService relicService;
    

    //input
    private String totalCode;
    private String name;
    private String zoneId;
    private String typeCode;
    // 设置条件默认值为-1
    private Integer eraId = -1;
    private Integer levelId = -1;
    private Integer textureId = -1;

    private List<Relic> relics;
    private int count;

    /**
     * 文物查询
     * @return
     */
    public String queryJson() {
        String siteId = Sessions.createByAction().currentSiteId();

        relics = relicService.findRelics(totalCode, name, zoneId, null,
                typeCode, eraId, levelId, textureId, siteId, -1, 0,
                Integer.MAX_VALUE,false);
        
        return Action.SUCCESS;
    }
    
    public String getTotalCode() {
        return totalCode;
    }
    public void setTotalCode(String totalCode) {
        this.totalCode = totalCode;
    }
    public List<Relic> getRelics() {
        return relics;
    }
    public void setRelics(List<Relic> relics) {
        this.relics = relics;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public Integer getEraId() {
		return eraId;
	}
	public void setEraId(Integer eraId) {
		this.eraId = eraId;
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public Integer getTextureId() {
		return textureId;
	}
	public void setTextureId(Integer textureId) {
		this.textureId = textureId;
	}
}
