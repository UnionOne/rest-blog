package com.github.solairerove.blog.controller;

import com.github.solairerove.blog.domain.Comment;
import com.github.solairerove.blog.domain.Post;
import com.github.solairerove.blog.dto.CommentDTO;
import com.github.solairerove.blog.dto.PostDTO;
import com.github.solairerove.blog.service.CommentService;
import com.github.solairerove.blog.service.PostService;
import com.github.solairerove.blog.web.PageResource;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Api
@RestController
@RequestMapping("/api/posts")
//TODO swagger io annotations fo doc
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "Get all posts", notes = "This can be done by all users.", position = 1)
    @ApiResponse(code = 200, message = "Ok")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPosts() {
        return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageResource<Post> getPostPageResource(@RequestParam(required = false) Integer page,
                                                  @RequestParam(required = false) Integer size) {
        if (page != null && size != null) {
            Pageable pageable = new PageRequest(page, size, new Sort("id"));

            Page<Post> pageResult = postService.findAll(pageable);
            return new PageResource<>(pageResult, "page", "size");
        }
        Pageable pageable = new PageRequest(0, 5, new Sort("id"));

        Page<Post> pageResult = postService.findAll(pageable);
        return new PageResource<>(pageResult, "page", "size");
    }

    @ApiOperation(value = "Get post by id", notes = "This can be done by all users.", position = 2, response = Post.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Post not found")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOnePostById(
            @ApiParam(value = "Post id", required = true) @PathVariable Integer id) {
        return new ResponseEntity<>(postService.findOnePostById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Add new post", notes = "By authenticated users only.", position = 3)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addNewPost(
            @ApiParam(value = "Created post object", required = true) @RequestBody PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setSubtitle(postDTO.getSubtitle());
        post.setContent(postDTO.getContent());
        post.setDate(LocalDate.now().toString());
        post.setAuthor(postDTO.getAuthor());
        postService.save(post);
        return new ResponseEntity<>(postDTO.getTitle(), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete post by id", notes = "By authenticated users only.", position = 4)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePostById(
            @ApiParam(value = "Post id", required = true) @PathVariable Integer id) {
        postService.deletePostById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @ApiOperation(value = "Update post by id", notes = "By authenticated users only.", position = 5)
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateContentById(
            @ApiParam(value = "Updated post object", required = true) @RequestBody PostDTO postDTO) {
        postService.updateContentById(postDTO);
        return new ResponseEntity<>(postDTO.getId(), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete all posts", notes = "By authenticated users only.", position = 6)
    @RequestMapping(value = "/clear", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAllPosts() {
        postService.deleteAllPosts();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Add new comment to post", notes = "By authenticated users only.", position = 7)
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.POST)
    public ResponseEntity<?> addNewCommentToPost(
            @ApiParam(value = "Post id", required = true) @PathVariable Integer id,
            @ApiParam(value = "Created comment object", required = true) @RequestBody CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setAuthor(commentDTO.getAuthor());
        comment.setReview(commentDTO.getReview());
        comment.setDate(commentDTO.getDate());
        commentService.save(comment);

        commentService.addNewCommentToPost(id, comment);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all comments from post by id", notes = "By authenticated users only.", position = 8)
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCommentsFromPost(
            @ApiParam(value = "Post id", required = true) @PathVariable Integer id) {
        return new ResponseEntity<>(commentService.findAllCommentsFromPostById(id), HttpStatus.OK);
    }
}
