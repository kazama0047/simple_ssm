package com.naoki.service;

import com.naoki.bean.Employee;

import java.util.List;

public interface IEmployeeService {
    List<Employee> getAll();

    boolean saveOne(Employee employee);

    boolean checkName(String name);

    Employee getOne(Integer id);

    boolean updateEmp(Employee employee);

    boolean delById(Integer id);

    int deleteBatch(List<Integer> list);
}
