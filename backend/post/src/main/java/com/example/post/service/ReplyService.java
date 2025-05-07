package com.example.post.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.post.dto.ReplyDetailDto;
import com.example.post.dto.ReplyTree;
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

    public void createReply(Integer postId) {

    }

    public List<ReplyTree> MakeTree(List<ReplyDetailDto> List) {
        Map<Integer, ReplyTree> replyMap = new HashMap<>();
        List<ReplyTree> root = new ArrayList<>();

        for (ReplyDetailDto reply : List) {
            replyMap.put(reply.getReplyId(), new ReplyTree(reply));
        }
        for (ReplyDetailDto reply : List) {
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
