package kr.cafeIn.cafeorder.mapper;

import org.apache.ibatis.annotations.Param;


public interface LikedMapper {

  void insertLiked(Liked liked);

  int updateLiked(Liked liked);

  int deleteLiked(@Param("userId") Long userId, @Param("likedId") Long likedId);

}
