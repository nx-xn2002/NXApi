import { getApiInfoById, invokeApiInfo } from '@/services/nx-api-backend/apiInfoController';
import { PageContainer } from '@ant-design/pro-components';
import { Button, Card, Descriptions, Divider, Form, Input, message } from 'antd';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';

/**
 * 接口信息页
 * @constructor
 */
const Index: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [invokeLoading, setInvokeLoading] = useState(false);
  const [data, setData] = useState<API.ApiInfo>();
  const [invokeRes, setInvokeRes] = useState<any>();
  const params = useParams();
  const loadData = async () => {
    if (!params.id) {
      message.error('请求失败');
      return;
    }
    setLoading(true);
    try {
      const res = await getApiInfoById({ id: Number(params.id) });
      setData(res.data);
    } catch (e: any) {
      message.error('请求失败', e.message);
    } finally {
      setLoading(false);
    }
  };
  const onFinish = async (values: any) => {
    if (!params.id) {
      message.error('接口不存在');
      return;
    }
    setInvokeLoading(true);
    try {
      const res = await invokeApiInfo({
        id: Number(params.id),
        userRequestParams: values.userRequestParams,
      });
      setInvokeRes(res.data);
      message.success('调用成功');
      return;
    } catch (e: any) {
      message.error('调用失败', e.message);
    } finally {
      setInvokeLoading(false);
    }
  };

  const onFinishFailed = () => {
    console.log('Failed');
  };
  useEffect(() => {
    loadData();
  }, []);
  return (
    <PageContainer title={'查看接口文档'}>
      <Card>
        {data ? (
          <Descriptions title={data.name} column={1}>
            <Descriptions.Item label="接口状态">
              {data.status === 1 ? '开启' : '关闭'}
            </Descriptions.Item>
            <Descriptions.Item label="描述">{data.description}</Descriptions.Item>
            <Descriptions.Item label="请求地址">{data.url}</Descriptions.Item>
            <Descriptions.Item label="请求方法">{data.method}</Descriptions.Item>
            <Descriptions.Item label="请求参数">{data.requestParams}</Descriptions.Item>
            <Descriptions.Item label="请求头">{data.requestHeader}</Descriptions.Item>
            <Descriptions.Item label="响应头">{data.responseHeader}</Descriptions.Item>
            <Descriptions.Item label="创建时间">{data.createTime}</Descriptions.Item>
            <Descriptions.Item label="更新时间">{data.updateTime}</Descriptions.Item>
          </Descriptions>
        ) : (
          <div>接口不存在</div>
        )}
      </Card>
      <Divider />
      <Card title={'在线测试'}>
        <Form name="Invoke" onFinish={onFinish} onFinishFailed={onFinishFailed} layout={'vertical'}>
          <Form.Item label="请求参数" name="userRequestParams">
            <Input.TextArea />
          </Form.Item>
          <Form.Item label={null}>
            <Button loading={invokeLoading} type="primary" htmlType="submit">
              调用
            </Button>
          </Form.Item>
        </Form>
      </Card>
      <Divider />
      <Card loading={invokeLoading} title={'调用结果'}>
        {invokeRes}
      </Card>
    </PageContainer>
  );
};

export default Index;
