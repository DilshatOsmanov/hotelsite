package com.example.kanzler.services;

import com.example.kanzler.models.Blog;
import com.example.kanzler.models.Image;
import com.example.kanzler.models.Room;
import com.example.kanzler.repositories.BlogRepository;
import com.example.kanzler.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository repository;

    @Autowired
    private ImageRepository imageRepository;

    public List<Blog> getAll(){
        List<Blog> list = (List<Blog>)repository.findAll();
        return list;
    }

    public List<Blog> getByCategory(String category){
        List<Blog> list = (List<Blog>)repository.findByCategory(category);
        return list;
    }

    public List<Blog> getLastNews(){
        List<Blog> list = (List<Blog>)repository.getLastNews();
        return list;
    }

    public void addBlog(Blog blog, MultipartFile file1) throws IOException {
        Image image1;

        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            blog.addImageToBlog(image1);
        }

        Blog blogFromDb = repository.save(blog);
        blogFromDb.setPreviewImageId(blogFromDb.getImages().get(0).getId());
        repository.save(blog);
    }

    public Blog getBlogById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteBlog(Long id) {
        repository.deleteById(id);
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
}
