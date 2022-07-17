package com.whl.mq.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author hl.Wu
 * @date 2022/7/14
 **/
@Data
@ApiModel("消息对象")
@Builder
public class MessageVO implements Serializable {

    @ApiModelProperty("手机号码")
    @NotBlank
    @Length(max = 11)
    private String mobile;

    @ApiModelProperty("消息内容")
    @NotBlank
    private String message;

}
