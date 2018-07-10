/*
 * Copyright 2014-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.speedycloud.services.s3.transfer.internal;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.SyncProgressListener;
import com.speedycloud.services.s3.transfer.PersistableTransfer;

/**
 * Abstract adapter class for an S3 progress listener that is safe to be
 * delivered with progress event synchronously.
 */
public abstract class S3SyncProgressListener extends SyncProgressListener
        implements S3ProgressListener {
    /**
     * Note this method is never actually invoked. Only
     * {@link #onPersistableTransfer(PersistableTransfer)}
     * is.
     */
    @Override public void progressChanged(ProgressEvent progressEvent) {}
}
