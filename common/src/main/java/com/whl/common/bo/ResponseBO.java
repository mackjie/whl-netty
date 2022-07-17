package com.whl.common.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hl.Wu
 * @date 2022/7/14
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseBO extends BaseBO{

    private String requestUid;
    /**
     * 响应code
     */
    private String code;

    /**
     * 响应提示信息
     */
    private String message;

    /**
     * 是否成功
     */
    private Boolean success;
}
