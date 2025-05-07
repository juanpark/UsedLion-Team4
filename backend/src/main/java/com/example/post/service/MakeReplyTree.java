package com.example.post.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.post.dto.ReplyDetailDto;
import com.example.post.dto.ReplyTree;

public class MakeReplyTree {

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
