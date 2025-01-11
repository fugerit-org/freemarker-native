/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.fugerit.java.freemarkernative;

import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;
import freemarker.log._Log4jOverSLF4JTester;

/**
 * This class avoid exception during GraalVM parsing of class _Log4jOverSLF4JTester :
 * https://github.com/apache/freemarker/blob/2.3-gae/freemarker-core/src/main/java/freemarker/log/_Log4jOverSLF4JTester.java
 *
 * where class org.apache.log4j.MDC would not be found.
 *
 * This is basically the same as the one in quarkus-freemarker project :
 * https://github.com/quarkiverse/quarkus-freemarker/blob/1.1.0/runtime/src/main/java/io/quarkiverse/freemarker/runtime/graal/Log4jOverSLF4JTesterSubstitute.java
 */
@TargetClass(_Log4jOverSLF4JTester.class)
public final class Log4jOverSLF4JTesterSubstitute {

    /**
     * The original method is substituted with a stub returning always false.
     *
     * @return always returns false
     */
    @Substitute
    public static final boolean test() {
        return false;
    }
}
