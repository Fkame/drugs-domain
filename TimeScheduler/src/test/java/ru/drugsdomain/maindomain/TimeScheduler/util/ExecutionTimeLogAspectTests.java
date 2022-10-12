package ru.drugsdomain.maindomain.TimeScheduler.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ExecutionTimeLogAspectTests {

    @Autowired
    TestClass testClass;

    @Test
    public void testOkLogWithReturn() {
        testClass.testOkReturn();
    }

    @Test
    public void testOkLogWithoutReturn() {
        testClass.testOkVoid();
    }

    @Test
    public void testFailLogWithReturn() {
        try {
            testClass.testFailReturn();
            assertTrue(false);
        } catch (Throwable ex) { }
    }

    @Test
    public void testFailLogWithoutReturn() {
        try {
            testClass.testFailVoid();
            assertTrue(false);
        } catch (Throwable ex) { }
    }
}

@Component
class TestClass {
    @LogExecutionTime
    public void testOkVoid() {
    }

    @LogExecutionTime
    public Integer testOkReturn() {
        return 5;
    }

    @LogExecutionTime
    public void testFailVoid() {
        throw new IllegalArgumentException("test fail void error");
    }

    @LogExecutionTime
    public Integer testFailReturn() {
        throw new IllegalArgumentException("test fail return error");
    }
}
