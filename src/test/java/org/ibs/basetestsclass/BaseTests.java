package org.ibs.basetestsclass;

import org.ibs.managers.InitManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseTests {

    @BeforeAll
    public static void beforeAll() {
        InitManager.initFramework();
    }

    @AfterAll
    public static void afterAll() {
        InitManager.quitFramework();
    }
}

