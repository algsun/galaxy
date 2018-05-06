package com.microwise.proxima.bean.webservice;

/**
 * 
 * @Title: 文物资产信息
 * @Description:
 * 
 * 
 * @version:
 * @author: 张奎 Copyright 2011 MicroWrise System Co.,Ltd.
 * @Date: May 5, 2011
 */

public class RelicInfoBean {
	// 编号
	private int Id;
	// 文物编码(全局唯一标识)
	private String relicCode;
	// 文物状态(0:正常;1:异常;2:判定中)
	private int relicState;
	// 藏品原始名称
	private String OldName;
	// 藏品学术名称
	private String Name;

	private String photoName; // 文物图片名称

	private String photoUrl; // 文物图片路径

	// 藏品部类
	private int Category;
	// 藏品大类
	private int Bigtype;
	// 藏品小类
	private int Small;
	// 制造年代
	private String MakeDate;
	// 使用年代
	private String UseDate;
	// 形成年代
	private String AppearDate;
	// 生存年代
	private String ExistDate;
	// 制造地
	private String ZZplace;
	// 出土地
	private String CTplace;
	// 采集地
	private String Cjplace;
	// 使用地
	private String Useplace;
	// 制造者
	private String Zzman;
	// 仿造者
	private String Ctman;
	// 使用者
	private String Cjman;
	// 属有者
	private String Useman;
	// 人文
	private String Cultural;
	// 人物传略
	private String Hearsay;
	// 质地类别
	private String MaterialType;
	// 功能大类
	private String FuncBigType;
	// 功能小类
	private String FuncType;
	// 功能
	private String Func;
	// 实际用途
	private String Sjyt;
	// 成型工艺
	private String CXGY;
	// 流派类别
	private String LPType;
	// 流派
	private String LP;
	// 技法部类
	private String techniqueCategory;
	// 技法大类
	private String techniqueType;
	// 装饰生成工艺
	private String ZSCXGY;
	// 文字生成工艺
	private String WXCXGY;
	// 物态类别
	private String WtType;
	// 形态特征
	private String XTTZ;
	// 独特标记
	private String uniqueBadge;
	// 完残程度
	private String WCCD;
	// 完残状况
	private String WCZK;
	// 颜色类别
	private String YSTYPE;
	// 颜色描述
	private String Colour;
	// 光泽类别
	private String GZTYPE;
	// 光泽描述
	private String Reflet;
	// 装饰形式类别
	private String ZSXSTYPE;
	// 装饰题材类别
	private String ZSTCTYPE;
	// 装饰组合方式
	private String ZSZHTYPE;
	// 施饰部位
	private String ZSBW;
	// 铭文
	private String MW;
	// 款识
	private String KS;
	// 题跋
	private String TB;
	// 题名
	private String TM;
	// 印鉴
	private String YJ;
	// 内容提要
	private String NRTY;
	// 文字种类
	private String WZZL;
	// 字体类别
	private String ZTTYPE;
	// 字体
	private String Typeface;
	// 字迹颜色
	private String ZJYS;
	// 字迹颜色描述
	private String handwritingColorBewrite;
	// 化石类别
	private String HSTYPE;
	// 化石
	private String HS;
	// 模式类别
	private String MSTYPE;
	// 发育阶段
	private String FYJD;
	// 性别
	private String XB;
	// 尺寸
	private String Dimensionless;
	// 尺寸单位
	private String DimensionlessUnits;
	// 容积
	private double Cubage;
	// 容积单位
	private String Ton;
	// 质量
	private double Weight;
	// 质量单位
	private String WeightUnits;
	// 实际数量
	private int Quantity;
	// 实际数量单位
	private String QuantityUnits;
	// 传统数量
	private int Tradition;
	// 传统数量单位
	private String TraditionUnits;
	// 文物包编号
	private String Packcode;
	// 查看级别
	private String VisibleType;
	// 是否被实时监测
	private int isSSJC;
	// 是否带标签（0、无1、有）
	private int isRFID;

	private String remark; // 备注/简述

