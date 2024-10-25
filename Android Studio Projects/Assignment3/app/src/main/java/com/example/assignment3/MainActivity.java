package com.example.assignment3;

import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

// Assignment 3
// Names: Nhu Nguyen and Jaiden Daya (Team 7)
// File Name: MainActivity

public class MainActivity extends AppCompatActivity {

    TextView gameStatus;
    private final int[] cards = {R.id.img1, R.id.img2, R.id.img3, R.id.img4, R.id.img5, R.id.img6, R.id.img7, R.id.img8, R.id.img9, R.id.img10, R.id.img11, R.id.img12,
            R.id.img13, R.id.img14, R.id.img15, R.id.img16, R.id.img17, R.id.img18, R.id.img19, R.id.img20, R.id.img21, R.id.img22, R.id.img23, R.id.img24, R.id.img25};
    private final int[] fruits = {R.drawable.apple, R.drawable.lemon, R.drawable.mango, R.drawable.peach, R.drawable.strawberry, R.drawable.tomato};
    private final String[] randomFruit = {"Apples", "Lemons", "Mangoes", "Peaches", "Strawberries", "Tomatoes"};
    private int num;
    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        gameStatus = findViewById(R.id.gameStatus);
        setUpGame();

        // set onClickListener to all the imageViews
        for (int i = 0; i < cards.length; i++) {
            ImageView imageView = findViewById(cards[i]);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageClick((ImageView) view);
                }
            });
        }

        // reset button
        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpGame();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // set up the game
    private void setUpGame() {
        count = (int) (Math.random() * 7) + 1;
        num = (int) (Math.random() * 6);
        gameStatus.setText("Find All " + randomFruit[num] + " (" + count + ")");

        Random random = new Random();
        Set<Integer> randomNums = new HashSet<>();
        while(randomNums.size() < count){
            int number = random.nextInt(25);
            randomNums.add(number);
        }
        ArrayList<Integer> randomNumsList = new ArrayList<>(randomNums);

        // choose the fruit that need to be find and set the imageViews
        for(int i = 0; i < count; i++){
            ImageView imageView = findViewById(cards[randomNumsList.get(i)]);
            if (randomFruit[num].equals("Apples")) {
                imageView.setImageDrawable(getDrawable(fruits[0]));
            } else if (randomFruit[num].equals("Lemons")) {
                imageView.setImageDrawable(getDrawable(fruits[1]));
            } else if (randomFruit[num].equals("Mangoes")) {
                imageView.setImageDrawable(getDrawable(fruits[2]));
            } else if (randomFruit[num].equals("Peaches")) {
                imageView.setImageDrawable(getDrawable(fruits[3]));
            } else if (randomFruit[num].equals("Strawberries")) {
                imageView.setImageDrawable(getDrawable(fruits[4]));
            } else {
                imageView.setImageDrawable(getDrawable(fruits[5]));
            }
            int drawableId = fruits[num];
            CardInfo card = new CardInfo(true, false, drawableId);
            imageView.setTag(card);
            imageView.setAlpha(1.0f);
        }

        // set the fruits for the rest of the imageViews (unfocused fruits)
        for (int j = 0; j < 25; j++) {
            int num2;
            do {
                num2 = (int) (Math.random() * 6);
            } while (num == num2);

            ImageView imageView = findViewById(cards[j]);
            CardInfo card;
            int drawableId = fruits[num2];
            if (!randomNumsList.contains(j)) {
                imageView.setImageDrawable(getDrawable(fruits[num2]));
                card = new CardInfo(false, false, drawableId);
                imageView.setTag(card);
                imageView.setAlpha(1.0f);
            }
        }
    }

    // shuffle all the unselected images
    private void shuffle() {
        ArrayList<CardInfo> unselectedCards = new ArrayList<>();
        ArrayList<ImageView> unselectedViews = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            ImageView imageView = findViewById(cards[i]);
            CardInfo cardInfo = (CardInfo) imageView.getTag();

            if (cardInfo != null && !cardInfo.getSelected()) {
                unselectedViews.add(imageView);
                unselectedCards.add(cardInfo);
            }
        }
        Collections.shuffle(unselectedCards);

        for (int i = 0; i < unselectedViews.size(); i++) {
            ImageView imageView = unselectedViews.get(i);
            CardInfo cardInfo = unselectedCards.get(i);
            imageView.setImageDrawable(getDrawable(cardInfo.getDrawableId()));
            imageView.setAlpha(1.0f);
            imageView.setTag(cardInfo);
        }
    }

    // click an imageView
    public void imageClick(ImageView imageView) {
        CardInfo cardInfo = (CardInfo) imageView.getTag();

        if (cardInfo != null && cardInfo.getFocused() && !cardInfo.getSelected()) {
            cardInfo.setSelected(true);
            imageView.setAlpha(0.5f);
            imageView.setOnClickListener(null);
            count--;

            if (count > 0) {
                gameStatus.setText("Find All " + randomFruit[num] + " (" + count + ")");
            } else {
                gameStatus.setText("Find " + randomFruit[num] + " (" + count + ")");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Found All Shapes");
                builder.setMessage("Congratulations!! You have found all the " + randomFruit[num]);
                builder.setPositiveButton("Ok", (DialogInterface.OnClickListener)(dialog, which) -> {
                    setUpGame();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            shuffle();
        }
    }
}