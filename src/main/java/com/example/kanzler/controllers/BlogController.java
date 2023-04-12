package com.example.kanzler.controllers;

import com.example.kanzler.models.Blog;
import com.example.kanzler.models.Room;
import com.example.kanzler.repositories.BlogRepository;
import com.example.kanzler.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Controller
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogService blogService;

    @GetMapping("/admin/blog")
    public String getRoomList(Model model) {
        List<Blog> list = blogService.getAll();
        model.addAttribute("news", list);

        return "admin-blog-list";
    }

    @GetMapping("/admin/blog/create")
    public String createBlog(Model model) {
        return "create-blog";
    }

    @PostMapping("/admin/blog/create")
    public String createBlogItem(@RequestParam("file1") MultipartFile file1, Blog blog) throws IOException {
        blogService.addBlog(blog, file1);
        return "redirect:/admin/blog";
    }

    @PostMapping("/admin/blog/delete/{id}")
    public String deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return "redirect:/admin/blog";
    }

    @GetMapping("/admin/blog/edit/{id}")
    public String editBlog(@PathVariable Long id, Model model) {
        if (!blogRepository.existsById(id)) {
            return "redirect:/admin/blog";
        }

        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
//        model.addAttribute("images", blog.getImages());

        return "edit-blog";
    }

    @PostMapping(path = "/admin/blog/edit/{id}")
    public String editPostBlog(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String subtitle, @RequestParam String description,
                               @RequestParam String category, Model model) throws IOException
    {
        if (title.isEmpty() || subtitle.isEmpty() || description.isEmpty() || category.isEmpty())
        {
            return "redirect:/admin/blog";
        }

        Blog blog = blogRepository.findById(id).orElseThrow();
        blog.setTitle(title);
        blog.setSubtitle(subtitle);
        blog.setDescription(description);
        blog.setCategory(category);
        blogRepository.save(blog);

        return "redirect:/admin/blog";
    }

    @GetMapping(path = "/blog/{id}")
    public String getBlog(@PathVariable(value = "id") long id, Model model) {
        if (!blogRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
        model.addAttribute("images", blog.getImages());

        return "discount";
    }

    @RequestMapping(path = "/news")
    public String getNews(String category, Model model) {
        List<Blog> list;

        if (category != null) {
            list = blogService.getByCategory(category);
            model.addAttribute("category", category);
        } else {
            list = blogService.getByCategory("novelty");
            model.addAttribute("category", "novelty");
        }

        model.addAttribute("news", list);
        return "news-page";
    }
}
