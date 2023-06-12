package com.m4xvel.aeroreserve;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;

public class PhoneNumberFormattingTextWatcher implements TextWatcher {

    private boolean mFormatting; // Флаг отвечающий за форматирование текста
    private boolean mDeletingHyphen; // Флаг, используемый для удаления дефиса, если он уже был введен

    private final WeakReference<EditText> mEditText; // Ссылка на EditText

    public PhoneNumberFormattingTextWatcher(EditText editText) {
        mEditText = new WeakReference<>(editText);
    }

    // Метод вызывается перед изменением текста
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mFormatting = false;

        // Проверяем, будет ли удален дефис
        mDeletingHyphen = (count > 0 && s.charAt(start) == '-' && after == 0);
    }

    // Метод вызывается сразу после изменения текста
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Ничего не делаем
    }

    // Метод вызывается после того, как текст был изменен
    @Override
    public void afterTextChanged(Editable s) {
        // Если текст не форматируется...
        if (!mFormatting) {
            mFormatting = true;

            // Удаляем дефис, если он уже был введен
            if (mDeletingHyphen) {
                s.delete(s.length() - 1, s.length());
            }

            // Удаляем все символы, кроме цифр
            String digits = s.toString().replaceAll("\\D", "");

            // Добавляем скобки и дефисы, чтобы сформировать нужный шаблон
            StringBuilder formattedStringBuilder = new StringBuilder("+");

            for (int i = 0; i < digits.length(); i++) {
                char digit = digits.charAt(i);

                if (i == 1) {
                    formattedStringBuilder.append("(");
                } else if (i == 4) {
                    formattedStringBuilder.append(")");
                } else if (i == 7 || i == 9) {
                    formattedStringBuilder.append("-");
                }

                formattedStringBuilder.append(digit);
            }

            mEditText.get().setText(formattedStringBuilder.toString());
            mEditText.get().setSelection(mEditText.get().getText().length());
        }
    }
}
