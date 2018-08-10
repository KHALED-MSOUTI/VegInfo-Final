package software.msouti.veginfo.Activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import software.msouti.veginfo.Adapter.VegAdapter;
import software.msouti.veginfo.R;
import software.msouti.veginfo.Utils.VegListType;

public class MainActivity extends AppCompatActivity implements VegAdapter.ListItemClickListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.headerImageView) ImageView header;
    @BindView(R.id.mainRecyclerView) RecyclerView recyclerView;
    @BindView(R.id.adView2) AdView mAdView;
    @BindView(R.id.mainScrollView)
    NestedScrollView scrollView;
    GridLayoutManager gridLayoutManager;
    ArrayList<VegListType> list;
    VegAdapter adapter;
    ArrayList<VegListType> result;
    @BindView(R.id.search_view)
    SearchView searchView;

    private static final String SCROLL_OFFSET = "SCROLL_OFFSET";
    private int mOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mOffset = savedInstanceState.getInt(SCROLL_OFFSET, 0);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        header.setContentDescription(getString(R.string.app_name));
        list= new ArrayList<>();
        list= Objects.requireNonNull(getIntent().getExtras()).getParcelableArrayList("list");
        toolbar.setTitle(R.string.app_name);
        gridLayoutManager= new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        runAnimations(recyclerView);
        recyclerView.smoothScrollBy(0, mOffset);


        //Load AdView with sample ad Unit Id (for test propose )
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.sample_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        recyclerView.smoothScrollBy(0, mOffset);
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(this,VegDetailsActivity.class);

        if (result==null){
            intent.putExtra(getString(R.string.intentListKey),list.get(clickedItemIndex));
        }else {
            intent.putExtra(getString(R.string.intentListKey),result.get(clickedItemIndex));
        }

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.smoothScrollBy(0, mOffset);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        ComponentName cn = new ComponentName(this, MainActivity.class);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(Objects.requireNonNull(searchManager).getSearchableInfo(cn));
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!=null && !newText.isEmpty()){
                    result = new ArrayList<>();
                    for (int i=0;i<=list.size()-1;i++) {
                        if(list.get(i).getTitle().contains(newText.trim())||list.get(i).getTitle().contains(newText.trim().toUpperCase())){
                            result.add(list.get(i));
                        }
                    }
                    adapter = new VegAdapter(MainActivity.this, result);
                    recyclerView.setAdapter(adapter);
                    recyclerView.smoothScrollBy(0, mOffset);

                }else {
                    result=null;
                    adapter = new VegAdapter(MainActivity.this, list);
                    recyclerView.setAdapter(adapter);
                    recyclerView.smoothScrollBy(0, mOffset);
                }
                return true;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if (!queryTextFocused) {
                    result=null;
                    MenuItemCompat.collapseActionView(searchItem);
                }
            }
        });

        return true;
    }

    private void runAnimations(RecyclerView rView){
        Context context=rView.getContext();
        LayoutAnimationController controller=
                AnimationUtils.loadLayoutAnimation(context,R.anim.layout_slide_from_bot);

        adapter = new VegAdapter(this, list);
        rView.setAdapter(adapter);
        rView.setLayoutAnimation(controller);
        rView.getAdapter().notifyDataSetChanged();
        rView.scheduleLayoutAnimation();
        recyclerView.smoothScrollBy(0, mOffset);
    }
    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt(SCROLL_OFFSET, recyclerView.computeVerticalScrollOffset());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mOffset = savedInstanceState.getInt(SCROLL_OFFSET, 0);
            recyclerView.smoothScrollBy(0, mOffset);


        }
    }


}
