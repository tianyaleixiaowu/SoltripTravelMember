package code.example.test;

import com.s.t.m.common.utils.SecurityKit;
import com.s.t.m.project.dao.MDictionaryMapper;
import com.s.t.m.project.dao.MMemberMapper;
import com.s.t.m.project.service.MDictionaryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestMapper extends Tester{
    @Autowired
    private MMemberMapper mMemberMapper;
    @Autowired
    private MDictionaryService mDictionaryService;
    /*@Test
    public void newPwd(){
        int i = mMemberMapper.ModifyPassword("kWy4b8AczZNmpvW+Ac7ovg==", SecurityKit.MD5.convert32("123"));
        System.out.println(i);
        //C7A37DB4FBE99B9B158BE77E493C683D
    }*/
    @Test
    public void dict(){
        String dictionaryDValue = mDictionaryService.getDictionaryDValue(49, "06");
        System.err.println(dictionaryDValue);
    }
}
