package hoandeptraicompany.com.tienganhnhamnhi.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import hoandeptraicompany.com.tienganhnhamnhi.Database.QueryEnglisTable;
import hoandeptraicompany.com.tienganhnhamnhi.ObjectClass.EnglishClass;
import hoandeptraicompany.com.tienganhnhamnhi.R;

import static android.R.attr.data;
import static android.R.attr.firstDayOfWeek;
import static android.R.attr.focusable;
import static android.R.attr.id;
import static android.R.attr.queryActionMsg;
import static android.R.attr.theme;
import static android.R.attr.track;

public class MainActivity extends AppCompatActivity {
    private CardView cdOpenABox;
    private CardView cdShare;
    private TextView txtCoin;
    private TextView txtQuestion;
    private Button btnHint1, btnHint2, btnHint3, btnHint4, btnHint5, btnHint6, btnHint7, btnHint8, btnHint9, btnHint10, btnHint11, btnHint12, btnHint13, btnHint14, btnHint15, btnHint16;
    private Button btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4, btnAnswer5, btnAnswer6, btnAnswer7, btnAnswer8, btnAnswer9, btnAnswer10, btnAnswer11, btnAnswer12, btnAnswer13, btnAnswer14, btnAnswer15, btnAnswer16;
    private List<Button> buttonHintMgr;
    private List<Button> buttonAnswerMgr;
    private List<EnglishClass> listQuestion;
    private int level;
    private String answer="";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComps();
        addEvent();
        getListQuestion(this);
        playGame();

    }

    private void playGame() {
        SharedPreferences share = getSharedPreferences("state", level);
        level = share.getInt("level", 0);
        EnglishClass question = listQuestion.get(level);
        displayQuestion(question);
        handingAnswerBox(question);
        handingHintBox(question);


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
        Log.d("length",""+lengthAnswer);
        for (int i = 15; i > (lengthAnswer - 1); i--) {
            Log.d("length",i+"");
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
    }

    private void traLoi(View view, int i) {
        for (int j = 0; j < 15; j++) {
            Button button = buttonAnswerMgr.get(j);
            if (button.getText().toString().trim().equals("") || button.getText() == null) {
                button.setText(((Button) view).getText().toString());
                ((Button) view).setText("");
                answer = answer + button.getText().toString().trim();
                Log.d("answer:", answer);
                if (checkResult(answer)){
                    Toast.makeText(MainActivity.this,"Bạn dã trả lời đúng",Toast.LENGTH_SHORT).show();
                    nextQuestion();

                }


                return;

            }
        }
    }

    private void nextQuestion() {
        answer="";
        level=level+1;
        SharedPreferences share = getSharedPreferences("state", level);
        SharedPreferences.Editor edit=share.edit();
        edit.putInt("level",level);
        edit.commit();
        for (int i=0;i<16;i++){
            buttonHintMgr.get(i).setText("");
            buttonAnswerMgr.get(i).setText("");
            buttonAnswerMgr.get(i).setVisibility(View.VISIBLE);
        }
        playGame();
    }

    public  boolean checkResult(String ans){
        Log.d("answer",ans+"-"+this.listQuestion.get(level).getVietnamese().trim());
        if (answer.trim().equals(this.listQuestion.get(level).getVietnamese().trim())){
            return true;
        }else {
            return false;
        }
    }
    public  boolean checkAnswerComplete(String answer){
        if (answer.trim().length()==this.listQuestion.get(level).getVietnamese().trim().length()){
            return true;
        }else {
            return false;
        }
    }

    private void initComps() {
        findView();
        addButtonHintMgr();
        addButtonAnswerMgr();

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
        cdOpenABox = (CardView) findViewById(R.id.cdOpenABox);
        cdShare = (CardView) findViewById(R.id.cdShare);
        txtCoin = (TextView) findViewById(R.id.txtCoin);
        txtQuestion = (TextView) findViewById(R.id.txtQuestrion);

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


}
