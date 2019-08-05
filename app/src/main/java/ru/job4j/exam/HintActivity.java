package ru.job4j.exam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class HintActivity extends AppCompatActivity {

    private final Map<Integer, String> questions = new HashMap<>();
    private final Map<Integer, String> answers = new HashMap<>();

    public HintActivity() {
        this.questions.put(0, "How many primitive variables does Java have?");
        this.questions.put(1, "What is Java Virtual Machine?");
        this.questions.put(2, "What is happen if we try unboxing null?");
        this.answers.put(0, "Hint 1");
        this.answers.put(1, "Hint 2");
        this.answers.put(2, "Hint 3");
    }

    @Override
    protected void onCreate(@Nullable Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.hint_activity);

        TextView textH = findViewById(R.id.hint);
        TextView textQ = findViewById(R.id.question1);
        int question = getIntent().getIntExtra(ExamActivity.QUESTION_FOR, 0);
        int answer = getIntent().getIntExtra(ExamActivity.HINT_FOR, 0);
        textH.setText(this.answers.get(answer));
        textQ.setText(this.questions.get(question));

        Button back = findViewById(R.id.back);
        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                }
        );
    }
}
