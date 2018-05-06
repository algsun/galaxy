package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.Relic;
import com.microwise.orion.bean.RelicLabel;
import com.microwise.orion.dao.RelicLabelDao;
import com.microwise.orion.service.RelicLabelService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 文物标签 Service 实现
 *
 * @author li.jianfei
 * @date 2014-04-25
 */
@Transactional
@Beans.Service
@Orion
public class RelicLabelServiceImpl implements RelicLabelService {

    /**
     * 文物标签 Dao
     */
    @Autowired
    private RelicLabelDao relicLabelDao;

    @Override
    public List<RelicLabel> findRelicLabelList(String siteId) {
        return relicLabelDao.findRelicLabelList(siteId);
    }

    @Override
    public List<RelicLabel> findRelicLabelListByRelicId(int relicId) {
        return relicLabelDao.findRelicLabelListByRelicId(relicId);
    }

    //TODO 使用hibernate删除
    @Override
    public boolean deleteRelicLabel(int relicId, String labelName) {
        boolean isSuccess = true;
        RelicLabel relicLabel = relicLabelDao.findRelicLabelByLabelName(labelName);
        isSuccess = relicLabelDao.deleteRelicLabel(relicId, relicLabel.getId());
        if (isSuccess && relicLabel.getRelics().isEmpty()) {
            isSuccess = relicLabelDao.deleteRelicLabel(relicLabel.getId());
        }
        return isSuccess;
    }

    @Override
    public void addRelicLabel(int relicId, String labelName) {
        RelicLabel relicLabel = relicLabelDao.findRelicLabelByLabelName(labelName);
        if (relicLabel == null) {
            relicLabel = new RelicLabel();
            relicLabel.setName(labelName);
        }
        Set<Relic> relics = new HashSet<Relic>();
        if (relicLabel.getRelics() != null) {
            relics = relicLabel.getRelics();
        }
        Relic relic = new Relic();
        relic.setId(relicId);
        relics.add(relic);
        relicLabel.setRelics(relics);
        relicLabelDao.addRelicLabel(relicLabel);
    }

    @Override
    public RelicLabel findRelicLabelByLabelName(String labelName) {
        return relicLabelDao.findRelicLabelByLabelName(labelName);
    }
}
