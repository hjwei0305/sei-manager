package com.changhong.sei.deploy.common;

import com.changhong.sei.deploy.dto.DeployStageParamDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-11-26 00:40
 */
public final class Constants {
    public static final List<DeployStageParamDto> DEFAULT_STAGE_PARAMS = new ArrayList<>();

    public static final String DEPLOY_PARAM_PROJECT_NAME = "PROJECT_NAME";
    public static final String DEPLOY_PARAM_GIT_PATH  = "PROJECT_GIT_PATH";
    public static final String DEPLOY_PARAM_BRANCH = "BRANCH";

    static {
        DEFAULT_STAGE_PARAMS.add(new DeployStageParamDto(DEPLOY_PARAM_PROJECT_NAME, "项目名称"));
        DEFAULT_STAGE_PARAMS.add(new DeployStageParamDto(DEPLOY_PARAM_GIT_PATH, "代码仓库地址"));
        DEFAULT_STAGE_PARAMS.add(new DeployStageParamDto(DEPLOY_PARAM_BRANCH, "代码分支或者TAG"));
    }


    private Constants() {
    }
}
