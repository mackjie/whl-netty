package com.whl.common.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证码短信
 *
 * @author hl.Wu
 * @date 2022/7/14
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationEmsBO extends BaseBO{

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 短信内容
     */
    private String message;
}
