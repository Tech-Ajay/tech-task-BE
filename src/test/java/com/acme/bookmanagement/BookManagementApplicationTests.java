package com.acme.bookmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// This annotation creates an application context containing all beans
// It's a full integration test that starts up the entire Spring context
@SpringBootTest
class BookManagementApplicationTests {

    // This test verifies that the Spring application context can start successfully
    // It will fail if:
    // - There are any configuration errors
    // - Required beans are missing
    // - Bean dependencies cannot be satisfied
    // - Component scanning fails
    @Test
    void contextLoads() {
        // No assertions needed - test passes if Spring context loads without errors
    }

}
