// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 POST /apiInfo/add */
export async function addApiInfo(body: API.ApiInfoAddRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponseLong>('/apiInfo/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /apiInfo/delete */
export async function deleteApiInfo(body: API.DeleteRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponseBoolean>('/apiInfo/delete', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /apiInfo/get */
export async function getApiInfoById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getApiInfoByIdParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseApiInfo>('/apiInfo/get', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /apiInfo/invoke */
export async function invokeApiInfo(
  body: API.ApiInfoInvokeRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseObject>('/apiInfo/invoke', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /apiInfo/list */
export async function listApiInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listApiInfoParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseListApiInfo>('/apiInfo/list', {
    method: 'GET',
    params: {
      ...params,
      apiInfoQueryRequest: undefined,
      ...params['apiInfoQueryRequest'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /apiInfo/list/page */
export async function listApiInfoByPage(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.listApiInfoByPageParams,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponsePageApiInfo>('/apiInfo/list/page', {
    method: 'GET',
    params: {
      ...params,
      apiInfoQueryRequest: undefined,
      ...params['apiInfoQueryRequest'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /apiInfo/offline */
export async function offlineApiInfo(body: API.IdRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponseBoolean>('/apiInfo/offline', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /apiInfo/online */
export async function onlineApiInfo(body: API.IdRequest, options?: { [key: string]: any }) {
  return request<API.BaseResponseBoolean>('/apiInfo/online', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /apiInfo/update */
export async function updateApiInfo(
  body: API.ApiInfoUpdateRequest,
  options?: { [key: string]: any },
) {
  return request<API.BaseResponseBoolean>('/apiInfo/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
