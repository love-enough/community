package com.newcoder.community.service;

import com.newcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
//@Scope("prototype")多例模式
public class AlphaService {
    @Autowired
    private AlphaDao alphaDao;
    public AlphaService() {
        System.out.println("实例化AlphaService");
    }
    @PostConstruct
    //初始化方法,构造器之后调用
    public void init() {
        System.out.println("初始化AlphaService");
    }
    @PreDestroy
    //销毁之前调用
    public void destory() {
        System.out.println("销毁AlphaService");
    }

    public String find() {
        return alphaDao.select();
    }
}
