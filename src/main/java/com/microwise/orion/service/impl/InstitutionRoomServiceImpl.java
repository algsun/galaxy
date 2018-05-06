package com.microwise.orion.service.impl;

import com.microwise.common.sys.annotation.Beans;
import com.microwise.orion.bean.InstitutionRoom;
import com.microwise.orion.dao.InstitutionRoomDao;
import com.microwise.orion.service.InstitutionRoomService;
import com.microwise.orion.sys.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 15-9-10.
 */
@Beans.Service
@Orion
@Transactional
public class InstitutionRoomServiceImpl implements InstitutionRoomService {

    @Autowired
    private InstitutionRoomDao institutionRoomDao;

    public InstitutionRoom findById(int id) {
        return institutionRoomDao.findById(id);
    }

    @Override
    public void add(InstitutionRoom institutionRoom) {
        institutionRoomDao.add(institutionRoom);
    }

    @Override
    public void deleteInstitutionRoom(InstitutionRoom institutionRoom) {
        institutionRoomDao.deleteInstitutionRoom(institutionRoom);
    }

    @Override
    public List<InstitutionRoom> findByInstitutionId(int institutionId) {
        return institutionRoomDao.findByInstitutionId(institutionId);
    }

    @Override
    public void deleteByInstitutionId(int id) {
        institutionRoomDao.deleteByInstitutionId(id);
    }
}
