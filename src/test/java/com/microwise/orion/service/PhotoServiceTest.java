package com.microwise.orion.service;

import java.util.Date;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.common.sys.test.CleanDBTest;
import com.microwise.orion.bean.Photo;
import com.microwise.orion.bean.Relic;

/**
 * 相片service 测试
 *
 * @author xubaoji
 * @date 2013-5-17
 * @check 2013-06-04 zhangpeng svn:3664
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhotoServiceTest extends CleanDBTest {

    /**
     * 相片service
     */
    @Autowired
    private PhotoService photoService;

    @Before
    public void setbefor() throws Exception {
        xmlInsert2("common/dbxml/orion/PhotoServiceTest.xml");
    }

    /**
     * 添加相片测试
     *
     * @author 许保吉
     * @date 2013-5-21
     */
    @Test
    public void testAddPhoto() {
        Relic relic = new Relic();
        relic.setId(1);
        Photo photo = new Photo();
        photo.setFilmCode("023");
        photo.setPath("aa.jpg");
        photo.setPhotoDate(new Date());
        photo.setRelic(relic);
        photoService.addPhoto(photo);
        Photo photo1 = photoService.findById(photo.getId());
        Assert.assertEquals(photo.getPath(), photo1.getPath());
    }

    /**
     * 相片删除测试
     *
     * @author 许保吉
     * @date 2013-5-21
     */
    @Test
    public void testDeletePhoto() {
        Photo photo = photoService.findById(1);
        photoService.delete(photo);
        photo = photoService.findById(1);
        Assert.assertNull(photo);
    }
}
