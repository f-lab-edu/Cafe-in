package kr.cafeIn.cafeorder.controller;


import javax.validation.Valid;
import kr.cafeIn.cafeorder.annotation.CurrentUserId;
import kr.cafeIn.cafeorder.annotation.LoginCheck;
import kr.cafeIn.cafeorder.model.dto.request.LikeRequest;
import kr.cafeIn.cafeorder.service.LikedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cafes")
@RequiredArgsConstructor
@RestController
public class LikedController {

  private final LikedService likedService;

  @PostMapping("/{id}/liked")
  @LoginCheck
  @ResponseStatus(HttpStatus.CREATED)
  public void createLiked(@CurrentUserId Long userId,
      @PathVariable("id") Long cafeId,
      @Valid @RequestBody LikeRequest likedReq) {
    likedService.createLiked(likedReq, userId, cafeId);
  }


  @PutMapping("/{id}/liked/{likedId}")
  @LoginCheck
  @ResponseStatus(HttpStatus.OK)
  public void updateLiked(@CurrentUserId Long userId,
      @PathVariable("id") Long cafeId,
      @PathVariable("likedId") Long likedId,
      @RequestBody LikeRequest likedReq) {
    likedService.updateLiked(likedReq, userId, likedId);
  }


  @DeleteMapping("/{id}/liked/{likedId}")
  @LoginCheck
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteLiked(@CurrentUserId Long userId,
      @PathVariable("id") Long cafeId, @PathVariable("likedId") Long likedId) {
    likedService.deleteLiked(userId, likedId);
  }

}





