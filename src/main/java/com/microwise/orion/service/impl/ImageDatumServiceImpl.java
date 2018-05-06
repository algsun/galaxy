package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.ImageDatum;
import com.microwise.orion.dao.ImageDatumDao;
import com.microwise.orion.service.ImageDatumService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 影像资料service实现
 *
 * @author liuzhu
 * @date 2015-11-10
 */

@Beans.Service
@Orion
@Transactional
public class ImageDatumServiceImpl implements ImageDatumService {

    @Autowired
    private ImageDatumDao imageDatumDao;

    @Override
    public void save(ImageDatum imageDatum) {
        imageDatumDao.save(imageDatum);
    }

    @Override
    public List<ImageDatum> findImageDatums(int repairRecordId) {
        return imageDatumDao.findImageDatums(repairRecordId);
    }

    @Override
    public ImageDatum findNewImageDatum() {
        return imageDatumDao.findNewImageDatum();
    }

    @Override
    public ImageDatum findById(int id) {
        return imageDatumDao.findById(id);
    }

    @Override
    public void update(ImageDatum imageDatum) {
        imageDatumDao.update(imageDatum);
    }

    @Override
    public void delete(ImageDatum imageDatum) {
        imageDatumDao.delete(imageDatum);
    }

    @Override
    public ImageDatum findImageDatum(int id) {
        return imageDatumDao.findImageDatum(id);
    }
}
