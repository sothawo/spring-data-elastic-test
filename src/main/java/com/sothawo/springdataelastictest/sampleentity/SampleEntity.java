/*
 * (c) Copyright 2020 codecentric AG
 */
package com.sothawo.springdataelastictest.sampleentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.query.SeqNoPrimaryTerm;
import org.springframework.lang.Nullable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Document(indexName = "sample-entities")
public record SampleEntity(
				@Id
				@Nullable
				String id,

				@Field(type = FieldType.Text)
				@Nullable String message,

				@CreatedDate
				@Field(type = FieldType.Date, format = DateFormat.basic_date_time)
				@Nullable
				Instant created,

				@CreatedBy
				@Nullable
				String createdBy,

				@LastModifiedDate
				@Field(type = FieldType.Date, format = DateFormat.basic_date_time)
				@Nullable
				Instant lastModified,

				@LastModifiedBy
				@Nullable
				String lastModifiedBy,

				@Field(type = FieldType.Nested)
				List<Object> objects,

				@Nullable SeqNoPrimaryTerm seqNoPrimaryTerm,

				@Field(type = FieldType.Date_Nanos, format = DateFormat.strict_date_optional_time_nanos)
				@Nullable Instant timestamp

) implements Persistable<String> {
		@PersistenceCreator
		public SampleEntity {
				if (timestamp == null) {
						timestamp = Instant.now();
				}
		}

		public SampleEntity(@Nullable String id, List<Object> objects) {
				this(id,
								null,
								null,
								null,
								null,
								null,
								objects,
								null,
								null);
		}

		@Override
		public String getId() {
				return id;
		}

		@JsonIgnore
		@Override
		public boolean isNew() {
				return id == null || (createdBy == null && created == null);
		}

		public SampleEntity withId(String id) {
				return new SampleEntity(id,
								this.message,
								this.created,
								this.createdBy,
								this.lastModified,
								this.lastModifiedBy,
								this.objects,
								this.seqNoPrimaryTerm,
								this.timestamp);
		}
}
