package com.riaylibrary.custom_component;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.appynitty.riaylibrary.R;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileChooserActivity extends ListActivity {
    private File currentFolder;
    private FileArrayAdapter fileArrayListAdapter;
    private FileFilter fileFilter;
    private File fileSelected;
    private ArrayList<String> extensions;

    public FileChooserActivity() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = this.getIntent().getExtras();
        if (extras != null && extras.getStringArrayList("EXTENSIONS") != null) {
            this.extensions = extras.getStringArrayList("EXTENSIONS");
            this.fileFilter = new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.isDirectory() || pathname.getName().contains(".") && FileChooserActivity.this.extensions.contains(pathname.getName().substring(pathname.getName().lastIndexOf(".")));
                }
            };
        }

        this.currentFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        this.fill(this.currentFolder);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        } else {
            if (!this.currentFolder.getName().equals(Environment.getExternalStorageDirectory().getName()) && this.currentFolder.getParentFile() != null) {
                this.currentFolder = this.currentFolder.getParentFile();
                this.fill(this.currentFolder);
            } else {
                Log.i("FILE CHOOSER", "canceled");
                this.setResult(0);
                this.finish();
            }

            return false;
        }
    }

    private void fill(File f) {
        File[] folders = null;
        if (this.fileFilter != null) {
            folders = f.listFiles(this.fileFilter);
        } else {
            folders = f.listFiles();
        }

        this.setTitle(this.getString(R.string.currentDir) + ": " + f.getName());
        List<FileInfo> dirs = new ArrayList();
        ArrayList files = new ArrayList();

        try {
            File[] var5 = folders;
            int var6 = folders.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                File file = var5[var7];
                if (file.isDirectory() && !file.isHidden()) {
                    dirs.add(new FileInfo(file.getName(), "Folder", file.getAbsolutePath(), true, false));
                } else if (!file.isHidden()) {
                    files.add(new FileInfo(file.getName(), this.getString(R.string.fileSize) + ": " + file.length(), file.getAbsolutePath(), false, false));
                }
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        Collections.sort(dirs);
        Collections.sort(files);
        dirs.addAll(files);
        if (!f.getName().equalsIgnoreCase(Environment.getExternalStorageDirectory().getName()) && f.getParentFile() != null) {
            dirs.add(0, new FileInfo("..", "ParentDirectory", f.getParent(), false, true));
        }

        this.fileArrayListAdapter = new FileArrayAdapter(this, R.layout.file_row, dirs);
        this.setListAdapter(this.fileArrayListAdapter);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        FileInfo fileDescriptor = this.fileArrayListAdapter.getItem(position);
        if (!fileDescriptor.isFolder() && !fileDescriptor.isParent()) {
            this.fileSelected = new File(fileDescriptor.getPath());
            Intent intent = new Intent();
            intent.putExtra("FILE_SELECTED", this.fileSelected.getAbsolutePath());
            this.setResult(-1, intent);
            Log.i("FILE CHOOSER", "result ok");
            this.finish();
        } else {
            this.currentFolder = new File(fileDescriptor.getPath());
            this.fill(this.currentFolder);
        }

    }
}