package com.changhong.sei.deploy.api;

import com.changhong.sei.core.api.BaseEntityApi;
import com.changhong.sei.core.api.FindAllApi;
import com.changhong.sei.deploy.dto.CertificateDto;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.Valid;

/**
 * 凭证(Certificate)API
 *
 * @author sei
 * @since 2020-11-23 08:33:57
 */
@Valid
@FeignClient(name = "sei-manager", path = "certificate")
public interface CertificateApi extends BaseEntityApi<CertificateDto>, FindAllApi<CertificateDto> {

}