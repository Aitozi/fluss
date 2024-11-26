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

package com.alibaba.fluss.server.metrics.group;

import com.alibaba.fluss.metadata.PhysicalTablePath;
import com.alibaba.fluss.metadata.TablePath;
import com.alibaba.fluss.metrics.registry.NOPMetricRegistry;

/** Utilities for various metric groups for testing. */
public class TestingMetricGroups {

    public static final TabletServerMetricGroup TABLET_SERVER_METRICS =
            new TabletServerMetricGroup(NOPMetricRegistry.INSTANCE, "fluss", "host", 0);

    public static final CoordinatorMetricGroup COORDINATOR_METRICS =
            new CoordinatorMetricGroup(NOPMetricRegistry.INSTANCE, "cluster1", "host", "0");

    public static final PhysicalTableMetricGroup TABLE_METRICS =
            new PhysicalTableMetricGroup(
                    NOPMetricRegistry.INSTANCE,
                    PhysicalTablePath.of(TablePath.of("mydb", "mytable"), null),
                    false,
                    TABLET_SERVER_METRICS);

    public static final BucketMetricGroup BUCKET_METRICS =
            new BucketMetricGroup(NOPMetricRegistry.INSTANCE, 0, TABLE_METRICS);
}