package com.changhong.sei.ge.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.ge.dto.ProjectUserDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 项目用户(ProjectUser)API
 *
 * @author sei
 * @since 2020-11-23 08:34:10
 */
@Valid
@FeignClient(name = "sei-manager", path = "projectUser")
public interface ProjectUserApi extends BaseEntityApi<ProjectUserDto>, FindAllApi<ProjectUserDto> {

}