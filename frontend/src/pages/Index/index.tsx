import { listApiInfoByPage } from '@/services/nx-api-backend/apiInfoController';
import { PageContainer } from '@ant-design/pro-components';
import { List, message } from 'antd';
import React, { useEffect, useState } from 'react';

/**
 * 主页
 * @constructor
 */
const Index: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [list, setList] = useState<API.ApiInfo[]>([]);
  const [total, setTotal] = useState(0);
  const loadData = async (current = 1, pageSize = 5) => {
    setLoading(true);
    try {
      const res = await listApiInfoByPage({
        apiInfoQueryRequest: {
          current: current,
          pageSize: pageSize,
        },
      });
      setList(res?.data?.records || []);
      setTotal(res.data?.total || 0);
    } catch (e: any) {
      message.error('请求失败', e.message);
    } finally {
      setLoading(false);
    }
  };
  useEffect(() => {
    loadData();
  }, []);
  return (
    <PageContainer title={'NXApi 接口开放平台'}>
      <List
        className="demo-loadmore-list"
        loading={loading}
        itemLayout="horizontal"
        dataSource={list}
        renderItem={(item) => {
          const apiLink = `/api_info/${item.id}`;
          return (
            <List.Item
              actions={[
                <a key={apiLink} href={apiLink}>
                  查看
                </a>,
              ]}
            >
              <List.Item.Meta
                title={<a href={apiLink}>{item.name}</a>}
                description={item.description}
              />
            </List.Item>
          );
        }}
        pagination={{
          pageSize: 5,
          total: total,
          showTotal(total) {
            return `共 ${total} 个接口`;
          },
          onChange(page, pageSize) {
            loadData(page, pageSize);
          },
        }}
      />
    </PageContainer>
  );
};

export default Index;
