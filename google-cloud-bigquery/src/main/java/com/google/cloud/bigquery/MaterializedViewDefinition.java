/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.bigquery;

import com.google.api.services.bigquery.model.Table;
import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

@AutoValue
public abstract class MaterializedViewDefinition extends TableDefinition {

  private static final long serialVersionUID = 5898696389126164276L;

  @AutoValue.Builder
  public abstract static class Builder
      extends TableDefinition.Builder<MaterializedViewDefinition, Builder> {

    /**
     * [Output-only] The time when this materialized view was last modified, in milliseconds since
     * the epoch.
     */
    abstract Builder setLastRefreshTime(Long lastRefreshTime);

    /** Sets the query whose result is persisted. */
    public abstract Builder setQuery(String query);

    /**
     * Set enable automatic refresh of the materialized view when the base table is updated. The
     * default value is "true".
     */
    public abstract Builder setEnableRefresh(Boolean enableRefresh);

    /**
     * Set a maximum frequency at which this materialized view will be refreshed. The default value
     * is "1800000" (30 minutes).
     */
    public abstract Builder setRefreshIntervalMs(Long refreshIntervalMs);

    /** Sets the table schema. */
    @Override
    public abstract Builder setSchema(Schema schema);

    @Override
    public abstract Builder setType(Type type);

    /** Creates a {@code MaterializedViewDefinition} object. */
    @Override
    public abstract MaterializedViewDefinition build();
  }

  /**
   * Returns time when this materialized view was last modified, in milliseconds since the epoch.
   */
  @Nullable
  public abstract Long getLastRefreshTime();

  /** Returns a query whose result is persisted. */
  @Nullable
  public abstract String getQuery();

  /**
   * Returns enable automatic refresh of the materialized view when the base table is updated. The
   * default value is "true".
   */
  @Nullable
  public abstract Boolean getEnableRefresh();

  /**
   * Returns a maximum frequency at which this materialized view will be refreshed. The default
   * value is "1800000" (30 minutes).
   */
  @Nullable
  public abstract Long getRefreshIntervalMs();

  /** Returns a builder for the {@code MaterializedViewDefinition} object. */
  public abstract Builder toBuilder();

  @Override
  Table toPb() {
    Table tablePb = super.toPb();
    com.google.api.services.bigquery.model.MaterializedViewDefinition materializedViewDefinition =
        new com.google.api.services.bigquery.model.MaterializedViewDefinition();
    if (getQuery() != null) {
      materializedViewDefinition.setQuery(getQuery());
    }
    if (getLastRefreshTime() != null) {
      materializedViewDefinition.setLastRefreshTime(getLastRefreshTime());
    }
    if (getEnableRefresh() != null) {
      materializedViewDefinition.setEnableRefresh(getEnableRefresh());
    }
    if (getRefreshIntervalMs() != null) {
      materializedViewDefinition.setRefreshIntervalMs(getRefreshIntervalMs());
    }
    tablePb.setMaterializedView(materializedViewDefinition);
    return tablePb;
  }

  static Builder newBuilder() {
    return new AutoValue_MaterializedViewDefinition.Builder().setType(Type.MATERIALIZED_VIEW);
  }

  /**
   * Returns a builder for a BigQuery materialized view definition.
   *
   * @param query the query used to generate the materialized view
   */
  public static Builder newBuilder(String query) {
    return newBuilder().setQuery(query);
  }

  /**
   * Returns a builder for a BigQuery materialized view definition.
   *
   * @param query the query used to generate the materialized view
   */
  public static MaterializedViewDefinition of(String query) {
    return newBuilder(query).build();
  }

  static MaterializedViewDefinition fromPb(Table tablePb) {
    Builder builder = newBuilder().table(tablePb);
    if (tablePb.getMaterializedView() != null) {
      com.google.api.services.bigquery.model.MaterializedViewDefinition materializedViewDefinition =
          tablePb.getMaterializedView();
      if (materializedViewDefinition.getQuery() != null) {
        builder.setQuery(materializedViewDefinition.getQuery());
      }
      if (materializedViewDefinition.getLastRefreshTime() != null) {
        builder.setLastRefreshTime(materializedViewDefinition.getLastRefreshTime());
      }
      if (materializedViewDefinition.getEnableRefresh() != null) {
        builder.setEnableRefresh(materializedViewDefinition.getEnableRefresh());
      }
      if (materializedViewDefinition.getRefreshIntervalMs() != null) {
        builder.setRefreshIntervalMs(materializedViewDefinition.getRefreshIntervalMs());
      }
    }
    return builder.build();
  }
}
