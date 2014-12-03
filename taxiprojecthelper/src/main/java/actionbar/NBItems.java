package actionbar;


import com.example.taxiprojecthelper.R;

public final class NBItems {

    public static final int DONE = R.id.NBDone;
    public static final int CANCEL = R.id.NBCalcel;
    public static final int BACK = R.id.NBBack;
    public static final int REGISTER = R.id.NBRegistration;
    public static final int SETTING = R.id.NBSettings;
    public static final int TITLE = R.id.NBTitle;
    public static final int AUTO_LIST = R.id.NBAutoList;
    public static final int SEND_COMMENT = R.id.NBSendComment;

    public static final int EMPTY = -1;

    public static int[] obtain() {
        return new int[]{
                DONE, CANCEL, BACK, REGISTER, SETTING, TITLE, AUTO_LIST, SEND_COMMENT
        };
    }
}
