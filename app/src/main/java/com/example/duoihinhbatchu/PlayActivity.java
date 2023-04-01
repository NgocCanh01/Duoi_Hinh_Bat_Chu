package com.example.duoihinhbatchu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayActivity extends Activity {
    private static final int CHECK_ANWSER = 0;
    private static final int GAME_OVER = 1;
    private int heart;
    private int point;
    private Handler handler;
    private Button btntiep;
    private TextView txtHeart;
    private TextView txtPoint;
    private ImageView imgPicture;
    private LinearLayout lnAnwser1, lnAnwser2, lnCh1, lnCh2;
    private List<Question> listQuestions;
    private Random random;
    private int i = 0;
    private int pst = 0;
    private String dapan;
    private List<IDButton>  listChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        initComponets();

        makeQuestion();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case CHECK_ANWSER:
                        checkAnswer();
                        break;
                    case GAME_OVER:
                        Toast.makeText(PlayActivity.this, "GAME OVER", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void checkAnswer() {
        if (checkAnwser()) {
            Toast.makeText(PlayActivity.this, "Thiên tài !", Toast.LENGTH_SHORT).show();
            point += 100;
            txtPoint.setText(point + "");
            for (int i = 16; i < dapan.length() + 16; i++) {
                ((Button) findViewById(i)).setBackgroundResource(R.drawable.ic_tile_true);
                ((Button) findViewById(i)).setClickable(false);
            }
            btntiep.setVisibility(View.VISIBLE);
        } else {
            heart--;
            txtHeart.setText(heart + "");
            if (heart <= 0) {
                handler.sendEmptyMessage(GAME_OVER);
                return;
            }
            Toast.makeText(PlayActivity.this, "Đáp án sai !", Toast.LENGTH_SHORT).show();

            for (int i = 16; i < dapan.length() + 16; i++) {
                ((Button) findViewById(i)).setBackgroundResource(R.drawable.ic_tile_false);
            }
            if (heart <= 0) {
                handler.sendEmptyMessage(GAME_OVER);
            }

        }
    }

    //todo Hàm tạo câu hỏi
    private void makeQuestion() {

        Question qs = listQuestions.get(i);

        dapan = qs.getContext();
        LayoutInflater inflater = LayoutInflater.from(this);//Đọc xml layout file và chuyển đổi thành 1 view trong java code
        if (dapan.length() > 8) {
            //ĐÁP ÁN >8 KÍ TỰ
            for (int i = 0; i < 8; i++) {
                Button view = (Button) inflater.inflate(R.layout.item_btn_anwser, lnAnwser1, false);
                //THAM SỐ 1: (int resource)ĐỐI TƯỢNG CẦN CHUYỂN THÀNH VIEW(Ở ĐÂY LÀ NÚT CỦA CÂU TRẢ LỜI.XML)
                //THAM SỐ 2: (ViewGroup Parrent) NƠI MÀ FILE XML(THAM SỐ THỨ 1) NHÚNG VÀO
                //THAM SỐ 3: (attachToRoot) set là false là sẽ chuyển nó thành view mà không làm gì thêm

                view.setId(16 + i);//Thiết lập id cho các nút(trắng) phần trả lời

                //view là những nút trắng
                lnAnwser1.addView(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (((Button) view).getText() != "") {
                            ((Button) view).setText("");
                            for (int i = 0; i < listChar.size(); i++) {
                                if (listChar.get(i).getIdAnwser() == view.getId()) {
                                    ((Button) findViewById(listChar.get(i).getIdPick())).setVisibility(View.VISIBLE);
                                    listChar.remove(i);

                                    break;
                                }
                            }
                            pst--;
                            for (int i = 16; i < dapan.length() + 16; i++) {
                                ((Button) findViewById(i)).setBackgroundResource(R.drawable.ic_anwser);//
                            }
                        }
                    }
                });
            }
            //
            for (int i = 8; i < dapan.length(); i++) {
                Button view = (Button) inflater.inflate(R.layout.item_btn_anwser, lnAnwser2, false);
                view.setId(16 + i);
                lnAnwser2.addView(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (((Button) view).getText() != "") {
                            ((Button) view).setText("");
                            for (int i = 0; i < listChar.size(); i++) {
                                if (listChar.get(i).getIdAnwser() == view.getId()) {
                                    ((Button) findViewById(listChar.get(i).getIdPick())).setVisibility(View.VISIBLE);
                                    listChar.remove(i);
                                    break;
                                }
                            }
                            pst--;
                            for (int i = 16; i < dapan.length() + 16; i++) {
                                ((Button) findViewById(i)).setBackgroundResource(R.drawable.ic_anwser);
                            }
                        }
                    }
                });
            }
            //ĐÁP ÁN <8 KÍ TỰ
        } else {
            for (int i = 0; i < dapan.length(); i++) {
                Button view = (Button) inflater.inflate(R.layout.item_btn_anwser, lnAnwser1, false);
                view.setId(16 + i);
                lnAnwser1.addView(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (((Button) view).getText() != "") {
                            ((Button) view).setText("");
                            for (int i = 0; i < listChar.size(); i++) {
                                if (listChar.get(i).getIdAnwser() == view.getId()) {
                                    ((Button) findViewById(listChar.get(i).getIdPick())).setVisibility(View.VISIBLE);
                                    listChar.remove(i);
                                    break;
                                }
                            }
                            pst--;
                            for (int i = 16; i < dapan.length() + 16; i++) {
                                ((Button) findViewById(i)).setBackgroundResource(R.drawable.ic_anwser);
                            }
                        }
                    }
                });
            }
        }
        imgPicture.setImageResource(qs.getId());





        //Tạo các nút trả lời



        String[] kt = {"a", "b", "c", "d", "e", "g", "h", "i", "k", "l", "m", "n", "o", "u", "q", "p", "r", "s", "t", "y", "v", "x"};//22 kí tự
        List<String> tl = new ArrayList();//Tạo ra một mảng kí tự

        for (int i = 0; i < dapan.length(); i++) {
            tl.add(dapan.charAt(i) + "");
        }

        for (int i = 0; i < 16 - dapan.length(); i++) {
            tl.add(kt[random.nextInt(kt.length)]);
        }
        Collections.shuffle(tl);

        for (int i = 0; i < 8; i++) {
            Button view = (Button) inflater.inflate(R.layout.item_btn, lnCh1, false);
            view.setId(i);
            view.setText(tl.get(i));
            lnCh1.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pst < dapan.length()) {
                        Button btn = (Button) view;
                        addChar(btn.getId(), btn.getText().toString());
                        btn.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
        for (int i = 8; i < 16; i++) {
            Button view = (Button) inflater.inflate(R.layout.item_btn, lnCh2, false);
            view.setId(i);
            view.setText(tl.get(i));
            lnCh2.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pst < dapan.length()) {
                        Button btn = (Button) view;
                        addChar(btn.getId(), btn.getText().toString());
                        btn.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }





    ///Ánh xạ với add câu trả lời
    private void initComponets() {
        listChar = new ArrayList(); //DANH SÁCH nút pick và nút trả lời
        heart = 5;
        point = 0;
        random = new Random();

        btntiep = (Button) findViewById(R.id.btn_tiep);//Ktra sự kiện bấm nút "Tiếp"

        btntiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newQuestion();
            }
        });

        imgPicture = (ImageView) findViewById(R.id.img_picture);//Biến hiển thị ảnh

        lnAnwser1 = (LinearLayout) findViewById(R.id.anwser1);//Ánh xạ tới (LinearLayout)hàng 1 hiển thị answer
        lnAnwser2 = (LinearLayout) findViewById(R.id.anwser2);
        lnCh1 = (LinearLayout) findViewById(R.id.ln_3);//Ánh xạ tới (LinearLayout)hàng 1 hiển thị nút nhập đáp án
        lnCh2 = (LinearLayout) findViewById(R.id.ln_4);

        txtHeart = (TextView) findViewById(R.id.txt_heart);
        txtPoint = (TextView) findViewById(R.id.txt_point);


        txtHeart.setText(heart + "");
        txtPoint.setText(point + "");

        listQuestions = new ArrayList<>();//Tạo ra 1 ds các câu hỏi

        listQuestions.add(new Question(R.drawable.aomua, "aomua"));
        listQuestions.add(new Question(R.drawable.baocao, "baocao"));
        listQuestions.add(new Question(R.drawable.canthiep, "canthiep"));
        listQuestions.add(new Question(R.drawable.cattuong, "cattuong"));
        listQuestions.add(new Question(R.drawable.chieutre, "chieutre"));
        listQuestions.add(new Question(R.drawable.danong, "danong"));
        listQuestions.add(new Question(R.drawable.danhlua, "danhlua"));
        listQuestions.add(new Question(R.drawable.giandiep, "giandiep"));
        listQuestions.add(new Question(R.drawable.giangmai, "giangmai"));
        listQuestions.add(new Question(R.drawable.kiemchuyen, "kiemchuyen"));
        listQuestions.add(new Question(R.drawable.nambancau, "nambancau"));
        listQuestions.add(new Question(R.drawable.masat, "masat"));
        listQuestions.add(new Question(R.drawable.lancan, "lancan"));
        listQuestions.add(new Question(R.drawable.quyhang, "quyhang"));
        listQuestions.add(new Question(R.drawable.xedapdien, "xedapdien"));
        listQuestions.add(new Question(R.drawable.xakep, "xakep"));
        listQuestions.add(new Question(R.drawable.xaphong, "xaphong"));
        listQuestions.add(new Question(R.drawable.vuonbachthu, "vuonbachthu"));
        listQuestions.add(new Question(R.drawable.vuaphaluoi, "vuaphaluoi"));
        listQuestions.add(new Question(R.drawable.tranhthu, "tranhthu"));
        listQuestions.add(new Question(R.drawable.totien, "totien"));
        listQuestions.add(new Question(R.drawable.tichphan, "tichphan"));
        listQuestions.add(new Question(R.drawable.thattinh, "thattinh"));
        listQuestions.add(new Question(R.drawable.thothe, "thothe"));
        //Xáo trộn danh sách câu hỏi
        Collections.shuffle(listQuestions);

    }





    //Hàm tạo mới câu hỏi
    private void newQuestion() {
        if (i < listQuestions.size() - 1) {
            listChar.clear();
            btntiep.setVisibility(View.INVISIBLE);
            pst = 0;
            i++;
            lnAnwser1.removeAllViews();
            lnAnwser2.removeAllViews();
            lnCh1.removeAllViews();
            lnCh2.removeAllViews();
            makeQuestion(); //Hàm tạo câu hỏi
        } else {
            Toast.makeText(this, "Bạn đã trả lời hết câu hỏi !", Toast.LENGTH_LONG).show();
        }

    }

    public void addChar(int id, String s) {
        for (int i = 16; i < dapan.length() + 16; i++) {
            if (((Button) findViewById(i)).getText() == "") {
                ((Button) findViewById(i)).setText(s);
                listChar.add(new IDButton(id, i));

                pst++;
                if (pst == dapan.length()) {
                    handler.sendEmptyMessage(CHECK_ANWSER);
                }
                return;
            }
        }

    }

    public boolean checkAnwser() {
        String da = "";
        for (int i = 16; i < dapan.length() + 16; i++) {
            da += ((Button) findViewById(i)).getText();
        }
        if (da.equals(dapan)) {
            return true;
        } else
            return false;
    }
}