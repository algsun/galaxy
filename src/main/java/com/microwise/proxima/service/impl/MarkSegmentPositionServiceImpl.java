package com.microwise.proxima.service.impl;

import com.microwise.proxima.bean.DVPlaceBean;
import com.microwise.proxima.bean.MarkSegmentBean;
import com.microwise.proxima.bean.MarkSegmentPositionBean;
import com.microwise.proxima.bean.PictureBean;
import com.microwise.proxima.dao.MarkSegmentDao;
import com.microwise.proxima.dao.MarkSegmentPositionDao;
import com.microwise.proxima.dao.PictureDao;
import com.microwise.proxima.service.MarkSegmentPositionService;
import com.microwise.proxima.sys.Proxima;
import com.microwise.proxima.util.PositionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * @author gaohui
 * @date 2012-7-13
 */
@Service
@Transactional
@Proxima
public class MarkSegmentPositionServiceImpl implements
        MarkSegmentPositionService {

    @Autowired
    private MarkSegmentDao markSegmentDao;
    @Autowired
    private MarkSegmentPositionDao markSegmentPositionDao;
    @Autowired
    private PictureDao pictureDao;

    @Override
    public MarkSegmentPositionBean findById(int id) {
        return markSegmentPositionDao.findById(id);
    }

    @Override
    public Serializable save(MarkSegmentPositionBean t) {
        return markSegmentPositionDao.save(t);
    }

    @Override
    public List<MarkSegmentPositionBean> findByPicId(String picId) {
        return markSegmentPositionDao.findByPicId(picId);
    }

    @Override
    public List<MarkSegmentPositionBean> findActiveByPicId(String picId) {
        return markSegmentPositionDao.findActiveByPicId(picId);
    }

    @Override
    public void update(MarkSegmentPositionBean t) {
        markSegmentPositionDao.update(t);
    }

    @Override
    public List<MarkSegmentPositionBean> findLatestBefore(String picId) {
        return markSegmentPositionDao.findLatestBefore(picId);
    }

    @Override
    public List<MarkSegmentPositionBean> findLatestActiveBefore(String picId) {
        return markSegmentPositionDao.findLatestActiveBefore(picId);
    }

    public List<MarkSegmentBean> findAllMarkSegmentByDvPlaceId(String dvPlaceId) {
        return markSegmentDao.findAllMarkSegmentByDvPlaceId(dvPlaceId);
    }

    @Override
    public boolean isNewOrLatestEditedPicture(String picId) {
        PictureBean picture = pictureDao.findById(picId);
        String dvPlaceId = picture.getDv().getId();
        PictureBean latestEditedPicture = markSegmentPositionDao
                .findLatestEditedPicture(dvPlaceId);

        // 如果没有被编辑过的图片
        if (latestEditedPicture == null) {
            return true;
        }

        // 如果 picture 的时间不小于 lastedEditedPicture 的时间
        if (picture.getSaveTime().compareTo(latestEditedPicture.getSaveTime()) >= 0) {
            return true;
        }
        return false;
    }

    @Override
    public void addOrUpdateMarkSegmentPosition(String picId,
                                               String markSegmentsJSON) {
        List<Map<String, Object>> markSegments = JSONTOMap(markSegmentsJSON);
        PictureBean picture = pictureDao.findById(picId);
        DVPlaceBean dvPlace = picture.getDv();
        // 之前一次编辑在图片
        PictureBean editedBeforePic = markSegmentPositionDao
                .findLastedEditedPictureBefore(picId);
        List<MarkSegmentPositionBean> positionsBefore = Collections.emptyList();
        if (editedBeforePic != null) {
            positionsBefore = markSegmentPositionDao
                    .findActiveByPicId(editedBeforePic.getId());
        }
        // 之后一次编辑的图片
        PictureBean editedAfterPic = markSegmentPositionDao
                .findLastedEditedPictureAfter(picId);
        List<MarkSegmentPositionBean> positionsAfter = Collections.emptyList();
        if (editedAfterPic != null) {
            positionsAfter = markSegmentPositionDao
                    .findActiveByPicId(editedAfterPic.getId());
        }

        // 遍历图片上在标记段
        for (Map<String, Object> markSegmentMap : markSegments) {
            String markSegmentId = markSegmentMap.get("markSegmentId").toString();

            // 查询此图片上此标记段的坐标
            MarkSegmentPositionBean markSegmentPosition = markSegmentPositionDao
                    .find(markSegmentId, picId);
            if (markSegmentPosition == null) { // 如果不存在，添加
                MarkSegmentBean markSegment = markSegmentDao
                        .findById(markSegmentId);

                markSegmentPosition = new MarkSegmentPositionBean();
                markSegmentPosition.setPicture(picture);
                markSegmentPosition.setMarkSegment(markSegment);

                markSegmentPositionDao.save(markSegmentPosition);
            }

            int positionX = Integer.parseInt(markSegmentMap.get("positionX").toString());

            int positionY = Integer.parseInt(markSegmentMap.get("positionY").toString());
            int positionX2 = Integer.parseInt(markSegmentMap.get("positionX2").toString());
            int positionY2 = Integer.parseInt(markSegmentMap.get("positionY2").toString());

            // 更新新的坐标
            markSegmentPosition.setPositionX(positionX);
            markSegmentPosition.setPositionY(positionY);
            markSegmentPosition.setPositionX2(positionX2);
            markSegmentPosition.setPositionY2(positionY2);
            markSegmentPosition.setUpdateTime(new Date());

            // 计算标记段长度
            // 像素数
            double lengthOfPixel = PositionUtil.length(positionX, positionY,
                    positionX2, positionY2);
            // 毫米(mm)数
            float lengthOfMM = PositionUtil.realLength(lengthOfPixel,
                    dvPlace.getImageRealWidth(), dvPlace.getImageWidth());
            markSegmentPosition.setMarkLength(lengthOfMM);

            markSegmentPosition.setLengthDelta(0);
            if (editedBeforePic != null) {
                for (MarkSegmentPositionBean position : positionsBefore) {
                    // 如果是同一个标记段
                    if (position.getMarkSegment().getId() == markSegmentId) {
                        double lengthOfPixelBefore = PositionUtil.length(
                                position.getPositionX(),
                                position.getPositionY(),
                                position.getPositionX2(),
                                position.getPositionY2());
                        double deltaPixel = Math.abs(lengthOfPixel
                                - lengthOfPixelBefore);
                        float deltaMMFloat = PositionUtil.realLength(
                                deltaPixel, dvPlace.getImageRealWidth(),
                                dvPlace.getImageWidth());
                        markSegmentPosition.setLengthDelta(deltaMMFloat);
                    }
                }
            }
            if (editedAfterPic != null) {
                for (MarkSegmentPositionBean position : positionsAfter) {
                    // 如果是同一个标记段
                    if (position.getMarkSegment().getId() == markSegmentId) {
                        double lengthOfPixelBefore = PositionUtil.length(
                                position.getPositionX(),
                                position.getPositionY(),
                                position.getPositionX2(),
                                position.getPositionY2());
                        double deltaPixel = Math.abs(lengthOfPixel
                                - lengthOfPixelBefore);
                        float deltaMMFloat = PositionUtil.realLength(
                                deltaPixel, dvPlace.getImageRealWidth(),
                                dvPlace.getImageWidth());
                        position.setLengthDelta(deltaMMFloat);
                    }
                }
            }
        }
    }

    @Override
    public List<MarkSegmentPositionBean> findAllMarkedSegementData(
            String dvPlaceId, String markId, Date startDate, Date endDate) {
        return markSegmentPositionDao.findAllMarkedSegmentData(dvPlaceId,
                markId, startDate, endDate);
    }

    @Override
    public List<MarkSegmentPositionBean> findAllMarkPositionData(String dvPlaceId,
                                                                 Date startDate, Date endDate) {
        return markSegmentPositionDao.findAllMarkPositionData(dvPlaceId, startDate, endDate);
    }

    @Override
    public Integer[] findMaxXY(String dvPlaceId, Date startDate, Date endDate) {
        return markSegmentPositionDao.findMaxXY(dvPlaceId, startDate, endDate);
    }

    @Override
    public List<MarkSegmentPositionBean> findMarkedSegmentData(String markId, Date startDate, Date endDate) {
        return markSegmentPositionDao.findMarkedSegmentData(markId, startDate, endDate);
    }

    public static List<Map<String, Object>> JSONTOMap(String json) {

        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();

        List<String> list = new ArrayList<String>();

        String JSON = json.substring(1, json.length() - 1);

        JSON = JSON.replaceAll("},", "}^");

        StringTokenizer strTokenizer = new StringTokenizer(JSON, "^");

        while (strTokenizer.hasMoreTokens()) {

            String token = strTokenizer.nextToken();

            list.add(token);

        }

        int start = 0;

        int end = 0;

        int start2 = 0;

        int end2 = 0;

        for (int i = 0; i < list.size(); i++) {

            String str = (String) list.get(i);

            String key = "";

            String value = "";

            Map<String, Object> map = new HashMap<String, Object>();

            for (int j = 0; j < str.length(); j++) {

                String sub = str.substring(j, j + 1);

                if (sub.equals("{")) {

                    start = j + 1;
                }


                if (sub.equals(",")) {

                    start = j + 1;
                }

                if (sub.equals(":")) {

                    end = j;

                    key = str.substring(start, end);

                    key = key.substring(1, key.length() - 1);
                }

                if (sub.equals(":")) {

                    start2 = j + 1;
                }

                if (sub.equals(",") || sub.equals("}")) {

                    end2 = j;

                    value = str.substring(start2, end2);

                    if (value.substring(0, 1).equals("\"") && value.substring(value.length() - 1, value.length()).equals("\"")) {
                        value = value.substring(1, value.length() - 1);
                    }

                    map.put(key, value);
                }
            }

            listMap.add(map);
        }
        return listMap;
    }
}
