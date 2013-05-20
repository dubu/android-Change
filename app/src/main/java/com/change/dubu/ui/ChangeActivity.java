package com.change.dubu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.InjectView;
import com.change.dubu.R;
import com.change.dubu.core.TimerService;
import com.squareup.otto.Bus;

import javax.inject.Inject;
import java.util.Random;

/**
 * User: kingkingdubu
 * Date: 13. 5. 21
 * Time: 오전 7:40
 */
public class ChangeActivity extends BootstrapFragmentActivity implements View.OnClickListener {

    @Inject
    Bus BUS;

    @InjectView(R.id.txt_up) protected TextView txtUp;
    @InjectView(R.id.txt_down) protected TextView txtDown;
    @InjectView(R.id.txt_cont) protected TextView txtCont;

    @InjectView(R.id.btn_up) protected Button btnUp;
    @InjectView(R.id.btn_down) protected Button btnDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.change);
        setTitle(R.string.change);

        btnUp.setOnClickListener(this);
        btnDown.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BUS.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BUS.unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_up:
                generateChangeUp();
                break;
            case R.id.btn_down:
                generateChangeDown();
                break;
        }
    }

    private void generateChangeUp() {

        Random rand = new Random();
        int upRanNum = rand.nextInt(DubuChanges.values().length);
        DubuChanges up = DubuChanges.values()[upRanNum];
        txtUp.setText(up.name);

        descChange();

    }

    private void descChange() {

        String up = txtUp.getText().toString();
        String down = txtDown.getText().toString();



        Changes rs = Changes.findName(up+down);
        if(rs != null){
            txtCont.setText(rs.toString());
        }
    }

    private void generateChangeDown() {
        Random rand = new Random();
        int downRanNum = rand.nextInt(DubuChanges.values().length);
        DubuChanges down = DubuChanges.values()[downRanNum];
        txtDown.setText(down.name);

        descChange();
    }

    public enum Changes {

        // 벼락뇌
        P100100("100100","중뢰")
        ,P110100("110100","뇌택")
        ,P111100("111100","뇌천")
        ,P101100("101100","뇌화")

        ,P011100("011100","뇌풍")
        ,P001100("001100","뇌산")
        ,P000100("000100","뇌지")
        ,P010100("010100","뇌수")

        // 연못택
        ,P100110("100110","택뢰")
        ,P110110("110110","택산")
        ,P111110("111110","택천")
        ,P101110("101110","택화")

        ,P011110("011110","택풍")
        ,P001110("001110","택산")
        ,P000110("000110","택지")
        ,P010110("010110","택수")

        // 하늘천
        ,P100111("100111","천뢰")
        ,P110111("110111","천택")
        ,P111111("111111","중천")
        ,P101111("101111","천화")

        ,P011111("011111","천풍")
        ,P001111("001111","천산")
        ,P000111("000111","천지")
        ,P010111("010111","천수")

        // 불화
        ,P100101("100101","화뢰")
        ,P110101("110101","화택")
        ,P111101("111101","화천")
        ,P101101("101101","중화")

        ,P011101("011101","화픙")
        ,P001101("001101","화산")
        ,P000101("000101","화지")
        ,P010101("010101","화수")

        // 바람풍
        ,P100011("100011","풍뢰")
        ,P110011("110011","풍택")
        ,P111011("111011","풍천")
        ,P101011("101011","풍화")

        ,P011011("011011","중풍")
        ,P001011("001011","풍산")
        ,P000011("000011","풍지")
        ,P010011("010011","풍수")

        // 뫼산
        ,P100001("100001","산뢰")
        ,P110001("110001","산택")
        ,P111001("111001","산천")
        ,P101001("101001","산화")

        ,P011001("011001","산풍")
        ,P001001("001001","중산")
        ,P000001("000001","산지")
        ,P010001("010001","산수")

        // 땅지
        ,P100000("100000","지뢰")
        ,P110000("110000","지택")
        ,P111000("111000","지천")
        ,P101000("101000","지화")

        ,P011000("011000","지풍")
        ,P001000("001000","지산")
        ,P000000("000000","중지")
        ,P010000("010000","지수")

        // 물수
        ,P100010("100010","수뢰")
        ,P110010("110010","수택")
        ,P111010("111010","수천")
        ,P101010("101010","수화")

        ,P011010("011010","수풍")
        ,P001010("001010","수산")
        ,P000010("000010","수지")
        ,P010010("010010","중수");

        int val;
        String name;
        String word;
        String desc;

        DubuChanges up;
        DubuChanges down;

        Changes(String name, String word) {
            this.name = name;
            this.val = convertTenTobitValue(name);
            this.word = word;

            int upVal = Integer.valueOf(name)%1000;
            int downVal = Integer.valueOf(name)/1000;

            this.up = DubuChanges.findName(String.format("%03d", upVal));
            this.down = DubuChanges.findName(String.format("%03d", downVal));
        }

        int convertTenTobitValue(String str) {

            int tenValue =  Integer.valueOf(str);
            int val = 0;

            val = val + tenValue / 100000 * 32;
            tenValue = tenValue % 100000;

            val = val + tenValue / 10000 * 16;
            tenValue = tenValue % 10000;

            val = val + tenValue / 1000 * 8;
            tenValue = tenValue % 1000;

            val = val + tenValue / 100 * 4;
            tenValue = tenValue % 100;

            val = val + tenValue / 10 * 2;
            tenValue = tenValue % 10;

            val = val + tenValue;

            return val;
        }

        public static Changes findName(String name){
            for (int i =0 ; i < Changes.values().length ; i++){
                if(Changes.values()[i].name.equals(name)){
                    return Changes.values()[i];
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.name + " " +
                    this.word + " " +
                    this.desc ;
        }
    }


    public enum DubuChanges  {
        // 벼락뇌 변화 변동 , 정신없이 행하다.. 용이 승천하는 맑은 상승
        P100("100","벼락뇌 변화 변동 , 정신없이 행하다. 용이 승천하는 맑은 상승")
        // 연못택 양의 실체화 되어 보이다. 변화의 끝. 모임. 결실, 양극의 이전이라 조심하라.
        ,P110("110","연못택 양의 실체화 되어 보이다. 변화의 끝. 모임. 결실, 양극의 이전이라 조심하라")
        // 하늘건 양의끝 건실. 쌓아둔 양이 최대인 상태. 고요. 정한 상태.
        ,P111("111","하늘천 양의끝 건실")
        // 불화  화려한 불 쌓아둔 장작이 많아 불이 빛을 발하다 이별, 남쪽, 식다. 홧토불. 따듯한 온기
        ,P101("101","화려한 불 쌓아둔 장작이 많아 불이 빛을 발하다 이별, 남쪽. 식다. 홧토불. 따듯한 온기")

        // 바람풍  바람따라 유순히 흘러가다. 찬기운. 시원함.
        ,P001("001","바람풍  바람따라 유순히 흘러가다")
        // 뫼산 정지, 음극의 이전이라 조심하라. 상승 기운이 더할 수록 산이 높아져 건다.
        ,P011("011","뫼산 정지, 음극의 이전이라 조심하라.")
        // 땅지 음의끝 유순 음의 끝 더하는 음이 적어도 쌓아둔 음은 최대인 상태. 평정한 상태. 정한 상태. 출정. 다시 시작
        ,P000("000","땅지 음의끝 유순")
        // 물, 험난, 북쪽, 얼음 녹는다.
        ,P010("010","물, 험난, 북쪽");

        int id;
        String name;
        int val;

        String korMean;
        String engMean;

        String desc;

        DubuChanges(String name,  String desc) {
            this.name = name;
            this.val = convertTenTobitValue(name);
            this.desc = desc;
        }

        DubuChanges(String val, int id, String name, String engMean, String korMean, String desc) {
            this.id = id;
            this.name = name;
            this.val = convertTenTobitValue(val);
            this.korMean = korMean;
            this.engMean = engMean;
            this.desc = desc;

        }

        public static DubuChanges findName(String name){
            for (int i =0 ; i < DubuChanges.values().length ; i++){
                if(DubuChanges.values()[i].name.equals(name)){
                    return DubuChanges.values()[i];
                }
            }
            return null;
        }

        int convertTenTobitValue(String str) {

            int tenValue =  Integer.valueOf(str);
            int val = 0;

            val = val + tenValue / 100000 * 32;
            tenValue = tenValue % 100000;

            val = val + tenValue / 10000 * 16;
            tenValue = tenValue % 10000;

            val = val + tenValue / 1000 * 8;
            tenValue = tenValue % 1000;

            val = val + tenValue / 100 * 4;
            tenValue = tenValue % 100;

            val = val + tenValue / 10 * 2;
            tenValue = tenValue % 10;

            val = val + tenValue;

            return val;
        }

        @Override
        public String toString() {
            String intStringVal = Integer.toBinaryString(this.val);
            return  this.val + " " +
                    //String.format("%04d", 1232) + " " +
                    //Integer.toBinaryString(this.val) + " " +
                    String.format("%03d", Integer.valueOf(intStringVal)) + " " +
                    this.desc;
        }
    }
}
