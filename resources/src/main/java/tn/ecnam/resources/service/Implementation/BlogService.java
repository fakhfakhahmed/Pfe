package tn.ecnam.resources.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.ecnam.resources.entity.Blog;
import tn.ecnam.resources.entity.User;
import tn.ecnam.resources.repository.BlogRepository;
import tn.ecnam.resources.repository.UserRepository;
import tn.ecnam.resources.service.IBlogService;
import tn.ecnam.resources.service.IUserService;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
public class  BlogService implements IBlogService {
    @Autowired
    BlogRepository br;
    @Autowired
    private IUserService us;
    @Autowired
    private UserRepository ur;
    @Override
    public Blog AddBlog(Blog blog) {
        blog.setDateCreation(new Date());
        return br.save(blog);
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

    public void toggleInterestingBlog(String blogId) throws  Exception {
        User user = us.LoggedInUser();
        Blog blog = br.findById(blogId)
                .orElseThrow(() -> new EntityNotFoundException("Blog not found with ID: " + blogId));

        if (user.getInterestingBlogIds().contains(blogId)) {
            user.getInterestingBlogIds().remove(blogId);
            blog.getInterestingUserIds().remove(user.getId());
        } else {
            user.getInterestingBlogIds().add(blogId);
            blog.getInterestingUserIds().add(user.getId());
        }

        ur.save(user);
        br.save(blog);
    }
}
