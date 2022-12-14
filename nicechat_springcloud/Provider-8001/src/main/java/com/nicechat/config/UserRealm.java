package com.nicechat.config;

import com.nicechat.dao.UserDao;
import com.nicechat.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


//自定义的UserRealm extends AuthorizingRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

    @Override
    public String getName() {
        return "MyShiroRealm";
    }


    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //拿到当前的用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        //赋予权限
//        info.addStringPermission(null);
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = null;
        try {
            //从数据库中查询用户
            user = userDao.getUserByAccountNum(token.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user == null) {
            throw new UnknownAccountException("该用户不存在"); //抛出异常，UnknownAccountException
        }

        //密码可加密 MD5
        //加盐
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getAccountNum());//使用账号作为盐值
        //密码认证,shiro做
        return new SimpleAuthenticationInfo(token.getPrincipal(), user.getPassword(), credentialsSalt, getName());
    }
}
