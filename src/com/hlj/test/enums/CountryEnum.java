package com.hlj.test.enums;

public enum CountryEnum {

    ONE(1,"齐"),
    TWO(2,"楚"),
    THREE(3,"燕"),
    FOUR(4,"赵"),
    FIVE(5,"魏"),
    SIX(6,"韩");
    private Integer retcode;
    private String retMessage;

    public Integer getRetcode() {
        return retcode;
    }

    public void setRetcode(Integer retcode) {
        this.retcode = retcode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    CountryEnum(Integer retcode, String retMessage) {
        this.retcode = retcode;
        this.retMessage = retMessage;
    }

    public static CountryEnum forEach_CountryEnum(int index){
        CountryEnum[] myArray = CountryEnum.values();
        for (CountryEnum element : myArray) {
            if (index==element.getRetcode()){
                return element;
            }
        }
        return null;
    }
}
