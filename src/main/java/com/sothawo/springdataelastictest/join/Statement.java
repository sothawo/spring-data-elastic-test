/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.join;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.JoinTypeRelation;
import org.springframework.data.elasticsearch.annotations.JoinTypeRelations;
import org.springframework.data.elasticsearch.core.join.JoinField;
import org.springframework.lang.Nullable;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@Document(indexName = "statements"/*, routing = "route"*/)
public class Statement {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String text;

    @JoinTypeRelations(
        relations =
            {
                @JoinTypeRelation(parent = "question", children = {"answer", "comment"}),
                @JoinTypeRelation(parent = "answer", children = "vote")
            }
    )
    private JoinField<String> relation;

    @Nullable
    private String route;

    private Statement() {
    }

    public static StatementBuilder builder() {
        return new StatementBuilder();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public JoinField<String> getRelation() {
        return relation;
    }

    public void setRelation(JoinField<String> relation) {
        this.relation = relation;
    }

    @Nullable
    public String getRoute() {
        return route;
    }

    public void setRoute(@Nullable String route) {
        this.route = route;
    }

    public static final class StatementBuilder {
        private String id;
        private String text;
        private JoinField<String> relation;
        private String routing;

        private StatementBuilder() {
        }

        public StatementBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public StatementBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public StatementBuilder withRouting(String routing) {
            this.routing = routing;
            return this;
        }

        public StatementBuilder withRelation(JoinField<String> relation) {
            this.relation = relation;
            return this;
        }

        public Statement build() {
            Statement statement = new Statement();
            statement.setId(id);
            statement.setText(text);
            statement.setRelation(relation);
            statement.setRoute(routing);
            return statement;
        }
    }
}
