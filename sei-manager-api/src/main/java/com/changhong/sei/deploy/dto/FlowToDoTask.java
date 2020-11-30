package com.changhong.sei.deploy.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 实现功能：待办任务
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-30 15:14
 */
@ApiModel(description = "待办任务")
public class FlowToDoTask implements Serializable {
    private static final long serialVersionUID = 7058966901999937565L;

    @ApiModelProperty(notes = "应用代码")
    private String code;


}
