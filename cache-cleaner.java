//package your.package.name
// change com.example.app -> actual app name
import android.content.Intent;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    protected String DataFromProcess(String CMD)
    {
        try
        {
            Process process=Runtime.getRuntime().exec(CMD);
            BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder sb=new StringBuilder();
            String line;
            while((line=br.readLine())!=null)
            {
                sb.append(line+"\n");
            }
            return sb.toString();
        }
        catch (Exception E)
        {
            return "N/A";
        }
    }
    protected String DataFromProcessRoot(String CMD)
    {
        try
        {
            Process process=Runtime.getRuntime().exec(new String[] {"su", "-c", CMD});
            BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder sb=new StringBuilder();
            String line;
            while((line=br.readLine())!=null)
            {
                sb.append(line+"\n");
            }
            return sb.toString();
        }
        catch (Exception E)
        {
            return "N/A";
        }
    }
    boolean DeleteRecursive(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null)
                for (File f : files) DeleteRecursive(f);
        }
        return file.delete();
    }
    protected void ExecAndPrint(String cmd)
    {
        String resultCmd=DataFromProcess(cmd);
        Toast.makeText(getBaseContext(),resultCmd,Toast.LENGTH_LONG);
    }
    protected void ExecRootAndPrint(String cmd)
    {
        String resultCmd=DataFromProcessRoot(cmd);
        Toast.makeText(getBaseContext(),resultCmd,Toast.LENGTH_LONG);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button StartButton=(Button)findViewById(R.id.MainButton);
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Runtime runtime=Runtime.getRuntime();
                    ExecRootAndPrint("pm clear com.example.app");
                    String DataPath="/sdcard/Android/data/com.example.app";
                    String CachePath="/sdcard/Android/obb/com.example.app";
                    DeleteRecursive(new File(DataPath));
                    DeleteRecursive(new File(CachePath));
                    ExecRootAndPrint("rm -rf /sdcard/Android/data/com.example.app");
                    ExecRootAndPrint("rm -rf /sdcard/Android/obb/com.example.app");
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.app");
                    if (launchIntent != null) {
                        startActivity(launchIntent);
                    }
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG);
                }
            }
        });

    }
}
