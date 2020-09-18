package com.zzxkj.blog.controller;

import com.zzxkj.blog.Entity.Blog;
import com.zzxkj.blog.Entity.Type;
import com.zzxkj.blog.WorkingException.NotFoundException;
import com.zzxkj.blog.search.BlogQuery;
import com.zzxkj.blog.service.BlogService;
import com.zzxkj.blog.service.TagService;
import com.zzxkj.blog.service.TypeService;
import com.zzxkj.blog.util.MarkdownUtils;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexConntroller {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @GetMapping("/")
    public String index(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model)
    {
     model.addAttribute("page",blogService.listBlog(pageable));
     model.addAttribute("types",typeService.listTypeTop(6));/*分类栏数目*/
     model.addAttribute("tags",tagService.listTagTop(10));/*标签栏数目*/
     model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
/*        String path = this.getClass().getResource("/static/gril.jpg").getPath();
/*        String blog = null;
        if(blog==null)
        {
            throw new NotFoundException("博客不存在");
        }*/
        System.out.println("--------index----------");

        return "index";
    }
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, @RequestParam String query, Model model)
    {
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id , Model model){
        Blog blog = blogService.getBlogAndIncrease(id);
        if(blog==null) throw new NotFoundException("此篇博客不存在，或被移除");
        //将md格式转换成html格式
        String HTMLContent = MarkdownUtils.markdownToHtmlExtensitons(blog.getContent());//MarkDown文本转成HTML文本
        blog.setContent(HTMLContent);
        model.addAttribute("blog",blog);
        return "blog";
    }
    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "_fragments  :: newblogList";
   }
}
