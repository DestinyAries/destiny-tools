package com.destiny.log;

import com.destiny.log.annotation.SensitiveData;
import com.destiny.log.enums.SensitiveDataTypeEnum;

/**
 * user entity for test
 * @Author linwanrong
 * @Date 2019/9/18 21:01
 */
public class User {
    @SensitiveData(SensitiveDataTypeEnum.CN_NAME)
    private String name;
    private int age;
    private String position;

    @SensitiveData(SensitiveDataTypeEnum.ID_CARD)
    private String idCard;

    @SensitiveData(SensitiveDataTypeEnum.BANK_CARD)
    private String bankCard;

    @SensitiveData(SensitiveDataTypeEnum.PHONE)
    private String phone;

    @SensitiveData(SensitiveDataTypeEnum.V_CODE_NUM_6)
    private String code;

    public User() {
    }

    public User(String name, int age, String position) {
        this.name = name;
        this.age = age;
        this.position = position;
    }

    public User(String name, int age, String position, String idCard, String bankCard, String phone) {
        this.name = name;
        this.age = age;
        this.position = position;
        this.idCard = idCard;
        this.bankCard = bankCard;
        this.phone = phone;
    }

    public User(String name, int age, String position, String idCard, String bankCard, String phone, String code) {
        this.name = name;
        this.age = age;
        this.position = position;
        this.idCard = idCard;
        this.bankCard = bankCard;
        this.phone = phone;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
