package uiTest;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class ExecutionWatcher implements TestWatcher {
    @Override
    public void testSuccessful(ExtensionContext context) {
        DemoQaTest.passedRequests.inc();
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        DemoQaTest.failedRequests.inc();
    }
}
