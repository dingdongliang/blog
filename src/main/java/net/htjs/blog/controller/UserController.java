package net.htjs.blog.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.entity.SysRole;
import net.htjs.blog.entity.SysUser;
import net.htjs.blog.exception.GlobalException;
import net.htjs.blog.exception.ResponseData;
import net.htjs.blog.service.SysRoleService;
import net.htjs.blog.service.SysUserService;
import net.htjs.blog.util.JsonUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * blog/net.htjs.blog.controller
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/14 10:37
 */
@RestController
@RequestMapping("/user")
@Api(value = "航天金穗研发部技术拾遗-用户相关操作接口")
@Slf4j
public class UserController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;


    /**
     * 添加用户
     *
     * @param sysUser
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/8/23 15:51
     */
    @PostMapping("/saveOrUpdateUser")
    public ResponseData saveOrUpdateUser(SysUser sysUser) {

        sysUserService.persistenceUser(sysUser);

        return ResponseData.success();
    }

    @PostMapping("/setUserRole")
    public ResponseData setUserRole(@RequestParam("userId") String userId, @RequestParam("roles") String roles) {

        if (null != roles && !"".equals(roles)) {
            sysUserService.putRoleToUser(userId, roles);
            return ResponseData.success();
        } else {
            return ResponseData.error(false, "角色不能为空");
        }

    }


    /**
     * 更新用户，状态全部为E
     *
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/4/18 16:11
     */
    @RequiresPermissions("user:update")
    @PostMapping("/updateUser")
    public ResponseData updateUser(@ApiParam(name = "requestJson",
            value = "格式为{\"userId\":\"1d55fc9860894fbc88d7d79085e2f55f\",\"realName\":\"good man!\",\"roleId\":" +
                    "\"8cefc3f9409348bb9677118aed62fdfb|fb7f035401204cff8c58f240b866c925\"}",
            required = true) @RequestBody JSONObject requestJson) throws GlobalException {
        JsonUtil.hasAllRequired(requestJson, "realName,roleId,userId");

        String userId = requestJson.getString("userId");
        String userName = requestJson.getString("realName");
        String roleStr = requestJson.getString("roleId");
        String[] roleIds = roleStr.split("\\|");

        SysUser sysUser = sysUserService.selectByPrimaryKey(userId);
        sysUser.setUserName(userName);

        sysUserService.update(sysUser, roleIds);

        return ResponseData.success();
    }

    /**
     * 获取所有的角色信息，用于给用户分配角色
     *
     * @return net.htjs.blog.exception.ResponseData
     * @author dingdongliang
     * @date 2018/4/18 16:12
     */
    @RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
    @GetMapping("/getAllRole")
    public ResponseData getAllRole() {
        List<SysRole> list = sysRoleService.selectAll();
        return ResponseData.success(list);
    }

    @PostMapping(value = "/delUser", produces = "application/json;charset=utf-8")
    public ResponseData delUser(@RequestParam("userId") String userId) {

        boolean flag = sysUserService.delUser(userId);
        if (flag) {
            return ResponseData.success();
        } else {
            return ResponseData.error(false, "删除失败");
        }
    }
}
