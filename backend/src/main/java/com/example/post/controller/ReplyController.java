package com.example.post.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.post.dto.ReplyDetailDto;
import com.example.post.entity.Reply;
import com.example.post.entity.UserInformation;
import com.example.post.service.ReplyService;
import com.example.post.service.UserService;

@Controller
@RequestMapping("/reply")
@CrossOrigin
public class ReplyController {
    private final ReplyService replyService;
    private final UserService userService;

    public ReplyController(ReplyService replyService, UserService userService) {
        this.replyService = replyService;
        this.userService = userService;
    }

    @GetMapping("/{postId}")
    public List<ReplyDetailDto> getReplyByPostId(@PathVariable Integer postId) {
        return replyService.getReplyByPostId(postId);
    }

    @PostMapping("/{postId}")
    public String createReply(@PathVariable Integer postId, @RequestParam String content, Principal principal) {

        Reply reply = new Reply();
        reply.setContent(content);
        reply.setPostId(postId);
        UserInformation user = userService.getUserByUsername(principal.getName());
        reply.setProfileId(user.getProfileId());
        reply.setRef(0);
        reply.setLevel(0);
        reply.setDate(LocalDateTime.now());

        replyService.createReply(reply);
        reply.setRef(0);
        reply.setStart(reply.getReplyId());
        replyService.createReply(reply);

        return "redirect:/post/" + postId;
    }

    @PostMapping("/{postId}/{parentReplyId}")
    public String createReReply(@PathVariable Integer postId, @PathVariable Integer parentReplyId,
            @RequestParam String content, Principal principal) {

        Reply reply = new Reply();
        Reply target = replyService.getReplyByReplyId(parentReplyId);
        reply.setContent(content);
        reply.setPostId(postId);
        UserInformation user = userService.getUserByUsername(principal.getName());
        reply.setProfileId(user.getProfileId());
        reply.setRef(target.getReplyId());
        reply.setLevel(target.getLevel() + 1);
        reply.setStart(target.getStart());
        reply.setDate(LocalDateTime.now());

        replyService.createReply(reply);
        return "redirect:/post/" + postId;
    }
}
