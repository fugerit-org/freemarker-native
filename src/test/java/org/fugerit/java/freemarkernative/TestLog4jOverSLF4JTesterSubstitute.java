package org.fugerit.java.freemarkernative;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestLog4jOverSLF4JTesterSubstitute {

    @Test
    void testOk() {
        Assertions.assertFalse( Log4jOverSLF4JTesterSubstitute.test() );
    }

}
