package com.example.android_newsky.navigation;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.content.pm.PackageManager;

import com.example.android_newsky.R;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class RecordActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private MediaPlayer player;
    private MediaRecorder recorder;

    private final File file = Environment.getExternalStorageDirectory();
    //갤럭시 S4기준으로 /storage/emulated/0/의 경로를 갖고 시작한다.
    String dataTimeNow = LocalDateTime.now().toString();
    private final String PATH = file.getAbsolutePath() + "/" + dataTimeNow + "_recorder.mp3";
    private String saveFilePath = null;
    private int position;
    String dirPath = file.getAbsoluteFile() + "/Recorder";
    File fileFolder = new File(dirPath);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);


        if(!fileFolder.exists())
            fileFolder.mkdirs();

        //saveFilePath = fileFolder + "/Recorder" + LocalDateTime.now().toString()+ "_recorder.mp3";
    }

    public void onButton1Clicked(View v) {
        Log.d("RecordActivity", "시작 버튼 클릭됨!");

        FileInputStream fis = null;
        try {
            try {
                KillPlayer();

                player = new MediaPlayer();

                fis = new FileInputStream(saveFilePath);
                FileDescriptor fd = fis.getFD();
                player.setDataSource(fd);

                player.prepare();
                player.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

            Toast.makeText(getApplicationContext(), "재생을 시작합니다!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "재생할 파일이 존재하지 않습니다!", Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
    }

    private void KillPlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void onButton2Clicked(View v) {
        Log.d("RecordActivity", "일시정지 버튼 클릭됨!");

        if (player != null && player.isPlaying()) {
            position = player.getCurrentPosition();
            player.pause();
            ;
        }
        Toast.makeText(getApplicationContext(), "재생을 일시정지 합니다!", Toast.LENGTH_LONG).show();
    }

    public void onButton3Clicked(View v) {
        Log.d("RecordActivity", "목록 버튼 클릭됨!");

//        if (player != null && !player.isPlaying()) {
//            player.start();
//            player.seekTo(position);
//        }
//        Toast.makeText(getApplicationContext(), "재생을 재시작합니다!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), RecorderPlayerActivity.class);
        startActivity(intent);
    }

    public void onButton4Clicked(View v) {
        Log.d("RecordActivity", "중지 버튼 클릭됨!");

        if (player != null && player.isPlaying()) {
            player.stop();
        }
        Toast.makeText(getApplicationContext(), "재생을 중지합니다!", Toast.LENGTH_LONG).show();
    }

    /**
     * 녹음 시작 버튼
     * 메소드명 onRecordButtonClicked로 수정해
     *
     * @param v
     */
    public void onButton5Clicked(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    10);
        } else {
            recordAudio();
        }
    }

    private void recordAudio() {
        try {
            System.out.println(recorder);
            if (recorder != null) {
                recorder.stop();
                recorder.release();
                recorder = null;
            }


            //File file= Environment.getExternalStorageDirectory();
//갤럭시 S4기준으로 /storage/emulated/0/의 경로를 갖고 시작한다.
            //String path=file.getAbsolutePath()+"/"+"recorder.mp3";

            saveFilePath = fileFolder + "/Recorder" + LocalDateTime.now().toString()+ "_recorder.mp3";

            recorder = new MediaRecorder();

            try {
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                recorder.setOutputFile(saveFilePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.out.println(PATH);
            recorder.prepare();
            recorder.start();

            Toast.makeText(getApplicationContext(), "녹음을 시작합니다!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onButton6Clicked(View v) {
        if (recorder != null) {
            recorder.stop();
/*            ContentValues values = new ContentValues(10);
            values.put(MediaStore.MediaColumns.TITLE, "Recorded");
            values.put(MediaStore.Audio.Media.ALBUM, "Audio Album");
            values.put(MediaStore.Audio.Media.ARTIST, "Mike");
            values.put(MediaStore.Audio.Media.DISPLAY_NAME, "Recorded Audio");
            values.put(MediaStore.Audio.Media.IS_RINGTONE, 1);
            values.put(MediaStore.Audio.Media.IS_MUSIC, 1);
            values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4");
            values.put(MediaStore.Audio.Media.DATA, PATH);
            Uri audioUri = getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);*/

            recorder.release();
            recorder = null;

            Toast.makeText(getApplicationContext(), "녹음을 중지합니다!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허가
                    // 해당 권한을 사용해서 작업을 진행할 수 있습니다
                } else {
                    // 권한 거부
                    // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                }
                return;
        }
    }

    // 앱이 종료가 되었을 때도
    @Override
    protected void onDestroy() {
        super.onDestroy();

        KillPlayer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_navigation_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_setting) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}