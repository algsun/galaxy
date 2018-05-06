package com.microwise.orion.bean;

import com.microwise.orion.vo.InventoryZoneVo;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 盘点 记录实体
 *
 * @author xubaoji
 * @date 2013-5-25
 * @check 2013-06-04 zhangpeng svn:4010
 */
public class Inventory {

    /**
     * id编号
     */
    private Integer id;

    /**
     * 站点编号
     */
    private String siteId;

    /**
     * 截至时间
     */
    private Date deadlineDate;

    /**
     * 盘点状态 true: 盘点结束 false：正在盘点
     */
    private boolean state;

    /**
     * 总数量
     */
    private Integer sumCount;

    /**
     * 总件数
     */
    private Integer sumNumber;

    /**
     * 总数 按区域统计盘点统计信息
     */
    private List<InventoryZoneVo> countList;

    /**
     * 在库数
     */
    private Integer stockInCount;

    /**
     * 在库总件数
     */
    private Integer stockInNumber;

    /**
     * 在库 按区域统计盘点统计信息
     */
    private List<InventoryZoneVo> stockInList;

    /**
     * 出库数
     */
    private Integer stockOutCount;

    /**
     * 出库总件数
     */
    private Integer stockOutNumber;

    /**
     * 出库 按区域统计盘点统计信息
     */
    private List<InventoryZoneVo> stockOutList;

    /**
     * 标签数
     */
    private Integer tagCount;

    /**
     * 标签总件数
     */
    private Integer tagNumber;

    /**
     * 标签 按区域统计盘点统计信息
     */
    private List<InventoryZoneVo> tagList;

    /**
     * 扫描到的数量
     */
    private Integer scanCount;

    /**
     * 扫描到总件数
     */
    private Integer scanNumber;

    /**
     * 扫描 按区域统计盘点统计信息
     */
    private List<InventoryZoneVo> scanList;

    /**
     * 异常数
     */
    private Integer errorCount;

    /**
     * 异常总件数
     */
    private Integer errorNumber;

    /**
     * 异常 按区域统计盘点统计信息
     */
    private List<InventoryZoneVo> errorList;

    /**
     * 备注
     */
    private String remark;

    /**
     * 盘点出库文物
     */
    private Set<InventoryOut> inventoryOuts;

    /**
     * 盘点异常文物
     */
    private Set<InventoryError> inventoryErrors;

    public Inventory() {
    }

    /**
     * @param id
     * @param siteId
     * @param state
     */
    public Inventory(Integer id, String siteId, boolean state, Date deadlineDate) {
        super();
        this.id = id;
        this.siteId = siteId;
        this.state = state;
        this.deadlineDate = deadlineDate;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getSiteId() {
        return this.siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Integer getSumCount() {
        return this.sumCount;
    }

    public void setSumCount(Integer sumCount) {
        this.sumCount = sumCount;
    }

    public Integer getSumNumber() {
        return this.sumNumber;
    }

    public void setSumNumber(Integer sumNumber) {
        this.sumNumber = sumNumber;
    }

    public Integer getStockInCount() {
        return this.stockInCount;
    }

    public void setStockInCount(Integer stockInCount) {
        this.stockInCount = stockInCount;
    }

    public Integer getStockInNumber() {
        return this.stockInNumber;
    }

    public void setStockInNumber(Integer stockInNumber) {
        this.stockInNumber = stockInNumber;
    }

    public Integer getStockOutCount() {
        return this.stockOutCount;
    }

    public void setStockOutCount(Integer stockOutCount) {
        this.stockOutCount = stockOutCount;
    }

    public Integer getStockOutNumber() {
        return this.stockOutNumber;
    }

    public void setStockOutNumber(Integer stockOutNumber) {
        this.stockOutNumber = stockOutNumber;
    }

    public Integer getTagCount() {
        return this.tagCount;
    }

    public void setTagCount(Integer tagCount) {
        this.tagCount = tagCount;
    }

    public Integer getTagNumber() {
        return this.tagNumber;
    }

    public void setTagNumber(Integer tagNumber) {
        this.tagNumber = tagNumber;
    }

    public Integer getScanCount() {
        return this.scanCount;
    }

    public void setScanCount(Integer scanCount) {
        this.scanCount = scanCount;
    }

    public Integer getScanNumber() {
        return this.scanNumber;
    }

    public void setScanNumber(Integer scanNumber) {
        this.scanNumber = scanNumber;
    }

    public Integer getErrorCount() {
        return this.errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Integer getErrorNumber() {
        return this.errorNumber;
    }

    public void setErrorNumber(Integer errorNumber) {
        this.errorNumber = errorNumber;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<InventoryOut> getInventoryOuts() {
        return inventoryOuts;
    }

    public void setInventoryOuts(Set<InventoryOut> inventoryOuts) {
        this.inventoryOuts = inventoryOuts;
    }

    public Set<InventoryError> getInventoryErrors() {
        return inventoryErrors;
    }

    public void setInventoryErrors(Set<InventoryError> inventoryErrors) {
        this.inventoryErrors = inventoryErrors;
    }

    public List<InventoryZoneVo> getCountList() {
        return countList;
    }

    public void setCountList(List<InventoryZoneVo> countList) {
        this.countList = countList;
    }

    public List<InventoryZoneVo> getStockInList() {
        return stockInList;
    }

    public void setStockInList(List<InventoryZoneVo> stockInList) {
        this.stockInList = stockInList;
    }

    public List<InventoryZoneVo> getStockOutList() {
        return stockOutList;
    }

    public void setStockOutList(List<InventoryZoneVo> stockOutList) {
        this.stockOutList = stockOutList;
    }

    public List<InventoryZoneVo> getTagList() {
        return tagList;
    }

    public void setTagList(List<InventoryZoneVo> tagList) {
        this.tagList = tagList;
    }

    public List<InventoryZoneVo> getScanList() {
        return scanList;
    }

    public void setScanList(List<InventoryZoneVo> scanList) {
        this.scanList = scanList;
    }

    public List<InventoryZoneVo> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<InventoryZoneVo> errorList) {
        this.errorList = errorList;
    }
}