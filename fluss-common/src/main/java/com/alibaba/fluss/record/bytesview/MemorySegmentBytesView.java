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

package com.alibaba.fluss.record.bytesview;

import com.alibaba.fluss.memory.MemorySegment;
import com.alibaba.fluss.shaded.netty4.io.netty.buffer.ByteBuf;
import com.alibaba.fluss.shaded.netty4.io.netty.buffer.Unpooled;

/** A {@link BytesView} that backends on Fluss {@link MemorySegment}. */
public class MemorySegmentBytesView implements BytesView {

    private final MemorySegment segment;
    private final int position;
    private final int size;

    public MemorySegmentBytesView(MemorySegment segment, int position, int size) {
        this.segment = segment;
        this.position = position;
        this.size = size;
    }

    @Override
    public ByteBuf getByteBuf() {
        return Unpooled.wrappedBuffer(segment.wrap(position, size));
    }

    @Override
    public int getBytesLength() {
        return size;
    }

    @Override
    public int getZeroCopyLength() {
        return size;
    }

    public MemorySegment getMemorySegment() {
        return segment;
    }

    public int getPosition() {
        return position;
    }
}