package software.msouti.veginfo.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import software.msouti.veginfo.R;

public class Tools {

    public static final String DEFAULT_BLANK_IMAGE_LINK="https://firebasestorage.googleapis.com/v0/b/veginfo-73ba7.appspot.com/o/newHeader.jpg?alt=media&token=9962540d-6bc6-4ab0-8a90-c3b2b2bbd91d";


    public static void loadPosterImage( final String url, final ImageView imageView) {
        Picasso.get().load(url).into(imageView);
    }

    public static void loadDefaultImage(ImageView imageView){
        Picasso.get().load(DEFAULT_BLANK_IMAGE_LINK).into(imageView);
    }
    public static void setBlankTitle(TextView textView){
        textView.setText(R.string.noName);
    }

    public static boolean isConnectionAvailable(Context context) {
        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE,
                ConnectivityManager.TYPE_WIFI};
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int networkType : networkTypes) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null &&
                        activeNetworkInfo.getType() == networkType)
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
