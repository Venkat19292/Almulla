package com.exchange.almulla.ui.Fragments;

import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.exchange.almulla.R;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.util.regex.Pattern;

public class EncryptionFragment extends Fragment {

    private EditText edit_message;
    private EditText edit_secretkey;
    private TextView txt_encrypted;
    private Button btn_decrypt;
    private Button btn_encrypt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_encryption, container, false);
        initControlls(view);
        return view;
    }

    private void initControlls(View view) {
        edit_message = (EditText) view.findViewById(R.id.edit_message);
        edit_secretkey = (EditText) view.findViewById(R.id.edit_secretkey);
        txt_encrypted = (TextView) view.findViewById(R.id.txt_encrypted);
        btn_encrypt = (Button) view.findViewById(R.id.btn_encrypt);
        btn_decrypt = (Button) view.findViewById(R.id.btn_decrypt);

        btn_encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Encrypt data
                getDataEncrypt();
            }
        });

        btn_decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get decrypted data
                getDataDecrypt();
            }
        });
    }

    private void getDataEncrypt() {
        String mMessage = edit_message.getText().toString().trim();
        String mSecret = edit_secretkey.getText().toString().trim();

        if (mMessage.isEmpty()) {
            Toast.makeText(getContext(), "Please enter message", Toast.LENGTH_SHORT).show();
        } else if (mSecret.isEmpty()) {
            Toast.makeText(getContext(), "Please enter secretkey", Toast.LENGTH_SHORT).show();
        } else {
            encrypt(mMessage, mSecret);
        }
    }

    private void getDataDecrypt() {
        String mMessage = edit_message.getText().toString().trim();
        String mSecret = edit_secretkey.getText().toString().trim();

        if (mMessage.isEmpty()) {
            Toast.makeText(getContext(), "Please enter message", Toast.LENGTH_SHORT).show();
        } else if (mSecret.isEmpty()) {
            Toast.makeText(getContext(), "Please enter secretkey", Toast.LENGTH_SHORT).show();
        } else {
            decrypt(mMessage, mSecret);
        }
    }


    private void decrypt(String encryptedmMessage, String mSecret) {
        String mStringData = "";
        try {
            mStringData = AESCrypt.decrypt(mSecret, encryptedmMessage);
        } catch (Exception e) {
            //handle error - could be due to incorrect password or tampered encryptedMsg
        }
        txt_encrypted.setText(mStringData);
    }

    private void encrypt(String mMessage, String mSecret) {
        // To Encrypt
        String mStringData = "";
        try {
            mStringData = AESCrypt.encrypt(mSecret, mMessage);

        } catch (GeneralSecurityException e) {
            //handle error
        }
        txt_encrypted.setText(mStringData);
    }


}
