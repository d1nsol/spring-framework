package com.d1nsol.controller;

import java.util.List;

import com.d1nsol.dao.IssueDao;
import com.d1nsol.vo.IssueVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IssueController {
    
    @Autowired
    IssueDao dao;

    @GetMapping
    public List<IssueVO> list() {
        return dao.issueList();
    }
}
