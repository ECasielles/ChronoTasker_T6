package com.example.usuario.chronotasker.data.model;

import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.usuario.chronotasker.mvvm.base.BaseModel;
import com.example.usuario.chronotasker.data.db.converter.DateTimeConverter;

import org.joda.time.DateTime;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Alarm extends BaseModel implements Parcelable {

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

    @Id
    private long id;
    private String title;
    @Convert(converter = DateTimeConverter.class, dbType = Long.class)
    private DateTime time;
    private String ringtoneUri;

    public Alarm() { }
    public Alarm(long id, String title, DateTime time, String ringtoneUri) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.ringtoneUri = ringtoneUri;
    }

    protected Alarm(Parcel in) {
        id = in.readLong();
        title = in.readString();
        ringtoneUri = in.readString();
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Bindable
    public String getTitle() {
        return title;
    }
    @Bindable
    public void setTitle(String title) {
        this.title = title;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public String getRingtoneUri() {
        return ringtoneUri;
    }

    public void setRingtoneUri(String ringtoneUri) {
        this.ringtoneUri = ringtoneUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(ringtoneUri);
    }
}
