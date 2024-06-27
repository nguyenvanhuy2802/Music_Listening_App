package com.example.music.Activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.music.Class.Song;
import com.example.music.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView playButton, nextButton, beforeButton, loopButton, randomButton, backButton, musicBG;
    TextView txtTimeInitial, txtTimeTotal, txtNameSong, txtSingerName;
    SeekBar seekBarTime;
    Song song;
    ArrayList<Song> songList;
    MediaPlayer musicPlayer;
    Animation animation;
    int count;
    boolean feature;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupInsets();

        // Nhận dữ liệu bài hát và danh sách bài hát
        song = (Song) getIntent().getSerializableExtra("selectedSong");
        songList = (ArrayList<Song>) getIntent().getSerializableExtra("songList");
        musicPlayer = new MediaPlayer();


        setInforMusic(song);
        loadSongImage(song);
        downloadAndPrepareMusic(song);
        setupSeekBarListener();
        playNextSong();
    }

    private void initViews() {
        musicBG = findViewById(R.id.musicBG);
        seekBarTime = findViewById(R.id.seekBarTime);
        txtTimeInitial = findViewById(R.id.txtTimeInitial);
        txtTimeTotal = findViewById(R.id.txtTimeTotal);
        txtNameSong = findViewById(R.id.txtNameSong);
        txtSingerName = findViewById(R.id.txtSingerName);
        playButton = findViewById(R.id.playButton);
        nextButton = findViewById(R.id.nextButton);
        beforeButton = findViewById(R.id.beforeButton);
        loopButton = findViewById(R.id.loopButton);
        randomButton = findViewById(R.id.randomButton);
        backButton = findViewById(R.id.backButton);
        count = 2;
        feature = true;

        playButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        beforeButton.setOnClickListener(this);
        loopButton.setOnClickListener(this);
        randomButton.setOnClickListener(this);
    }

    private void setupInsets() {
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setInforMusic(Song song) {
        txtNameSong.setText(song.getTenBaiHat());
        txtSingerName.setText(song.getCaSi());
    }

    private void loadSongImage(Song song) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRefPathImage = storage.getReference().child(song.getHinhBaiHat());
        try {
            File localFileImage = File.createTempFile("image", "jpg");
            storageRefPathImage.getFile(localFileImage)
                    .addOnSuccessListener(taskSnapshot -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFileImage.getAbsolutePath());
                        Bitmap circularBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(circularBitmap);
                        Paint paint = new Paint();
                        paint.setAntiAlias(true);
                        Drawable drawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.circular_background);
                        if (drawable != null) {
                            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                            drawable.draw(canvas);
                        }
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(bitmap, 0, 0, paint);
                        musicBG.setImageBitmap(circularBitmap);
                    })
                    .addOnFailureListener(e -> e.printStackTrace());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadAndPrepareMusic(Song song) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRefPathMusic = storage.getReference().child(song.getPathBaiHat());
        try {
            File localFile = File.createTempFile("audio", "mp3");
            storageRefPathMusic.getFile(localFile)
                    .addOnSuccessListener(taskSnapshot -> {
                        try {
                            musicPlayer.setDataSource(localFile.getAbsolutePath());
                            musicPlayer.setOnPreparedListener(mp -> {
                                mp.start();
                                int duration = musicPlayer.getDuration();
                                seekBarTime.setMax(duration);
                                updateSeekBar();
                                txtTimeTotal.setText(convertSecondsToString(duration));
                                animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.music_animation);
                                musicBG.startAnimation(animation);

                            });
                            musicPlayer.prepareAsync();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    })
                    .addOnFailureListener(e -> e.printStackTrace());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupSeekBarListener() {
        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    txtTimeInitial.setText(convertSecondsToString(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Xử lý khi bắt đầu chạm vào SeekBar

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Xử lý khi dừng chạm vào SeekBar
                int progress = seekBar.getProgress();
//                musicPlayer.reset();
                musicPlayer.seekTo(progress);
//                musicPlayer.start();
                // Cập nhật thanh SeekBar và giao diện người dùng khác
                playButton.setImageResource(R.drawable.pause_button);
                setImageRotation(musicPlayer.getCurrentPosition());
            }
        });
    }

    private void updateSeekBar() {
        new Thread(() -> {
            while (musicPlayer != null && musicPlayer.isPlaying()) {
                try {
                    final int current = musicPlayer.getCurrentPosition();
                    runOnUiThread(() -> {
                        seekBarTime.setProgress(current);
                        txtTimeInitial.setText(convertSecondsToString(current));
                    });
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void playNextSong() {
        musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Song nextSong = getNextSongById(song.getIdBaiHat());
                if (count == 1 && feature) {
                    loopCurrentSong();
                } else if (count == 2 && feature && nextSong == null) {
                    loopSongList();
                } else if (!feature) {
                    randomSong();
                } else {
                    setImageRotation(0);
                    mp.reset();
                    animation.cancel();
                    if (nextSong != null) {
                        song = nextSong;
                        runOnUiThread(() -> {
                            setInforMusic(nextSong);
                            loadSongImage(nextSong);
                        });
                        downloadAndPrepareMusic(nextSong);
                    }
                }
            }
        });
    }

    private void nextSong() {
        Song nextSong = getNextSongById(song.getIdBaiHat());
        musicPlayer.pause();
        animation.cancel();
        setImageRotation(0);
        if (nextSong == null) {
            nextSong = songList.get(0);
        }
        musicPlayer.reset();
        song = nextSong;
        Song finalNextSong = nextSong;
        runOnUiThread(() -> {
            setInforMusic(finalNextSong);
            loadSongImage(finalNextSong);
        });
        downloadAndPrepareMusic(nextSong);
    }

    private Song getNextSongById(int currentSong) {
        Song nextSong = null;
        for (Song song : songList) {
            if (song.getIdBaiHat() == (currentSong + 1)) {
                nextSong = song;
            }
        }
        return nextSong;
    }

    private void beforeSong() {
        Song beforeSong = getBeforeSongById(song.getIdBaiHat());
        if (musicPlayer.getCurrentPosition() > 5000 || beforeSong == null) {
            animation.cancel();
            setImageRotation(0);
            animation.start();
            musicPlayer.seekTo(0);
        } else {
            musicPlayer.pause();
            animation.cancel();
            setImageRotation(0);
            musicPlayer.reset();
            song = beforeSong;
            runOnUiThread(() -> {
                setInforMusic(beforeSong);
                loadSongImage(beforeSong);
            });
            downloadAndPrepareMusic(beforeSong);
        }
    }

    public Song getBeforeSongById(int currentSong) {
        Song beforeSong = null;
        for (Song song : songList) {
            if (song.getIdBaiHat() == (currentSong - 1)) {
                beforeSong = song;
            }
        }
        return beforeSong;
    }

    public void loopSongList() {
        Song nextSong = getNextSongById(song.getIdBaiHat());
        animation.cancel();
        setImageRotation(0);
        musicPlayer.reset();
        Song firstSong = songList.get(0);
        song = firstSong;
        runOnUiThread(() -> {
            setInforMusic(firstSong);
            loadSongImage(firstSong);
        });
        downloadAndPrepareMusic(firstSong);
    }

    public void loopCurrentSong() {
        animation.cancel();
        setImageRotation(0);
        musicPlayer.reset();
        runOnUiThread(() -> {
            setInforMusic(song);
            loadSongImage(song);
        });
        downloadAndPrepareMusic(song);
    }

    public Song getRandomSong() {
        Song randomSong = null;
        int numSongs = songList.size();
        if (numSongs > 0) {
            int random = (int) (Math.random() * numSongs);
            randomSong = songList.get(random);
        }
        return randomSong;
    }

    public void randomSong() {
        animation.cancel();
        setImageRotation(0);
        musicPlayer.reset();
        Song randomSong = getRandomSong();
        if (randomSong != null) {
            song = randomSong;
            runOnUiThread(() -> {
                setInforMusic(randomSong);
                loadSongImage(randomSong);
            });
            downloadAndPrepareMusic(song);
        }
    }

    public void setImageRotation(double currentPosition) {
        int animDuration = (int) animation.getDuration();
        double remainder = (double) currentPosition % animDuration;
        double rotation = (double) remainder * 360 / animDuration;
        musicBG.setRotation((float) rotation);
    }


    // Hàm đổi thời gian sang chuỗi
    public String convertSecondsToString(int time) {
        int minutes = time / (1000 * 60);
        int seconds = (time / 1000) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.playButton) {
            if (musicPlayer.isPlaying()) {
                musicPlayer.pause();
                playButton.setImageResource(R.drawable.play_button);
                animation.cancel();
                setImageRotation(musicPlayer.getCurrentPosition());

            } else {
                musicPlayer.start();
                updateSeekBar();
                playButton.setImageResource(R.drawable.pause_button);
                musicBG.startAnimation(animation);
            }
        }
        if (v.getId() == R.id.nextButton) {
            playButton.setImageResource(R.drawable.pause_button);
            nextSong();

        }
        if (v.getId() == R.id.beforeButton) {
            playButton.setImageResource(R.drawable.pause_button);
            beforeSong();
        }
        if (v.getId() == R.id.loopButton) {
            feature = true;
            loopButton.setImageResource(R.drawable.loop_song_list_button);
            randomButton.setImageResource(R.drawable.random_button);
            if (count == 2) {
                loopButton.setImageResource(R.drawable.loop_current_song_button);
                count = 1;
            } else {
                loopButton.setImageResource(R.drawable.loop_song_list_button);
                count = 2;
            }
        }
        if (v.getId() == R.id.randomButton) {
            feature = false;
            randomButton.setImageResource(R.drawable.random_button_turn);
            loopButton.setImageResource(R.drawable.loop_button);
        }
    }
}

