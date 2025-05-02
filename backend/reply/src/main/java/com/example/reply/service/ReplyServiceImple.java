package com.example.reply.service;

import com.example.reply.dto.ReplyDTO;
import com.example.reply.entity.Reply;
import com.example.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImple implements ReplyService {
    private final ReplyRepository repo;

    private ReplyDTO toDTO(Reply e){
        return ReplyDTO.builder()
                .replyId(e.getReplyId())
                .profileId(e.getProfileId())
                .ref(e.getRef())
                .level(e.getLevel())
                .postId(e.getPostId())
                .content(e.getContent())
                .date(e.getDate())
                .build();
    }
    private Reply toEntity(ReplyDTO dto){
        return Reply.builder()
                .replyId(dto.getReplyId())
                .profileId(dto.getProfileId())
                .ref(dto.getRef())
                .level(dto.getLevel())
                .postId(dto.getPostId())
                .content(dto.getContent())
                .build();
    }
    @Override
    public ReplyDTO create(ReplyDTO dto) {
        Reply saved = repo.save(toEntity(dto));
        return toDTO(saved);
    }
    @Override
    public ReplyDTO update(Integer reply_id,ReplyDTO dto) {
        Reply origin = repo.findById(reply_id).orElseThrow(()->new IllegalArgumentException("댓글을 찾을 수 없습니다. id="+reply_id));
        origin.setContent(dto.getContent());
        return toDTO(repo.save(origin));
    }
    @Override
    public void delete(Integer reply_id){
        if(!repo.existsById(reply_id)){
            throw new IllegalArgumentException("댓글이 없습니다 id="+reply_id);
        }
        repo.deleteById(reply_id);
    }
    // 특정 게시글에 달린 모든 댓글 리스트 조회
    @Override
    public List<ReplyDTO> findByPostId(Integer postId){
        return repo.findByPostId(postId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    // 특정 댓글 하나를 기준으로 조회
    @Override
    public ReplyDTO findById(Integer replyId) {
        Reply entity = repo.findById(replyId)
                .orElseThrow(() ->
                        new IllegalArgumentException("댓글을 찾을 수 없습니다. id=" + replyId)
                );
        return toDTO(entity);
    }
}
