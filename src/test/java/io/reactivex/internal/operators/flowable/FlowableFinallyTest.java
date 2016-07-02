/**
 * Copyright 2016 Netflix, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package io.reactivex.internal.operators.flowable;

import static org.mockito.Mockito.*;

import org.junit.*;
import org.reactivestreams.Subscriber;

import io.reactivex.*;

public class FlowableFinallyTest {

    private Runnable aAction0;
    private Subscriber<String> observer;

    // mocking has to be unchecked, unfortunately
    @Before
    public void before() {
        aAction0 = mock(Runnable.class);
        observer = TestHelper.mockSubscriber();
    }

    private void checkActionCalled(Flowable<String> input) {
        input.finallyDo(aAction0).subscribe(observer);
        verify(aAction0, times(1)).run();
    }

    @Test
    public void testFinallyCalledOnComplete() {
        checkActionCalled(Flowable.fromArray("1", "2", "3"));
    }

    @Test
    public void testFinallyCalledOnError() {
        checkActionCalled(Flowable.<String> error(new RuntimeException("expected")));
    }
}