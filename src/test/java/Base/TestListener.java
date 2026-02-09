package Base;

import Utils.LogUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    // Chạy sau tất cả các testcase được thực thi
    @Override
    public void onFinish(ITestContext arg0) {
        // TODO Auto-generated method stub				
//        System.out.println("Kết thúc AUTOTEST");
        // Dùng log4j2
        LogUtils.info("Kết thúc AUTOTEST");
    }

    // Chạy đầu tiên rồi mới tới các testcase thực thi
    @Override
    public void onStart(ITestContext arg0) {
        // TODO Auto-generated method stub
//        System.out.println("Bắt đầu AUTOTEST");
        // Dùng log4j2
        LogUtils.info("Bắt đầu AUTOTEST");


    }
// kiểm tra thất bại với phần trăm
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        // TODO Auto-generated method stub
//        System.out.println("Đây là testcase bị fail với phần trăm pass");
        // Dùng log4j2
        LogUtils.error("Đây là testcase bị fail với phần trăm pass");

    }

    @Override
    public void onTestFailure(ITestResult arg0) {
        // TODO Auto-generated method stub
//        System.out.println("Đây là testcase thất bại" +  arg0.getName());
        // Dùng log4j2
        LogUtils.error("Đây là testcase thất bại" +  arg0.getName());

    }

    @Override
    public void onTestSkipped(ITestResult arg0) {
        // TODO Auto-generated method stub
//        System.out.println("Đây là testcase bị bỏ qua" + arg0.getName());
        // Dùng log4j2
        LogUtils.info("Đây là testcase thất bại" +  arg0.getName());



    }

    @Override
    public void onTestStart(ITestResult arg0) {
        // TODO Auto-generated method stub				

    }

    @Override
    public void onTestSuccess(ITestResult arg0) {
        // TODO Auto-generated method stub
//        System.out.println("Đây là testcase thành công"+  arg0.getName());
        // Dùng log4j2
        LogUtils.info("Đây là testcase thành công" +  arg0.getName());

    }
}