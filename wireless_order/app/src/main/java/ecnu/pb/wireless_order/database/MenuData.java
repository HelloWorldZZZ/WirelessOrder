//package ecnu.pb.wireless_order.database;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import ecnu.pb.wireless_order.R;
//import ecnu.pb.wireless_order.model.MenuModel;
//
//public class MenuData {
//
//    private List<MenuModel> list;
//
//    private int[] menuId = {101, 102, 103};
//    private int[] menuPhoto = {R.mipmap.img0, R.mipmap.img1, R.mipmap.img2};
//    private String[] menuName = {"菜品1", "菜品2", "菜品3"};
//    private int[] menuPrice = {12, 13, 14};
//    private String[] menuIntro = {"ASDQQQQQQQ", "DDDDDDDD", "ASSSSSSSSSS"};
//
//    private MenuManager menuManager;
//
//    public void initData() {
//        for ( int i = 0; i < menuId.length; i++) {
//            menuManager.createContentValues(new MenuModel(menuId[i], menuName[i], menuPrice[i],
//                    menuPhoto[i], 0, menuIntro[i], 0));
//        }
//    }
//
//    public List<MenuModel> getData() {
//        list = new ArrayList<>();
//        MenuModel menuModel;
//        for ( int i = 0; i < menuId.length; i++) {
//            menuModel = new MenuModel(menuId[i], menuName[i], menuPrice[i],
//                    menuPhoto[i], 0, menuIntro[i], 1);
//            list.add(menuModel);
//        }
//        return list;
//    }
//
//}
