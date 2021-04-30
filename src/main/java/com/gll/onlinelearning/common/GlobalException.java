package com.gll.onlinelearning.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GlobalException extends RuntimeException {
    /**
     * 异常状态码
     */
    private Integer code;
    /**
     * 异常message
     */
    private String message;
}
