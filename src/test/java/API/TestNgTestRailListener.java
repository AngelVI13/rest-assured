package API;

import java.util.Arrays;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;

public class TestNgTestRailListener implements IInvokedMethodListener {

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    	if (method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(UseAsTestRailId.class) == null) {
    		return;
    	}
        UseAsTestRailId useAsTestRailId =
                method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(UseAsTestRailId.class);
        //Data driven tests need to be handled differently
        if (method.getTestMethod().isDataDriven()) {
            // Get the Parameters from the Result
            Object[] parameters = testResult.getParameters();
            //Added this Code as There is an Issue with the TestNG Right now in Skip Status version: '7.1.0'
            if (testResult.getThrowable() instanceof SkipException) {
                testResult.setStatus(ITestResult.SKIP);
            }
            // Post the result to Test Rail
            new PostResults().postTestRailResult(
                    useAsTestRailId.testRailId(), testResult, Arrays.toString(parameters));
        } else {
            new PostResults().postTestRailResult(useAsTestRailId.testRailId(), testResult);
        }
    }

}
