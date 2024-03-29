package com.hlj.test.javase;

import com.hlj.test.entity.Person;

public class TestTransferValue {
    public void changeValue1(int age){
        age=30;
    }
    public void changeValue2(Person person){
        person.setPersonName("xxx");
    }
    public void changeValue3(String str){
        str="xxx";
    }

    /**
     * 栈管运行，堆管存储
     *
     * @param args
     */
    public static void main(String[] args) {
        TestTransferValue test = new TestTransferValue();
        int age=20;
        test.changeValue1(age);
        System.out.println("age------"+age);

        Person person = new Person("abc");
        test.changeValue2(person);
        System.out.println("personName--------"+person.getPersonName());


        String str="abc";
        test.changeValue3(str);
        System.out.println("String------------"+str);
    }
}
