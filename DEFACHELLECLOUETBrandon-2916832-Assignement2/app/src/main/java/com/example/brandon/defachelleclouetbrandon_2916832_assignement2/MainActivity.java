package com.example.brandon.defachelleclouetbrandon_2916832_assignement2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    MineView[][] board;
    Button[][] listButton;
    Button restart;
    int i;
    int j;
    int[] listColor;
    Random rdm;
    int nbCaseLeft, highscore;
    TextView textView;
    TextView txt_highScore;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listButton = new Button[10][10];
        listColor = new int[]{Color.WHITE, Color.BLUE, Color.GREEN, Color.YELLOW};
        restart = (Button)(findViewById(R.id.restart_button));
        textView= (TextView) findViewById(R.id.txt_score);
        txt_highScore=(TextView) findViewById(R.id.txt_highScore);
        GridLayout gridLayout = (GridLayout)findViewById(R.id.grid);
        resetGame();

        for(i=0;i<10;i++)
                {
                    for(j = 0; j < 10; j++)
                    {
                        listButton[i][j] = (Button) gridLayout.getChildAt(i * 10 + j);
                        listButton[i][j].setEnabled(true);
                        listButton[i][j].setText("");
                        listButton[i][j].setTextSize(8f);
                        listButton[i][j].setBackgroundColor(Color.BLACK);
                        listButton[i][j].setOnClickListener(new View.OnClickListener()
                {

                    int y = j;
                    int x = i;

                    @Override
                    public void onClick(View v)
                    {
                        listButton[x][y].setBackgroundColor(Color.LTGRAY);
                        if (board[x][y].isBomb)
                        {
                            listButton[x][y].setText("B");
                            GameOver();
                        }
                        else
                        {
                            listButton[x][y].setText(String.valueOf(board[x][y].nbBombAround));
                            int color = board[x][y].nbBombAround;
                            if (color > 3)
                            {
                                listButton[x][y].setTextColor(Color.RED);
                            }
                            else
                            {
                                listButton[x][y].setTextColor(listColor[board[x][y].nbBombAround]);
                            }
                            UpdateNbCaseLeft();
                            UpdateHighScore();
                            listButton[x][y].setEnabled(false);
                        }
                    }
                });

                listButton[i][j].setOnLongClickListener(new View.OnLongClickListener()
                {
                    boolean HasBeenMarked = false;

                    @Override
                    public boolean onLongClick(View v)
                    {
                        Button button = (Button) v;
                        if (!HasBeenMarked)
                        {
                            button.setText("M");
                            button.setBackgroundColor(Color.RED);
                            HasBeenMarked = true;
                        }
                        else
                        {
                            button.setText("");
                            button.setBackgroundColor(Color.BLACK);
                            HasBeenMarked = false;
                        }
                        return true;
                    }
                });

            }
        }
        restart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                resetGame();
                resetTextView();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void resetGame()
    {
        board = new MineView[10][10];
        int nbMine = 20;
        rdm = new Random(0);

           for(int i=0;i<10;i++)
           {
               for(int j=0;j<10;j++)
               {
                   board[i][j]= new MineView(getApplicationContext());
               }
           }


        while(nbMine>0)
        {
            int i = rdm.nextInt(10);
            int j = rdm.nextInt(10);
            if(!board[i][j].isBomb)
            {
                board[i][j].isBomb=true;
                updateNbBomb(i,j);
                nbMine--;
            }
        }
        nbCaseLeft=81;      //because of NbCase-- from the next function
        UpdateNbCaseLeft();
    }
    public void resetTextView()
    {
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<10;j++)
            {
                listButton[i][j].setEnabled(true);
                listButton[i][j].setText("");
                listButton[i][j].setBackgroundColor(Color.BLACK);
            }
        }
    }
    public void GameOver()
    {
        for(i=0;i<10;i++)
        {
            for (j = 0; j < 10; j++)
            {
                listButton[i][j].setEnabled(false);
            }
        }
        if(nbCaseLeft!=0)
        {
            textView.setText("You lose!");
        }
    }

    public void UpdateNbCaseLeft()
    {
        nbCaseLeft--;
        if(nbCaseLeft==0)
        {
            textView.setText("You win!");
            GameOver();
        }
        else
           textView.setText(String.valueOf(nbCaseLeft) + " case(s) left");
    }
    public void updateNbBomb(int i, int j)
    {
       if(i>0 && j>0)
       {
           board[i-1][j-1].nbBombAround++;
       }
        if(j>0)
        {
            board[i][j-1].nbBombAround++;
        }
        if(i<9 && j>0)
        {
            board[i+1][j-1].nbBombAround++;
        }
        if(i>0)
        {
            board[i-1][j].nbBombAround++;
        }
        if(i<9)
        {
            board[i+1][j].nbBombAround++;
        }
        if(i>0 && j<9)
        {
            board[i-1][j+1].nbBombAround++;
        }
        if(j<9)
        {
            board[i][j+1].nbBombAround++;
        }
        if(i<9 && j<9)
        {
            board[i+1][j+1].nbBombAround++;
        }
    }
    public void UpdateHighScore()
    {
        if(highscore<80-nbCaseLeft)
        {
            txt_highScore.setText("HIGH SCORE:\n" + (80-nbCaseLeft) + " case(s)");
            highscore=80-nbCaseLeft;
        }
    }
}
