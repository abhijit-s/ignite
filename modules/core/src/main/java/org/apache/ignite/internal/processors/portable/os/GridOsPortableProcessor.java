/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.processors.portable.os;

import org.apache.ignite.*;
import org.apache.ignite.cluster.*;
import org.apache.ignite.internal.*;
import org.apache.ignite.internal.client.marshaller.*;
import org.apache.ignite.internal.processors.cache.*;
import org.apache.ignite.internal.processors.portable.*;
import org.apache.ignite.internal.util.typedef.*;
import org.jetbrains.annotations.*;

import java.nio.*;

/**
 * No-op implementation of {@link GridPortableProcessor}.
 */
public class GridOsPortableProcessor extends IgniteCacheObjectProcessorAdapter {
    /**
     * @param ctx Kernal context.
     */
    public GridOsPortableProcessor(GridKernalContext ctx) {
        super(ctx);
    }

    /** {@inheritDoc} */
    @Override public void onCacheProcessorStarted() {
        // No-op.
    }

    /** {@inheritDoc} */
    @Override public int typeId(String typeName) {
        return 0;
    }

    /** {@inheritDoc} */
    @Override public ByteBuffer marshal(@Nullable Object obj, boolean trim) throws IgniteException {
        return null;
    }

    /** {@inheritDoc} */
    @Nullable @Override public Object unmarshal(byte[] arr, int off) throws IgniteException {
        return null;
    }

    /** {@inheritDoc} */
    @Override public Object unmarshal(long ptr, boolean forceHeap) throws IgniteException {
        return null;
    }

    /** {@inheritDoc} */
    @Override public Object unwrapTemporary(Object obj) throws IgniteException {
        return null;
    }

    /** {@inheritDoc} */
    @Nullable @Override public Object marshalToPortable(@Nullable Object obj) throws IgniteException {
        return obj;
    }

    /** {@inheritDoc} */
    @Override public Object detachPortable(@Nullable Object obj, GridCacheContext cctx) {
        if (obj == null)
            return obj;

        assert obj instanceof CacheObject : obj;

        return ((CacheObject)obj).prepareForCache(cctx);
    }

    /** {@inheritDoc} */
    @Nullable @Override public GridClientMarshaller portableMarshaller() {
        return null;
    }

    /** {@inheritDoc} */
    @Override public boolean isPortable(GridClientMarshaller marsh) {
        return false;
    }

    /** {@inheritDoc} */
    @Override public boolean portableEnabled(ClusterNode node, String cacheName) {
        return false;
    }

    /** {@inheritDoc} */
    @Override public boolean isPortableObject(Object obj) {
        return false;
    }

    /** {@inheritDoc} */
    @Override public Object affinityKey(Object obj) {
        return obj;
    }

    /** {@inheritDoc} */
    @Override public int typeId(Object obj) {
        return 0;
    }

    /** {@inheritDoc} */
    @Override public Object field(Object obj, String fieldName) {
        return null;
    }

    /** {@inheritDoc} */
    @Override public boolean hasField(Object obj, String fieldName) {
        return false;
    }

    /** {@inheritDoc} */
    @Override public CacheObjectContext dataLoadContext(@Nullable String cacheName) {
        ClusterNode node = F.first(ctx.grid().cluster().forCacheNodes(cacheName).nodes());

        if (node == null)
            throw new IllegalStateException("Cache doesn't exist: " + cacheName);

        return new CacheObjectContext(ctx);
    }

    /** {@inheritDoc} */
    @Nullable public KeyCacheObject toCacheKeyObject(CacheObjectContext ctx, Object obj) {
        if (obj instanceof KeyCacheObject)
            return (KeyCacheObject)obj;

        return new UserKeyCacheObjectImpl(obj);
    }

    /** {@inheritDoc} */
    @Nullable @Override public CacheObject toCacheObject(CacheObjectContext ctx, @Nullable Object obj, byte[] bytes) {
        if ((obj == null && bytes == null) || obj instanceof CacheObject)
            return (CacheObject)obj;

        if (bytes != null)
            return new CacheObjectImpl(obj, bytes);

        return new UserCacheObjectImpl(obj);
    }
}
