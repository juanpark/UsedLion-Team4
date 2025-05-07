package com.example.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.post.dto.ReplyDetailDto;
import com.example.post.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Query("SELECT new com.example.post.dto.ReplyDetailDto(r.replyId,r.start,r.profileId,r.ref,r.level,r.postId,r.content,r.date,u.username,p.title) FROM Reply r JOIN UserInformation u on u.profileId = r.profileId JOIN Post p on p.postId = r.postId  WHERE r.postId = ?1 order by r.start,r.replyId,r.date")
    public List<ReplyDetailDto> getReplyByPostId(Integer postId);

    @Query("SELECT new com.example.post.dto.ReplyDetailDto(r.replyId,r.start,r.profileId,r.ref,r.level,r.postId,r.content,r.date,u.username,p.title) FROM Reply r JOIN UserInformation u on u.profileId = r.profileId JOIN Post p on p.postId = r.postId  WHERE r.profileId = ?1 order by r.start,r.replyId,r.date")
    public List<ReplyDetailDto> getReplyByProfileId(Integer profileId);
}
