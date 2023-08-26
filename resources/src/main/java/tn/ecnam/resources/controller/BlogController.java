package tn.ecnam.resources.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.ecnam.resources.entity.Blog;
import tn.ecnam.resources.service.IBlogService;


import java.util.List;
@RestController
@RequestMapping("/Blog")
public class BlogController {
    @Autowired
    IBlogService bs;

    @PostMapping("/add-blog")
    public Blog addBlog(@RequestBody Blog blog) throws Exception {
        Blog b = bs.AddBlog(blog);
        bs.AddBlogToUser(b);
        return b;
    }
    @PostMapping("/update-blog")
    public Blog UpdateBlog(@RequestParam("BlogId") String BlogId, @RequestBody Blog blog){
        blog.setId(BlogId);
        return bs.UpdateBlog(blog);
    }
    @DeleteMapping("/delete-blog/{blogId}")
    public void deleteBlog(@PathVariable("blogId") String blogId) {
        Blog blog= bs.GetBlog(blogId);
        bs.DeleteBlog(blog);
    }
    @GetMapping("/get-all")
    public List<Blog> getAll(){
        return bs.GetAllBlog();
    }
    @GetMapping("/{blogId}")
    public Blog getBlog(@PathVariable("BlogId") String BlogId){
        return  bs.GetBlog(BlogId);
    }
}
