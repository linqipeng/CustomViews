package xyz.no21.customsview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.barrageView, R.id.activity_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.barrageView:
                startActivity(new Intent(this,BarrageActivity.class));
                break;
            case R.id.activity_main:
                break;
        }
    }
}
