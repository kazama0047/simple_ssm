package com.naoki.service.impl;

import com.naoki.bean.Department;
import com.naoki.dao.DepartmentMapper;
import com.naoki.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService implements IDepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAll() {
        return departmentMapper.selectByExample(null);
    }
}
