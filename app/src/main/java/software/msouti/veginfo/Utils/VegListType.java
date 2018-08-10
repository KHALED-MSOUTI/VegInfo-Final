package software.msouti.veginfo.Utils;

import android.os.Parcel;
import android.os.Parcelable;

public class VegListType implements Parcelable {

    public VegListType(Parcel source){
        /*
         * Reconstruct from the Parcel
         */
        title = source.readString();
        whatToLookFor = source.readString();
        availability = source.readString();
        store = source.readString();
        howToPrepare = source.readString();
        waysToEat = source.readString();
        cookingMethods = source.readString();
        retailing = source.readString();
        imagePath = source.readString();
        Advice = source.readString();

    }

    public static final Creator<VegListType> CREATOR = new Creator<VegListType>() {
        @Override
        public VegListType createFromParcel(Parcel in) {
            return new VegListType(in);
        }

        @Override
        public VegListType[] newArray(int size) {
            return new VegListType[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;
    String whatToLookFor;
    String availability;
    String store;
    String howToPrepare;
    String waysToEat;
    String cookingMethods;
    String retailing;
    String imagePath;
    String Advice;

    public String getAdvice() {
        return Advice;
    }

    public void setAdvice(String advice) {
        Advice = advice;
    }

    public VegListType(String title, String whatToLookFor, String availability, String store, String howToPrepare, String waysToEat, String cookingMethods, String retailing, String Advice , String imagePath) {
        this.title = title;
        this.whatToLookFor = whatToLookFor;
        this.availability = availability;
        this.store = store;
        this.howToPrepare = howToPrepare;
        this.waysToEat = waysToEat;
        this.cookingMethods = cookingMethods;
        this.retailing = retailing;
        this.Advice=Advice;

        this.imagePath = imagePath;
    }

    public String getWhatToLookFor() {
        return whatToLookFor;
    }

    public void setWhatToLookFor(String whatToLookFor) {
        this.whatToLookFor = whatToLookFor;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getHowToPrepare() {
        return howToPrepare;
    }

    public void setHowToPrepare(String howToPrepare) {
        this.howToPrepare = howToPrepare;
    }

    public String getWaysToEat() {
        return waysToEat;
    }

    public void setWaysToEat(String waysToEat) {
        this.waysToEat = waysToEat;
    }

    public String getCookingMethods() {
        return cookingMethods;
    }

    public void setCookingMethods(String cookingMethods) {
        this.cookingMethods = cookingMethods;
    }

    public String getRetailing() {
        return retailing;
    }

    public void setRetailing(String retailing) {
        this.retailing = retailing;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(whatToLookFor);
        dest.writeString(availability);
        dest.writeString(store);
        dest.writeString(howToPrepare);
        dest.writeString(waysToEat);
        dest.writeString(cookingMethods);
        dest.writeString(retailing);
        dest.writeString(imagePath);
        dest.writeString(Advice);

    }

}
