/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hbase.client;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.apache.hadoop.hbase.HRegionLocation;
import org.apache.hadoop.hbase.TableName;
import org.apache.yetus.audience.InterfaceAudience;

/**
 * The asynchronous version of RegionLocator.
 * <p>
 * Usually the implementations will not throw any exception directly, you need to get the exception
 * from the returned {@link CompletableFuture}.
 * @since 2.0.0
 */
@InterfaceAudience.Public
public interface AsyncTableRegionLocator {

  /**
   * Gets the fully qualified table name instance of the table whose region we want to locate.
   */
  TableName getName();

  /**
   * Finds the region on which the given row is being served. Does not reload the cache.
   * <p/>
   * Returns the location of the region to which the row belongs.
   * @param row Row to find.
   */
  default CompletableFuture<HRegionLocation> getRegionLocation(byte[] row) {
    return getRegionLocation(row, false);
  }

  /**
   * Finds the region on which the given row is being served.
   * <p/>
   * Returns the location of the region to which the row belongs.
   * @param row Row to find.
   * @param reload true to reload information or false to use cached information
   */
  default CompletableFuture<HRegionLocation> getRegionLocation(byte[] row, boolean reload) {
    return getRegionLocation(row, RegionReplicaUtil.DEFAULT_REPLICA_ID, reload);
  }

  /**
   * Finds the region with the given <code>replicaId</code> on which the given row is being served.
   * <p/>
   * Returns the location of the region with the given <code>replicaId</code> to which the row
   * belongs.
   * @param row Row to find.
   * @param replicaId the replica id of the region
   */
  default CompletableFuture<HRegionLocation> getRegionLocation(byte[] row, int replicaId) {
    return getRegionLocation(row, replicaId, false);
  }

  /**
   * Finds the region with the given <code>replicaId</code> on which the given row is being served.
   * <p/>
   * Returns the location of the region with the given <code>replicaId</code> to which the row
   * belongs.
   * @param row Row to find.
   * @param replicaId the replica id of the region
   * @param reload true to reload information or false to use cached information
   */
  CompletableFuture<HRegionLocation> getRegionLocation(byte[] row, int replicaId, boolean reload);

  /**
   * Retrieves all of the regions associated with this table.
   * @return a {@link List} of all regions associated with this table.
   */
  CompletableFuture<List<HRegionLocation>> getAllRegionLocations();
}
