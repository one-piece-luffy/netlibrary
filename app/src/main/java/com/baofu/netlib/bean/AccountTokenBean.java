package com.baofu.netlib.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AccountTokenBean implements Parcelable {

    /**
     * code : 200
     * count : 1
     * data : {"token":"40c174972b868f412cb3dde017c2de52"}
     * msg : 下单成功
     */

    public int code;
    public int count;
    public DataBean data;
    public String msg;


    public static class DataBean implements Parcelable {
        /**
         * token : 40c174972b868f412cb3dde017c2de52
         */

        public String token;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.token);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.token = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeInt(this.count);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.msg);
    }

    public AccountTokenBean() {
    }

    protected AccountTokenBean(Parcel in) {
        this.code = in.readInt();
        this.count = in.readInt();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
        this.msg = in.readString();
    }

    public static final Creator<AccountTokenBean> CREATOR = new Creator<AccountTokenBean>() {
        @Override
        public AccountTokenBean createFromParcel(Parcel source) {
            return new AccountTokenBean(source);
        }

        @Override
        public AccountTokenBean[] newArray(int size) {
            return new AccountTokenBean[size];
        }
    };
}
