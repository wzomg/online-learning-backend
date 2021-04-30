package com.gll.onlinelearning.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gll.onlinelearning.entity.LoginTicket;
import com.gll.onlinelearning.entity.User;
import com.gll.onlinelearning.entity.vo.UpdatePasswordRequestVO;
import com.gll.onlinelearning.mapper.UserMapper;
import com.gll.onlinelearning.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gll.onlinelearning.utils.RedisKeyUtil;
import com.gll.onlinelearning.utils.SystemUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author gll
 * @since 2021-04-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Map<String, Object> login(String username, String password, int expiredSeconds) {
        Map<String, Object> map = new HashMap<>();
        // 空值处理
        if (StringUtils.isBlank(username)) {
            map.put("errMsg", "账号不能为空!");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("errMsg", "密码不能为空!");
            return map;
        }
        // 验证账号
        User user = baseMapper.getUserByName(username);
        if (user == null) {
            map.put("errMsg", "该账号不存在!");
            return map;
        }
        // 验证密码
        password = SystemUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            map.put("errMsg", "密码不正确!");
            return map;
        }

        // 生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(SystemUtil.generateUUID());
        // 默认有效状态
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));

        // 将登录凭证保存在 redis 中
        String redisKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
        // key 为登录凭证，值为 loginTicket
        redisTemplate.opsForValue().set(redisKey, loginTicket);

        map.put("ticket", loginTicket.getTicket());
        map.put("userInfo", SystemUtil.handleUser(user));
        return map;
    }


    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();
        // 空值处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            map.put("errMsg", "账号不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("errMsg", "密码不能为空!");
            return map;
        }
        // 验证账号
        User u = baseMapper.getUserByName(user.getUsername());
        if (u != null) {
            map.put("errMsg", "该账号已存在!");
            return map;
        }

        // 注册用户
        // 密码加密盐值
        user.setSalt(SystemUtil.generateUUID().substring(0, 5));
        user.setPassword(SystemUtil.md5(user.getPassword() + user.getSalt()));
        // 设置用户默认头像
        user.setAvatar(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        boolean isOk = baseMapper.insert(user) > 0;
        if(!isOk) map.put("errMsg", "注册失败！");
        return map;
    }

    // 查询凭证
    public LoginTicket findLoginTicket(String ticket) {
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        return (LoginTicket) redisTemplate.opsForValue().get(redisKey);
    }

    public User getUserById(Integer id) {
        // 先从缓存中取
        User user = getUserInfoCache(id);
        if (user == null) {
            // 没有才去查库，再写入缓冲中
            user = initUserInfoCache(id);
        }
        return user;
    }

    // 1.优先从缓存中取值
    private User getUserInfoCache(Integer userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        return (User) redisTemplate.opsForValue().get(redisKey);
    }

    // 2.取不到时初始化缓存数据
    private User initUserInfoCache(Integer userId) {
        User user = baseMapper.getUserById(userId);
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.opsForValue().set(redisKey, user, 3600, TimeUnit.SECONDS);
        return user;
    }

    // 3.数据变更时清除缓存数据
    private void clearUserInfoCache(int userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(redisKey);
    }

    // 登出
    public void logout(String ticket) {
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        // 获取登录凭证对象
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
        if (loginTicket != null) {
            // 改为无效状态
            loginTicket.setStatus(1);
            redisTemplate.opsForValue().set(redisKey, loginTicket);
        }
    }

    public void updateHeader(int userId, String avatarUrl) {
        baseMapper.updateUserAvatar(userId, avatarUrl);
        clearUserInfoCache(userId);
    }

    /**
     * 更新密码
     */
    public String updatePassword(UpdatePasswordRequestVO requestVO, User user) {
        if (StringUtils.isBlank(requestVO.getOldPassword())) return "旧密码不能为空！";
        if (StringUtils.isBlank(requestVO.getNewPassword())) return "新密码不能为空！";
        if (StringUtils.isBlank(requestVO.getNewPasswordConfirm())) return "确认密码不能为空！";
        if (requestVO.getOldPassword().equals(requestVO.getNewPassword())) return "新密码不能和原密码相同！";
        if (!requestVO.getNewPassword().equals(requestVO.getNewPasswordConfirm())) return "确认密码与新密码不同！";
        String oldPassword = SystemUtil.md5(requestVO.getOldPassword() + user.getSalt());
        if (!oldPassword.equals(user.getPassword())) return "原密码错误！";
        baseMapper.updateUserPassword(user.getId(), SystemUtil.md5(requestVO.getNewPassword() + user.getSalt()));
        //还要清除缓存中的用户信息
        clearUserInfoCache(user.getId());
        return "";
    }

    @Override
    public boolean updateProfile(User user, Integer uid) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", uid);
        //还要清除缓存中的用户信息
        clearUserInfoCache(uid);
        return baseMapper.update(user, updateWrapper) > 0;
    }
}
