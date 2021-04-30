package com.gll.onlinelearning.entity.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePasswordRequestVO {
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirm;
}
