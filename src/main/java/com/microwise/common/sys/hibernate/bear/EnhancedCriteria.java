package com.microwise.common.sys.hibernate.bear;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;
import java.util.List;

/**
 * 对 criteria 有一个简单的增强
 * <p/>
 *
 * @author bastengao
 * @check @wang.yunlong  & li.jianfei # 314 2012-12-18
 */
public class EnhancedCriteria {

	/**
	 * 通过 Session 创建
	 * 
	 * @param session
	 * @param entityClass
	 * @return
	 */
	public static EnhancedCriteria create(Session session, Class entityClass) {
		return new EnhancedCriteria(session.createCriteria(entityClass));
	}

	protected Criteria criteria;

	/**
	 * 通过给定的 Criteria 创建
	 * 
	 * @param criteria
	 */
	public EnhancedCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	/**
	 * equal
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public EnhancedCriteria equal(String property, Object value) {
		criteria.add(Restrictions.eq(property, value));
		return this;
	}

	/**
	 * not equal
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public EnhancedCriteria not(String property, Object value) {
		criteria.add(Restrictions.ne(property, value));
		return this;
	}

	public EnhancedCriteria isNull(String property) {
		criteria.add(Restrictions.isNull(property));
		return this;
	}

	public EnhancedCriteria isNotNull(String property) {
		criteria.add(Restrictions.isNotNull(property));
		return this;
	}

	public EnhancedCriteria like(String property, Object value) {
		// TODO 我不知道为啥这个 value 是 Object 类型而不是 String 类型
		criteria.add(Restrictions.like(property, value));
		return this;
	}

	public EnhancedCriteria like(String property, String value,
			MatchMode matchMode) {
		// TODO 我不知道为啥这个 value 是 Object 类型而不是 String 类型
		// TODO 将来不要暴露 MatchMode ，换成自己的枚举类型
		criteria.add(Restrictions.like(property, value, matchMode));
		return this;
	}

	public EnhancedCriteria between(String property, Object low, Object high) {
		criteria.add(Restrictions.between(property, low, high));
		return this;
	}

	public EnhancedCriteria in(String property, Collection values) {
		criteria.add(Restrictions.in(property, values));
		return this;
	}

	public EnhancedCriteria in(String property, Object[] values) {
		criteria.add(Restrictions.in(property, values));
		return this;
	}

	/**
	 * great than
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public EnhancedCriteria gt(String property, Object value) {
		criteria.add(Restrictions.gt(property, value));
		return this;
	}

	/**
	 * great than or equal
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public EnhancedCriteria ge(String property, Object value) {
		criteria.add(Restrictions.ge(property, value));
		return this;
	}

	/**
	 * less than
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public EnhancedCriteria lt(String property, Object value) {
		criteria.add(Restrictions.lt(property, value));
		return this;
	}

	/**
	 * less than or equal
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public EnhancedCriteria le(String property, Object value) {
		criteria.add(Restrictions.le(property, value));
		return this;
	}

	/**
	 * order by specific property (ASC), alias orderASC
	 * 
	 * @param property
	 * @return
	 */
	public EnhancedCriteria order(String property) {
		return orderASC(property);
	}

	/**
	 * order by specific property (ASC)
	 * 
	 * @param property
	 * @return
	 */
	public EnhancedCriteria orderASC(String property) {
		criteria.addOrder(Order.asc(property));
		return this;
	}

	/**
	 * order by specific property (DESC)
	 * 
	 * @param property
	 * @return
	 */
	public EnhancedCriteria orderDESC(String property) {
		criteria.addOrder(Order.desc(property));
		return this;
	}

	/**
	 * return count(*)
	 * 
	 * @return
	 */
	public long count() {
		// TODO "count(*)" change to "count(1)"
		criteria.setProjection(Projections.rowCount());
		return (Long) one();
	}

	/**
	 * return count(property)
	 * 
	 * @param property
	 * @return
	 */
	public long count(String property) {
		criteria.setProjection(Projections.count(property));
		return (Long) one();
	}

	/**
	 * @return return single result
	 */
	public Object one() {
		criteria.setMaxResults(1);
		return criteria.uniqueResult();
	}

	/**
	 * @return return list
	 */
	public List list() {
		return criteria.list();
	}

	/**
	 * return list by paging
	 * 
	 * @param first
	 *            firstResult
	 * @param max
	 *            maxResult
	 * @return
	 */
	public List list(int first, int max) {
		return criteria.setFirstResult(first).setMaxResults(max).list();
	}
}
