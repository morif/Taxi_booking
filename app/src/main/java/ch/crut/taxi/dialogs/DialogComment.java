package ch.crut.taxi.dialogs;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import ch.crut.taxi.R;
import ch.crut.taxi.utils.ShakeAnimator;
import ch.crut.taxi.utils.TextUtils;

public class DialogComment {

    public DialogComment(Context context) {
        this.context = context;
    }

    private final Context context;

    public void show() {

        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = layoutInflater.inflate(R.layout.dialog_comment, null);

        final EditText ETcomment = (EditText) dialogView.findViewById(R.id.dialogCommentEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        /**
         * positive handler beside
         */
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setNegativeButton(android.R.string.cancel, null);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.empty(ETcomment)) {

                    if (onCommentComplete != null) {
                        onCommentComplete.commentComplete(TextUtils.get(ETcomment));
                    }

                    alertDialog.dismiss();

                } else {
                    new ShakeAnimator().animate(
                            alertDialog.getWindow().getDecorView().findViewById(android.R.id.content)
                    );
                }
            }
        });
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onCommentComplete != null) {
                    onCommentComplete.commentComplete("");
                }
                alertDialog.dismiss();
            }
        });
    }


    public void setOnCommentComplete(OnCommentComplete onCommentComplete) {
        this.onCommentComplete = onCommentComplete;
    }

    private OnCommentComplete onCommentComplete;

    public static interface OnCommentComplete {
        public void commentComplete(String commentBody);
    }
}
