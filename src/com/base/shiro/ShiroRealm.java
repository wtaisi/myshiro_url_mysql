/**
 * 
 */
package com.base.shiro;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import com.base.entity.User;
import com.base.service.UserServiceImpl;

public class ShiroRealm extends AuthorizingRealm {
	@Resource
	private UserServiceImpl userService;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 根据用户配置用户与权限
		if (principals == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}
		String name = (String) getAvailablePrincipal(principals);
		List<String> roles = new ArrayList<String>();
		List<String> per = new ArrayList<String>();
		// 简单默认一个用户与角色，实际项目应
		User user = userService.findByUserName(name);
		if (user.getUsername().equals(name)) {
			if (user.getRoleList().size()>0) {
				for(int i=0;i<user.getRoleList().size();i++){
					roles.add(user.getRoleList().get(i).getRole());
					for(int k=0;k<user.getRoleList().get(i).getPermissionsList().size();k++){
						per.add(user.getRoleList().get(i).getPermissionsList().get(k).getPermission());
					}
				}
			}
		} else {
			throw new AuthorizationException();
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 增加角色
		info.addRoles(roles);
		info.addStringPermissions(per);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = userService.findByUserName(token.getUsername());
		Session session=SecurityUtils.getSubject().getSession();
		if (user == null) {
			throw new AuthorizationException("用户不存在");
		}
		SimpleAuthenticationInfo info = null;
		if (user.getUsername().equals(token.getUsername())) {
			info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
			session.setAttribute("user", user);
		}
		return info;
	}
	
	
	
	@Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
