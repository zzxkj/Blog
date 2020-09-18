package com.zzxkj.blog.service.imp;

import com.zzxkj.blog.Entity.Type;
import com.zzxkj.blog.WorkingException.NotFoundException;
import com.zzxkj.blog.dao.TyepRespository;
import com.zzxkj.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TypeServiceimpl implements TypeService {
    @Autowired
    private TyepRespository tyepRespository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return tyepRespository.save(type);
    }
    @Transactional
    @Override
    public Type getType(Long id) {
        return tyepRespository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = tyepRespository.findById(id).orElse(null);
        if(t==null)
        {
            throw new NotFoundException("震惊！！！本不该存在该标签！！！！");
        }
        BeanUtils.copyProperties(type,t);
        return tyepRespository.save(t);
    }
    @Transactional
    @Override
    public void deleteType(Long id) {
           tyepRespository.deleteById(id);
    }
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return tyepRespository.findAll(pageable);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return tyepRespository.findTop(pageable);
    }

    @Override
    public List<Type> listType() {
        return tyepRespository.findAll();
    }

    @Override
    public Type getTypeByName(String name) {
        return tyepRespository.findByName(name);
    }
}
