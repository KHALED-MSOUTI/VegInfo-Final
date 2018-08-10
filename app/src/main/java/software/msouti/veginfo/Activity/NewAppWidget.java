package software.msouti.veginfo.Activity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import software.msouti.veginfo.R;
import software.msouti.veginfo.Utils.VegListType;

import static java.lang.Thread.sleep;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static String title;
    static String advice;
    static ArrayList<VegListType> arrayList;
    static Random random;
    static int r;
    static RemoteViews remoteViews;
    static Context thisContext;
    static Intent intent;
    static PendingIntent pendingIntent;

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId){
        thisContext=context;
        arrayList=new ArrayList<>();
        getInfo();
        random=new Random();
        remoteViews= new RemoteViews(context.getPackageName(),R.layout.new_app_widget);

        remoteViews.setTextViewText(R.id.appwidget_title,context.getString(R.string.plz_wait));
        appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                r=random.nextInt(arrayList.size());
                title=arrayList.get(r).getTitle();
                advice=arrayList.get(r).getAdvice();
                remoteViews.setTextViewText(R.id.appwidget_title,title);
                remoteViews.setTextViewText(R.id.appwidget_text,advice);
                if (intent!=null){
                    intent=null;
                }
                intent=new Intent(context,VegDetailsActivity.class);
                intent.putExtra(context.getString(R.string.intentListKey),arrayList.get(r));
                pendingIntent= PendingIntent.getActivity(context,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
                remoteViews.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
                appWidgetManager.updateAppWidget(appWidgetId,remoteViews);

            }
        }, 5000);   //5 seconds
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWigetId : appWidgetIds){
            updateAppWidget(context,appWidgetManager,appWigetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void getInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.keepSynced(true);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                snapshotToArray(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void snapshotToArray(DataSnapshot snapshot) {
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
        if (snapshot.hasChildren()) {
            Iterator i = snapshot.getChildren().iterator();
            while (i.hasNext()) {
                availability = (String) ((DataSnapshot) i.next()).getValue();
                howToPrepare = (String) ((DataSnapshot) i.next()).getValue();
                retailing = (String) ((DataSnapshot) i.next()).getValue();
                store = (String) ((DataSnapshot) i.next()).getValue();
                title = (String) ((DataSnapshot) i.next()).getValue();
                waysToEat = (String) ((DataSnapshot) i.next()).getValue();
                whatToLookFor = (String) ((DataSnapshot) i.next()).getValue();
                Advice = (String) ((DataSnapshot) i.next()).getValue();
                cookingMethods = (String) ((DataSnapshot) i.next()).getValue();
                imagePath = (String) ((DataSnapshot) i.next()).getValue();
                arrayList.add(new VegListType(title, whatToLookFor, availability, store, howToPrepare, waysToEat, cookingMethods, retailing, Advice, imagePath));
            }
        }


    }

}

