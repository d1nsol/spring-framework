package com.d1nsol.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import com.d1nsol.dao.IssueDao;
import com.d1nsol.vo.IssueVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RequestMapping("/")
public class IssueController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<Integer, IssueVO> issueMap;
    
    @Autowired
    IssueDao dao;
    
    @PostConstruct
    public void init() {
        issueMap = new HashMap<Integer, IssueVO>();
        for (IssueVO vo : dao.issueList()) {
            issueMap.put(vo.getIssue_sid(), vo);
        }
        System.out.println("========== issueMap : " + issueMap);
    }

    @GetMapping("/issue/list")
    public List<IssueVO> list() {
        logger.info("========== IssueController list()");
        return dao.issueList();
    }

    @GetMapping("/issue/{sid}")
    public IssueVO getIssue(@PathVariable("sid") int sid) {
        logger.info("========== IssueController getIssue()");
        //return issueMap.get(sid);
        return dao.getIssue(sid);
    }

    @PostMapping("/issue/insert")
    public String insertIssue(@RequestBody IssueVO issueVo)  {
        logger.info("========== IssueController insertIssue()");

        int resultCnt = dao.insertIssue(issueVo);
        
        String resultMsg = "insert fail...";
        if(resultCnt > 0) {
            resultMsg = "insert IssueVO : " + issueVo.toString();
        }
        return resultMsg;
    }

    @PutMapping("/issue/put")
    public void putIssue(@RequestParam("sid") Integer sid, @RequestParam("projNo") int projNo, @RequestParam("title") String title, @RequestParam("status") String status) {
        logger.info("========== IssueController putIssue()");

        IssueVO issueVo = new IssueVO();
        issueVo.setIssue_sid(sid);
        issueVo.setProj_no(projNo);
        issueVo.setIssue_title(title);
        issueVo.setStatus(status);
        dao.insertIssue(issueVo);
    }

    @DeleteMapping("/issue/delete/{sid}")
    public String deleteIssue(@PathVariable("sid") int sid) {
        logger.info("========== IssueController deleteIssue()");

        int resultCnt = dao.deleteIssue(sid);
        
        String resultMsg = "delete fail...";
        if(resultCnt > 0) {
            resultMsg = "delete sid : " + sid;
        }
        return resultMsg;
    }
    
}
