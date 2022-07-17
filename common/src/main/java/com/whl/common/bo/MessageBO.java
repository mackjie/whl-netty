package com.whl.common.bo;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;

/**
 * @author hl.Wu
 * @date 2022/7/14
 **/
@Data
@FieldNameConstants
public class MessageBO extends BaseBO{

    /**
     * 请求唯一标识id
     */
    private String requestUid;

    private String token;

    private Boolean heartbeat;

    private List<VerificationEmsBO> data;
}
