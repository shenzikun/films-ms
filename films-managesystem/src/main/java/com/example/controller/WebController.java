package com.example.controller;

import com.example.common.Result;
import com.example.common.RoleEnum;
import com.example.pojo.User;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    @Autowired
    private UserService userService;

    /**
     * 默认请求接口
     * 当访问根路径"/"时，返回一个成功的 Result
     */

    @GetMapping("/")
    public Result hello() {
        return Result.success();
    }

    /**
     * 登录请求接口
     * 当用户提交登录请求时，根据角色进行登录验证
     *
     * @param user 包含用户名、密码和角色信息的请求体
     * @return 返回登录结果，成功时返回包含用户信息的 Result，失败时返回错误信息的 Result
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        try {
            User dbUser;

            // 判断是管理员还是员工
            if (RoleEnum.ADMIN.name().equals(user.getRole())) {
                // 管理员登录
                dbUser = userService.login(user);
            } else if (RoleEnum.WORKER.name().equals(user.getRole())) {
                // 员工登录
                dbUser = userService.login(user);
            } else {
                // 角色错误
                return Result.error("角色错误");
            }

            // 登录成功，返回包含用户信息的 Result
            return Result.success(dbUser);
        } catch (Exception e) {
            // 登录失败，捕获异常并返回错误信息
            return Result.error("登录失败：" + e.getMessage());
        }
    }

}
