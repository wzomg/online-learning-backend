package com.gll.onlinelearning.service;

import com.gll.onlinelearning.entity.LoginTicket;
import com.gll.onlinelearning.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gll.onlinelearning.entity.vo.UpdatePasswordRequestVO;

import java.util.Map;

/**
 * @author gll
 * @since 2021-04-02
 */
public interface UserService extends IService<User> {
    public Map<String, Object> login(String username, String password, int expiredSeconds);

    public Map<String, Object> register(User user);

    public LoginTicket findLoginTicket(String ticket);

    public User getUserById(Integer id);

    public void logout(String ticket);

    public void updateHeader(int userId, String avatarUrl);

    public String updatePassword(UpdatePasswordRequestVO requestVO, User user);

    public boolean updateProfile(User user, Integer uid);
}
