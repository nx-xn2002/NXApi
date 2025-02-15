package com.nx.nxapi.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nx.demoapisdk.client.DemoApiClient;
import com.nx.nxapi.annotation.AuthCheck;
import com.nx.nxapi.common.*;
import com.nx.nxapi.constant.CommonConstant;
import com.nx.nxapi.constant.UserConstant;
import com.nx.nxapi.exception.BusinessException;
import com.nx.nxapi.model.dto.apiinfo.ApiInfoAddRequest;
import com.nx.nxapi.model.dto.apiinfo.ApiInfoInvokeRequest;
import com.nx.nxapi.model.dto.apiinfo.ApiInfoQueryRequest;
import com.nx.nxapi.model.dto.apiinfo.ApiInfoUpdateRequest;
import com.nx.nxapi.model.entity.ApiInfo;
import com.nx.nxapi.model.entity.User;
import com.nx.nxapi.service.ApiInfoService;
import com.nx.nxapi.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.nx.nxapi.model.enums.ApiInfoStatusEnum.OFFLINE;
import static com.nx.nxapi.model.enums.ApiInfoStatusEnum.ONLINE;

/**
 * api info controller
 *
 * @author nx-xn2002
 * @date 2025-01-12
 */
@RestController
@RequestMapping("/apiInfo")
@Slf4j
public class ApiInfoController {

    @Resource
    private ApiInfoService apiInfoService;

    @Resource
    private UserService userService;

    @Resource
    private DemoApiClient demoApiClient;

