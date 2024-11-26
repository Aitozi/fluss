/*
 * Copyright (c) 2024 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.fluss.client.lookup;

import com.alibaba.fluss.annotation.Internal;
import com.alibaba.fluss.exception.FlussRuntimeException;
import com.alibaba.fluss.metadata.TableBucket;
import com.alibaba.fluss.rpc.messages.PbValue;

import java.util.ArrayList;
import java.util.List;

/** A batch that contains the lookup operations that send to same tablet bucket together. */
@Internal
public class LookupBatch {

    /** The table bucket that the lookup operations should fall into. */
    private final TableBucket tableBucket;

    private final List<Lookup> lookups;

    public LookupBatch(TableBucket tableBucket) {
        this.tableBucket = tableBucket;
        this.lookups = new ArrayList<>();
    }

    public void addLookup(Lookup lookup) {
        lookups.add(lookup);
    }

    public List<Lookup> lookups() {
        return lookups;
    }

    public TableBucket tableBucket() {
        return tableBucket;
    }

    /** Complete the lookup operations using given values . */
    public void complete(List<PbValue> pbValues) {
        // if the size of return values of lookup operation are not equal to the number of lookups,
        // should complete an exception.
        if (pbValues.size() != lookups.size()) {
            completeExceptionally(
                    new FlussRuntimeException(
                            String.format(
                                    "The number of return values of lookup operation is not equal to the number of "
                                            + "lookups. Return %d values, but expected %d.",
                                    pbValues.size(), lookups.size())));
        } else {
            for (int i = 0; i < pbValues.size(); i++) {
                Lookup lookup = lookups.get(i);
                PbValue pbValue = pbValues.get(i);
                lookup.future().complete(pbValue.hasValues() ? pbValue.getValues() : null);
            }
        }
    }

    /** Complete the lookup operations with given exception. */
    public void completeExceptionally(Exception exception) {
        for (Lookup lookup : lookups) {
            lookup.future().completeExceptionally(exception);
        }
    }
}