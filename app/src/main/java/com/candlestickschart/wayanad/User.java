package com.candlestickschart.wayanad;

public class User {
    String username;
    String vidhansabha;
    String area;
    String searchNum;
    public User(String username,
            String vidhansabha,
            String area,
            String searchNum) {
        this.area = area;
        this.searchNum = searchNum;
        this.username = username;
        this.vidhansabha = vidhansabha;
    }
    public String getUsername(){
        return this.username;
    }
    public String getVidhansabha(){
        return this.vidhansabha;
    }
    public String getArea(){
        return this.area;
    }
    public String getSearchNum(){
        return this.searchNum;
    }
}
