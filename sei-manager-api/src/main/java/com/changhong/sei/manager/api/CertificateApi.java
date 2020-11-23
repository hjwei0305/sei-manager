package com.changhong.sei.manager.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.manager.dto.CertificateDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 凭证(Certificate)API
 *
 * @author sei
 * @since 2020-11-23 08:33:57
 * TODO @FeignClient(name = "请修改为项目服务名")
 */
@Valid
@FeignClient(name = "sei-manager", path = "certificate")
public interface CertificateApi extends BaseEntityApi<CertificateDto>, FindAllApi<CertificateDto> {

}