package tn.ecnam.resources.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.ecnam.resources.entity.Blog;
import tn.ecnam.resources.entity.User;
import tn.ecnam.resources.repository.BlogRepository;
import tn.ecnam.resources.repository.UserRepository;
import tn.ecnam.resources.service.IBlogService;
import tn.ecnam.resources.service.IUserService;

import java.util.List;
import java.util.Set;

@Service
public class BlogService implements IBlogService {
    @Autowired
    BlogRepository br;
    @Autowired
    private IUserService us;
    @Autowired
    private UserRepository ur;
    @Override
    public Blog AddBlog(Blog blog) {
        return br.save(blog);
    }

    @Override
    public void AddBlogToUser(Blog blog) throws Exception {
        User user = us.LoggedInUser();
        Set<Blog> blogs = user.getBlogs();
        blogs.add(blog);
        user.setBlogs(blogs);
        ur.save(user);

    }

    @Override
    public Blog UpdateBlog(Blog blog) {
        return br.save(blog);
    }

    @Override
    public void DeleteBlog(Blog blog) {
        br.delete(blog);
    }

    @Override
    public List<Blog> GetAllBlog() {
        return (List<Blog>)  br.findAll();
    }

    @Override
    public Blog GetBlog(String BlogId) {
        return br.findById(BlogId).orElse(null);

    }
}
