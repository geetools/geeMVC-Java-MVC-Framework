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

package com.geemvc.reader.bean;

import com.geemvc.bind.PropertyNode;
import com.geemvc.inject.Injectors;
import com.google.inject.Injector;

public class AbstractBeanReader {
    protected Object _lookup(String expression, Object beanInstance) {
        if (expression == null || beanInstance == null)
            return null;

        Injector injector = Injectors.provide();

        PropertyNode propertyNode = injector.getInstance(PropertyNode.class).build(expression, beanInstance.getClass());

        return propertyNode.get(beanInstance);
    }
}
