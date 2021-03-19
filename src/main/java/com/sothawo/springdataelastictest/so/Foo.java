package com.sothawo.springdataelastictest.so;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

@Document(indexName = "foo")
public class Foo {
    @Id
    private String id;

    @Field(type = FieldType.Nested)
    private AE ae;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AE getAe() {
        return ae;
    }

    public void setAe(AE ae) {
        this.ae = ae;
    }

    static class AE {
        @Field(type = FieldType.Text)
        private String atb;
        @MultiField(
            mainField = @Field(type = FieldType.Text),
            otherFields = {
                @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
        )
        private String su;

        public String getAtb() {
            return atb;
        }

        public void setAtb(String atb) {
            this.atb = atb;
        }

        public String getSu() {
            return su;
        }

        public void setSu(String su) {
            this.su = su;
        }
    }
}
