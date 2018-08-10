package software.msouti.veginfo.Utils;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class VegInfo extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        //Change Entire App Font
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "HTOWERTI.TTF");


        //Support Firebase Offline Data
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        //Support Picasso Offline Cache
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso builter=builder.build();
        builter.setIndicatorsEnabled(false);
        builter.setLoggingEnabled(true);
        Picasso.setSingletonInstance(builter);

    }


}
