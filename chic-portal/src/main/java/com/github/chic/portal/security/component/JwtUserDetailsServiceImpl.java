package com.github.chic.portal.security.component;

import com.github.chic.entity.Role;
import com.github.chic.entity.User;
import com.github.chic.portal.security.entity.JwtUserDetails;
import com.github.chic.portal.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Spring Security 用户的业务实现
 */
@Component
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserService userService;

    @Override
    public JwtUserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        // 获取用户信息
        User user = userService.getByMobile(mobile);
        if (user == null) {
            throw new UsernameNotFoundException("无效用户");
        }
        // 获取角色列表
        List<Role> roleList = userService.listRoleByUserId(user.getUserId());
        // 构建Security用户
        return JwtUserDetails.create(user, roleList);
    }
}
