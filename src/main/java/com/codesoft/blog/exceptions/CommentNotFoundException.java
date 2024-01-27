package com.codesoft.blog.exceptions;

public class CommentNotFoundException extends RuntimeException{
    private String field;
    private String fieldIdentifier;
    private int fieldId;

    public CommentNotFoundException(String field, String fieldIdentifier, int fieldId) {
        super(String.format("%s not found with %s with %d",field,fieldIdentifier,fieldId));
        this.field = field;
        this.fieldIdentifier = fieldIdentifier;
        this.fieldId = fieldId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFieldIdentifier() {
        return fieldIdentifier;
    }

    public void setFieldIdentifier(String fieldIdentifier) {
        this.fieldIdentifier = fieldIdentifier;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    @Override
    public String toString() {
        return "CommentNotFoundException{" +
                "field='" + field + '\'' +
                ", fieldIdentifier='" + fieldIdentifier + '\'' +
                ", fieldId=" + fieldId +
                '}';
    }
}
