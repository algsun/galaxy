package com.microwise.uma.bean;

/**
 * 人员行为规则
 *
 * @author gaohui
 * @date 13-4-27 13:34
 */
public class AbstractUserActionBean {

    /**
     * 人员行为 id
     */
    private int id;

    /**
     * 类型 (1.单程, 2.往返, 3.往, 4. 返)
     */
    protected int type;

    /**
     * 匹配的人
     */
    protected PersonBean person;

    /**
     * 匹配的规则
     */
    protected RuleBean rule;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public PersonBean getPerson() {
        return person;
    }

    public void setPerson(PersonBean person) {
        this.person = person;
    }

    public RuleBean getRule() {
        return rule;
    }

    public void setRule(RuleBean rule) {
        this.rule = rule;
    }
}
