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

    public static final String DEPLOY_STAGE_PARAM_PROJECT_NAME = "PROJECT_NAME";
    public static final String DEPLOY_STAGE_PARAM_BETA_VERSION = "BETA_VERSION";
    public static final String DEPLOY_STAGE_PARAM_RELEASE_VERSION = "BETA_VERSION";

    static {
        DEFAULT_STAGE_PARAMS.add(new DeployStageParamDto(DEPLOY_STAGE_PARAM_PROJECT_NAME, "项目名称"));
        DEFAULT_STAGE_PARAMS.add(new DeployStageParamDto(DEPLOY_STAGE_PARAM_BETA_VERSION, "参考的测试镜像版本"));
        DEFAULT_STAGE_PARAMS.add(new DeployStageParamDto(DEPLOY_STAGE_PARAM_RELEASE_VERSION, "部署的生产镜像版本"));
    }


    private Constants() {
    }
}
