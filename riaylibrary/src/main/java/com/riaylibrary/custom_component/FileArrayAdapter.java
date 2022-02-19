package com.riaylibrary.custom_component;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appynitty.riaylibrary.R;

import java.util.List;


public class FileArrayAdapter extends ArrayAdapter<FileInfo> {
    private final Context context;
    private final int resorceID;
    private final List<FileInfo> items;

    public FileArrayAdapter(Context context, int textViewResourceId, List<FileInfo> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.resorceID = textViewResourceId;
        this.items = objects;
    }

    public FileInfo getItem(int i) {
        return (FileInfo)this.items.get(i);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        FileArrayAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(this.resorceID, (ViewGroup)null);
            viewHolder = new FileArrayAdapter.ViewHolder();
            viewHolder.icon = convertView.findViewById(R.id.icon);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.details = convertView.findViewById(R.id.details);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FileArrayAdapter.ViewHolder)convertView.getTag();
        }

        FileInfo option = (FileInfo)this.items.get(position);
        if (option != null) {
            if (option.getData().equalsIgnoreCase("Folder")) {
                viewHolder.icon.setImageResource(R.drawable.ic_folder);
            } else if (option.getData().equalsIgnoreCase("ParentDirectory")) {
                viewHolder.icon.setImageResource(R.drawable.ic_back);
            } else {
                String name = option.getName().toLowerCase();
                if (!name.endsWith(".xls") && !name.endsWith(".xls")) {
                    if (!name.endsWith(".doc") && !name.endsWith(".docx")) {
                        if (!name.endsWith(".ppt") && !option.getName().endsWith(".pptx")) {
                            if (name.endsWith(".pdf")) {
                                viewHolder.icon.setImageResource(R.drawable.ic_pdf);
                            } else if (name.endsWith(".apk")) {
                                viewHolder.icon.setImageResource(R.drawable.ic_apk);
                            } else if (name.endsWith(".txt")) {
                                viewHolder.icon.setImageResource(R.drawable.ic_txt);
                            } else if (!name.endsWith(".jpg") && !name.endsWith(".jpeg")) {
                                if (name.endsWith(".png")) {
                                    viewHolder.icon.setImageResource(R.drawable.ic_png);
                                } else if (name.endsWith(".zip")) {
                                    viewHolder.icon.setImageResource(R.drawable.ic_zip);
                                } else if (name.endsWith(".rtf")) {
                                    viewHolder.icon.setImageResource(R.drawable.ic_rtf);
                                } else if (name.endsWith(".gif")) {
                                    viewHolder.icon.setImageResource(R.drawable.ic_gif);
                                } else if (name.endsWith(".avi")) {
                                    viewHolder.icon.setImageResource(R.drawable.ic_avi);
                                } else if (name.endsWith(".mp3")) {
                                    viewHolder.icon.setImageResource(R.drawable.ic_mp3);
                                } else if (name.endsWith(".mp4")) {
                                    viewHolder.icon.setImageResource(R.drawable.ic_mp4);
                                } else if (name.endsWith(".rar")) {
                                    viewHolder.icon.setImageResource(R.drawable.ic_rar);
                                } else if (name.endsWith(".acc")) {
                                    viewHolder.icon.setImageResource(R.drawable.ic_aac);
                                } else {
                                    viewHolder.icon.setImageResource(R.drawable.ic_blank);
                                }
                            } else {
                                viewHolder.icon.setImageResource(R.drawable.ic_jpg);
                            }
                        } else {
                            viewHolder.icon.setImageResource(R.drawable.ic_ppt);
                        }
                    } else {
                        viewHolder.icon.setImageResource(R.drawable.ic_doc);
                    }
                } else {
                    viewHolder.icon.setImageResource(R.drawable.ic_xls);
                }
            }

            viewHolder.name.setText(option.getName());
            viewHolder.details.setText(option.getData());
        }

        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView name;
        TextView details;

        ViewHolder() {
        }
    }
}