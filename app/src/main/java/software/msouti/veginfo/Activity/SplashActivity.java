package software.msouti.veginfo.Activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import software.msouti.veginfo.R;
import software.msouti.veginfo.Utils.Tools;
import software.msouti.veginfo.Utils.VegListType;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.image_logo)
    ImageView imageView;
    @BindView(R.id.text_under_Logo)
    ImageView imageView_text;
    ArrayList<VegListType> retArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity2);
        ButterKnife.bind(this);
        setAnimations();
        retArray = new ArrayList<>();
        getInfo();
        new myTask().execute();

    }

    public void getInfo() {
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

    public void snapshotToArray(DataSnapshot snapshot) {
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
                retArray.add(new VegListType(title, whatToLookFor, availability, store, howToPrepare, waysToEat, cookingMethods, retailing, Advice, imagePath));
            }
        }

    }


    private void done() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putParcelableArrayListExtra(getString(R.string.intentListKey), retArray);
        startActivity(intent);
        finish();
    }

    private class myTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                //wait until Animaition finish
                sleep(3000);
                // then check if data ready or not
                for (int i=0;i<10;i++){
                    // Wait time max 12 second
                    if (retArray.size()==0){
                        // if not wait for another second
                        sleep(1000);
                    }else{
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //ConnectivityTest and if There is no Data
            if(!Tools.isConnectionAvailable(SplashActivity.this) && retArray.size()==0){
                Toast.makeText(SplashActivity.this, getString(R.string.no_connection_splash), Toast.LENGTH_SHORT).show();
            }else{
                done();

            }
        }
    }



    private void setAnimations(){
        ObjectAnimator scalXanimation = ObjectAnimator.ofFloat(imageView_text,"scaleX",5.0F,1.0F);
        scalXanimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scalXanimation.setDuration(1700);
        ObjectAnimator scalYanimation = ObjectAnimator.ofFloat(imageView_text,"scaleY",5.0F,1.0F);
        scalYanimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scalYanimation.setDuration(1700);
        ObjectAnimator alphAnimation = ObjectAnimator.ofFloat(imageView_text,"alpha",0.0F,1.0F);
        alphAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphAnimation.setDuration(1700);

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(scalXanimation).with(scalYanimation).with(alphAnimation);
        animatorSet.setStartDelay(300);
        animatorSet.start();

        imageView.setAlpha(1.0F);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.translate_top_to_center);
        imageView.startAnimation(anim);

    }

}


