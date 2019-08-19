package ru.job4j.exam;

        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.widget.TextView;

        import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        TextView text = findViewById(R.id.textResult);
        ArrayList<Integer> arr = (ArrayList<Integer>) getIntent().getSerializableExtra("list");
        text.setText(arr.toString());
    }



}
