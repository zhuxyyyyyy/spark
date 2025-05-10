/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.spark.sql.execution.datasources.parquet;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.parquet.bytes.ByteBufferInputStream;
import org.apache.parquet.column.values.ValuesReader;
import org.apache.parquet.io.api.Binary;
import org.apache.parquet.io.ParquetDecodingException;
import org.apache.parquet.column.values.colzip.ColZipValuesReader;

import org.apache.spark.SparkUnsupportedOperationException;
import org.apache.spark.sql.catalyst.util.RebaseDateTime;
import org.apache.spark.sql.execution.datasources.DataSourceUtils;
import org.apache.spark.sql.execution.vectorized.WritableColumnVector;

public class VectorizedColZipValuesReader extends VectorizedReaderBase implements VectorizedValuesReader {
    ColZipValuesReader reader = null;

    public VectorizedColZipValuesReader() {
    }

    @Override
    public void initFromPage(int valueCount, ByteBufferInputStream in) throws IOException {
        reader = new ColZipValuesReader();
        reader.initFromPage(valueCount, in);
    }

    @Override
    public final void readBinary(int total, WritableColumnVector v, int rowId) {
        for (int i = 0; i < total; i++) {
            Binary bytes = reader.readBytes();
            v.putByteArray(rowId + i, bytes.getBytes());
        }
    }

    @Override
    public final void skipBinary(int total) {
        for (int i = 0; i < total; i++) {
            Binary bytes = reader.readBytes();
        }
    }

}
