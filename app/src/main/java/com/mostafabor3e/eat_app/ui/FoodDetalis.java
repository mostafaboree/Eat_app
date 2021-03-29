package com.mostafabor3e.eat_app.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mostafabor3e.eat_app.Adapter.AdapterComment;
import com.mostafabor3e.eat_app.Model.Comment;
import com.mostafabor3e.eat_app.Model.FoodM;
import com.mostafabor3e.eat_app.R;
import com.mostafabor3e.eat_app.RooM.RoomFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FoodDetalis extends AppCompatActivity {
    ImageView foodImage, cart, back,increas,decreas;
    TextView name, fname, descraption, prize, rating, viewer,quentity;
    Button add;
    EditText comment;
    String foodname;
    String phone;
    RatingBar ratingBar, ratingBarUser;
    FloatingActionButton addRating;
    FoodM foodM;
    RecyclerView recyclerView;
    RoomFactory roomFactory;
    AdapterComment adapterComment;
    List<Comment>comments;
    int quentitey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detalis);

        foodImage = findViewById(R.id.imageView5);
        add = findViewById(R.id.button);
        cart = findViewById(R.id.imageView4);
        name = findViewById(R.id.name);
        descraption = findViewById(R.id.tv_food_descraption);
        prize = findViewById(R.id.price);
        back = findViewById(R.id.iv_back);


        rating = findViewById(R.id.rating);
        ratingBar = findViewById(R.id.ratingBar);
        addRating = findViewById(R.id.fab_rating);

        increas=findViewById(R.id.iv_add);
        decreas=findViewById(R.id.iv_qun_sub);
        quentity=findViewById(R.id.tv_quntity);

        //recycle view
        recyclerView=findViewById(R.id.rec_comment);
        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),1));
        recyclerView.setClipToPadding(true);
        comments=new ArrayList<>();
        getComment();


        SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
        phone = preferences.getString("phone", "none");
        getyourRating();

        addRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRating();
            }
        });
        //rating.setText(ratingBar.getRating());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Home.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        //String f=intent.getStringExtra("name");

        foodM = (FoodM) intent.getSerializableExtra("name");
        int q=Integer.parseInt((String) quentity.getText());
        increas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++quentitey;
                quentity.setText(""+quentitey);
                foodM.setQuentity(quentitey);
            }
        });

        decreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quentitey>0)
                    --quentitey;
                quentity.setText(""+quentitey);
                foodM.setQuentity(quentitey);

            }
        });


        foodname = foodM.getName();
        descraption.setText(foodM.getDescription());
        prize.setText(foodM.getPrice());
        Glide.with(getBaseContext()).load(foodM.getImage()).into(foodImage);
        name.setText(foodM.getName());

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getBaseContext(), Cart.class);
                startActivity(intent1);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCart(foodM);
                quentitey=0;
                quentity.setText(""+quentitey);
                addRating();
            }
        });
    }

    private void addCart(final FoodM food) {
        roomFactory = RoomFactory.getInstace(getBaseContext());
        roomFactory.dataAcess().insert(food).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(FoodDetalis.this, "success add to Cart"+foodM.getQuentity(), Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void addRating() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comment");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rate this food");
        builder.setMessage("Please select some starts and give your feedback");
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.feedback_client, null);
        comment = view.findViewById(R.id.ed_comment_user);
        ratingBarUser = view.findViewById(R.id.rating_user);
        builder.setIcon(R.drawable.star);
        viewer = view.findViewById(R.id.tv_rating_user);

        ratingBarUser.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Float rat = ratingBar.getRating();
                if (rat == 1 || rat > 0) {
                    viewer.setText("Very bad");
                }
                if (rat == 2 || rat > 1) {
                    viewer.setText("Not good");
                }
                if (rat == 3 || rat > 2) {
                    viewer.setText("good");
                }
                if (rat == 4 || rat > 3) {
                    viewer.setText(" Very good");
                }
                if (rat == 5 || rat > 4) {
                    viewer.setText("Excellent");
                }

            }
        });

        builder.setView(view);
        builder.setPositiveButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final Comment comment1 = new Comment(comment.getText().toString(), ratingBarUser.getRating(), phone, foodname);

                reference.push().setValue(comment1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FoodDetalis.this, "Thank for your feedback", Toast.LENGTH_SHORT).show();
                        ratingBar.setRating(ratingBarUser.getRating());
                        rating.setText(ratingBarUser.getRating() + "");
                        String id=reference.getKey();
                        comment1.setId(id);
                        //reference.child(id).setValue("id",id);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FoodDetalis.this, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        builder.show();

    }
      public void  getyourRating(){
          final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comment");
          reference.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                      Comment comment=dataSnapshot.getValue(Comment.class);
                      if (comment.getUserPhone().equals(phone)&&comment.getFood().equals(foodname)){
                          ratingBar.setRating(comment.getRating());
                          rating.setText(comment.getRating()+"");
                          ratingBar.setEnabled(false);
                          addRating.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                  Toast.makeText(FoodDetalis.this, "you send your feedback about this food", Toast.LENGTH_SHORT).show();
                                //  addRating.setEnabled(false);
                              }
                          });


                      }
                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });

      }
      public void getComment(){
          final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comment");
          reference.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  comments.clear();
                  for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                      Comment comment=dataSnapshot.getValue(Comment.class);
                      if (comment.getFood().equals(foodname)){
                          comments.add(comment);
                      }
                  }
                  adapterComment=new AdapterComment(comments,getBaseContext());
                  recyclerView.setAdapter(adapterComment);
                  adapterComment.notifyDataSetChanged();
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });


      }

}