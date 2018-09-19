package com.example.schilling.smsweb.sms.sms;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;

@Entity
public class Sms {


    public static final Migration EMPTY_MIGRATION = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

        }
    };
    public static final Migration EMPTY_MIGRATION_2 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

        }
    };
    @PrimaryKey(autoGenerate = true)
    private long _id;
    private String _address;
    private String _msg;
    private String _time;
    private String _folderName;
    private String _read;
    private boolean _alreadySendToEmail;

    public Sms(long id) {
        this._id = id;
        _alreadySendToEmail = false;
    }

    @NonNull
    public long get_id() {
        return _id;
    }

    public void set_id(@NonNull long _id) {
        this._id = _id;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_msg() {
        return _msg;
    }

    public void set_msg(String _msg) {
        this._msg = _msg;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public String get_folderName() {
        return _folderName;
    }

    public void set_folderName(String _folderName) {
        this._folderName = _folderName;
    }

    public String get_read() {
        return _read;
    }

    public void set_read(String _read) {
        this._read = _read;
    }

    public boolean is_alreadySendToEmail() {
        return _alreadySendToEmail;
    }

    public void set_alreadySendToEmail(boolean _alreadySendToEmail) {
        this._alreadySendToEmail = _alreadySendToEmail;
    }

    public static class Builder {
        private long id;
        private String address;
        private String msg;
        private String time;
        private String folderName;
        private String read;
        private boolean alreadySendToEmail;

        public Builder(String _msg) {
            id = 0;
            msg = _msg;
            time = "3.3.2000";
            read = "0";
            folderName = "no";
            alreadySendToEmail = false;
        }

        /**
         * constructor for SmsMessage @{@link SmsMessage}
         *
         * @param msg which should be converted to @{@link Sms}
         */
        public Builder(SmsMessage msg) {
            this(msg.getMessageBody());
            this.address(msg.getOriginatingAddress())
                    .folderName("inbox")
                    .sendToEmail(false)
                    .id(msg.getIndexOnIcc())
                    .time(msg.getTimestampMillis() + "")
                    .read("0")
                    .built();
            return;
        }

        public Sms built() {
            Sms sms = new Sms(id);
            sms._address = address;
            sms._folderName = folderName;
            sms._msg = msg;
            sms._read = read;
            sms._time = time;
            sms._alreadySendToEmail = alreadySendToEmail;
            return sms;
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder sendToEmail(boolean send) {
            this.alreadySendToEmail = send;
            return this;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder time(String time) {
            this.time = time;
            return this;
        }

        public Builder folderName(String folderName) {
            this.folderName = folderName;
            return this;
        }

        public Builder read(String read) {
            this.read = read;
            return this;
        }

    }
}
