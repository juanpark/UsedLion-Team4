package com.example.post.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.post.dto.ReplyDetailDto;
import com.example.post.dto.ReplyTree;
import com.example.post.entity.Reply;
import com.example.post.repository.ReplyRepository;

@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    public List<ReplyDetailDto> getReplyByPostId(Integer postId) {
        return replyRepository.getReplyByPostId(postId);
    }

    public List<ReplyDetailDto> getReplyByProfileId(Integer profileId) {
        return replyRepository.getReplyByProfileId(profileId);
    }

    public Reply getReplyByReplyId(Integer replyId) {
        return replyRepository.findById(replyId).orElse(null);
    }

    public void createReply(Reply reply) {
        replyRepository.save(reply);
    }

    public void updateReply(Integer replyId, Reply updatedReply) {
        Reply existing = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));
        existing.setContent(updatedReply.getContent());
        existing.setDate(updatedReply.getDate()); // 수정 시 시간도 갱신
        replyRepository.save(existing);
    }

    public void deleteReply(Integer replyId) {
        replyRepository.deleteById(replyId);
    }

    public List<ReplyTree> MakeTree(List<ReplyDetailDto> list) {
        Map<Integer, ReplyTree> replyMap = new HashMap<>();
        List<ReplyTree> root = new ArrayList<>();

        for (ReplyDetailDto reply : list) {
            replyMap.put(reply.getReplyId(), new ReplyTree(reply));
        }
        for (ReplyDetailDto reply : list) {
            if (reply.getRef() == 0) {
                root.add(replyMap.get(reply.getReplyId()));
            } else {
                ReplyTree parent = replyMap.get(reply.getRef());
                if (parent != null) {
                    parent.getChildren().add(replyMap.get(reply.getReplyId()));
                }
            }
        }
        return root;
    }
}
