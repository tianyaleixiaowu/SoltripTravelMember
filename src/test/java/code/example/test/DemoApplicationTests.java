package code.example.test;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.s.t.m.common.excel.ExportExcel;
import com.s.t.m.common.excel.ImportExcel;
import com.s.t.m.common.fileupload.UploadActionUtil;
import com.s.t.m.common.mail.Mail;
import com.s.t.m.common.mail.MailConstant;
import com.s.t.m.common.mail.MailUtils;
import com.s.t.m.common.redis.RedisUtils;
import com.s.t.m.common.result.JsonResult;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DemoApplicationTests extends Tester{
	
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private MailUtils mailUtils;
	
	
	@Test
	public void mailTest(){
		//模板方式发送邮件
		String identifyingCode = "33333";
		Mail mail = new Mail();
        mail.setSubject("欢迎注册邮件发送");
        //接收邮箱
        String[] s = {"1135621750@qq.com"};
        mail.setTo(s);
        //模板名称
        mail.setTemplateName(MailConstant.RETGISTEREMPLATE);
        //存储
        Map<String,String> map = new HashMap<>();
        map.put("identifyingCode",identifyingCode);
        map.put("to",mail.getTo()[0]);
        mail.setTemplateModel(map);
        //发送
        //mailUtils.sendTemplateMail(mail);
        System.out.println("成功");
	}
//	批量接收文件示例
    public JsonResult<List<String>> upload(HttpServletRequest httpServletRequest) throws Exception {
        List<String> list = UploadActionUtil.uploadFile(httpServletRequest);
        return JsonResult.success(list);
    }
	
	/**
	 * 导出测试
	 */
	public void exportExcel() throws Exception{
		
		List<String> headerList = Lists.newArrayList();
		for (int i = 1; i <= 10; i++) {
			headerList.add("表头"+i);
		}
		
		List<String> dataRowList = Lists.newArrayList();
		for (int i = 1; i <= headerList.size(); i++) {
			dataRowList.add("数据"+i);
		}
		
		List<List<String>> dataList = Lists.newArrayList();
		for (int i = 1; i <=1000000; i++) {
			dataList.add(dataRowList);
		}

		ExportExcel ee = new ExportExcel("表格标题", headerList);
		
		for (int i = 0; i < dataList.size(); i++) {
			Row row = ee.addRow();
			for (int j = 0; j < dataList.get(i).size(); j++) {
				ee.addCell(row, j, dataList.get(i).get(j));
			}
		}
		
		ee.writeFile("C:\\Users\\Bai\\Desktop\\新建文件夹/export.xlsx");

		ee.dispose();
		
		System.out.println("is OK");
		
	}
	/**
	 * 导入测试
	 */
	public void importExcel() throws Exception{
		
		ImportExcel ei = new ImportExcel("C:\\Users\\Bai\\Desktop\\工会共青团及外包UAT&stage(含先锋党建)测试账号（全量）.xlsx", 0);
		List<TestExl> dataList = ei.getDataList(TestExl.class);
		System.out.println(JSONObject.toJSONString(dataList));
		
		for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 0; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(row, j);
				System.out.print(val+", ");
			}
			System.out.print("\n");
		}
		
	}
}
