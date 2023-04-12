package com.example.kanzler.repositories;

import com.example.kanzler.models.Blog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository  extends CrudRepository<Blog, Long> {
    @Query(value = "SELECT * FROM blog LIMIT 6", nativeQuery = true)
    List<Blog> getLastNews();

    @Query(value = "SELECT * FROM blog WHERE blog.category LIKE :category", nativeQuery = true)
    List<Blog> findByCategory( @Param("category") String category);
}
