/*
 * Licensed to ElasticSearch and Shay Banon under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. ElasticSearch licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.index.query;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

/**
 * A filter that will execute the wrapped filter only for the specified indices, and "match_all" when
 * it does not match those indices (by default).
 */
public class Indices2416FilterBuilder extends BaseFilterBuilder {

    private final FilterBuilder filterBuilder;

    private final String[] indices;

    private String sNoMatchFilter;
    private FilterBuilder noMatchFilter;

    private String filterName;

    public Indices2416FilterBuilder(FilterBuilder filterBuilder, String... indices) {
        this.filterBuilder = filterBuilder;
        this.indices = indices;
    }

    /**
     * Sets the no match filter, can either be <tt>all</tt> or <tt>none</tt>.
     */
    public Indices2416FilterBuilder noMatchFilter(String type) {
        this.sNoMatchFilter = type;
        return this;
    }

    /**
     * Sets the filter to use when it executes on an index that does not match the indices provided.
     */
    public Indices2416FilterBuilder noMatchFilter(FilterBuilder noMatchFilter) {
        this.noMatchFilter = noMatchFilter;
        return this;
    }

    /**
     * Sets the filter name for the filter that can be used when searching for matched_filters per hit.
     */
    public Indices2416FilterBuilder filterName(String filterName) {
        this.filterName = filterName;
        return this;
    }

    @Override
    protected void doXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject(Indices2416FilterParser.NAME);
        builder.field("indices", indices);
        builder.field("filter");
        filterBuilder.toXContent(builder, params);
        if (noMatchFilter != null) {
            builder.field("no_match_filter");
            noMatchFilter.toXContent(builder, params);
        } else if (sNoMatchFilter != null) {
            builder.field("no_match_filter", sNoMatchFilter);
        }

        if (filterName != null) {
            builder.field("_name", filterName);
        }

        builder.endObject();
    }
}
