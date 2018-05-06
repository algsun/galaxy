package com.microwise.orion.dao.impl;

import com.microwise.common.sys.annotation.Beans.Dao;
import com.microwise.orion.bean.Rove;
import com.microwise.orion.dao.RoveDao;
import com.microwise.orion.sys.Orion;
import com.microwise.orion.sys.OrionBaseDao;
import com.microwise.orion.vo.RelicVo;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import java.util.List;

/**
 * 文物流传经历 dao 实现
 * 
 * @author xubaoji
 * @date 2013-5-27
 *
 * @check 2013-06-04 zhangpeng svn:4045
 */
@Dao
@Orion
public class RoveDaoImpl extends OrionBaseDao<Rove> implements RoveDao {

	public RoveDaoImpl() {
		super(Rove.class);
	}
}
