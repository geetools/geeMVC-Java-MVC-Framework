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

package com.cb.geemvc.handler;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.cb.geemvc.annotation.Request;
import com.cb.geemvc.bind.MethodParam;
import com.cb.geemvc.matcher.PathMatcher;

public interface RequestHandler extends Comparable<RequestHandler> {
    RequestHandler build(Class<?> controllerClass, Method handlerMethod, String consumes, String produces);

    Class<?> controllerClass();

    Method handlerMethod();

    String name();

    RequestHandler name(String name);

    String consumes();

    String produces();

    RequestHandler pathMatcher(PathMatcher pathMatcher);

    PathMatcher pathMatcher();

    Collection<String> resolvedParameters();

    RequestHandler resolvedParameters(Collection<String> resolvedParameters);

    Collection<String> resolvedHeaders();

    RequestHandler resolvedHeaders(Collection<String> resolvedHeaders);

    Collection<String> resolvedCookies();

    RequestHandler resolvedCookies(Collection<String> resolvedCookes);

    Collection<String> resolvedHandlesScripts();

    RequestHandler resolvedHandlesScripts(Collection<String> resolvedHandlesScripts);

    HandlerResolverStats handlerResolverStats();

    Request controllerRequestMapping();

    Request handlerRequestMapping();

    List<MethodParam> methodParams();

    Object invoke(Map<String, Object> args);

    String toGenericString();
}
