package software.msouti.veginfo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import software.msouti.veginfo.R;
import software.msouti.veginfo.Utils.Tools;
import software.msouti.veginfo.Utils.VegListType;

public class VegDetailsActivity extends AppCompatActivity {
@BindView(R.id.d_toolbar)
    Toolbar toolbar;
@BindView(R.id.d_headerImageView)
    ImageView header;
@BindView(R.id.wtlfBody)
    TextView whatToLookFore;
@BindView(R.id.avaBody)
    TextView availability;
@BindView(R.id.storeBody)
    TextView stor;
@BindView(R.id.htpBody)
    TextView howToPrepare;
@BindView(R.id.wteBody)
    TextView waysToEat;
@BindView(R.id.cmBody)
    TextView cookingMethod;
@BindView(R.id.retalBody)
    TextView retailing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veg_details);
        ButterKnife.bind(this);
        VegListType list = (VegListType) Objects.requireNonNull(getIntent().getExtras()).get(getString(R.string.intentListKey));

        toolbar.setTitle(Objects.requireNonNull(list).getTitle());
        Tools.loadPosterImage(list.getImagePath(),header);
        header.setContentDescription(list.getTitle());
        whatToLookFore.setText(list.getWhatToLookFor());
        availability.setText(list.getAvailability());
        stor.setText(list.getStore());
        howToPrepare.setText(list.getHowToPrepare());
        waysToEat.setText(list.getWaysToEat());
        cookingMethod.setText(list.getCookingMethods());
        retailing.setText(list.getRetailing());

    }
}
