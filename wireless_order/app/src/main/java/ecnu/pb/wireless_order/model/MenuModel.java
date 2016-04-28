package ecnu.pb.wireless_order.model;

import java.util.List;

public class MenuModel {
//
//    private int id;
//    private int photo;
//    private String name;
//    private int price;
//    private int choose;
//    private String intro;
//    private int num;

    private List<MealModel> menu;

    public MenuModel() {}

//    public MenuModel (int id, String name, int price, int photo, int choose, String intro, int num) {
//        this.name = name;
//        this.id = id;
//        this.photo = photo;
//        this.price = price;
//        this.choose = choose;
//        this.intro = intro;
//        this.num = num;
//    }

    public void setMealModels(List<MealModel> mealModels) {
        this.menu = mealModels;
    }

    public List<MealModel> getMealModels() {
        return menu;
    }

//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public void setPrice(int price) {
//        this.price = price;
//    }
//
//    public void setChoose(int choose) {
//        this.choose = choose;
//    }
//
//    public void setPhoto(int photo) {
//        this.photo = photo;
//    }
//
//    public void setIntro(String intro) {
//        this.intro = intro;
//    }
//
//    public void setNum(int num) {
//        this.num = num;
//    }
//
//    public int getChoose() {
//        return choose;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getPhoto() {
//        return photo;
//    }
//
//    public String getIntro() {
//        return intro;
//    }
//
//    public int getNum() {
//        return num;
//    }
}
