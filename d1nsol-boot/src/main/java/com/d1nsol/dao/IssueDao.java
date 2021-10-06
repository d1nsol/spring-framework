package com.d1nsol.dao;

import java.util.List;
import com.d1nsol.vo.IssueVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IssueDao {
    List<IssueVO> issueList();
    IssueVO getIssue(int sid);
    int insertIssue(IssueVO issueVo);
    int deleteIssue(int sid);
}
