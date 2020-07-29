package comp3350.group6.homiez.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import comp3350.group6.homiez.R;
import comp3350.group6.homiez.application.Main;
import comp3350.group6.homiez.business.AccessUser;

public class LoginActivity extends Activity {

    private AccessUser accessUser;
    final private String NOT_FOUND = "User not found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        copyDatabaseToDevice();

        Main.startUp();

        accessUser = new AccessUser();

        setContentView(R.layout.login_page);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Main.shutDown();
    }

    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            Main.setDBPathName(dataDirectory.toString() + "/" + Main.dbName);

        } catch (IOException ioe) {
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];
            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

    public void loginPressed(View v) {
        EditText IDField = findViewById(R.id.editUserID);
        String userID = IDField.getText().toString();

        if(accessUser.getUser(userID) != null) {
            Intent startIntent = new Intent(LoginActivity.this, StartActivity.class);
            Bundle b = new Bundle();
            b.putString("userID", userID);
            startIntent.putExtras(b);
            LoginActivity.this.startActivity(startIntent);

        }
        else {
            Messages.warning(this, NOT_FOUND);
        }

    }

}
