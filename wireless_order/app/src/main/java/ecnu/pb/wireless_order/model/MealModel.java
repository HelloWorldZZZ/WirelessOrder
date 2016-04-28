package ecnu.pb.wireless_order.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MealModel implements Parcelable {

    private int meal_id;
    private int meal_type;
    private String meal_name;
    private int meal_price;
    private String meal_image_url;
    private String meal_intro;
    private int count;

    public MealModel(int id, int type, String name, int price, String photo, String intro, int count) {
        this.meal_id = id;
        this.meal_type = type;
        this.meal_name = name;
        this.meal_price = price;
        this.meal_image_url = photo;
        this.meal_intro = intro;
        this.count = count;
    }

    protected MealModel(Parcel in) {
        meal_id = in.readInt();
        meal_type = in.readInt();
        meal_name = in.readString();
        meal_price = in.readInt();
        meal_image_url = in.readString();
        meal_intro = in.readString();
        count = in.readInt();
    }

    public static final Creator<MealModel> CREATOR = new Creator<MealModel>() {
        @Override
        public MealModel createFromParcel(Parcel in) {
            return new MealModel(in);
        }

        @Override
        public MealModel[] newArray(int size) {
            return new MealModel[size];
        }
    };

    public int getCount() {
        return count;
    }

    public int getMeal_id() {
        return meal_id;
    }

    public int getMeal_type() {
        return meal_type;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public int getMeal_price() {
        return meal_price;
    }

    public String getMeal_image_url() {
        return meal_image_url;
    }

    public String getMeal_intro() {
        return meal_intro;
    }

    public void setCount(int count) {
        this.count =count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(meal_id);
        dest.writeInt(meal_type);
        dest.writeString(meal_name);
        dest.writeInt(meal_price);
        dest.writeString(meal_image_url);
        dest.writeString(meal_intro);
        dest.writeInt(count);
    }
}
