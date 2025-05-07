package com.example.kyu.controller;

import com.example.kyu.dto.ReplyDTO;
import com.example.kyu.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/replies")
@RequiredArgsConstructor
public class ReplyViewcontroller {

    private final ReplyService svc;

    /** 1) 특정 게시글 댓글 조회 **/
    @GetMapping("/post/{postId}")
    public String list(
            @PathVariable Integer postId,
            Model model
    ) {
        // 서비스 호출로 댓글 리스트를 가져오고
        List<ReplyDTO> replies = svc.findByPostId(postId);
        //뷰로 넘겨줄 이름("replies")과 값을 model 에 담아 줘야만
        model.addAttribute("replies", replies);
        System.out.println(">>>>>>>>replies.size() = " + replies.size());
        // (폼 바인딩용 빈 DTO)
        model.addAttribute("newReply",
                new ReplyDTO(null, null, 0, 0, postId, "", null));

        return "list";  // → list.html 에서 ${replies} 로 접근 가능
    }


    /** 2) 댓글 작성 **/
    @PostMapping("/post/{postId}")
    public String create(
            @PathVariable Integer postId,
            @ModelAttribute("newReply") @Valid ReplyDTO dto,
            BindingResult br,
            Model model
    ){
        if(br.hasErrors()) {
            model.addAttribute("replies",svc.findByPostId(postId));
            return "postDetail";
        }
        svc.create(dto);
        return "redirect:/post/" + postId;
    }

    /** 3) 댓글 수정 Form 보여주기 - Fragment 반환 **/
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        ReplyDTO dto = svc.findById(id);
        model.addAttribute("reply", dto);
        return "edit :: editForm";  // Fragment only
    }

    /** 4) 수정 처리하기 **/
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute ReplyDTO dto,
                         BindingResult br) {
        if (br.hasErrors()) {
            return "edit :: editForm";  // 유효성 에러 시 다시 fragment 반환
        }
        svc.update(id, dto);
        return "redirect:/post/" + dto.getPostId();
    }

    /** 5) 삭제하기  **/
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Integer postId = svc.findById(id).getPostId();
        svc.delete(id);
        return "redirect:/post/" + postId;
    }
}
