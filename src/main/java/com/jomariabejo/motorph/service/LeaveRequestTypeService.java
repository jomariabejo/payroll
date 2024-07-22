package com.jomariabejo.motorph.service;

import com.jomariabejo.motorph.model.LeaveRequestType;
import com.jomariabejo.motorph.repository.LeaveRequestTypeRepository;

import java.util.List;

public class LeaveRequestTypeService {

    private final LeaveRequestTypeRepository leaveRequestTypeRepository;

    public LeaveRequestTypeService(LeaveRequestTypeRepository leaveRequestTypeRepository) {
        this.leaveRequestTypeRepository = leaveRequestTypeRepository;
    }

    public LeaveRequestType getLeaveRequestTypeById(Integer id) {
        return leaveRequestTypeRepository.findById(id);
    }

    public List<LeaveRequestType> getAllLeaveRequestTypes() {
        return leaveRequestTypeRepository.findAll();
    }

    public void saveLeaveRequestType(LeaveRequestType leaveRequestType) {
        leaveRequestTypeRepository.save(leaveRequestType);
    }

    public void updateLeaveRequestType(LeaveRequestType leaveRequestType) {
        leaveRequestTypeRepository.update(leaveRequestType);
    }

    public void deleteLeaveRequestType(LeaveRequestType leaveRequestType) {
        leaveRequestTypeRepository.delete(leaveRequestType);
    }
}
