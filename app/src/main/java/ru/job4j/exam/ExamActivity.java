package ru.job4j.exam;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamActivity extends AppCompatActivity {

    private static final String TAG = "ExamActivity";
    private static final String KEY = "key";
    private int cntTurn = 0;
    private int position = 0;
    private final List<Question> questions = Arrays.asList(
            new Question(
                    1, "How many primitive variables does Java have?",
                    Arrays.asList(
                            new Option(1, "1.1"), new Option(2, "1.2"),
                            new Option(3, "1.3"), new Option(4, "1.4")
                    ), 4
            ),
            new Question(
                    2, "What is Java Virtual Machine?",
                    Arrays.asList(
                            new Option(1, "2.1"), new Option(2, "2.2"),
                            new Option(3, "2.3"), new Option(4, "2.4")
                    ), 4
            ),
            new Question(
                    3, "What is happen if we try unboxing null?",
                    Arrays.asList(
                            new Option(1, "3.1"), new Option(2, "3.2"),
                            new Option(3, "3.3"), new Option(4, "3.4")
                    ), 4
            )
    );

    public static final String HINT_FOR = "hint_for";
    public static final String QUESTION_FOR = "question_for";
    int cnt = 0;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        load(state);
        this.fillForm();

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.variants);
        final int checked = radioGroup.getCheckedRadioButtonId();
        Button next = findViewById(R.id.next);
        List<Integer> list = new ArrayList<>();
        next.setOnClickListener(
                view -> {
                    cnt++;
                    showAnswer();
                    position++;
                    fillForm();
                    if (cnt == 3) {
                        Intent intent = new Intent(ExamActivity.this, ResultActivity.class);
                        intent.putExtra("list", (Serializable) list);
                        startActivity(intent);
                    }
                }
        );

        Button previous = findViewById(R.id.previous);
        previous.setOnClickListener(this::prevBtn);

        Button hint = findViewById(R.id.hint);
        hint.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ExamActivity.this, HintActivity.class);
                        intent.putExtra(HINT_FOR, position);
                        intent.putExtra(QUESTION_FOR, position);
                        startActivity(intent);
                    }
                }
        );
    }

    public void nextBtn(View view) {

        //List<Integer> list = new ArrayList<>();
        //list.add(position, questions.get(position).getId());
        showAnswer();
        position++;
        fillForm();

        //if(position >= 2) {
//            Intent intent = new Intent(ExamActivity.this, ResultActivity.class);
//            intent.putExtra("list", (Serializable) list);
//            startActivity(intent);
        //}
    }

    public void prevBtn(View view) {
        position--;
        fillForm();
    }

    public void showAnswer() {
        RadioGroup variants = findViewById(R.id.variants);
        int id = variants.getCheckedRadioButtonId();
        Question question = this.questions.get(this.position);
        Toast.makeText(
                this, "Your answer is " + id + ", correct is " + question.getAnswer(),
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        cntTurn++;
        state.putInt(KEY, cntTurn);
        Log.d(ExamActivity.class.getName(), String.valueOf(cntTurn));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    public void load(Bundle state) {
        if (state != null) {
            cntTurn = state.getInt(KEY);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public void fillForm() {
        findViewById(R.id.previous).setEnabled(position != 0);
        findViewById(R.id.next).setEnabled(position != questions.size() - 1);
        final TextView text = findViewById(R.id.question);
        Question question = this.questions.get(this.position);
        text.setText(question.getText());
        RadioGroup variants = findViewById(R.id.variants);
        for (int index = 0; index != variants.getChildCount(); index++) {
            RadioButton button = (RadioButton) variants.getChildAt(index);
            Option option = question.getOptions().get(index);
            button.setId(option.getId());
            button.setText(option.getText());
        }
    }

    public static class ExamHolder extends RecyclerView.ViewHolder {

        private View view;

        public ExamHolder(@NonNull View view) {
            super(view);
            this.view = itemView;
        }
    }

    public static class ExamAdapter extends RecyclerView.Adapter<ExamHolder> {

        private final List<Exam> exams;

        public ExamAdapter(List<Exam> exams) {
            this.exams = exams;
        }

        /**
         * Это метод загружает внутренних вид RecyclyView. В нашем случае это шаблон res/layout/info_exam,xml
         * @param parent
         * @param i
         * @return - отдаем модель Holder с загруженным видом.
         */
        @NonNull
        @Override
        public ExamHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.info_exam, parent, false);
            return new ExamHolder(view);
        }

        /**
         * Это метод загружает данные в наш вид.
         * @param holder - это вид
         * @param i - указатель элемента в списке.
         */
        @Override
        public void onBindViewHolder(@NonNull ExamHolder holder, int i) {
            final Exam exam = this.exams.get(i);
            TextView text = holder.view.findViewById(R.id.info);
            text.setText(exam.getName());
        }

        /**
         * это метод указывает сколько всего элементов в списке.
         * @return
         */
        @Override
        public int getItemCount() {
            return this.exams.size();
        }



    }
}
