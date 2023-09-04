package tn.ecnam.resources.service;

import org.springframework.stereotype.Service;
import tn.ecnam.resources.entity.Blog;
import tn.ecnam.resources.entity.Product;

import java.util.List;


public interface IBlogService {
    Blog AddBlog(Blog blog);
    Blog UpdateBlog(Blog blog);
    void DeleteBlog(Blog blog);
    List<Blog> GetAllBlog();
    Blog GetBlog(String BlogId);
    void toggleInterestingBlog(String blogId) throws  Exception;
}
