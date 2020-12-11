package com.bd.controller;

import com.bd.controller.common.Result;
import com.bd.entitys.dto.AddUserDTO;
import com.bd.entitys.dto.UpdateUserDTO;
import com.bd.entitys.dto.UserLoginDTO;
import com.bd.entitys.enumerate.ResultCode;
import com.bd.entitys.model.User;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.parame.RegisterUserParame;
import com.bd.entitys.query.UserQuery;
import com.bd.service.UserService;
import com.bd.utils.JsonUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;


@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 逻辑登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@Valid UserLoginDTO userLoginDTO){
        Result login = userService.login(userLoginDTO);
        if (login.getCode() != 0) {
            //校验失败跳转到登录页面
            return new ModelAndView("login")
                    .addObject("loginErrorInfo", login.getMessage());
        }

        //success：重定向到首页
        return new ModelAndView("redirect:/index");
    }

    /**
     * 用户注册
     * @param userInfoParame
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView userRegister(@Valid RegisterUserParame userInfoParame) {
        Result result = userService.insertUserInfo(userInfoParame);
        if (result.getCode() != 0) {
            return new ModelAndView("login").
                    addObject("registerErrorInfo", result.getMessage());
        }
        //注册成功后重定向到登录页面
        return new ModelAndView("redirect:/beidou");
    }

    /**
     * 用户列表
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(UserQuery query, PageParam pageParam){
        Result result = userService.list(query, pageParam);
        // 如果直接将result.getData()转成PageInfo<User>会报错
        String dataJson = JsonUtils.toJSONString(result.getData());
        PageInfo<User> pageInfo = JsonUtils.toBean(dataJson, PageInfo.class);
        return new ModelAndView("/user/list").
                addObject("list", pageInfo.getList()).
                addObject("total", pageInfo.getTotal()).
                addObject("totalPage", pageInfo.getPages()).
                addObject("currPageNumber", pageParam.getCurrentPageNumber()).
                addObject("query", query);
    }

    /**
     * 跳转至添加页面
     * @return
     */
    @GetMapping("/toAddView")
    public ModelAndView toAddView() {
        return new ModelAndView("/user/add");
    }

    /**
     * 添加用户信息
     * @param addUserDTO
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody @Valid AddUserDTO addUserDTO){
        User add = userService.add(addUserDTO);
        if (Objects.isNull(add)){
            return Result.fail(ResultCode.ERROR.code(), ResultCode.ERROR.msg());
        }
        return Result.ok(add);
    }

    /**
     * 跳转至编辑页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/toEditView/{id}", method = RequestMethod.GET)
    public ModelAndView toEditView(@PathVariable(name = "id") Long id) {
        Result result = userService.userDetails(id);
        Object data = result.getData();
        User user = JsonUtils.toBean(JsonUtils.toJSONString(data), User.class);
        return new ModelAndView("/user/edit").
                addObject("userInfo", user);
    }

    /**
     * 更新用户信息
     * @param updateUserDTO
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public Result update(@RequestBody @Valid UpdateUserDTO updateUserDTO){
        Result update = userService.update(updateUserDTO);
        return Result.ok(update);
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    @ResponseBody
    public Result deleteById(@PathVariable(name = "id") Long id){
        Result delete = userService.delete(id);
        return Result.ok(delete);
    }

    /**
     * 批量删除用户信息
     * @param idListStr
     * @return
     */
    @PostMapping("/batchDelete/{idListStr}")
    @ResponseBody
    public Result batchDelete(@PathVariable("idListStr") String idListStr){
        if (StringUtils.isEmpty(idListStr)){
            log.error("批量删除用户信息时参数为空，param: {}", idListStr);
            return Result.fail(ResultCode.PARAM_ERROR.code(), ResultCode.PARAM_ERROR.msg());
        }

        Integer batchDelete = userService.batchDelete(idListStr);
        return Result.ok(batchDelete);
    }





}
