package com.naoki.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.naoki.bean.Employee;
import com.naoki.bean.Msg;
import com.naoki.dao.EmployeeMapper;
import com.naoki.service.IEmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping("/emps")
    public String emps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
        //必须紧跟查询语句,设置当前页数以及每页显示数
        PageHelper.startPage(pn, 5);
        List<Employee> all = employeeService.getAll();
        // navigatePages为分页选项可选页数,页面分页部分的1,2,3,4,5页
        PageInfo<Employee> pageInfo = new PageInfo<>(all, 5);
        model.addAttribute("pageInfo", pageInfo);
        return "list";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/emps2")
    @ResponseBody
    public Msg emps2(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
        PageHelper.startPage(pn, 5);
        List<Employee> all = employeeService.getAll();
        PageInfo<Employee> pageInfo = new PageInfo<>(all, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }

    @PostMapping("/emp")
    @ResponseBody
    // @Valid 使用实体类上的注解教养,校验结果封装到 BindingResult 对象中
    public Msg postEmp(@Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                map.put(error.getField(), error.getDefaultMessage());
            }
            return Msg.fail().add("errorFields", map);
        } else {
            boolean flag = employeeService.saveOne(employee);
            return flag ? Msg.success() : Msg.fail();
        }
    }

    @GetMapping("/emp/{id}")
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id) {
        if (id == null) return Msg.fail();
        Employee employee = employeeService.getOne(id);
        return employee != null ? Msg.success().add("emp", employee) : Msg.fail();
    }

    @PutMapping("/emp")
    @ResponseBody
    public Msg putEmp(Employee employee) {
        boolean flag = employeeService.updateEmp(employee);
        return flag ? Msg.success() : Msg.fail();
    }

    @DeleteMapping("/emp/{ids}")
    @ResponseBody
    public Msg delEmp(@PathVariable("ids") String ids) {
        String[] idStr = ids.split(",");
        if (idStr.length == 1) {
            boolean flag = employeeService.delById(Integer.parseInt(idStr[0]));
            return flag ? Msg.success() : Msg.fail();
        } else {
            List<Integer> list = new ArrayList<Integer>();
            for (String s : idStr) {
                list.add(Integer.parseInt(s));
            }
            int i = employeeService.deleteBatch(list);
            return Msg.success("成功删除" + i + "条记录");
        }
    }

    @RequestMapping("/checkname")
    @ResponseBody
    public Msg checkName(@RequestParam("name") String name) {
        String reg = "(^[a-zA-Z0-9_-]{3,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";
        if (!name.matches(reg)) return Msg.fail("用户名不合法");
        boolean flag = employeeService.checkName(name);
        return flag ? Msg.success() : Msg.fail("用户名已存在");
    }
}
