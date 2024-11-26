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

package com.alibaba.fluss.server.cli;

import org.apache.commons.cli.Option;

/**
 * Container class for command line options.
 *
 * <p>It's used to initialize the Fluss configuration from server.yaml in the directory configured
 * in {@link #CONFIG_DIR_OPTION} and the user specified parameters as {@link
 * #DYNAMIC_PROPERTY_OPTION}.
 */
public class CommandLineOptions {

    public static final Option CONFIG_DIR_OPTION =
            Option.builder("c")
                    .longOpt("configDir")
                    .required(true)
                    .hasArg(true)
                    .argName("configuration directory")
                    .desc("Directory which contains the configuration file server.yaml.")
                    .build();

    public static final Option DYNAMIC_PROPERTY_OPTION =
            Option.builder("D")
                    .argName("property=value")
                    .numberOfArgs(2)
                    .valueSeparator('=')
                    .desc("use value for given property")
                    .build();
}