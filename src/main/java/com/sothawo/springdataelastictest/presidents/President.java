/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.presidents;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
    @Document(indexName = "presidents")
    public class President {
        @Id
        private String id;

        @Field(type = FieldType.Text)
        private String name;

        @Field(type = FieldType.Integer_Range)
        private Term term;

        static President of(String name, Integer from, Integer to) {
            return new President(name, new Term(from, to));
        }

        public President() {
        }

        public President(String name, Term term) {
            this(UUID.randomUUID().toString(), name, term);
        }

        public President(String id, String name, Term term) {
            this.id = id;
            this.name = name;
            this.term = term;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Term getTerm() {
            return term;
        }

        public void setTerm(Term term) {
            this.term = term;
        }

        static class Term {
            @Field(name = "gte")
            private Integer from;
            @Field(name = "lte")
            private Integer to;

            public Term() {
            }

            public Term(Integer from, Integer to) {
                this.from = from;
                this.to = to;
            }

            public Integer getFrom() {
                return from;
            }

            public void setFrom(Integer from) {
                this.from = from;
            }

            public Integer getTo() {
                return to;
            }

            public void setTo(Integer to) {
                this.to = to;
            }
        }
    }
