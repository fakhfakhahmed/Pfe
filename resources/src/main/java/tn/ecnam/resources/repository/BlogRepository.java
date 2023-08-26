package tn.ecnam.resources.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.ecnam.resources.entity.Blog;
@Repository
public interface BlogRepository extends MongoRepository<Blog,String> {
}
