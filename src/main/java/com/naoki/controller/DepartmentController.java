package com.naoki.controller;

import com.naoki.bean.Department;
import com.naoki.bean.Msg;
import com.naoki.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    @RequestMapping("/depts")
    @ResponseBody
    public Msg depts() {
        List<Department> list = departmentService.getAll();
        return Msg.success().add("departments", list);
    }
}
