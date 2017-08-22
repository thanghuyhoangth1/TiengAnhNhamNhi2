package hoandeptraicompany.com.tienganhnhamnhi.Activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import hoandeptraicompany.com.tienganhnhamnhi.Database.QueryEnglisTable;
import hoandeptraicompany.com.tienganhnhamnhi.ObjectClass.EnglishClass;
import hoandeptraicompany.com.tienganhnhamnhi.R;

import static android.R.attr.button;
import static android.R.attr.data;
import static android.R.attr.firstDayOfWeek;
import static android.R.attr.focusable;
import static android.R.attr.id;
import static android.R.attr.logo;
import static android.R.attr.queryActionMsg;
import static android.R.attr.theme;
import static android.R.attr.track;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CONFIRMACTIVIATY = 0;
    public static final int RESULT_CONFIRMACTIVIATY = 1;

    private CardView cdOpenABox;
    private TextView txtLevel;
    private TextView txtCoint;
    private ImageView btnOpenABox;
    private ImageView btnShare;
    private ImageView btnHelp;
    private ImageView btnBack;
    private int coin = 250;

    //    private CardView cdShare;
    private TextView txtCoin;
    private TextView txtQuestion;
    private Button btnHint1, btnHint2, btnHint3, btnHint4, btnHint5, btnHint6, btnHint7, btnHint8, btnHint9, btnHint10, btnHint11, btnHint12, btnHint13, btnHint14, btnHint15, btnHint16;
    private Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4, btnAnswer5, btnAnswer6, btnAnswer7, btnAnswer8, btnAnswer9, btnAnswer10, btnAnswer11, btnAnswer12, btnAnswer13, btnAnswer14, btnAnswer15, btnAnswer16;
    private List<Button> buttonHintMgr;
    private List<Button> buttonAnswerMgr;
    private List<EnglishClass> listQuestion;
    private int level;
    private TextView txtNotifi;
    private String answer = "";
    private int indexQuestion;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComps();
        addEvent();
        getListQuestion(this);
        Log.d("checklevel", "oncreat");
        playGame();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CONFIRMACTIVIATY) {
            if (resultCode == RESULT_CONFIRMACTIVIATY) {
                nextQuestion();
            }
        }
    }

    public void showConfirmActivity() {
//
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_confirm);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnNext = (Button) dialog.findViewById(R.id.btnNext);
        setFontForButton(btnNext);
        TextView txtHappy = (TextView) dialog.findViewById(R.id.txtHappy);
        setFontForTextView(txtHappy);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void playGame() {


        SharedPreferences share = getSharedPreferences("state", MODE_PRIVATE);
        level = share.getInt("level", 0);
//        int indexQuestionFirs = ran.nextInt(listQuestion.size());
//        indexQuestion = share.getInt("index", indexQuestionFirs);

        Log.d("checklevel", "chayroi");
        Log.d("checklevel", level + "");
        txtLevel.setText("Level " + level);
        coin = share.getInt("coin", 250);
        txtCoin.setText(coin + "");
        EnglishClass question = listQuestion.get(level);
        displayQuestion(question);
        handingAnswerBox(question);
        handingHintBox(question);


    }

    public void setFontForTextView(TextView textView) {
        Typeface font = Typeface.createFromAsset(getAssets(), "AstounderSquaredBB.otf");
        textView.setTypeface(font);
    }

    public void setFontForButton(Button btn) {
        Typeface font = Typeface.createFromAsset(getAssets(), "AstounderSquaredBB.otf");
        btn.setTypeface(font);

    }


    private void handingHintBox(EnglishClass question) {
        Random random = new Random();
        String[] alphabet = {"A", "B", "C", "D", "E", "G", "H", "I", "K", "L", "M", "N", "O", "P", "K", "R", "S", "T", "U", "V", "X", "Y", "Z"};
        String ans = question.getVietnamese();
        int ansLenth = ans.length();
        int i = 0;
        while (i < ansLenth) {
            int index = random.nextInt(15);
            Button button = buttonHintMgr.get(index);
            if (button.getText() == null || button.getText().toString().trim().equals("")) {
                button.setText(ans.charAt(i) + "");
                i = i + 1;

            }
        }
        for (int j = 0; j < 16; j++) {
            Button button = buttonHintMgr.get(j);
            if (button.getText() == null || button.getText().toString().trim().equals("")) {
                int index = random.nextInt(alphabet.length);
                button.setText(alphabet[index]);


            }
        }
    }

    private void handingAnswerBox(EnglishClass question) {

        int lengthAnswer = question.getVietnamese().length();
        Log.d("length", "" + lengthAnswer);
        for (int i = 15; i > (lengthAnswer - 1); i--) {
            Log.d("length", i + "");
            buttonAnswerMgr.get(i).setVisibility(View.GONE);
        }

    }

    private void displayQuestion(EnglishClass question) {
        txtQuestion.setText(question.getEnglish());
    }

    private void getListQuestion(Context context) {
        QueryEnglisTable queryEnglisTable = new QueryEnglisTable(context);
        listQuestion = queryEnglisTable.getData();

    }

    private void addEvent() {
        for (int i = 0; i < 16; i++) {
            final int finalI = i;
            buttonHintMgr.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    traLoi(v, finalI);

                }
            });
        }
        for (int i = 0; i < 16; i++) {
            final int finalI = i;
            buttonAnswerMgr.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    answer = layCauTraLoi();
                    if (checkAnswerComplete(answer)) {
                        txtNotifi.setText("Sai rồi!");
                        txtNotifi.setVisibility(View.INVISIBLE);
                        for (int i = 0; i < 16; i++) {
                            buttonAnswerMgr.get(i).setBackgroundResource(R.drawable.button_nor);

                        }
                    }

                    if (!buttonAnswerMgr.get(finalI).getText().toString().trim().equals("") && buttonAnswerMgr.get(finalI).getText() != null) {
                        int index = Integer.parseInt(button.getHint() + "");
                        buttonHintMgr.get(index).setText(button.getText().toString());
                        button.setText("");
                        button.setHint("");

                    }
                }
            });
        }
        btnOpenABox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coin < 5) {
                    Toast.makeText(MainActivity.this, "Bạn không đủ coin để mở ô", Toast.LENGTH_SHORT).show();
                } else {
                    openABox();
                }
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View rootView = findViewById(android.R.id.content).getRootView();

                initShareIntent(MainActivity.this, "com.facebook.katana", getScreenShot(rootView));
            }
        });
    }


    private void openABox() {
        coin = coin - 5;
        txtCoin.setText(coin + "");
        Log.d("kiemtrachay", "bomaychaynhe");
        EnglishClass question = listQuestion.get(level);
        int length = question.getVietnamese().length();
        Random random = new Random();
        int index = 0;
        do {
            index = random.nextInt(length);

        } while (!buttonAnswerMgr.get(index).getText().toString().trim().equals(""));

        String dapan = question.getVietnamese();
        for (int i = 0; i < 16; i++) {
            Button button = buttonHintMgr.get(i);
            Log.d("checkresult", button.getText().toString().trim() + "-" + dapan.charAt(index));
            if (button.getText().toString().trim().equals(dapan.charAt(index) + "")) {
                buttonAnswerMgr.get(index).setText(button.getText().toString().trim());
                buttonAnswerMgr.get(index).setHint(i + "");
                button.setText("");
                break;
            }
        }
        answer = layCauTraLoi();
        if (checkResult(answer)) {
            Toast.makeText(MainActivity.this, "Bạn dã trả lời đúng", Toast.LENGTH_SHORT).show();

            showConfirmActivity();

        }

    }

    private void traLoi(View view, int i) {
        for (int j = 0; j < 15; j++) {
            Button button = buttonAnswerMgr.get(j);
            Log.d("kiemtravisibility", button.getVisibility() + "-" + View.VISIBLE);
            if ((button.getText().toString().trim().equals("") || button.getText() == null) && button.getVisibility() == View.VISIBLE) {
                button.setText(((Button) view).getText().toString());

                if (((Button) view).getText() != null && !((Button) view).getText().toString().equals("")) {
                    button.setHint(i + "");
                }


                ((Button) view).setText("");
//                answer = answer + button.getText().toString().trim();
                answer = layCauTraLoi();
                if (checkResult(answer)) {
                    if (checkAnswerComplete(answer)) {
                        txtNotifi.setText("Đúng rồi!");
                        txtNotifi.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(MainActivity.this, "Bạn dã trả lời đúng", Toast.LENGTH_SHORT).show();
//                    nextQuestion();
                    coin = coin + 10;
                    SharedPreferences share = getSharedPreferences("state", MODE_PRIVATE);
                    SharedPreferences.Editor edit = share.edit();
                    edit.putInt("coin", coin);
                    edit.commit();
                    showConfirmActivity();

                } else {

                    if (checkAnswerComplete(answer)) {

                        for (int k = 0; k < 16; k++) {
                            txtNotifi.setText("Sai rồi!");
                            txtNotifi.setVisibility(View.VISIBLE);
                            buttonAnswerMgr.get(k).setBackgroundResource(R.drawable.button_red);
                            YoYo.with(Techniques.Shake)
                                    .duration(700)
                                    .repeat(0)
                                    .playOn(buttonAnswerMgr.get(k));
                        }
                    }
                }


                return;

            }
        }
    }

    public void nextQuestion() {

//        QueryEnglisTable queryEnglisTable = new QueryEnglisTable(this);
////        queryEnglisTable.deleteRowQuestion(listQuestion.get(indexQuestion));
//////        listQuestion.remove(indexQuestion);
//////        Random ran = new Random();
//////        indexQuestion = ran.nextInt(listQuestion.size());
        answer = "";
        level = level + 1;
        txtNotifi.setText("");
        txtNotifi.setVisibility(View.INVISIBLE);
        SharedPreferences share = getSharedPreferences("state", level);
        SharedPreferences.Editor edit = share.edit();
        edit.putInt("level", level);
        txtLevel.setText("Level " + level);
        txtCoin.setText(coin + "");
//        edit.putInt("index", indexQuestion);
        edit.commit();
        for (int i = 0; i < 16; i++) {
            buttonHintMgr.get(i).setText("");
            buttonAnswerMgr.get(i).setText("");
            buttonAnswerMgr.get(i).setHint("");
            buttonAnswerMgr.get(i).setVisibility(View.VISIBLE);
        }
        playGame();
    }

    public boolean checkResult(String ans) {
        Log.d("answer", ans + "-" + this.listQuestion.get(level).getVietnamese().trim());
        if (answer.trim().equals(this.listQuestion.get(level).getVietnamese().trim())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkAnswerComplete(String answer) {
        if (answer.trim().length() == this.listQuestion.get(level).getVietnamese().trim().length()) {
            return true;
        } else {
            return false;
        }
    }

    public String layCauTraLoi() {
        String cautraloi = "";
        for (int i = 0; i < 16; i++) {
            cautraloi = cautraloi + buttonAnswerMgr.get(i).getText().toString();
        }
        return cautraloi;

    }


    private void initComps() {
        findView();
        addButtonHintMgr();
        addButtonAnswerMgr();
        for (int i = 0; i < 16; i++) {
            Button btnAns = buttonAnswerMgr.get(i);
            setFontForButton(btnAns);
            Button btnHint = buttonHintMgr.get(i);
            setFontForButton(btnHint);
        }

    }

    private void addButtonAnswerMgr() {
        buttonAnswerMgr = new ArrayList<>();
        buttonAnswerMgr.add(btnAnswer1);
        buttonAnswerMgr.add(btnAnswer2);
        buttonAnswerMgr.add(btnAnswer3);
        buttonAnswerMgr.add(btnAnswer4);
        buttonAnswerMgr.add(btnAnswer5);
        buttonAnswerMgr.add(btnAnswer6);
        buttonAnswerMgr.add(btnAnswer7);
        buttonAnswerMgr.add(btnAnswer8);
        buttonAnswerMgr.add(btnAnswer9);
        buttonAnswerMgr.add(btnAnswer10);
        buttonAnswerMgr.add(btnAnswer11);
        buttonAnswerMgr.add(btnAnswer12);
        buttonAnswerMgr.add(btnAnswer13);
        buttonAnswerMgr.add(btnAnswer14);
        buttonAnswerMgr.add(btnAnswer15);
        buttonAnswerMgr.add(btnAnswer16);

    }

    private void addButtonHintMgr() {
        buttonHintMgr = new ArrayList<>();
        buttonHintMgr.add(btnHint1);
        buttonHintMgr.add(btnHint2);
        buttonHintMgr.add(btnHint3);
        buttonHintMgr.add(btnHint4);
        buttonHintMgr.add(btnHint5);
        buttonHintMgr.add(btnHint6);
        buttonHintMgr.add(btnHint7);
        buttonHintMgr.add(btnHint8);
        buttonHintMgr.add(btnHint9);
        buttonHintMgr.add(btnHint10);
        buttonHintMgr.add(btnHint11);
        buttonHintMgr.add(btnHint12);
        buttonHintMgr.add(btnHint13);
        buttonHintMgr.add(btnHint14);
        buttonHintMgr.add(btnHint15);
        buttonHintMgr.add(btnHint16);

    }

    private void findView() {
        txtNotifi = (TextView) findViewById(R.id.txtNotifi);
        setFontForTextView(txtNotifi);
        btnOpenABox = (ImageView) findViewById(R.id.btnOpenABox);
        btnShare = (ImageView) findViewById(R.id.btnShare);
//        btnHelp = (ImageView) findViewById(R.id.btnHelp);
//        btnBack = (ImageView) findViewById(R.id.btnBack);
        txtLevel = (TextView) findViewById(R.id.txtLevel);
        setFontForTextView(txtLevel);
        txtCoin = (TextView) findViewById(R.id.txtCoin);
        setFontForTextView(txtCoin);
        txtQuestion = (TextView) findViewById(R.id.txtQuestrion);
        setFontForTextView(txtQuestion);
        btnHint1 = (Button) findViewById(R.id.btnHint1);
        btnHint2 = (Button) findViewById(R.id.btnHint2);
        btnHint3 = (Button) findViewById(R.id.btnHint3);
        btnHint4 = (Button) findViewById(R.id.btnHint4);
        btnHint5 = (Button) findViewById(R.id.btnHint5);
        btnHint6 = (Button) findViewById(R.id.btnHint6);
        btnHint7 = (Button) findViewById(R.id.btnHint7);
        btnHint8 = (Button) findViewById(R.id.btnHint8);
        btnHint9 = (Button) findViewById(R.id.btnHint9);
        btnHint10 = (Button) findViewById(R.id.btnHint10);
        btnHint11 = (Button) findViewById(R.id.btnHint11);
        btnHint12 = (Button) findViewById(R.id.btnHint12);
        btnHint13 = (Button) findViewById(R.id.btnHint13);
        btnHint14 = (Button) findViewById(R.id.btnHint14);
        btnHint15 = (Button) findViewById(R.id.btnHint15);
        btnHint16 = (Button) findViewById(R.id.btnHint16);

        btnAnswer1 = (Button) findViewById(R.id.btnAnswer1);
        btnAnswer2 = (Button) findViewById(R.id.btnAnswer2);
        btnAnswer3 = (Button) findViewById(R.id.btnAnswer3);
        btnAnswer4 = (Button) findViewById(R.id.btnAnswer4);
        btnAnswer5 = (Button) findViewById(R.id.btnAnswer5);
        btnAnswer6 = (Button) findViewById(R.id.btnAnswer6);
        btnAnswer7 = (Button) findViewById(R.id.btnAnswer7);
        btnAnswer8 = (Button) findViewById(R.id.btnAnswer8);
        btnAnswer9 = (Button) findViewById(R.id.btnAnswer9);
        btnAnswer10 = (Button) findViewById(R.id.btnAnswer10);
        btnAnswer11 = (Button) findViewById(R.id.btnAnswer11);
        btnAnswer12 = (Button) findViewById(R.id.btnAnswer12);
        btnAnswer13 = (Button) findViewById(R.id.btnAnswer13);
        btnAnswer14 = (Button) findViewById(R.id.btnAnswer14);
        btnAnswer15 = (Button) findViewById(R.id.btnAnswer15);
        btnAnswer16 = (Button) findViewById(R.id.btnAnswer16);


    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void shareImage() {

    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public static void initShareIntent(Context context, String type, Bitmap shareBody) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        shareBody.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), shareBody, "Title", null);
        Uri imageUri = Uri.parse(path);

        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("image/jpeg");

        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type)) {
                    share.putExtra(Intent.EXTRA_STREAM, imageUri);
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found) {
                Toast.makeText(context, "Ứng dụng này chưa được cài đặt", Toast.LENGTH_SHORT).show();
                return;
            }

            context.startActivity(Intent.createChooser(share, "Share via"));
        }
    }

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

}
