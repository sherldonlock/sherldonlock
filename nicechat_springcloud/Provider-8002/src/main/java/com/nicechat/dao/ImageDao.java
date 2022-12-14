package com.nicechat.dao;

import com.nicechat.pojo.Image;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImageDao {

    int addImage(Image image);

    List<Image> getImageByImageIds(@Param("imageIds") List<String> imageIds);

    int deleteImageByImageIds(@Param("imageIds") List<String> imageIds);
}
