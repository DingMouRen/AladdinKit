package com.dingmouren.aladdinkit.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dingmouren.aladdinkit.R;

public class AdKitDialogEdit extends Dialog {

    private OnDialogClickListener mListener;
    private TextView tvTitle;
    private EditText editText;
    private Button btnCancel,btnConfirm;
    private String mTitle;

    public AdKitDialogEdit(Context context,String title) {
        super(context, R.style.CommonDialog);
        getWindow().setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_adkit_edit_common, null);
        setContentView(view, new ViewGroup.LayoutParams(getContext().getResources().getDisplayMetrics().widthPixels,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mTitle = title;
        initView();
        initListener();
    }


    private void initView(){
        tvTitle = findViewById(R.id.tvTitle);
        editText = findViewById(R.id.editText);
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnConfirm);

        tvTitle.setText(mTitle);

    }
    private void initListener() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (null != mListener)mListener.onClick(false,editText.getText().toString().trim());
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (null != mListener)mListener.onClick(true,editText.getText().toString().trim());
            }
        });

    }

    public void setOnDialogClickListener(OnDialogClickListener listener){
        this.mListener = listener;
    }

    public interface OnDialogClickListener{

        void onClick(boolean confirm,String newHostAddress);
    }
}
