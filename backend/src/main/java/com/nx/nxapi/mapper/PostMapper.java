package com.nx.nxapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nx.nxapi.model.entity.Post;
import java.util.Date;
import java.util.List;

/**
 * 帖子数据库操作
 *
 * @author nx-xn2002
 * @date 2025-01-11
 */
public interface PostMapper extends BaseMapper<Post> {

    /**
     * 查询帖子列表（包括已被删除的数据）
     *
     * @param minUpdateTime min update time
     * @return {@link List }<{@link Post }>
     */
    List<Post> listPostWithDelete(Date minUpdateTime);

}