	private int registerState; // 编目状态 （0、未编目 1、已编目）

	private String relicTypeName; // 文物部类名称（页面展现用）
	private String relicTypeName2; // 文物大类名称（页面展现用）
	private int storageLocationId; // 存储位置ID
	private String storageLocationName; // 储存位置名称
	private String frid; // 文物绑定的frid（页面展现用）
	private int relicCurrentlyState; // 文物当前状态（0 、正常 1、业务审核中 2、业务处理中 3、已调拨
	// 4、已出库 5、已注销 6、注销审核中）

	private String registerCode; // 编目_总登记号
	private String catalogueCode; // 编目号
	private String classifyCode; // 编目_分类号
	private String storageCode; // 编目_库存次号
	private String epoch; // 编目_时代
	private String collectionLevel; // 编目_藏品级别
	private String relicSource; // 编目_来源
	private String photoCode; // 编目_照片号
	private String rubbingsCode; // 编目_拓片号
	private String catalogueRemark; // 编目_编目描述
	private String expertopinion; // 编目_鉴定意见
	private String appraiser; // 编目_鉴定人
	private String appraisalTime; // 编目_鉴定时间
	private String Literature; // 编目_著作文献
	private String epigraph; // 编目_铭文题跋(文字)
	private String epigraphImage; // 编目_铭文题跋(图片)
	private String excavatedSites; // 编目_出土地点或产地
	private String catalogueUser; // 编目人
	private String catalogueTime; // 编目时间
	private String storyRecord; // 编目_故事记录
	private String charnelType; //原编号      /*之前为 入藏类型（0、出土点类型 1、入藏类型）
	private int accessoriesCode; // 附件
	private String texture; // 编目_质地
	private String heft; // 编目_重量
	private String dimension; // 编目_尺寸
	private String actuality; // 编目_现状
	private String renovateCircs; // 编目_修复情况
	private String archivesCode; // 档案编码,
	private String archivesTime; // 制档时间,
	private String archivesDate; // 制档日期,
	private String archivesUser; // 制档人,
	private String author; // 作者,
	private String purpose; // 用途,
	private String authorSynopsis; // 作者小传,
	private String source; // 档案_来源,
	private String archivesLiterature; // 档案_著作及有关资料书目,
	private String circulateProcess; // 流传经历,
	private String archivesExpertopinion; // 档案_鉴定意见,
	private String actualityAnnal; // 现状记录,
	private String archivesDimension; // 档案_尺寸,
	public RelicInfoBean(){}
	public RelicInfoBean(int id, String oldName, String registerCode) {
		this.Id = id;
		this.OldName = oldName;
		this.registerCode = registerCode;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getRelicCode() {
		return relicCode;
	}
	public void setRelicCode(String relicCode) {
		this.relicCode = relicCode;
	}
	public int getRelicState() {
		return relicState;
	}
	public void setRelicState(int relicState) {
		this.relicState = relicState;
	}
	public String getOldName() {
		return OldName;
	}
	public void setOldName(String oldName) {
		OldName = oldName;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public int getCategory() {
		return Category;
	}
	public void setCategory(int category) {
		Category = category;
	}
	public int getBigtype() {
		return Bigtype;
	}
	public void setBigtype(int bigtype) {
		Bigtype = bigtype;
	}
	public int getSmall() {
		return Small;
	}
	public void setSmall(int small) {
		Small = small;
	}
	public String getMakeDate() {
		return MakeDate;
	}
	public void setMakeDate(String makeDate) {
		MakeDate = makeDate;
	}
	public String getUseDate() {
		return UseDate;
	}
	public void setUseDate(String useDate) {
		UseDate = useDate;
	}
	public String getAppearDate() {
		return AppearDate;
	}
	public void setAppearDate(String appearDate) {
		AppearDate = appearDate;
	}
	public String getExistDate() {
		return ExistDate;
	}
	public void setExistDate(String existDate) {
		ExistDate = existDate;
	}
	public String getZZplace() {
		return ZZplace;
	}
	public void setZZplace(String zplace) {
		ZZplace = zplace;
	}
	public String getCTplace() {
		return CTplace;
	}
	public void setCTplace(String tplace) {
		CTplace = tplace;
	}
	public String getCjplace() {
		return Cjplace;
	}
	public void setCjplace(String cjplace) {
		Cjplace = cjplace;
	}
	public String getUseplace() {
		return Useplace;
	}
	public void setUseplace(String useplace) {
		Useplace = useplace;
	}
	public String getZzman() {
		return Zzman;
	}
	public void setZzman(String zzman) {
		Zzman = zzman;
	}
	public String getCtman() {
		return Ctman;
	}
	public void setCtman(String ctman) {
		Ctman = ctman;
	}
	public String getCjman() {
		return Cjman;
	}
	public void setCjman(String cjman) {
		Cjman = cjman;
	}
	public String getUseman() {
		return Useman;
	}
	public void setUseman(String useman) {
		Useman = useman;
	}
	public String getCultural() {
		return Cultural;
	}
	public void setCultural(String cultural) {
		Cultural = cultural;
	}
	public String getHearsay() {
		return Hearsay;
	}
	public void setHearsay(String hearsay) {
		Hearsay = hearsay;
	}
	public String getMaterialType() {
		return MaterialType;
	}
	public void setMaterialType(String materialType) {
		MaterialType = materialType;
	}
	public String getFuncBigType() {
		return FuncBigType;
	}
	public void setFuncBigType(String funcBigType) {
		FuncBigType = funcBigType;
	}
	public String getFuncType() {
		return FuncType;
	}
	public void setFuncType(String funcType) {
		FuncType = funcType;
	}
	public String getFunc() {
		return Func;
	}
	public void setFunc(String func) {
		Func = func;
	}
	public String getSjyt() {
		return Sjyt;
	}
	public void setSjyt(String sjyt) {
		Sjyt = sjyt;
	}
	public String getCXGY() {
		return CXGY;
	}
	public void setCXGY(String cxgy) {
		CXGY = cxgy;
	}
	public String getLPType() {
		return LPType;
	}
	public void setLPType(String type) {
		LPType = type;
	}
	public String getLP() {
		return LP;
	}
	public void setLP(String lp) {
		LP = lp;
	}
	public String getTechniqueCategory() {
		return techniqueCategory;
	}
	public void setTechniqueCategory(String techniqueCategory) {
		this.techniqueCategory = techniqueCategory;
	}
	public String getTechniqueType() {
		return techniqueType;
	}
	public void setTechniqueType(String techniqueType) {
		this.techniqueType = techniqueType;
	}
	public String getZSCXGY() {
		return ZSCXGY;
	}
	public void setZSCXGY(String zscxgy) {
		ZSCXGY = zscxgy;
	}
	public String getWXCXGY() {
		return WXCXGY;
	}
	public void setWXCXGY(String wxcxgy) {
		WXCXGY = wxcxgy;
	}
	public String getWtType() {
		return WtType;
	}
	public void setWtType(String wtType) {
		WtType = wtType;
	}
	public String getXTTZ() {
		return XTTZ;
	}
	public void setXTTZ(String xttz) {
		XTTZ = xttz;
	}
	public String getUniqueBadge() {
		return uniqueBadge;
	}
	public void setUniqueBadge(String uniqueBadge) {
		this.uniqueBadge = uniqueBadge;
	}
	public String getWCCD() {
		return WCCD;
	}
	public void setWCCD(String wccd) {
		WCCD = wccd;
	}
	public String getWCZK() {
		return WCZK;
	}
	public void setWCZK(String wczk) {
		WCZK = wczk;
	}
	public String getYSTYPE() {
		return YSTYPE;
	}
	public void setYSTYPE(String ystype) {
		YSTYPE = ystype;
	}
	public String getColour() {
		return Colour;
	}
	public void setColour(String colour) {
		Colour = colour;
	}
	public String getGZTYPE() {
		return GZTYPE;
	}
	public void setGZTYPE(String gztype) {
		GZTYPE = gztype;
	}
	public String getReflet() {
		return Reflet;
	}
	public void setReflet(String reflet) {
		Reflet = reflet;
	}
	public String getZSXSTYPE() {
		return ZSXSTYPE;
	}
	public void setZSXSTYPE(String zsxstype) {
		ZSXSTYPE = zsxstype;
	}
	public String getZSTCTYPE() {
		return ZSTCTYPE;
	}
	public void setZSTCTYPE(String zstctype) {
		ZSTCTYPE = zstctype;
	}
	public String getZSZHTYPE() {
		return ZSZHTYPE;
	}
	public void setZSZHTYPE(String zszhtype) {
		ZSZHTYPE = zszhtype;
	}
	public String getZSBW() {
		return ZSBW;
	}
	public void setZSBW(String zsbw) {
		ZSBW = zsbw;
	}
	public String getMW() {
		return MW;
	}
	public void setMW(String mw) {
		MW = mw;
	}
	public String getKS() {
		return KS;
	}
	public void setKS(String ks) {
		KS = ks;
	}
	public String getTB() {
		return TB;
	}
	public void setTB(String tb) {
		TB = tb;
	}
	public String getTM() {
		return TM;
	}
	public void setTM(String tm) {
		TM = tm;
	}
	public String getYJ() {
		return YJ;
	}
	public void setYJ(String yj) {
		YJ = yj;
	}
	public String getNRTY() {
		return NRTY;
	}
	public void setNRTY(String nrty) {
		NRTY = nrty;
	}
	public String getWZZL() {
		return WZZL;
	}
	public void setWZZL(String wzzl) {
		WZZL = wzzl;
	}
	public String getZTTYPE() {
		return ZTTYPE;
	}
	public void setZTTYPE(String zttype) {
		ZTTYPE = zttype;
	}
	public String getTypeface() {
		return Typeface;
	}
	public void setTypeface(String typeface) {
		Typeface = typeface;
	}
	public String getZJYS() {
		return ZJYS;
	}
	public void setZJYS(String zjys) {
		ZJYS = zjys;
	}
	public String getHandwritingColorBewrite() {
		return handwritingColorBewrite;
	}
	public void setHandwritingColorBewrite(String handwritingColorBewrite) {
		this.handwritingColorBewrite = handwritingColorBewrite;
	}
	public String getHSTYPE() {
		return HSTYPE;
	}
	public void setHSTYPE(String hstype) {
		HSTYPE = hstype;
	}
	public String getHS() {
		return HS;
	}
	public void setHS(String hs) {
		HS = hs;
	}
	public String getMSTYPE() {
		return MSTYPE;
	}
	public void setMSTYPE(String mstype) {
		MSTYPE = mstype;
	}
	public String getFYJD() {
		return FYJD;
	}
	public void setFYJD(String fyjd) {
		FYJD = fyjd;
	}
	public String getXB() {
		return XB;
	}
	public void setXB(String xb) {
		XB = xb;
	}
	public String getDimensionless() {
		return Dimensionless;
	}
	public void setDimensionless(String dimensionless) {
		Dimensionless = dimensionless;
	}
	public String getDimensionlessUnits() {
		return DimensionlessUnits;
	}
	public void setDimensionlessUnits(String dimensionlessUnits) {
		DimensionlessUnits = dimensionlessUnits;
	}
	public double getCubage() {
		return Cubage;
	}
	public void setCubage(double cubage) {
		Cubage = cubage;
	}
	public String getTon() {
		return Ton;
	}
	public void setTon(String ton) {
		Ton = ton;
	}
	public double getWeight() {
		return Weight;
	}
	public void setWeight(double weight) {
		Weight = weight;
	}
	public String getWeightUnits() {
		return WeightUnits;
	}
	public void setWeightUnits(String weightUnits) {
		WeightUnits = weightUnits;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public String getQuantityUnits() {
		return QuantityUnits;
	}
	public void setQuantityUnits(String quantityUnits) {
		QuantityUnits = quantityUnits;
	}
	public int getTradition() {
		return Tradition;
	}
	public void setTradition(int tradition) {
		Tradition = tradition;
	}
	public String getTraditionUnits() {
		return TraditionUnits;
	}
	public void setTraditionUnits(String traditionUnits) {
		TraditionUnits = traditionUnits;
	}
	public String getPackcode() {
		return Packcode;
	}
	public void setPackcode(String packcode) {
		Packcode = packcode;
	}
	public String getVisibleType() {
		return VisibleType;
	}
	public void setVisibleType(String visibleType) {
		VisibleType = visibleType;
	}
	public int getIsSSJC() {
		return isSSJC;
	}
	public void setIsSSJC(int isSSJC) {
		this.isSSJC = isSSJC;
	}
	public void setIsSSJC(String isSSJC) {
		this.isSSJC = Integer.parseInt(isSSJC);
	}
	public int getIsRFID() {
		return isRFID;
	}
	public void setIsRFID(int isRFID) {
		this.isRFID = isRFID;
	}
	public void setIsRFID(String isRFID) {
		this.isRFID = Integer.parseInt(isRFID);
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getRegisterState() {
		return registerState;
	}
	public void setRegisterState(int registerState) {
		this.registerState = registerState;
	}
	public String getRelicTypeName() {
		return relicTypeName;
	}
	public void setRelicTypeName(String relicTypeName) {
		this.relicTypeName = relicTypeName;
	}
	public String getRelicTypeName2() {
		return relicTypeName2;
	}
	public void setRelicTypeName2(String relicTypeName2) {
		this.relicTypeName2 = relicTypeName2;
	}
	public int getStorageLocationId() {
		return storageLocationId;
	}
	public void setStorageLocationId(int storageLocationId) {
		this.storageLocationId = storageLocationId;
	}
	public String getStorageLocationName() {
		return storageLocationName;
	}
	public void setStorageLocationName(String storageLocationName) {
		this.storageLocationName = storageLocationName;
	}
	public String getFrid() {
		return frid;
	}
	public void setFrid(String frid) {
		this.frid = frid;
	}
	public int getRelicCurrentlyState() {
		return relicCurrentlyState;
	}
	public void setRelicCurrentlyState(int relicCurrentlyState) {
		this.relicCurrentlyState = relicCurrentlyState;
	}
	public String getRegisterCode() {
		return registerCode;
	}
	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}
	public String getCatalogueCode() {
		return catalogueCode;
	}
	public void setCatalogueCode(String catalogueCode) {
		this.catalogueCode = catalogueCode;
	}
	public String getClassifyCode() {
		return classifyCode;
	}
	public void setClassifyCode(String classifyCode) {
		this.classifyCode = classifyCode;
	}
	public String getStorageCode() {
		return storageCode;
	}
	public void setStorageCode(String storageCode) {
		this.storageCode = storageCode;
	}
	public String getEpoch() {
		return epoch;
	}
	public void setEpoch(String epoch) {
		this.epoch = epoch;
	}
	public String getCollectionLevel() {
		return collectionLevel;
	}
	public void setCollectionLevel(String collectionLevel) {
		this.collectionLevel = collectionLevel;
	}
	public String getRelicSource() {
		return relicSource;
	}
	public void setRelicSource(String relicSource) {
		this.relicSource = relicSource;
	}
	public String getPhotoCode() {
		return photoCode;
	}
	public void setPhotoCode(String photoCode) {
		this.photoCode = photoCode;
	}
	public String getRubbingsCode() {
		return rubbingsCode;
	}
	public void setRubbingsCode(String rubbingsCode) {
		this.rubbingsCode = rubbingsCode;
	}
	public String getCatalogueRemark() {
		return catalogueRemark;
	}
	public void setCatalogueRemark(String catalogueRemark) {
		this.catalogueRemark = catalogueRemark;
	}
	public String getExpertopinion() {
		return expertopinion;
	}
	public void setExpertopinion(String expertopinion) {
		this.expertopinion = expertopinion;
	}
	public String getAppraiser() {
		return appraiser;
	}
	public void setAppraiser(String appraiser) {
		this.appraiser = appraiser;
	}
	public String getAppraisalTime() {
		return appraisalTime;
	}
	public void setAppraisalTime(String appraisalTime) {
		this.appraisalTime = appraisalTime;
	}
	public String getLiterature() {
		return Literature;
	}
	public void setLiterature(String literature) {
		Literature = literature;
	}
	public String getEpigraph() {
		return epigraph;
	}
	public void setEpigraph(String epigraph) {
		this.epigraph = epigraph;
	}
	public String getExcavatedSites() {
		return excavatedSites;
	}
	public void setExcavatedSites(String excavatedSites) {
		this.excavatedSites = excavatedSites;
	}
	public String getCatalogueUser() {
		return catalogueUser;
	}
	public void setCatalogueUser(String catalogueUser) {
		this.catalogueUser = catalogueUser;
	}
	public String getCatalogueTime() {
		return catalogueTime;
	}
	public void setCatalogueTime(String catalogueTime) {
		this.catalogueTime = catalogueTime;
	}
	public String getStoryRecord() {
		return storyRecord;
	}
	public void setStoryRecord(String storyRecord) {
		this.storyRecord = storyRecord;
	}
	
	public String getCharnelType() {
		return charnelType;
	}
	public void setCharnelType(String charnelType) {
		this.charnelType = charnelType;
	}
	public int getAccessoriesCode() {
		return accessoriesCode;
	}
	public void setAccessoriesCode(int accessoriesCode) {
		this.accessoriesCode = accessoriesCode;
	}
	public String getTexture() {
		return texture;
	}
	public void setTexture(String texture) {
		this.texture = texture;
	}
	public String getHeft() {
		return heft;
	}
	public void setHeft(String heft) {
		this.heft = heft;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getActuality() {
		return actuality;
	}
	public void setActuality(String actuality) {
		this.actuality = actuality;
	}
	public String getRenovateCircs() {
		return renovateCircs;
	}
	public void setRenovateCircs(String renovateCircs) {
		this.renovateCircs = renovateCircs;
	}
	public String getArchivesCode() {
		return archivesCode;
	}
	public void setArchivesCode(String archivesCode) {
		this.archivesCode = archivesCode;
	}
	public String getArchivesTime() {
		return archivesTime;
	}
	public void setArchivesTime(String archivesTime) {
		this.archivesTime = archivesTime;
	}
	public String getArchivesDate() {
		return archivesDate;
	}
	public void setArchivesDate(String archivesDate) {
		this.archivesDate = archivesDate;
	}
	public String getArchivesUser() {
		return archivesUser;
	}
	public void setArchivesUser(String archivesUser) {
		this.archivesUser = archivesUser;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getAuthorSynopsis() {
		return authorSynopsis;
	}
	public void setAuthorSynopsis(String authorSynopsis) {
		this.authorSynopsis = authorSynopsis;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getArchivesLiterature() {
		return archivesLiterature;
	}
	public void setArchivesLiterature(String archivesLiterature) {
		this.archivesLiterature = archivesLiterature;
	}
	public String getCirculateProcess() {
		return circulateProcess;
	}
	public void setCirculateProcess(String circulateProcess) {
		this.circulateProcess = circulateProcess;
	}
	public String getArchivesExpertopinion() {
		return archivesExpertopinion;
	}
	public void setArchivesExpertopinion(String archivesExpertopinion) {
		this.archivesExpertopinion = archivesExpertopinion;
	}
	public String getActualityAnnal() {
		return actualityAnnal;
	}
	public void setActualityAnnal(String actualityAnnal) {
		this.actualityAnnal = actualityAnnal;
	}
	public String getArchivesDimension() {
		return archivesDimension;
	}
	public void setArchivesDimension(String archivesDimension) {
		this.archivesDimension = archivesDimension;
	}
	public String getEpigraphImage() {
		return epigraphImage;
	}
	public void setEpigraphImage(String epigraphImage) {
		this.epigraphImage = epigraphImage;
	}

	
}
