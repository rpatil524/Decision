/**
 * Copyright (C) 2014 Stratio (http://stratio.com)
 * <promptProvider>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <promptProvider>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <promptProvider>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.streaming.unit.engine.shell.commands;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandResult;

import com.stratio.streaming.commons.exceptions.StratioAPIGenericException;
import com.stratio.streaming.commons.exceptions.StratioAPISecurityException;
import com.stratio.streaming.commons.exceptions.StratioEngineConnectionException;
import com.stratio.streaming.commons.exceptions.StratioEngineOperationException;
import com.stratio.streaming.commons.exceptions.StratioEngineStatusException;
import com.stratio.streaming.shell.dao.CachedStreamsDAO;
import com.stratio.streaming.shell.exception.StreamingShellException;

public class ErrorHandlingTest extends BaseShellTest {

    private final static String GOOD_ERROR_MESSAGE = "GOOD ERROR MESSAGE";
    private final static String REPEATED_QUERY = "REPEATED_QUERY";

    private final static String STREAM_NAME = "STREAM_NAME";
    @Autowired
    private CachedStreamsDAO cachedStreamsDAO;

    @Before
    public void setUp() {
        init();
    }

    @Test
    public void listWith0StreamsTest() throws StratioEngineStatusException, StratioAPIGenericException,
            StratioAPISecurityException, StratioEngineOperationException, StratioEngineConnectionException {
        Mockito.when(ssaw.api().addQuery(anyString(), anyString())).thenThrow(
                new StratioEngineOperationException(GOOD_ERROR_MESSAGE));

        CommandResult cr = shell
                .executeCommand("add query --stream " + STREAM_NAME + " --definition " + REPEATED_QUERY);
        assertEquals(false, cr.isSuccess());
        assertEquals("-> " + GOOD_ERROR_MESSAGE, cr.getException().toString());
    }
    @Test
    public void streamingShellExceptionTest() throws StratioEngineStatusException, StratioAPIGenericException,
            StratioAPISecurityException, StratioEngineOperationException, StratioEngineConnectionException {

        Mockito.when(ssaw.api().addQuery(anyString(), anyString())).thenThrow(
                new StreamingShellException(GOOD_ERROR_MESSAGE));

        CommandResult cr = shell
                .executeCommand("add query --stream " + STREAM_NAME + " --definition " + REPEATED_QUERY);
        assertEquals(false, cr.isSuccess());
        assertEquals("-> " + GOOD_ERROR_MESSAGE, cr.getException().toString());
    }

}
