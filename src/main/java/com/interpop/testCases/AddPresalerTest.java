package com.interpop.testCases;

import com.interpop.constant.Constants;
import com.interpop.entity.API;
import com.interpop.entity.Cases;
import com.interpop.utils.AuthorizationUtils;
import com.interpop.utils.ExcelUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddPresalerTest extends BaseCase {
    @Test(dataProvider = "datas" ,description = "新建售前")
    public void execute(API api , Cases cases){
        super.execute(api,cases);
    }
    @Override
    public void executeAll(API api, Cases cases,String language){
        try{
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String reqBody = call(api,cases,language,true);
        AuthorizationUtils.storeToken(reqBody);
        boolean assertResponseFlag = assertResponse(cases,language,reqBody);
        String assertContent = (assertResponseFlag) ?"Pass":"Fail";
        if("zh-Hans".equals(language)){
            addWBD(Integer.parseInt(cases.getCaseNumber()), Constants.ACTUAL_WAITER_BACK_CELL_NUM, reqBody);
            addWBD(Integer.parseInt(cases.getCaseNumber()),Constants.ACTUAL_result_CALL_CELL_NUM,assertContent);
            System.out.println();
        }
    }
    @DataProvider(name = "datas")
    public Object[][] datas(){
        Object[][] datas = ExcelUtils.getAPIandCaseByApiId("5");
        return datas;
    }
}