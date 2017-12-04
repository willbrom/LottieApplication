package com.example.user.lottieapplication;

import android.content.Intent;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements RecognitionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private LottieAnimationView lottieAnimationView;
    private ImageView micImageView;
    private int i = 0;
    private Timer timer;
    private Timer secTimer;
    private SpeechRecognizer speechRecognizer;
    private Random random = new Random();
    private boolean haveAppointmentAsked = false;
    private boolean haveAppointment = false;
    private boolean bookAppointment = false;
    private boolean haveInsuranceAsked = false;
    private boolean tellNameAsked = false;
    private boolean haveFileAsked = false;
    private boolean tellFileNumber = false;
    private boolean lessonFinished = false;
    private String userName = "";
    private int secTimerCount = 0;
    private boolean selectIdAsked = false;
    private TextToSpeech textToSpeech;
    private FrameLayout bottomSheet;
    private ConstraintLayout bagBig;
    private ImageButton bag;
    private TextView nameTextView;
    private ImageButton promptItsMe;
    private Intent speechIntent;
    //    private GridView gridView;
    private BottomSheetBehavior bottomSheetBehavior;
//    private TextView textView;
    private Bundle params = new Bundle();
    private Runnable runOnMainThread = new Runnable() {
        @Override
        public void run() {
            lottieAnimationView.playAnimation(i, i += 15);
            switch (i) {
                case 15:
                    textToSpeech.speak("How can I help you?", TextToSpeech.QUEUE_FLUSH, null, null);
                    break;
                case 30:
                    textToSpeech.speak("I would like to see a doctor", TextToSpeech.QUEUE_FLUSH, null, null);
                    break;
                case 45:
                    textToSpeech.speak("Which doctor?", TextToSpeech.QUEUE_FLUSH, null, null);
                    break;
            }
            if (i == 45) {
                timer.cancel();
                micImageView.setVisibility(View.VISIBLE);
            }
        }
    };
    private Runnable runAfterIdSelected = new Runnable() {
        @Override
        public void run() {
            if (!lessonFinished) {
                micImageView.setVisibility(View.INVISIBLE);
                lottieAnimationView.playAnimation(456, 456);
                textToSpeech.speak("Please go to the reception", TextToSpeech.QUEUE_FLUSH, null, null);
                lessonFinished = true;
            } else {
                switch (secTimerCount) {
                    case 0:
                        lottieAnimationView.playAnimation(505, 525);
                        textToSpeech.speak("Mr. John", TextToSpeech.QUEUE_FLUSH, null, null);
                        secTimerCount++;
                        break;
                    case 1:
                        lottieAnimationView.playAnimation(526, 543);
                        textToSpeech.speak("Yes", TextToSpeech.QUEUE_FLUSH, null, null);
                        secTimerCount++;
                        break;
                    case 2:
                        secTimerCount = 0;
                        lottieAnimationView.playAnimation(546, 565);
                        textToSpeech.speak("Do you have insurance?", TextToSpeech.QUEUE_FLUSH, null, null);
                        selectIdAsked = false;
                        secTimer.cancel();
                        haveInsuranceAsked = true;
                        micImageView.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    };
    private boolean isRecording = false;
    private boolean whichDayAsked = false;
    private boolean morAfterEveAsked = false;
    private boolean appointmentConfirmation = false;
    private String doctorNameSelected = "";
    private String daySelected = "";
    private String timeSelected = "";
    private boolean whatTimeAsked = false;
    private boolean selectInsuranceAsked = false;
    private boolean selectcashAsked = false;
    private ImageView idImageView;
    private Runnable runAfterInsuranceSelected = new Runnable() {
        @Override
        public void run() {
            micImageView.setVisibility(View.INVISIBLE);
            switch (secTimerCount) {
                case 0:
                    lottieAnimationView.playAnimation(669, 687);
                    textToSpeech.speak("Please wait. The nurse will call your name", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount++;
                    break;
                case 1:
                    lottieAnimationView.playAnimation(733, 735);
                    textToSpeech.speak("Mr. Ahmed", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount++;
                    break;
                case 2:
                    lottieAnimationView.playAnimation(736, 738);
                    textToSpeech.speak("Mr. Ben", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount++;
                    break;
                case 3:
                    lottieAnimationView.playAnimation(739, 741);
                    textToSpeech.speak("Miss Kate", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount++;
                    break;
                case 4:
                    lottieAnimationView.playAnimation(730, 732);
                    textToSpeech.speak("Mr. John Anderson", TextToSpeech.QUEUE_FLUSH, null, null);
                    promptItsMe.setVisibility(View.VISIBLE);
                    thirdTimer.cancel();
                    secTimerCount = 0;
                    selectInsuranceAsked = false;
                    selectcashAsked = false;
                    break;
            }
        }
    };
    private Timer thirdTimer;
    private Timer fourthTimer;
    private boolean bodyPartHighlighted = false;
    private Runnable runAfterPromptShow = new Runnable() {
        @Override
        public void run() {
            switch (secTimerCount) {
                case 0:
                    lottieAnimationView.playAnimation(742, 757);
                    textToSpeech.speak("Yes", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount++;
                    break;
                case 1:
                    lottieAnimationView.playAnimation(761, 780);
                    textToSpeech.speak("Please, come with me", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount++;
                    break;
                case 2:
                    lottieAnimationView.playAnimation(783, 798);
                    textToSpeech.speak("Ok", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount++;
                    break;
                case 3:
                    lottieAnimationView.playAnimation(800, 830);
                    secTimerCount ++;
                    break;
                case 4:
                    lottieAnimationView.playAnimation(831, 852);
                    textToSpeech.speak("What's the problem?", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount ++;
                    break;
                case 5:
                    lottieAnimationView.playAnimation(889, 923);
                    secTimerCount = 0;
                    micImageView.setVisibility(View.VISIBLE);
                    bodyPartHighlighted = true;
                    promptClicked = false;
                    fourthTimer.cancel();
                    break;
            }
        }
    };
    private boolean promptClicked = false;
    private boolean forHowLongAsked = false;
    private boolean haveFeverAsked = false;
    private Timer fifthTimer;
    private boolean haveFeverAnswered = false;
    private boolean givePrescriptionAsked = false;
    private Runnable runAfterFeverAsked = new Runnable() {
        @Override
        public void run() {
            switch (secTimerCount) {
                case 0:
                    lottieAnimationView.playAnimation(1161, 1182);
                    textToSpeech.speak("Would you please lie down on the bed?", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount++;
                    break;
                case 1:
                    lottieAnimationView.playAnimation(1214, 1235);
                    textToSpeech.speak("I've examined you and i would like to run some test. Kindly, go to the lab", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount++;
                    break;
                case 2:
                    lottieAnimationView.playAnimation(1236, 1262);
                    secTimerCount++;
                    break;
                case 3:
                    lottieAnimationView.playAnimation(1263, 1299);
                    textToSpeech.speak("Have a seat please", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount ++;
                    break;
                case 4:
                    lottieAnimationView.playAnimation(1300, 1320);
                    textToSpeech.speak("Please put your hand on the table", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount ++;
                    break;
                case 5:
                    lottieAnimationView.playAnimation(1321, 1340);
                    textToSpeech.speak("ok", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount ++;
                    break;
                case 6:
                    lottieAnimationView.playAnimation(1341, 1362);
                    textToSpeech.speak("I'll send the report to your doctor and he will give you the prescription", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount++;
                    break;
                case 7:
                    lottieAnimationView.playAnimation(1387, 1408);
                    textToSpeech.speak("May i have the prescription?", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount = 0;
                    haveFeverAnswered = false;
                    givePrescriptionAsked = true;
                    fifthTimer.cancel();
                    break;
            }
        }
    };
    private Timer sixthTimer;
    private Runnable runAfterGivePrescription = new Runnable() {
        @Override
        public void run() {
            switch (secTimerCount) {
                case 0:
                    lottieAnimationView.playAnimation(1409, 1428);
                    textToSpeech.speak("Here you go", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount++;
                    break;
                case 1:
                    lottieAnimationView.playAnimation(1429, 1450);
                    textToSpeech.speak("Here is your medicine you have to take it three times a day after meal for two weeks.", TextToSpeech.QUEUE_FLUSH, null, null);
                    secTimerCount++;
                    break;
                case 2:
                    lottieAnimationView.playAnimation(1451, 1470);
                    textToSpeech.speak("Thanks", TextToSpeech.QUEUE_FLUSH, null, null);
                    givePrescriptionAsked = false;
                    secTimerCount = 0;
                    break;
            }
        }
    };
    private String mAESelected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        textView = (TextView) findViewById(R.id.text_view);
//
//        try {
//            textView.setText(loadJSONFromAsset());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        timer = new Timer();
        secTimer = new Timer();
        thirdTimer = new Timer();
        fourthTimer = new Timer();
        fifthTimer = new Timer();
        sixthTimer = new Timer();
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation_view);
        micImageView = (ImageView) findViewById(R.id.mic_imageView);
        bottomSheet = (FrameLayout) findViewById(R.id.bottom_sheet);
        bagBig = (ConstraintLayout) findViewById(R.id.bag_big);
        bag = (ImageButton) findViewById(R.id.bag);
        nameTextView = (TextView) findViewById(R.id.name_textView);
        promptItsMe = (ImageButton) findViewById(R.id.prompt_its_me);
//        gridView = (GridView) findViewById(R.id.grid_view);
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "12345");
        lottieAnimationView.playAnimation(0, 0);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        idImageView = (ImageView) findViewById(R.id.id_card_imageView);
//        gridView.setAdapter(new PocketAdapter(this));
        micImageView.setVisibility(View.INVISIBLE);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runAnimation();
            }
        }, 2000, 3000);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        micImageView.setVisibility(View.INVISIBLE);
                    }
                });
            }

            @Override
            public void onDone(String s) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        micImageView.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onError(String s) {

            }
        });

        micImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        view.setPressed(true);
                        if (!isRecording) {
                            initSpeechRecognition();
                            if (SpeechRecognizer.isRecognitionAvailable(MainActivity.this)) {
                                speechRecognizer.startListening(speechIntent);
                                micImageView.setImageResource(R.drawable.mic_busy);
                                isRecording = true;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        view.setPressed(false);
                        if (isRecording) {
                            speechRecognizer.stopListening();
                            micImageView.setImageResource(R.drawable.mic);
                        }
                        break;
                }
                return true;
            }
        });

    }

    public String loadJSONFromAsset() throws IOException {
        String json = null;
        try {
            InputStream is = getAssets().open("hospital.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private void runAnimation() {
        if (selectIdAsked)
            this.runOnUiThread(runAfterIdSelected);
        else if (selectInsuranceAsked || selectcashAsked)
            this.runOnUiThread(runAfterInsuranceSelected);
        else if (promptClicked)
            this.runOnUiThread(runAfterPromptShow);
        else if (haveFeverAnswered)
            this.runOnUiThread(runAfterFeverAsked);
        else if (givePrescriptionAsked)
            this.runOnUiThread(runAfterGivePrescription);
        else
            this.runOnUiThread(runOnMainThread);
    }

    private void initSpeechRecognition() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);
        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        speechIntent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"en"});
        speechIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
    }

    private void checkResult(String result) {
        Log.d(TAG, "checkResult approached!");
        Log.d(TAG, "hasInsurance: " + haveInsuranceAsked);
        result = result.toLowerCase();
        Log.d(TAG, result);
        String[] doctorNames = new String[]{"dr jack", "any doctor", "dr ahmad", "dr nancy", "dr ahmed", "doctor nancy", "doctor ahmad", "doctor ahmed", "doctor jack", "any doctor"};
        String[] daysOfWeek = new String[]{"monday", "tuesday", "wednesday", "thursday", "friday"};
        String[] timeOfDay = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
        String[] whatIsTheProblem = new String[]{"I have headache", "I have stomachache", "I have chest pain", "headache", "stomachache", "chest pain", "stomach ache", "head ache", "chestpain"};
        String[] problemForHowLong = new String[]{"1 day", "2 day", "3 day", "4 day", "to day", "too day"};
        String[] morAfterEve = new String[]{"morning", "afternoon", "evening"};

        if (haveAppointmentAsked || haveFileAsked) {
            Log.d(TAG, "hasappointmentAsked or havefileAsked: " + haveAppointmentAsked + haveFileAsked);
            if (TextUtils.equals(result, "yes"))
                rightAnswer("yes");
            else if (TextUtils.equals(result, "no"))
                rightAnswer("no");
            else
                wrongAnswer("");
        } else if (tellNameAsked) {
            Log.d(TAG, "tellNameAsked: " + tellNameAsked);
            userName = result;
            if (TextUtils.equals(result, "john"))
                rightAnswer(userName);
            else
                wrongAnswer("");
        } else if (selectIdAsked) {
//            micImageView.setVisibility(View.INVISIBLE);

        } else if (whichDayAsked) {
            for (String day : daysOfWeek) {
                if (TextUtils.equals(result, day)) {
                    daySelected = day;
                    rightAnswer(day);
                    return;
                }
            }
            wrongAnswer("day");
        } else if (morAfterEveAsked) {
            for (String mAE : morAfterEve) {
                if (TextUtils.equals(result, mAE)) {
                    mAESelected = mAE;
                    rightAnswer(mAE);
                    return;
                }
            }
            wrongAnswer("mAE");
        } else if (whatTimeAsked) {
            for (String time : timeOfDay) {
                if (result.contains(time)) {
                    timeSelected = time + " o'clock";
                    rightAnswer(time);
                    return;
                }
            }
            wrongAnswer("time");
        } else if (haveInsuranceAsked) {
            Log.d(TAG, "have insurance asked");
            if (TextUtils.equals(result, "yes"))
                rightAnswer("yes");
            else if (TextUtils.equals(result, "no"))
                rightAnswer("no");
            else
                wrongAnswer("");
        } else if (bodyPartHighlighted) {
            Log.d(TAG, "bodyPartHighlighted");
            for (String problem : whatIsTheProblem) {
                if (TextUtils.equals(result, problem)) {
                    rightAnswer(problem);
                    return;
                }
            }
            wrongAnswer("problem");
        } else if(forHowLongAsked) {
            if (TextUtils.equals(result, "1 day") || TextUtils.equals(result, "one day") || TextUtils.equals(result, "oneday"))
                rightAnswer("one day");
            else
                wrongAnswer("one day");
        } else if(haveFeverAsked) {
            Log.d(TAG, "haveFeverAsked");
            if (TextUtils.equals(result, "yes") || TextUtils.equals(result, "no"))
                rightAnswer("haveFeverAsked");
            else
                wrongAnswer("haveFeverAsked");
        } else {
            for (String docName : doctorNames) {
                if (TextUtils.equals(result, docName)) {
                    doctorNameSelected = docName;
                    rightAnswer(docName);
                    return;
                }
            }
            wrongAnswer("doc");
        }
    }

    private void wrongAnswer(String check) {
        if (check.equals("doc"))
            Toast.makeText(this, "There is no doctor available with that name", Toast.LENGTH_SHORT).show();
        else if (check.equals("day"))
            Toast.makeText(this, "There is no such day", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "That was not the right answer", Toast.LENGTH_SHORT).show();
    }

    private void rightAnswer(String check) {
        Log.d(TAG, "rightAnswer approached!");
        if (haveAppointmentAsked) {
            haveAppointment = true;
            haveAppointmentAsked = false;
            if (check.equals("yes")) {
                Log.d(TAG, "does have appointment");
                lottieAnimationView.playAnimation(330, 338);
                textToSpeech.speak("May I have your name?", TextToSpeech.QUEUE_FLUSH, params, "12345");
                tellNameAsked = true;
            } else if (check.equals("no")) {
                haveAppointment = false;
                Log.d(TAG, "don't have appointment");
                lottieAnimationView.playAnimation(330, 338);
                textToSpeech.speak("May I have your name?", TextToSpeech.QUEUE_FLUSH, params, "12345");
                tellNameAsked = true;
            }
        } else if(tellNameAsked) {
            tellNameAsked = false;
            if (haveAppointment) {
                lottieAnimationView.playAnimation(370, 378);
                textToSpeech.speak("Do you have a file?", TextToSpeech.QUEUE_FLUSH, params, "12345");
                haveFileAsked = true;
            } else {
                lottieAnimationView.playAnimation(221, 221);
                textToSpeech.speak("Which day?", TextToSpeech.QUEUE_FLUSH, params, "12345");
                whichDayAsked = true;
            }
        } else if (haveFileAsked) {
            haveFileAsked = false;
            micImageView.setVisibility(View.INVISIBLE);
            lottieAnimationView.playAnimation(452, 454);
            textToSpeech.speak("May i have your ID?", TextToSpeech.QUEUE_FLUSH, params, "12345");
            selectIdAsked = true;
        } else if (whichDayAsked) {
            Log.d(TAG, "which day asked");
            whichDayAsked = false;
            lottieAnimationView.playAnimation(273, 273);
            textToSpeech.speak("Morning, afternoon or evening?", TextToSpeech.QUEUE_FLUSH, params, "12345");
            morAfterEveAsked = true;
        } else if (morAfterEveAsked){
            morAfterEveAsked = false;
            lottieAnimationView.playAnimation(301, 301);
            textToSpeech.speak("What time?", TextToSpeech.QUEUE_FLUSH, params, "12345");
            whatTimeAsked = true;
        } else if (whatTimeAsked) {
            whatTimeAsked = false;
            lottieAnimationView.playAnimation(0, 0);
            Toast.makeText(this, "Your appointment with " + doctorNameSelected + " is on " + daySelected + " at " + timeSelected, Toast.LENGTH_LONG).show();
//            textToSpeech.speak("Your appointment with Dr. " + doctorNameSelected + " is on " + daySelected + " at " + timeSelected, TextToSpeech.QUEUE_FLUSH, params, "12345");
            new CountDownTimer(2000, 2000) {

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    lottieAnimationView.playAnimation(370, 378);
                    textToSpeech.speak("Do you have a file?", TextToSpeech.QUEUE_FLUSH, params, "12345");
                    haveFileAsked = true;
                    micImageView.setVisibility(View.VISIBLE);
                }
            }.start();
        } else if (haveInsuranceAsked) {
            haveInsuranceAsked = false;
            micImageView.setVisibility(View.INVISIBLE);
            if (check.equals("yes")) {
                Log.d(TAG, "have insurance");
                lottieAnimationView.playAnimation(627, 647);
                textToSpeech.speak("May i have your insurance card?", TextToSpeech.QUEUE_FLUSH, params, "12345");
                selectInsuranceAsked = true;
            } else if (check.equals("no")) {
                Log.d(TAG, "don't have insurance");
                lottieAnimationView.playAnimation(620, 625);
                textToSpeech.speak("it's $15", TextToSpeech.QUEUE_FLUSH, params, "12345");
                selectcashAsked = true;
            }
        } else if (bodyPartHighlighted) {
            bodyPartHighlighted = false;
            lottieAnimationView.playAnimation(1057, 1078);
            textToSpeech.speak("For how long?", TextToSpeech.QUEUE_FLUSH, params, "12345");
            forHowLongAsked = true;
         } else if (forHowLongAsked) {
            forHowLongAsked = false;
            lottieAnimationView.playAnimation(1100, 1120);
            textToSpeech.speak("Do you have fever?", TextToSpeech.QUEUE_FLUSH, params, "12345");
            haveFeverAsked = true;
        } else if (haveFeverAsked) {
            Log.d(TAG, "HaveFeverInRightAnswer");
            haveFeverAsked = false;
            haveFeverAnswered = true;
            fifthTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runAnimation();
                }
            }, 2000, 4000);
        } else {
            int randomNum = random.nextInt(1);
            switch (randomNum) {
                case 0:
                    i = 77;
                    lottieAnimationView.playAnimation(i, 81);
                    textToSpeech.speak("Do you have an appointment?", TextToSpeech.QUEUE_FLUSH, params, "12345");
                    haveAppointmentAsked = true;
                    break;
                case 1:
                    Toast.makeText(this, "case 1", Toast.LENGTH_SHORT).show();
                    i = 86;
                    lottieAnimationView.playAnimation(i, i);
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    int ia = 725;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_play:
//                lottieAnimationView.playAnimation(i, i + 15);
//                i += 15;
//                rightAnswer("");
//                bagBig.setBackground(getResources().getDrawable(R.drawable.bag_big));
                return  true;
            case R.id.action_restart:
//                Log.d(TAG, "ia: " + ia);
//                lottieAnimationView.playAnimation(ia, ia);
//                ia ++;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
        micImageView.setImageResource(R.drawable.mic);
        isRecording = false;
    }

    @Override
    public void onError(int i) {
        String errorMessage = getErrorMessage(i);
        isRecording = false;
        speechRecognizer.destroy();
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResults(Bundle bundle) {
        String result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
        checkResult(result);
//        micImageView.setImageResource(R.drawable.mic);
        speechRecognizer.destroy();
    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }

    private static String getErrorMessage(int errorCode) {
        String errorMessage;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                errorMessage = "Audio isRecording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                errorMessage = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                errorMessage = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                errorMessage = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                errorMessage = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                errorMessage = "Sorry I didn't hear you";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                errorMessage = "I am busy processing your speech";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                errorMessage = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                errorMessage = "Sorry I didn't hear you";
                break;
            default:
                errorMessage = "Didn't understand, please try again.";
        }
        return errorMessage;
    }

    public void bagClick(View view) {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    public void onClickCash(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_anim));
        Log.d(TAG, "onClickCash");
        if (selectcashAsked) {
            micImageView.setVisibility(View.INVISIBLE);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            lottieAnimationView.playAnimation(650, 666);
            textToSpeech.speak("Here it is", TextToSpeech.QUEUE_FLUSH, params, "12345");
            thirdTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runAnimation();
                }
            }, 2000, 4000);
        }
    }

    public void onCLickId(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_anim));
        if (selectIdAsked) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            lottieAnimationView.playAnimation(458, 458);
            textToSpeech.speak("Here it is", TextToSpeech.QUEUE_FLUSH, params, "12345");
            secTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runAnimation();
                }
            }, 2000, 2000);
        }
    }

    public void onClickInsurance(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_anim));
        Log.d(TAG, "onClickInsurance");
        if (selectInsuranceAsked) {
            micImageView.setVisibility(View.INVISIBLE);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            lottieAnimationView.playAnimation(650, 666);
            textToSpeech.speak("Here it is", TextToSpeech.QUEUE_FLUSH, params, "12345");
            thirdTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runAnimation();
                }
            }, 2000, 4000);
        }
    }

    public void onClickPrescription(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.image_anim));
        if (givePrescriptionAsked) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            sixthTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runAnimation();
                }
            }, 1000, 4000);
        }
    }

    public void onClickPromptItsMe(View view) {
        promptItsMe.setVisibility(View.INVISIBLE);
        promptClicked = true;
        fourthTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runAnimation();
            }
        }, 1000, 2000);
    }
}
