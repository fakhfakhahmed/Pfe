package tn.ecnam.resources.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.ecnam.resources.entity.Blog;
import tn.ecnam.resources.service.IBlogService;
import javax.persistence.EntityNotFoundException;
import java.util.List;
@RestController
@RequestMapping("/Blog")
public class BlogController {
    @Autowired
    IBlogService bs;

    @PostMapping("/add-blog")
    public Blog addBlog(@RequestBody Blog blog) {
        return bs.AddBlog(blog);

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


    @PostMapping("/{blogId}/toggle-interesting")
    public ResponseEntity<String> toggleInteresting(
            @PathVariable String blogId) {
        try {
            bs.toggleInterestingBlog(blogId);
            return ResponseEntity.ok("Blog interesting status toggled successfully.");
        } catch (EntityNotFoundException e) {
            // Handle the case where the blog or user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog or User not found.");
        } catch (Exception e) {
            // Handle other exceptions as needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }
}
