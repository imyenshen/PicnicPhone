package com.example.java.picnicphone.generalMember;



public class GeneralMember {

    private String MEM_NO;
    private String MEM_NAME;
    private String MEM_GEN;

    public GeneralMember(String MEM_NO, String MEM_NAME, String MEM_GEN) {
        super();
        this.MEM_NO = MEM_NO;
        this.MEM_NAME = MEM_NAME;
        this.MEM_GEN = MEM_GEN;
    }

    public String getMEM_NO() {
        return MEM_NO;
    }

    public void setMEM_NO(String MEM_NO) {
        this.MEM_NO = MEM_NO;
    }

    public String getMEM_NAME() {
        return MEM_NAME;
    }

    public void setMEM_NAME(String MEM_NAME) {
        this.MEM_NAME = MEM_NAME;
    }

    public String getMEM_GEN() {
        return MEM_GEN;
    }

    public void setMEM_GEN(String MEM_GEN) {
        this.MEM_GEN = MEM_GEN;
    }
}
