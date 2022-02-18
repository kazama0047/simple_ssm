package com.naoki.service.impl;

import com.naoki.bean.Employee;
import com.naoki.bean.EmployeeExample;
import com.naoki.dao.EmployeeMapper;
import com.naoki.service.IEmployeeService;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }

    @Override
    public boolean saveOne(Employee employee) {
        return employeeMapper.insertSelective(employee) == 1;
    }

    @Override
    public boolean checkName(String name) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        // 根据名称相同条件查找
        criteria.andNameEqualTo(name);
        return employeeMapper.countByExample(example) == 0;
    }

    @Override
    public Employee getOne(Integer id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKey(employee) == 1;
    }

    @Override
    public boolean delById(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id) == 1;
    }

    @Override
    public int deleteBatch(List<Integer> list) {
        EmployeeExample ex = new EmployeeExample();
        EmployeeExample.Criteria criteria = ex.createCriteria();
        criteria.andIdIn(list);
        return employeeMapper.deleteByExample(ex);
    }
}
