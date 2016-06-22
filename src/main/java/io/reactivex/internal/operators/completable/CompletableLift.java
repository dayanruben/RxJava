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

package io.reactivex.internal.operators.completable;

import io.reactivex.*;
import io.reactivex.internal.util.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;

public final class CompletableLift extends Completable {

    final CompletableConsumable source;
    
    final CompletableOperator onLift;
    
    public CompletableLift(CompletableConsumable source, CompletableOperator onLift) {
        this.source = source;
        this.onLift = onLift;
    }

    @Override
    protected void subscribeActual(CompletableSubscriber s) {
        try {
            // TODO plugin wrapping

            CompletableSubscriber sw = onLift.apply(s);
            
            source.subscribe(sw);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            RxJavaPlugins.onError(ex);
        }
    }

}