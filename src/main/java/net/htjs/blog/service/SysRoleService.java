package net.htjs.blog.service;

import net.htjs.blog.entity.SysRole;
import net.htjs.blog.entity.SysUser;

import java.util.List;

/**
 * blog/net.htjs.blog.service
 *
 * @Description:
 * @Author: dingdongliang
 * @Date: 2018/8/14 11:02
 */
public interface SysRoleService extends BaseService<SysRole> {



    /**
     * 更新用户的角色
     *
     * @param roleId  角色ID
     * @param pmsnIds 权限ID数组
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 9:31
     */
    void updatePmsnToRole(String roleId, String[] pmsnIds);

    /**
     * 新增角色，同时分配权限
     *
     * @param sysRole 角色对象
     * @param pmsnIds 权限集合字符串
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 11:13
     */
    void insert(SysRole sysRole, String pmsnIds);

    /**
     * 修改角色，同时修改角色-权限映射
     *
     * @param sysRole 角色对象
     * @param pmsnIds 权限集合
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 11:13
     */
    void update(SysRole sysRole, String[] pmsnIds);

    /**
     * 删除角色，在删除角色之前，要先删除该角色对应的权限
     *
     * @param roleId 角色ID
     * @return void
     * @author dingdongliang
     * @date 2018/4/25 16:24
     */
    void delete(String roleId);

    /**
     * 查询默认权限ID集合，新用户添加的时候，绑定
     *
     * @param
     * @return java.util.List<String>
     * @author dingdongliang
     * @date 2018/9/7 8:21
     */
    List<String> selectDefault();


    /**
     * 保存分配角色权限
     *
     * @param roleId  角色id
     * @param pmsnIds 菜单权限ID集合
     * @return boolean
     * @author dingdongliang
     * @date 2018/9/12 19:57
     */
    boolean savePermission(String roleId, String pmsnIds);

    /**
     * 持久化角色信息
     *
     * @param sysRole
     * @return boolean
     * @author dingdongliang
     * @date 2018/9/13 9:28
     */
    boolean persistenceRole(SysRole sysRole);
}


