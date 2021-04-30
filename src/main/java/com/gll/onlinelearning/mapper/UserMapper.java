package com.gll.onlinelearning.mapper;

import com.gll.onlinelearning.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gll.onlinelearning.entity.vo.SimpleUserVO;

/**
 * @author gll
 * @since 2021-04-02
 */
public interface UserMapper extends BaseMapper<User> {
    User getUserById(Integer id);

    User getUserByName(String username);

    int updateUserAvatar(int id, String avatarUrl);

    int updateUserPassword(int id, String password);

    SimpleUserVO getSimpleUserById(int id);
}
