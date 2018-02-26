package com.example.usuario.chronotasker.data.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.usuario.chronotasker.R;
import com.example.usuario.chronotasker.data.db.ChronoTaskerApplication;

import org.joda.time.DateTime;

/**
 * Representa los tipos de alarma de la aplicación.
 *
 * @author Enrique Casielles Lapeira
 * @version 1.0
 */
public class Alarm implements Parcelable {
    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };
    public static String TAG;
    int id;
    String title;
    String content;
    DateTime time;

    public Alarm() {
        this.id = -1;
        this.title = ChronoTaskerApplication.getContext().getResources().getString(R.string.alarm_title);
        this.content = ChronoTaskerApplication.getContext().getResources().getString(R.string.alarm_content);
        this.time = new DateTime().plusMinutes(1);
    }

    public Alarm(int id, String title, String content, DateTime time) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    protected Alarm(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(content);
    }
}
