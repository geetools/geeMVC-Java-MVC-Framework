/*
 * Copyright 2016 the original author or authors.
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

package com.cb.geemvc.matcher;

import java.util.Collection;

public class DefaultMatcherContext implements MatcherContext {
    protected Collection<String> resolvedExpressions = null;

    public MatcherContext resolve(Collection<String> resolvedExpressions) {
        if (this.resolvedExpressions != null) {
            this.resolvedExpressions.addAll(resolvedExpressions);
        } else {
            this.resolvedExpressions = resolvedExpressions;
        }

        return this;
    }

    public Collection<String> resolvedExpressions() {
        return resolvedExpressions;
    }

    @Override
    public String toString() {
        return "DefaultMatcherContext [resolvedExpressions=" + resolvedExpressions + "]";
    }
}