    /**
     * 创建
     *
     * @param apiInfoAddRequest api info add request
     * @param request           request
     * @return {@link BaseResponse }<{@link Long }>
     */
    @PostMapping("/add")
    public BaseResponse<Long> addApiInfo(@RequestBody ApiInfoAddRequest apiInfoAddRequest, HttpServletRequest request) {
        if (apiInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApiInfo apiInfo = new ApiInfo();
        BeanUtils.copyProperties(apiInfoAddRequest, apiInfo);
        // 校验
        apiInfoService.validApiInfo(apiInfo, true);
        User loginUser = userService.getLoginUser(request);
        apiInfo.setUserId(loginUser.getId());
        boolean result = apiInfoService.save(apiInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newApiInfoId = apiInfo.getId();
        return ResultUtils.success(newApiInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest delete request
     * @param request       request
     * @return {@link BaseResponse }<{@link Boolean }>
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteApiInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        ApiInfo oldApiInfo = apiInfoService.getById(id);
        if (oldApiInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldApiInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = apiInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param apiInfoUpdateRequest api info update request
     * @param request              request
     * @return {@link BaseResponse }<{@link Boolean }>
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateApiInfo(@RequestBody ApiInfoUpdateRequest apiInfoUpdateRequest,
                                               HttpServletRequest request) {
        if (apiInfoUpdateRequest == null || apiInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApiInfo apiInfo = new ApiInfo();
        BeanUtils.copyProperties(apiInfoUpdateRequest, apiInfo);
        // 参数校验
        apiInfoService.validApiInfo(apiInfo, false);
        User user = userService.getLoginUser(request);
        long id = apiInfoUpdateRequest.getId();
        // 判断是否存在
        ApiInfo oldApiInfo = apiInfoService.getById(id);
        if (oldApiInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldApiInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = apiInfoService.updateById(apiInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id id
     * @return {@link BaseResponse }<{@link ApiInfo }>
     */
    @GetMapping("/get")
    public BaseResponse<ApiInfo> getApiInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApiInfo apiInfo = apiInfoService.getById(id);
        return ResultUtils.success(apiInfo);
    }

    /**
     * 获取列表
     *
     * @param apiInfoQueryRequest api info query request
     * @return {@link BaseResponse }<{@link List }<{@link ApiInfo }>>
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<ApiInfo>> listApiInfo(ApiInfoQueryRequest apiInfoQueryRequest) {
        ApiInfo apiInfoQuery = new ApiInfo();
        if (apiInfoQueryRequest != null) {
            BeanUtils.copyProperties(apiInfoQueryRequest, apiInfoQuery);
        }
        QueryWrapper<ApiInfo> queryWrapper = new QueryWrapper<>(apiInfoQuery);
        List<ApiInfo> apiInfoList = apiInfoService.list(queryWrapper);
        return ResultUtils.success(apiInfoList);
    }

    /**
     * 分页获取列表
     *
     * @param apiInfoQueryRequest api info query request
     * @param request             request
     * @return {@link BaseResponse }<{@link Page }<{@link ApiInfo }>>
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<ApiInfo>> listApiInfoByPage(ApiInfoQueryRequest apiInfoQueryRequest,
                                                         HttpServletRequest request) {
        if (apiInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ApiInfo apiInfoQuery = new ApiInfo();
        BeanUtils.copyProperties(apiInfoQueryRequest, apiInfoQuery);
        long current = apiInfoQueryRequest.getCurrent();
        long size = apiInfoQueryRequest.getPageSize();
        String sortField = apiInfoQueryRequest.getSortField();
        String sortOrder = apiInfoQueryRequest.getSortOrder();
        String description = apiInfoQuery.getDescription();
        // description 需支持模糊搜索
        apiInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<ApiInfo> queryWrapper = new QueryWrapper<>(apiInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        Page<ApiInfo> apiInfoPage = apiInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(apiInfoPage);
    }

    /**
     * 发布接口
     *
     * @param idRequest id request
     * @return {@link BaseResponse }<{@link Boolean }>
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> onlineApiInfo(@RequestBody IdRequest idRequest) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //校验接口是否存在
        Long id = idRequest.getId();
        ApiInfo oldApiInfo = apiInfoService.getById(id);
        if (oldApiInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //判断是否可以调用
        com.nx.demoapisdk.model.User user = new com.nx.demoapisdk.model.User("test");
        String response = demoApiClient.getUserNameByPost(user);
        if (StringUtils.isBlank(response)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口失效");
        }
        ApiInfo newApiInfo = new ApiInfo();
        newApiInfo.setId(id);
        newApiInfo.setStatus(ONLINE.getValue());
        boolean result = apiInfoService.updateById(newApiInfo);
        return ResultUtils.success(result);
    }

    /**
     * 下线接口
     *
     * @param idRequest id request
     * @return {@link BaseResponse }<{@link Boolean }>
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> offlineApiInfo(@RequestBody IdRequest idRequest) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //校验接口是否存在
        Long id = idRequest.getId();
        ApiInfo oldApiInfo = apiInfoService.getById(id);
        if (oldApiInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        ApiInfo newApiInfo = new ApiInfo();
        newApiInfo.setId(id);
        newApiInfo.setStatus(OFFLINE.getValue());
        boolean result = apiInfoService.updateById(newApiInfo);
        return ResultUtils.success(result);
    }

    /**
     * 测试调用
     *
     * @param apiInfoInvokeRequest api info invoke request
     * @param request              request
     * @return {@link BaseResponse }<{@link Boolean }>
     */
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeApiInfo(@RequestBody ApiInfoInvokeRequest apiInfoInvokeRequest,
                                              HttpServletRequest request) {
        if (apiInfoInvokeRequest == null || apiInfoInvokeRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = apiInfoInvokeRequest.getId();
        String userRequestParams = apiInfoInvokeRequest.getUserRequestParams();
        //判断接口是否存在
        ApiInfo oldApiInfo = apiInfoService.getById(id);
        if (oldApiInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        if (!oldApiInfo.getStatus().equals(ONLINE.getValue())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口已关闭");
        }
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        com.nx.demoapisdk.model.User user = JSONUtil.toBean(apiInfoInvokeRequest.getUserRequestParams(),
                com.nx.demoapisdk.model.User.class);
        DemoApiClient apiClient = new DemoApiClient(accessKey, secretKey);
        String userNameByPost = apiClient.getUserNameByPost(user);
        return ResultUtils.success(userNameByPost);
    }
}
