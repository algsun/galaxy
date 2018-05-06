package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans.Service;
import com.microwise.orion.bean.Photo;
import com.microwise.orion.dao.PhotoDao;
import com.microwise.orion.service.PhotoService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xubaoji
 * @date  2013-5-21
 *
 * @check 2013-06-04 zhangpeng svn:3576
 */
@Service
@Orion
@Transactional
public class PhotoServiceImpl implements PhotoService {
	
	/**相片dao*/
	@Autowired
	private  PhotoDao photoDao;
	
	@Override
	public void addPhoto(Photo photo) {
		photoDao.save(photo);
	}

    @Override
    public Photo findById(Integer photoId) {
        return photoDao.findById(photoId);
    }

	@Override
	public void delete(Photo photo) {
		photoDao.delete(photo);
	}

}
