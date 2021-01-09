package com.summer.blog.dao;

import com.summer.blog.model.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);

    List<Blog> selectByUserIdAndModTimeDescAndAudiStatus(@Param("userId") Integer userId, @Param("audiStatus") Integer audiStatus);

    String selectLinkById(Integer id);

    int selectCountByUserId(@Param("userId") Integer userId, @Param("audiStatus") Integer audiStatus);

}