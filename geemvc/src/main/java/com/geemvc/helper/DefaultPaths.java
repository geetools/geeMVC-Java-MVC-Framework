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

package com.geemvc.helper;

import com.geemvc.Char;
import com.geemvc.HttpMethod;
import com.geemvc.RequestContext;
import com.geemvc.Str;
import com.geemvc.handler.RequestHandler;

/**
 * Created by Michael on 14.07.2016.
 */
public class DefaultPaths implements Paths {

    @Override
    public boolean isValidForRequest(String[] on, RequestHandler requestHandler, RequestContext requestCtx) {
        String mappedHandlerPath = requestHandler.handlerRequestMapping().path();
        String resolvedMappedPath = requestHandler.pathMatcher().getMappedPath();
        String handlerEventName = requestHandler.handlerRequestMapping().name();
        String handlerMethodName = requestHandler.handlerMethod().getName();

        String requestMethod = requestCtx.getMethod();

        if (on == null || on.length == 0)
            return true;

        boolean pathMatches = false;

        for (String onRequest : on) {
            String trimmedOn = onRequest.trim();

            if (startsWithHttpMethod(trimmedOn)) {
                if (!containsHttpMethod(trimmedOn, requestMethod)) {
                    continue;
                }

                // Remove http method so that we can just concentrate on path or name part.
                trimmedOn = trimmedOn.substring(trimmedOn.indexOf(Char.SPACE)).trim();
            }

            // Assuming path mapping.
            if (trimmedOn.startsWith(Str.SLASH)) {
                pathMatches = pathExists(trimmedOn, mappedHandlerPath, resolvedMappedPath);
            } else {
                // Assuming event name or method name mapping.
                pathMatches = trimmedOn.equals(handlerEventName) || trimmedOn.equals(handlerMethodName);
            }

            if (pathMatches)
                break;
        }

        return pathMatches;
    }

    @Override
    public boolean startsWithHttpMethod(String path) {
        if (path.startsWith(HttpMethod.GET + Str.SPACE)
                || path.startsWith(HttpMethod.POST + Str.SPACE)
                || path.startsWith(HttpMethod.PUT + Str.SPACE)
                || path.startsWith(HttpMethod.DELETE + Str.SPACE)
                || path.startsWith(HttpMethod.OPTIONS + Str.SPACE)
                || path.startsWith(HttpMethod.HEAD + Str.SPACE)
                || path.startsWith(HttpMethod.TRACE + Str.SPACE)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean containsHttpMethod(String path, String httpMethod) {
        return path.startsWith(httpMethod);
    }

    protected boolean pathExists(String onPath, String mappedPath, String resolvedMappedPath) {
        if (resolvedMappedPath.equals(onPath))
            return true;

        if (!mappedPath.startsWith(Str.SLASH))
            mappedPath = Str.SLASH + mappedPath;

        if (mappedPath.equals(onPath))
            return true;

        return false;
    }
}
