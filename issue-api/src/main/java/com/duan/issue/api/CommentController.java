package com.duan.issue.api;

import com.duan.issue.common.ResultModel;
import com.duan.issue.common.dto.CommentDTO;
import com.duan.issue.common.exceptions.CommentException;
import com.duan.issue.service.CommentService;
import com.duan.issue.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created on 2019/10/25.
 *
 * @author DuanJiaNing
 */
@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Reference
    private CommentService commentService;

    @PostMapping
    public ResultModel<CommentDTO> add(@RequestBody CommentDTO comment) {
        if (StringUtils.isBlank(comment.getContent())) {
            return ResultUtils.error("请输入内容"); // TODO 字数限制
        }

        if (comment.getTopicId() == null) {
            return ResultUtils.fail("missing parameter: topicId", HttpStatus.BAD_REQUEST);
        }

        try {
            comment = commentService.add(comment);
        } catch (CommentException e) {
            return ResultUtils.fail(e);
        }

        return ResultUtils.success(comment);
    }

    @GetMapping("/{commentId}")
    public ResultModel<CommentDTO> get(@PathVariable Integer commentId) {
        CommentDTO comment = commentService.get(commentId);
        return ResultUtils.success(comment);
    }

    @PutMapping("/{commentId}/dislike")
    public ResultModel<CommentDTO> dislike(@PathVariable Integer commentId) {
        CommentDTO comment = commentService.dislike(commentId);
        return ResultUtils.success(comment);
    }

    @PutMapping("/{commentId}/like")
    public ResultModel<CommentDTO> like(@PathVariable Integer commentId) {
        CommentDTO comment = commentService.like(commentId);
        return ResultUtils.success(comment);
    }

}
